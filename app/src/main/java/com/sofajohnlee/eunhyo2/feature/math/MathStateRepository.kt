package com.sofajohnlee.eunhyo2.feature.math

import android.content.Context

class MathStateRepository(context: Context) {
    private val preferences = context.applicationContext
        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun load(): Int = preferences.getInt(KEY_STATE, 0).coerceIn(0, 4)

    fun save(state: Int) {
        preferences.edit().putInt(KEY_STATE, state.coerceIn(0, 4)).apply()
    }

    companion object {
        private const val PREF_NAME = "math_state"
        private const val KEY_STATE = "state"
    }
}
