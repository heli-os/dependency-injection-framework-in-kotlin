pluginManagement {
    val kotlinVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
    }
}

rootProject.name = "dependency-injection-framework-in-kotlin"

include("heli-container")
include("example")
