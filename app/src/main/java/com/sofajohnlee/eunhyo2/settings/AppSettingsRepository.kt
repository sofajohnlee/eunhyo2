package com.sofajohnlee.eunhyo2.settings

import android.content.Context

class AppSettingsRepository(context: Context) {
    private val preferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun load(): AppSettings = AppSettings(
        fontStyle = preferences.getInt(KEY_FONT_STYLE, 0),
        textColor = preferences.getInt(KEY_TEXT_COLOR, 0),
        penColor = preferences.getInt(KEY_PEN_COLOR, 9),
    )

    fun save(settings: AppSettings) {
        preferences.edit()
            .putInt(KEY_FONT_STYLE, settings.fontStyle)
            .putInt(KEY_TEXT_COLOR, settings.textColor)
            .putInt(KEY_PEN_COLOR, settings.penColor)
            .apply()
    }

    companion object {
        private const val PREF_NAME = "pref"
        private const val KEY_FONT_STYLE = "ftype"
        private const val KEY_TEXT_COLOR = "fcolor"
        private const val KEY_PEN_COLOR = "pfcolor"
    }
}
