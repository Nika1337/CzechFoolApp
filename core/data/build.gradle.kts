plugins {
    alias(libs.plugins.czechfoolapp.android.library)
    alias(libs.plugins.czechfoolapp.android.hilt)
}

android {
    namespace = "com.example.czechfoolapp.core.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
}