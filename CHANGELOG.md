## 0.1.1 (2019-01-xx)

### Paths.kt

* #### Documentation
  * Tidied up all the ported over documentation.
  * Ported over all of the documentation from `Files.java`.

* #### Functions
  * Added the default value of `StandardCharsets.UTF_8` to the `charset` parameter on `Path.linesStream(..)`.
  * Renamed `Path.lines(...)` to `Path.linesStream(...)`.
  * Renamed `Path.readAllLines(...)` to `Path.readLines(...)`.
  * Renamed `Path.readAllBytes(...)` to `Path.readBytes(...)`.
  * Renamed `Path.pathToString(...)` to `Path.readToString(...)`.

## 0.1.0-beta (2019-01-09)
Initial release.