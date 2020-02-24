@file:Suppress("unused")

/*
    Put everything into a package
    so we can use nested Objects (e.g. Libs.Core.*)
 */
package dependencies

object Versions {

    object Core {
        const val GRADLE_ANDROID = "3.5.1"
        const val KOTLIN = "1.3.61"
        const val GRADLE_VERSIONS = "0.21.0"
        const val GOOGLE = "4.3.0"
        const val DAGGER = "2.25.2"
        const val COROUTINES = "1.3.2"
    }

    object AndroidX {
        const val ANDROIDX_CORE = "1.0.2"
        const val ANDROIDX_APPCOMPAT = "1.0.2"
        const val ANDROIDX_RECYCLER_VIEW = "1.0.0"
        const val ANDROIDX_NAVIGATION = "2.0.0"
        const val ANDROIDX_CONSTRAINT_LAYOUT = "1.1.3"
        const val ANDROIDX_CARD_VIEW = "1.0.0"
        const val ANDROIDX_LEGACY_SUPPORT = "1.0.0"
        const val ANDROIDX_LIFECYCLE_EXTENSIONS = "2.1.0-rc01"
        const val ANDROIDX_LIFECYCLE_VIEW_MODEL_KTX = "2.2.0-alpha03"
        const val ANDROIDX_FRAGMENT_KTX = "1.1.0-rc01"
    }

    object FireBase {
        const val FIREBASE = "5.0.0"
    }

    object Network {
        const val GSON = "2.8.5"
        const val RETROFIT = "2.7.1"
        const val OKHTTP = "4.4.0"
    }

    object Ui {
        const val GROUPIE = "2.5.0"
        const val MATERIAL = "1.0.0"
    }

    object DataBase {
        const val ORM_LITE = "5.1"
        const val ROOM = "2.2.1"
    }

    object Utils {
        const val TIMBER = "4.7.1"
    }

    object Test {
        const val JUNIT = "4.12"
        const val ANDROIDX_ESPRESSO = "3.2.0"
        const val ANDROIDX_TESTING = "1.2.0"
    }
}

object BuildPlugins {
    const val GRADLE_ANDROID = "com.android.tools.build:gradle:${Versions.Core.GRADLE_ANDROID}"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Core.KOTLIN}"
    const val GRADLE_VERSIONS =
        "com.github.ben-manes:gradle-versions-plugin:${Versions.Core.GRADLE_VERSIONS}"
    const val GOOGLE_SERVICES = "com.google.gms:google-services:${Versions.Core.GOOGLE}"
}

object Libs {

    object Core {
        const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Core.KOTLIN}"
        const val DAGGER = "com.google.dagger:dagger:${Versions.Core.DAGGER}"
        const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.Core.DAGGER}"

        const val COROUTINES_CORE =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.Core.COROUTINES}"

        const val COROUTINES_ANDROID =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Core.COROUTINES}"
    }

    object AndroidX {
        const val ANDROIDX_APPCOMPAT =
            "androidx.appcompat:appcompat:${Versions.AndroidX.ANDROIDX_APPCOMPAT}"

        const val ANDROIDX_CORE_KTX = "androidx.core:core-ktx:${Versions.AndroidX.ANDROIDX_CORE}"

        const val ANDROIDX_CONSTRAINT_LAYOUT =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.ANDROIDX_CONSTRAINT_LAYOUT}"

        const val ANDROIDX_NAVIGATION_FRAGMENT =
            "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.ANDROIDX_NAVIGATION}"

        const val ANDROIDX_NAVIGATION_UI =
            "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.ANDROIDX_NAVIGATION}"

        const val ANDROIDX_RECYCLER_VIEW =
            "androidx.recyclerview:recyclerview:${Versions.AndroidX.ANDROIDX_RECYCLER_VIEW}"

        const val ANDROIDX_CARD_VIEW =
            "androidx.cardview:cardview:${Versions.AndroidX.ANDROIDX_CARD_VIEW}"

        const val ANDROIDX_LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:${Versions.AndroidX.ANDROIDX_LEGACY_SUPPORT}"
        const val ANDROIDX_LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.ANDROIDX_LIFECYCLE_EXTENSIONS}"
        const val ANDROIDX_LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.ANDROIDX_LIFECYCLE_VIEW_MODEL_KTX}"
        const val ANDROIDX_FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.AndroidX.ANDROIDX_FRAGMENT_KTX}"

    }

    object FireBase {
        const val FIREBASE_UI_AUTH = "com.firebaseui:firebase-ui-auth:${Versions.FireBase.FIREBASE}"
    }

    object Network {
        const val GSON = "com.squareup.retrofit2:converter-gson:${Versions.Network.RETROFIT}"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.Network.OKHTTP}"
        const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OKHTTP}"
    }

    object Ui {
        const val GROUPIE = "com.xwray:groupie:${Versions.Ui.GROUPIE}"
        const val GROUPIE_KOTLIN_EXTENSIONS =
            "com.xwray:groupie-kotlin-android-extensions:${Versions.Ui.GROUPIE}"
        const val MATERIAL = "com.google.android.material:material:${Versions.Ui.MATERIAL}"
    }

    object DataBase {
        const val ORM_LITE_CORE = "com.j256.ormlite:ormlite-core:${Versions.DataBase.ORM_LITE}"
        const val ORM_LITE_ANDROID = "com.j256.ormlite:ormlite-android:${Versions.DataBase.ORM_LITE}"

        const val ROOM = "androidx.room:room-runtime:${Versions.DataBase.ROOM}"
        const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.DataBase.ROOM}"
        const val ROOM_KTX = "androidx.room:room-ktx:${Versions.DataBase.ROOM}"
    }

    object Utils {
        const val TIMBER = "com.jakewharton.timber:timber:${Versions.Utils.TIMBER}"
    }

    object Test {
        const val TEST_LIB_JUNIT = "junit:junit:${Versions.Test.JUNIT}"
        const val TEST_ANDROIDX_RULES = "androidx.test:rules:${Versions.Test.ANDROIDX_TESTING}"
        const val TEST_ANDROIDX_RUNNER = "androidx.test:runner:${Versions.Test.ANDROIDX_TESTING}"
        const val TEST_ANDROIDX_RUNNER_EXT = "'androidx.test.ext:junit:1.1.1'"
        const val TEST_ANDROIDX_ESPRESSO_CORE =
            "androidx.test.espresso:espresso-core:${Versions.Test.ANDROIDX_ESPRESSO}"
    }

}
