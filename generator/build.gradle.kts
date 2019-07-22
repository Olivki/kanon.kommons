extra["packageName"] = "generator"

dependencies {
    implementation(group = "com.squareup", name = "kotlinpoet", version = "1.3.0")
    implementation(group = "net.bytebuddy", name = "byte-buddy", version = "1.9.13")
    implementation(group = "net.bytebuddy", name = "byte-buddy-agent", version = "1.9.13")
}