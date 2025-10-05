package com.atesz.build_logic.convention

import com.atesz.build_logic.constant.PluginID
import com.atesz.build_logic.extension.getLibs
import com.atesz.build_logic.extension.getMultiplatformExtension
import com.atesz.build_logic.extension.logAppliedPlugin
import com.atesz.build_logic.extension.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.getLibs()
        target.pluginManager.apply{
            apply(libs.plugin(PluginID.COMPOSE_MULTIPLATFORM))
            apply(libs.plugin(PluginID.COMPOSE_COMPILER))
        }

        target.getMultiplatformExtension()?.apply {
            sourceSets {
                val commonMain by getting
                val commonTest by getting
                val jvmMain by getting
                val jvmTest by getting
                val iosMain by creating {
                    dependsOn(commonMain)
                }
                listOf(iosArm64(), iosX64(), iosSimulatorArm64()).forEach { target ->
                    target.compilations.getByName("main").defaultSourceSet.dependsOn(iosMain)
                }
            }
        }
        logAppliedPlugin()
    }
}