plugins {
    alias(libs.plugins.czechfoolapp.android.feature)
    alias(libs.plugins.czechfoolapp.android.library.compose)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.czechfoolapp.feature.gameoptions"

}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}