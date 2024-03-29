plugins {
    alias(libs.plugins.czechfoolapp.android.library)
    alias(libs.plugins.czechfoolapp.android.hilt)
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.example.czechfoolapp.core.datastore"
}

dependencies {
    api(libs.androidx.dataStore.core)
    api(project(":core:datastore-proto"))

    androidTestImplementation(kotlin("test"))
    implementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.runner)
}