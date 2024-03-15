plugins {
    alias(libs.plugins.czechfoolapp.android.library)
    alias(libs.plugins.czechfoolapp.android.hilt)
    alias(libs.plugins.czechfoolapp.android.room)
}

android {
    namespace = "com.example.czechfoolapp.core.database"
}


dependencies {
    api(project(":core:model"))
}