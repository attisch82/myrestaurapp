package com.atesz.build_logic.convention

import com.atesz.build_logic.constant.LibraryID
import com.atesz.build_logic.extension.getLibs
import com.atesz.build_logic.extension.getMultiplatformExtension
import com.atesz.build_logic.extension.library
import com.atesz.build_logic.extension.logAppliedPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke

class KoinConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.getLibs()
        target.getMultiplatformExtension()?.apply {
            sourceSets {
                val commonMain by getting {
                    dependencies {
                        implementation(libs.library(LibraryID.KOIN_CORE))
                    }
                }
                val androidMain by getting {
                    dependencies {
                        implementation(libs.library(LibraryID.KOIN_ANDROID))
                    }
                }

                val iosMain by creating {
                    dependsOn(commonMain)
                    dependencies {
                        implementation(libs.library(LibraryID.KOIN_CORE))
                    }
                }

                listOf(iosArm64(), iosX64(), iosSimulatorArm64()).forEach { target ->
                    target.compilations.getByName("main").defaultSourceSet.dependsOn(iosMain)
                }
            }
        }
        logAppliedPlugin()
    }
}