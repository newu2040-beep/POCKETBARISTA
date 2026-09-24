package com.example.model

enum class BaristaTheme(val id: String, val displayName: String, val subtitle: String) {
    ESPRESSO("espresso", "Espresso", "Dark roast & golden crema"),
    VANILLA_CREAM("vanilla_cream", "Vanilla Cream", "Soft ivory & warm custard"),
    MATCHA("matcha", "Matcha", "Ceremonial jade & sage oat"),
    CARAMEL("caramel", "Caramel", "Toffee & golden butterscotch"),
    MOCHA("mocha", "Mocha", "Dark cocoa & espresso cream"),
    MIDNIGHT_COFFEE("midnight", "Midnight Coffee", "Obsidian & electric amber"),
    ROSE_LATTE("rose_latte", "Rose Latte", "Dusty rose & blush cream");

    companion object {
        fun fromId(id: String?): BaristaTheme {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: ESPRESSO
        }
    }
}

enum class ThemeMode(val id: String, val label: String) {
    SYSTEM("system", "System Default"),
    LIGHT("light", "Light"),
    DARK("dark", "Dark");

    companion object {
        fun fromId(id: String?): ThemeMode {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: SYSTEM
        }
    }
}

enum class MeasurementUnit(val id: String, val label: String) {
    METRIC("metric", "Metric (g, ml, °C)"),
    IMPERIAL("imperial", "Imperial (oz, fl oz, °F)");

    companion object {
        fun fromId(id: String?): MeasurementUnit {
            return entries.firstOrNull { it.id.equals(id, ignoreCase = true) } ?: METRIC
        }
    }
}

data class UserProfile(
    val displayName: String = "Barista Enthusiast",
    val bio: String = "Crafting specialty cups one extraction at a time.",
    val preferredCoffee: String = "Flat White",
    val experienceLevel: String = "Specialty Barista",
    val measurementUnit: MeasurementUnit = MeasurementUnit.METRIC,
    val age: String = "",
    val gender: String = "Not Specified",
    val photoUri: String? = null,
    val isOnboarded: Boolean = false
)
