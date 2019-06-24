The following text is some basic information regarding how all the `immutable` collection implementations provided by
`kommons.collections` work, and some basic information of the "magic" the Kotlin compiler does behind the scenes.

*(Note that while this document mainly refers to the `List`  and `MutableList` interfaces, the behaviour described is the same for a number of interfaces in the Kotlin std-lib, like; `Collection`, `MutableCollection`, `Set`, `MutableSet`, `Map`, `MutableMap`, `Iterator`, `MutableIterator`, `ListIterator`, `MutableListIterator`,  and probably many more.)*

All the `Immutable...` implementations provided by this library are *very* simple implementations and all are done in only single line of code. This is possible because Kotlin provides [constructs for delegation](https://kotlinlang.org/docs/reference/delegation.html) built into the language itself. 

Using the delegation pattern means we can create a immutable collection *very* easily and quickly, however, unless one knows what the compiler *actually* does behind the scenes something like `class ImmutableList<T>(private val backing: List<T>) : List<T> by backing` might look a bit confusing, and maybe like *too* much magic. In this document I will try my best to make it clear *what* the Kotlin compiler actually does, and how this implementation works.

Before I can start to explain *how* this works, I need to first clear up the magic behind the `List` and `MutableList` interfaces. Now, you might asking "What magic? There's just List and MutableList, there's no magic going on there?", and if that's the case, I ask you to think about the following; Java does *not* have a `List` and `MutableList` interface, it only has a `List` interface, which is identical to the Kotlin `MutableList` interface, yet there is no need to convert between Kotlin lists and Java lists when interoping between the two languages, how can this be? 

To make it easier to show how this works, I will show some Kotlin code, and then the same Kotlin code, but compiled to Java instead of Kotlin.

```kotlin
val list: List<String> = listOf()
val mutableList: MutableList<String> = mutableListOf()
```

So in the above Kotlin code we're just creating two lists, one "read-only" and one mutable, and this is how these instances will look like when compiled to Java: *(the following Java code has been "prettified" to re-include generics and more closely look like what "idiomatic" Java code would look like)*

```java
final List<String> list = CollectionsKt.emptyList();
final List<String> mutableList = new ArrayList();
```

The important thing to notice in the Java code is that both instances are referring to the `List` interface, which is the *Java* `List` interface, and *not* the Kotlin one. You can also see that our `mutableList` variable is also referring to the `List` interface, and not the `MutableList` interface as it did in our Kotlin code. This is because the Kotlin compiler will replace *any* references to the Kotlin `List`/`MutableList` interfaces with the Java `List` interface, which as stated above, is essentially the same as the `MutableList` interface.

Now that we've got that covered we can move onto what happens when we implement the `List` or `MutableList` interfaces on a class. The compiler will also replace any instances of implementations of `List` will be replaced with the Java `List` interface, but that poses another problem, because the Java `List` interface has more functions than the Kotlin `List` interface, which means we would be breaking the contract set forth by the Java `List` interface, and that would mean we would end up with a invalid class implementation. To fix this, the Kotlin compiler implements the missing functions by itself when it compiles our code down into bytecode.

Once again to make it easier to show how this works I will be showing some Kotlin code, and then the same code compiled down into Java: *(the following code uses a modified version of the `EmptyList ` implementation provided by this library simply for the purpose of showing an implementation and how the compiler handles it)*

```kotlin
class EmptyList<T> : List<T> {
    override val size: Int = 0

    override fun contains(element: T): Boolean = false

    override fun containsAll(elements: Collection<T>): Boolean = false

    override fun get(index: Int): T = throw NoSuchElementException()

    override fun indexOf(element: T): Int = -1

    override fun isEmpty(): Boolean = true

    override fun iterator(): Iterator<T> = EmptyIterator

    override fun lastIndexOf(element: T): Int = -1

    override fun listIterator(): ListIterator<T> = EmptyListIterator

    override fun listIterator(index: Int): ListIterator<T> = EmptyListIterator

    override fun subList(fromIndex: Int, toIndex: Int): List<T> = EmptyList()
}
```

In the above Kotlin code we are just creating a very simple *(and not correct)* implementation of a empty list, below is the resulting Java code:

```java
public final class EmptyList implements List, KMappedMarker {
   private final int size;

   public int getSize() {
      return this.size;
   }

   // $FF: bridge method
   public final int size() {
      return this.getSize();
   }

   public boolean contains(Object element) {
      return false;
   }

   public boolean containsAll(@NotNull Collection elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      return false;
   }

   public Object get(int index) {
      throw (Throwable)(new NoSuchElementException());
   }

   public int indexOf(Object element) {
      return -1;
   }

   public boolean isEmpty() {
      return true;
   }

   @NotNull
   public Iterator iterator() {
      return (Iterator)EmptyIterator.INSTANCE;
   }

   public int lastIndexOf(Object element) {
      return -1;
   }

   @NotNull
   public ListIterator listIterator() {
      return (ListIterator)EmptyListIterator.INSTANCE;
   }

   @NotNull
   public ListIterator listIterator(int index) {
      return (ListIterator)EmptyListIterator.INSTANCE;
   }

   @NotNull
   public List subList(int fromIndex, int toIndex) {
      return (List)(new EmptyList());
   }

   public boolean add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void add(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(int var1, Collection var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean removeAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void replaceAll(UnaryOperator var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean retainAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object set(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void sort(Comparator var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   public Object[] toArray(Object[] var1) {
      return CollectionToArray.toArray(this, var1);
   }
}
```

As one can see, the Kotlin compiler will implement any missing functions and make any invocations of said functions throw a `UnsupportedOperationException` with the message set to `"Operation is not supported for read-only collection"`, this means that we can very easily and quickly replicate the behaviour of the List provided by the `Collections.unmodifiableList` function in the Java std-lib.

For the sake of being thorough, I will also show how Kotlin code that uses delegation on the `List` interface will compile down into Java code, for the example we'll be using our `ImmutableList` implementation:

```kotlin
class ImmutableList<T>(private val delegate: List<T>) : List<T> by delegate
```

The above Kotlin code is *very* short and concise, the resulting Java code, is, however, *not* short and concise:

```java
public final class ImmutableList implements List, KMappedMarker {
   private final List delegate;

   public ImmutableList(@NotNull List delegate) {
      Intrinsics.checkParameterIsNotNull(delegate, "delegate");
      super();
      this.delegate = delegate;
   }

   public int getSize() {
      return this.delegate.size();
   }

   // $FF: bridge method
   public final int size() {
      return this.getSize();
   }

   public boolean contains(Object element) {
      return this.delegate.contains(element);
   }

   public boolean containsAll(@NotNull Collection elements) {
      Intrinsics.checkParameterIsNotNull(elements, "elements");
      return this.delegate.containsAll(elements);
   }

   public Object get(int index) {
      return this.delegate.get(index);
   }

   public int indexOf(Object element) {
      return this.delegate.indexOf(element);
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   @NotNull
   public Iterator iterator() {
      return this.delegate.iterator();
   }

   public int lastIndexOf(Object element) {
      return this.delegate.lastIndexOf(element);
   }

   @NotNull
   public ListIterator listIterator() {
      return this.delegate.listIterator();
   }

   @NotNull
   public ListIterator listIterator(int index) {
      return this.delegate.listIterator(index);
   }

   @NotNull
   public List subList(int fromIndex, int toIndex) {
      return this.delegate.subList(fromIndex, toIndex);
   }

   public boolean add(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void add(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(int var1, Collection var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean addAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void clear() {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean remove(Object var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean removeAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object remove(int var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void replaceAll(UnaryOperator var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public boolean retainAll(Collection var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object set(int var1, Object var2) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public void sort(Comparator var1) {
      throw new UnsupportedOperationException("Operation is not supported for read-only collection");
   }

   public Object[] toArray() {
      return CollectionToArray.toArray(this);
   }

   public Object[] toArray(Object[] var1) {
      return CollectionToArray.toArray(this, var1);
   }
}
```

Hopefully this document cleared up *some* of the magic that the Kotlin compiler does behind the scenes to make sure that  interop with Java works very well.