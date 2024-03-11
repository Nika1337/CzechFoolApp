plugins {
    alias(libs.plugins.czechfoolapp.android.feature)
    alias(libs.plugins.czechfoolapp.android.library.compose)
}

android {
    namespace = "com.example.czechfoolapp.feature.gameshistory"
}

dependencies {
    implementation(libs.androidx.compose.material3.windowSizeClass)
}