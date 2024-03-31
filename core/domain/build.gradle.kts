plugins {
    alias(libs.plugins.czechfoolapp.android.library)
}

android {
    namespace = "com.example.czechfoolapp.core.domain"
}
dependencies {
    api(project(":core:model"))
    api(project(":core:data"))
    implementation(libs.javax.inject)

    testImplementation(libs.kotlinx.coroutines.test)
}
