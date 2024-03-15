plugins {
    alias(libs.plugins.czechfoolapp.android.feature)
    alias(libs.plugins.czechfoolapp.android.library.compose)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.czechfoolapp.feature.game"
}

dependencies {
    implementation(project(":core:data"))
    implementation(libs.androidx.compose.material3.windowSizeClass)
}