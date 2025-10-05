package com.atesz.build_logic.extension

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

fun VersionCatalog.plugin(id: String) = findPlugin(id).get().get().pluginId
fun VersionCatalog.versions(alias: String) = findVersion(alias).get().requiredVersion
fun VersionCatalog.library(alias: String) = findLibrary(alias).get()
fun VersionCatalog.bundle(alias: String) = findBundle(alias).get()
fun Project.getLibs() = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
fun Plugin<Project>.logApplyingPlugin() {
    println("--------------------Applying plugin: ${this::class.simpleName}--------------------")
}
fun Project.getMultiplatformExtension() = extensions.findByType(KotlinMultiplatformExtension::class.java)