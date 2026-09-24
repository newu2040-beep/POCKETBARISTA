package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.BaristaTheme
import com.example.model.MeasurementUnit
import com.example.model.ThemeMode
import com.example.model.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recipe_pocket_prefs")

class UserPreferencesRepository(private val context: Context) {

    private object PrefKeys {
        val BARISTA_THEME = stringPreferencesKey("barista_theme")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val MEASUREMENT_UNIT = stringPreferencesKey("measurement_unit")
        val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        val DEFAULT_RATIO = stringPreferencesKey("default_ratio")
        val COMPACT_MODE = booleanPreferencesKey("compact_mode")

        // User profile
        val USER_NAME = stringPreferencesKey("user_display_name")
        val USER_BIO = stringPreferencesKey("user_bio")
        val USER_COFFEE = stringPreferencesKey("user_pref_coffee")
        val USER_EXPERIENCE = stringPreferencesKey("user_experience")
        val USER_AGE = stringPreferencesKey("user_age")
        val USER_GENDER = stringPreferencesKey("user_gender")
        val USER_PHOTO_URI = stringPreferencesKey("user_photo_uri")
        val USER_ONBOARDED = booleanPreferencesKey("user_onboarded")
    }

    val baristaTheme: Flow<BaristaTheme> = context.dataStore.data.map { prefs ->
        BaristaTheme.fromId(prefs[PrefKeys.BARISTA_THEME])
    }

    val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        ThemeMode.fromId(prefs[PrefKeys.THEME_MODE])
    }

    val measurementUnit: Flow<MeasurementUnit> = context.dataStore.data.map { prefs ->
        MeasurementUnit.fromId(prefs[PrefKeys.MEASUREMENT_UNIT])
    }

    val hapticsEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[PrefKeys.HAPTICS_ENABLED] ?: true
    }

    val soundEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[PrefKeys.SOUND_ENABLED] ?: true
    }

    val defaultRatio: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[PrefKeys.DEFAULT_RATIO] ?: "1:16"
    }

    val compactMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[PrefKeys.COMPACT_MODE] ?: false
    }

    val userProfile: Flow<UserProfile> = context.dataStore.data.map { prefs ->
        UserProfile(
            displayName = prefs[PrefKeys.USER_NAME] ?: "Artisan Barista",
            bio = prefs[PrefKeys.USER_BIO] ?: "Brewing specialty cups with precision and care.",
            preferredCoffee = prefs[PrefKeys.USER_COFFEE] ?: "Flat White",
            experienceLevel = prefs[PrefKeys.USER_EXPERIENCE] ?: "Specialty Barista",
            measurementUnit = MeasurementUnit.fromId(prefs[PrefKeys.MEASUREMENT_UNIT]),
            age = prefs[PrefKeys.USER_AGE] ?: "",
            gender = prefs[PrefKeys.USER_GENDER] ?: "Not Specified",
            photoUri = prefs[PrefKeys.USER_PHOTO_URI],
            isOnboarded = prefs[PrefKeys.USER_ONBOARDED] ?: false
        )
    }

    suspend fun setBaristaTheme(theme: BaristaTheme) {
        context.dataStore.edit { it[PrefKeys.BARISTA_THEME] = theme.id }
    }

    suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { it[PrefKeys.THEME_MODE] = mode.id }
    }

    suspend fun setMeasurementUnit(unit: MeasurementUnit) {
        context.dataStore.edit { it[PrefKeys.MEASUREMENT_UNIT] = unit.id }
    }

    suspend fun setHapticsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[PrefKeys.HAPTICS_ENABLED] = enabled }
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        context.dataStore.edit { it[PrefKeys.SOUND_ENABLED] = enabled }
    }

    suspend fun setDefaultRatio(ratio: String) {
        context.dataStore.edit { it[PrefKeys.DEFAULT_RATIO] = ratio }
    }

    suspend fun setCompactMode(enabled: Boolean) {
        context.dataStore.edit { it[PrefKeys.COMPACT_MODE] = enabled }
    }

    suspend fun updateUserProfile(profile: UserProfile) {
        context.dataStore.edit { prefs ->
            prefs[PrefKeys.USER_NAME] = profile.displayName
            prefs[PrefKeys.USER_BIO] = profile.bio
            prefs[PrefKeys.USER_COFFEE] = profile.preferredCoffee
            prefs[PrefKeys.USER_EXPERIENCE] = profile.experienceLevel
            prefs[PrefKeys.MEASUREMENT_UNIT] = profile.measurementUnit.id
            prefs[PrefKeys.USER_AGE] = profile.age
            prefs[PrefKeys.USER_GENDER] = profile.gender
            if (profile.photoUri != null) {
                prefs[PrefKeys.USER_PHOTO_URI] = profile.photoUri
            }
            prefs[PrefKeys.USER_ONBOARDED] = profile.isOnboarded
        }
    }

    suspend fun completeOnboarding() {
        context.dataStore.edit { it[PrefKeys.USER_ONBOARDED] = true }
    }
}
