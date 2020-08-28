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

# Retrofit
-dontnote retrofit2.Platform # Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor # Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8 # Platform used when running on Java 8 VMs. Will not be used at runtime.

# OkHttp 3
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

-keep class khoiron.com.datalayer.datanetwork.** { *; }
-keep class khoiron.com.datalayer.request_model.** { *; }
-keep class khoiron.com.datalayer.network.** { *; }
-keep class khoiron.com.datalayer.model.** { *; }

-keep public class khoiron.com.datalayer.request_model.**
-keep public class khoiron.com.datalayer.request_model.** {
  public protected *;
}
-keep public class khoiron.com.datalayer.datanetwork.** {
  public protected *;
}
-keep public class khoiron.com.datalayer.mapper.** {
  public protected *;
}
-keep public class khoiron.com.datalayer.model.** {
  public protected *;
}
-keep public class khoiron.com.datalayer.network.** {
  public protected *;
}


-keepattributes EnclosingMethod
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
-dontnote retrofit2.Platform
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-dontwarn retrofit2.Platform$Java8
-keepattributes Signature
-keepattributes Exceptions

-keep public class com.opsigo.opsicorp.utility.** {
  public protected *;
}
-keep public class com.opsigo.opsicorp.module.approval.** {
  public protected *;
}

-keep class retrofit2.** { *; }
-keep class okhttp3.internal.** { *; }
-dontwarn okhttp3.internal.**
-dontwarn retrofit2.**
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-dontwarn org.kobjects.**
-dontwarn org.ksoap2.**
-dontwarn org.kxml2.**
-dontwarn org.xmlpull.v1.**

-keep class org.kobjects.** { *; }
-keep class org.ksoap2.** { *; }
-keep class org.kxml2.** { *; }
-keep class org.xmlpull.** { *; }

-dontwarn com.google.gms.**
-dontwarn groovy.**
-dontwarn org.**
-dontwarn java.**
-dontwarn javax.**
-dontwarn okhttp3.internal.platform.*

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-dontpreverify

-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-dontwarn com.bumptech.glide.load.resource.bitmap.VideoDecoder
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-keep class com.google.**
-dontwarn com.google.**
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

-keep class * implements android.arch.lifecycle.GeneratedAdapter {<init>(...);}
-keep class android.arch.lifecycle.** {*;}

#-keep class android.arch.paging.** { *; }
#-dontwarn android.arch.paging.**
#-keep public class * implements androidx.versionedparcelable.VersionedParcelable
-keep public class * extends androidx.versionedparcelable.VersionedParcelable

#-dontwarn org.mockito.**
#-dontwarn sun.reflect.**
#-dontwarn android.test.**