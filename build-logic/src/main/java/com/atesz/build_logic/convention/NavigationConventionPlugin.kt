package com.atesz.build_logic.convention

import com.atesz.build_logic.constant.BundleID.NAVIGATION
import com.atesz.build_logic.constant.PluginID.KOTLINX_SERIALIZATION
import com.atesz.build_logic.extension.bundle
import com.atesz.build_logic.extension.getLibs
import com.atesz.build_logic.extension.getMultiplatformExtension
import com.atesz.build_logic.extension.logAppliedPlugin
import com.atesz.build_logic.extension.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.getting
import org.gradle.kotlin.dsl.invoke

class NavigationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.getLibs()
        target.pluginManager.apply {
            apply(libs.plugin(KOTLINX_SERIALIZATION))
        }
        target.getMultiplatformExtension()?.apply {
            sourceSets {
                val commonMain by getting {
                    dependencies { libs.bundle(NAVIGATION) }
                }
            }
        }
        logAppliedPlugin()
    }
}