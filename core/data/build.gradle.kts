plugins {
    alias(libs.plugins.czechfoolapp.android.library)
    alias(libs.plugins.czechfoolapp.android.hilt)
}

android {
    namespace = "com.example.czechfoolapp.core.data"
}

dependencies {
    api(project(":core:database"))

    implementation(libs.kotlinx.coroutines.test)
}