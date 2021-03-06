# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn org.xmlpull.v1.XmlPullParser
-dontwarn org.xmlpull.v1.XmlSerializer
-keep class org.xmlpull.v1.* {*;}
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-keepattributes Annotation
-keep class com.google.api.** {*;}
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }
-dontwarn com.j256.ormlite.logger.**
-dontwarn com.j256.ormlite.misc.**
-dontwarn org.mockito.**
-keep class ru.cherepanovk.core_db_impl.db.** { *; }
-assumenosideeffects class android.util.Log { public static int d(...); }

-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

-keep class com.huawei.agconnect.**{*;}
