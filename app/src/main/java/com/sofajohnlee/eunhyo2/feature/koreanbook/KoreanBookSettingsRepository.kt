package com.sofajohnlee.eunhyo2.feature.koreanbook

import android.content.Context

class KoreanBookSettingsRepository(context: Context) {
    private val preferences = context.applicationContext
        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun load(): KoreanBookSettings = KoreanBookSettings(
        character = preferences.getInt(KEY_CHARACTER, 1),
        plot = preferences.getInt(KEY_PLOT, 1),
        language = preferences.getInt(KEY_LANGUAGE, 1),
    )

    fun save(settings: KoreanBookSettings) {
        preferences.edit()
            .putInt(KEY_CHARACTER, settings.character)
            .putInt(KEY_PLOT, settings.plot)
            .putInt(KEY_LANGUAGE, settings.language)
            .apply()
    }

    companion object {
        private const val PREF_NAME = "pref_book"
        private const val KEY_CHARACTER = "mcrt"
        private const val KEY_PLOT = "plot"
        private const val KEY_LANGUAGE = "lang"
    }
}
