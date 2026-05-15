# Standard ProGuard rules
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }

# Hilt
-keep class dagger.hilt.** { *; }

# Compose
-keep class androidx.compose.** { *; }

# Firebase Model keep
-keepclassmembers class com.example.nammareshme.data.models.** { *; }