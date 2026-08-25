package com.sofajohnlee.eunhyo2.storage

import android.content.Context
import java.io.File

/**
 * Replaces ad-hoc score/state file access in legacy Main_env.
 * Data stays in app-private storage; SAF import/export can wrap these files later.
 */
class LearningStateStorage(context: Context) {
    private val filesDir = context.applicationContext.filesDir

    fun saveMyScore(score: Int) = write(MY_SCORE, score.toString())
    fun loadMyScore(): Int = read(MY_SCORE)?.toIntOrNull() ?: 0

    fun savePeerScore(score: Int) = write(PEER_SCORE, score.toString())
    fun loadPeerScore(): Int = read(PEER_SCORE)?.toIntOrNull() ?: 0

    fun saveMyState(state: Int) = write(MY_STATE, state.toString())
    fun loadMyState(): Int = read(MY_STATE)?.toIntOrNull() ?: 0

    fun fileFor(type: StateFile): File = when (type) {
        StateFile.MY_SCORE -> File(filesDir, MY_SCORE)
        StateFile.PEER_SCORE -> File(filesDir, PEER_SCORE)
        StateFile.MY_STATE -> File(filesDir, MY_STATE)
    }

    private fun write(name: String, value: String) {
        File(filesDir, name).writeText(value, Charsets.UTF_8)
    }

    private fun read(name: String): String? = File(filesDir, name)
        .takeIf(File::exists)
        ?.readText(Charsets.UTF_8)
        ?.trim()

    enum class StateFile { MY_SCORE, PEER_SCORE, MY_STATE }

    companion object {
        private const val MY_SCORE = "mscore.txt"
        private const val PEER_SCORE = "yscore.txt"
        private const val MY_STATE = "mstate.txt"
    }
}
