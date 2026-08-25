package com.sofajohnlee.eunhyo2.feature.clock

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClockState(val hour: Int = 12, val minute: Int = 0) {
    init {
        require(hour in 1..12)
        require(minute in 0..59)
    }

    val label: String
        get() = if (minute == 0) "${hour}시 정각" else "${hour}시 ${minute}분"

    fun plusMinutes(delta: Int): ClockState {
        val total = ((hour % 12) * 60 + minute + delta).mod(12 * 60)
        val nextHour = (total / 60).let { if (it == 0) 12 else it }
        return ClockState(nextHour, total % 60)
    }
}

class ClockStudyViewModel : ViewModel() {
    private val _state = MutableStateFlow(ClockState())
    val state: StateFlow<ClockState> = _state.asStateFlow()

    fun plusHour() { _state.value = _state.value.plusMinutes(60) }
    fun plus30() { _state.value = _state.value.plusMinutes(30) }
    fun plus10() { _state.value = _state.value.plusMinutes(10) }
    fun plus5() { _state.value = _state.value.plusMinutes(5) }
    fun reset() { _state.value = ClockState() }
}
