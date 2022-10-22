import org.gradle.api.JavaVersion

object AndroidBuildConfig {
    const val COMPILE_SDK = 31
    const val MIN_SDK = 21
    const val TARGET_SDK = 31
    const val BUILD_TOOLS_VERSION = "30.0.3"
    val javaVersion = JavaVersion.VERSION_11
}
