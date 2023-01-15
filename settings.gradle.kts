pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "kmm-architecture"
includeBuild("convention-plugins")
//include(":kmm-architecture")
//include(":kmm-architecture-common-flow")
//include(":kmm-architecture-core")
//include(":kmm-architecture-domain")
//include(":kmm-architecture-data")
include(":kmm-architecture-presentation")


