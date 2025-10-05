package com.atesz.build_logic.convention

import com.android.build.gradle.LibraryExtension
import com.atesz.build_logic.constant.PluginID.ANDROID_LIBRARY
import com.atesz.build_logic.constant.PluginID.KOTLIN_MULTIPLATFORM
import com.atesz.build_logic.constant.VersionID
import com.atesz.build_logic.extension.getLibs
import com.atesz.build_logic.extension.getMultiplatformExtension
import com.atesz.build_logic.extension.logAppliedPlugin
import com.atesz.build_logic.extension.plugin
import com.atesz.build_logic.extension.versions
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke

class LibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.getLibs()
        with(target.pluginManager) {
            apply(libs.plugin(KOTLIN_MULTIPLATFORM))
            apply(libs.plugin(ANDROID_LIBRARY))
        }
        target.extensions.configure(LibraryExtension::class.java) {
            namespace = target.provideNameSpace()
            compileSdk = libs.versions(VersionID.COMPILE_SDK).toInt()
            defaultConfig {
                minSdk = libs.versions(VersionID.MIN_SDK).toInt()
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
        }
        target.getMultiplatformExtension()?.apply {
            androidTarget()
            jvm()
            iosArm64()
            iosX64()
            iosSimulatorArm64()

            sourceSets {
                val commonMain by getting
                val commonTest by getting
                val androidMain by getting
                val iosMain by creating {
                    dependsOn(commonMain)
                }
                listOf(iosArm64(), iosX64(), iosSimulatorArm64()).forEach { target ->
                    target.compilations.getByName("main").defaultSourceSet.dependsOn(iosMain)
                }
                val jvmMain by getting
            }
        }
        logAppliedPlugin()
    }
}

private fun Project.provideNameSpace(basePackage: String = "com.atesz") =
    path.removePrefix(":")
        .replace(":", ".")
        .let { suffix -> if (suffix.isBlank()) basePackage else "$basePackage.$suffix" }