# ===============================================
# SocialMovie - ProGuard Rules
# Professional configuration for release builds
# ===============================================

# Preserve line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Preserve annotations
-keepattributes *Annotation*

# ===============================================
# Firebase Rules
# ===============================================
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Keep Firebase model classes
-keep class com.example.aplicacion.ui.models.** { *; }

# ===============================================
# Retrofit & OkHttp Rules
# ===============================================
# Retrofit does reflection on generic parameters
-keepattributes Signature
-keepattributes Exceptions

# Retrofit interfaces
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# Retrofit service interfaces
-keep interface com.example.aplicacion.ui.utils.** { *; }

# OkHttp platform used only on JVM and when Conscrypt dependency is available
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

# ===============================================
# Gson Rules
# ===============================================
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**

# Keep generic types for Gson
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# Keep model classes for Gson
-keep class com.example.aplicacion.ui.models.** { <fields>; }
-keep class com.example.aplicacion.ui.response.** { <fields>; }

# ===============================================
# Glide Rules
# ===============================================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# ===============================================
# Android Architecture Components
# ===============================================
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.AndroidViewModel { *; }

# ===============================================
# Facebook SDK Rules
# ===============================================
-keep class com.facebook.** { *; }
-keep interface com.facebook.** { *; }
-keep enum com.facebook.** { *; }
-dontwarn com.facebook.**

# ===============================================
# General Android Rules
# ===============================================
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep setters in Views for XML inflation
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# Keep click handlers
-keepclassmembers class * {
    void *(*Click*);
}

# Parcelable implementations
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# ===============================================
# Optimization & Obfuscation Settings
# ===============================================
# Enable aggressive optimization
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

# Remove logging in production
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# ===============================================
# Warnings to Ignore
# ===============================================
-dontwarn javax.annotation.**
-dontwarn org.codehaus.mojo.animal_sniffer.*