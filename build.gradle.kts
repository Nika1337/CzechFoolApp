// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra.apply {
        set("lifecycle_version", "2.7.0")
        set("room_version", "2.6.1")
        set("hilt_version", "2.4.8")
    }
}

// Lists all plugins used throughout the project without applying them.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.hilt) apply false
}