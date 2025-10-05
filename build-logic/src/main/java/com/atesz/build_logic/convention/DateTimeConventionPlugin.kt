package com.atesz.build_logic.convention

import com.atesz.build_logic.constant.LibraryID.DATE_TIME
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

class DateTimeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        val libs = target.getLibs()
        target.getMultiplatformExtension()?.apply {
            sourceSets {
                val commonMain by getting {
                    dependencies {
                        implementation(libs.library(DATE_TIME))
                    }
                }
            }
        }
        logAppliedPlugin()
    }
}