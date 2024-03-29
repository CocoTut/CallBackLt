@file:Suppress("unused")

/*
    Put everything into a package
    so we can use nested Objects (e.g. Libs.Core.*)
 */
package dependencies

object Versions {

    object Core {
        const val GRADLE_ANDROID = "7.3.1"
        const val KOTLIN = "1.7.20"
        const val GRADLE_VERSIONS = "0.21.0"
        const val GOOGLE = "4.3.4"
        const val DAGGER = "2.44"
        const val COROUTINES = "1.6.4"
        const val PLAY_SERVICES = "20.3.0"
        const val FIREBASE_CRASHLITYCS = "2.9.2"
        const val HUAWEI_CONNECT = "1.5.0.300"
        const val FIREBASE_GRADLE = "2.9.2"
    }

    object AndroidX {
        const val ANDROIDX_CORE = "1.3.2"
        const val ANDROIDX_APPCOMPAT = "1.0.2"
        const val ANDROIDX_RECYCLER_VIEW = "1.0.0"
        const val ANDROIDX_NAVIGATION = "2.3.3"
        const val ANDROIDX_CONSTRAINT_LAYOUT = "2.0.4"
        const val ANDROIDX_CARD_VIEW = "1.0.0"
        const val ANDROIDX_LEGACY_SUPPORT = "1.0.0"
        const val ANDROIDX_LIFECYCLE = "2.3.0"
        const val ANDROIDX_FRAGMENT_KTX = "1.3.1"
        const val ANDROIDX_ACTIVITY_KTX = "1.2.0"
    }

    object FireBase {
        const val FIREBASE = "21.1.0"
        const val FIREBASE_BOM = "31.0.1"
    }

    object Network {
        const val GSON = "2.8.5"
        const val RETROFIT = "2.7.1"
        const val OKHTTP = "4.4.0"
        const val GOOGLE_API_CLIENT = "1.23.0"
        const val GOOGLE_SERVICES_CALENDAR = "v3-rev305-1.23.0"
    }

    object Ui {
        const val GROUPIE = "2.8.1"
        const val MATERIAL = "1.3.0"
    }

    object DataBase {
        const val ORM_LITE = "5.1"
        const val ROOM = "2.2.1"
    }

    object Utils {
        const val TIMBER = "4.7.1"
        const val EASY_PERMISSIONS = "3.0.0"
        const val CRASHLYTICS = "17.2.1"
        const val ANALYTICS = "21.2.0"
        const val HUAWEI_CRASHLITICS = "1.4.2.300"
        const val HUAWEI_ANALYTICS = "5.0.5.300"
    }

    object Test {
        const val JUNIT = "4.12"
        const val ANDROIDX_ESPRESSO = "3.2.0"
        const val ANDROIDX_TESTING = "1.2.0"
        const val ANDROIDX_ARCH_CORE_TESTING = "2.1.0"
        const val MOCKITO = "3.1.0"
        const val KOTLIN_MOCKITO = "2.2.0"
    }

}

object BuildPlugins {
    const val GRADLE_ANDROID = "com.android.tools.build:gradle:${Versions.Core.GRADLE_ANDROID}"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Core.KOTLIN}"
    const val GRADLE_VERSIONS =
        "com.github.ben-manes:gradle-versions-plugin:${Versions.Core.GRADLE_VERSIONS}"
    const val GOOGLE_SERVICES = "com.google.gms:google-services:${Versions.Core.GOOGLE}"
    const val FIREBASE_GRADLE = "com.google.firebase:firebase-crashlytics-gradle:${Versions.Core.FIREBASE_GRADLE}"
    const val FIREBASE_CRASHLITYCS = "com.google.firebase:firebase-crashlytics-gradle"
    const val HUAWEI_CONNECT = "com.huawei.agconnect:agcp:${Versions.Core.HUAWEI_CONNECT}"

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

        const val PLAY_SERVICES = "com.google.android.gms:play-services-auth:${Versions.Core.PLAY_SERVICES}"
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
        const val ANDROIDX_NAVIGATION_DYNAMIC_FEATURE =
            "androidx.navigation:navigation-dynamic-features-fragment:${Versions.AndroidX.ANDROIDX_NAVIGATION}"

        const val ANDROIDX_RECYCLER_VIEW =
            "androidx.recyclerview:recyclerview:${Versions.AndroidX.ANDROIDX_RECYCLER_VIEW}"

        const val ANDROIDX_CARD_VIEW =
            "androidx.cardview:cardview:${Versions.AndroidX.ANDROIDX_CARD_VIEW}"

        const val ANDROIDX_LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:${Versions.AndroidX.ANDROIDX_LEGACY_SUPPORT}"
        const val ANDROIDX_LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.ANDROIDX_LIFECYCLE}"
        const val ANDROIDX_LIFECYCLE_VIEW_MODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.ANDROIDX_LIFECYCLE}"
        const val ANDROIDX_LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.ANDROIDX_LIFECYCLE}"
        const val ANDROIDX_LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime:${Versions.AndroidX.ANDROIDX_LIFECYCLE}"
        const val ANDROIDX_LIFECYCLE_COMMON_JAVA8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.AndroidX.ANDROIDX_LIFECYCLE}"
        const val ANDROIDX_LIFECYCLE_SAVED_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.AndroidX.ANDROIDX_LIFECYCLE}"

        const val ANDROIDX_FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${Versions.AndroidX.ANDROIDX_FRAGMENT_KTX}"
        const val ANDROIDX_ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.AndroidX.ANDROIDX_ACTIVITY_KTX}"

    }

    object FireBase {
        const val FIREBASE_UI_AUTH = "com.google.firebase:firebase-auth:${Versions.FireBase.FIREBASE}"
        const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FireBase.FIREBASE_BOM}"
    }

    object Network {
        const val GSON = "com.squareup.retrofit2:converter-gson:${Versions.Network.RETROFIT}"
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.Network.OKHTTP}"
        const val OKHTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.Network.OKHTTP}"
        const val GOOGLE_API_CLIENT = "com.google.api-client:google-api-client-android:${Versions.Network.GOOGLE_API_CLIENT}"
        const val GOOGLE_SERVICES_CALENDAR = "com.google.apis:google-api-services-calendar:${Versions.Network.GOOGLE_SERVICES_CALENDAR}"
    }

    object Ui {
        const val GROUPIE = "com.xwray:groupie:${Versions.Ui.GROUPIE}"
        const val GROUPIE_VIEW_BINDING = "com.xwray:groupie-viewbinding:${Versions.Ui.GROUPIE}"
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
        const val EASY_PERMISSIONS = "pub.devrel:easypermissions:${Versions.Utils.EASY_PERMISSIONS}"
        const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics:${Versions.Utils.CRASHLYTICS}"
        const val ANALYTICS = "com.google.firebase:firebase-analytics-ktx:${Versions.Utils.ANALYTICS}"

        const val HUAWEI_ANALITYCS = "com.huawei.hms:hianalytics:${Versions.Utils.HUAWEI_ANALYTICS}"
        const val HUAWEI_CRASHLITICS = "com.huawei.agconnect:agconnect-crash:${Versions.Utils.HUAWEI_CRASHLITICS}"
        const val HUAWEI_AG_CONNECT = "com.huawei.agconnect:agconnect-core:${Versions.Core.HUAWEI_CONNECT}"
    }

    object Test {
        const val TEST_LIB_JUNIT = "junit:junit:${Versions.Test.JUNIT}"
        const val TEST_ANDROIDX_RULES = "androidx.test:rules:${Versions.Test.ANDROIDX_TESTING}"
        const val TEST_ANDROIDX_RUNNER = "androidx.test:runner:${Versions.Test.ANDROIDX_TESTING}"
        const val TEST_ANDROIDX_RUNNER_EXT = "androidx.test.ext:junit:1.1.1"
        const val TEST_ANDROIDX_ESPRESSO_CORE =
            "androidx.test.espresso:espresso-core:${Versions.Test.ANDROIDX_ESPRESSO}"
        const val TEST_ANDROIDX_ARCH_CORE = "androidx.arch.core:core-testing:${Versions.Test.ANDROIDX_ARCH_CORE_TESTING}"
        const val TEST_MOCKITO_CORE = "org.mockito:mockito-core:${Versions.Test.MOCKITO}"
        const val TEST_MOCKITO_INLINE = "org.mockito:mockito-inline:${Versions.Test.MOCKITO}"
        const val TEST_KOTLINX_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.Core.COROUTINES}"
        const val TEST_KOTLIN_MOCKITO = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.Test.KOTLIN_MOCKITO}"
    }

}
