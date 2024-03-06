plugins {
    alias(libs.plugins.czechfoolapp.android.library)
    alias(libs.plugins.czechfoolapp.android.hilt)
}

android {
    namespace = "com.example.czechfoolapp.core.datastore"
}

dependencies {
    api(libs.androidx.dataStore.core)
    api(project(":core:datastore-proto"))
}