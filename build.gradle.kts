plugins {
    base
    kotlin("jvm")
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "ac.weekly"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "kotlin")

    java.sourceCompatibility = JavaVersion.VERSION_16
    java.targetCompatibility = JavaVersion.VERSION_16

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
    }
}
