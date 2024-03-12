plugins {
    alias(libs.plugins.czechfoolapp.android.library)
    alias(libs.plugins.czechfoolapp.android.library.compose)
}

android {
    namespace = "com.example.czechfoolapp.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.core.ktx)
    debugApi(libs.androidx.compose.ui.tooling)
}