buildscript {

    val kotlin_version by extra("1.4.10")

    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.1")
    }
}
group = "com.digitalcrafts.kmmweather"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
