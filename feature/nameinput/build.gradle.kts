plugins {
    alias(libs.plugins.czechfoolapp.android.feature)
    alias(libs.plugins.czechfoolapp.android.library.compose)
}

android {
    namespace = "com.example.czechfoolapp.feature.nameinput"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
}