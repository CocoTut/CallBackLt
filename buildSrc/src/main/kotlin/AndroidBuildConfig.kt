import org.gradle.api.JavaVersion

object AndroidBuildConfig {
    const val COMPILE_SDK = 28
    const val MIN_SDK = 21
    const val TARGET_SDK = 28
    val javaVersion = JavaVersion.VERSION_1_8
}
