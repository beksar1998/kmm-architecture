pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "kmm-architecture"
includeBuild("convention-plugins")
include(":kmm-architecture")
include(":kmm-architecture-domain")
include(":kmm-architecture-data")
include(":kmm-architecture-presentation")
include(":kmm-architecture-common-flow")
include(":kmm-architecture-core")
