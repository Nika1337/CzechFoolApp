plugins {
    alias(libs.plugins.czechfoolapp.android.library)
}

android {
    namespace = "com.example.czechfoolapp.core.domain"
}
dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:data"))
    implementation(libs.javax.inject)
}
