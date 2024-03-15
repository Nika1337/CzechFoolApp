plugins {
    alias(libs.plugins.czechfoolapp.android.feature)
    alias(libs.plugins.czechfoolapp.android.library.compose)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.czechfoolapp.feature.nameinput"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
}