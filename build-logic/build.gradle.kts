plugins {
    `kotlin-dsl`
}

group = "com.atesz.buildlogic"

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.12.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.0")
}

gradlePlugin {
    plugins {
        create("library"){
            id = "library"
            implementationClass = "com.atesz.build_logic.convention.LibraryConventionPlugin"
        }
        create("compose"){
            id = "compose"
            implementationClass = "com.atesz.build_logic.convention.ComposeConventionPlugin"
        }
        create("koin"){
            id = "koin"
            implementationClass = "com.atesz.build_logic.convention.KoinConventionPlugin"
        }
    }
}