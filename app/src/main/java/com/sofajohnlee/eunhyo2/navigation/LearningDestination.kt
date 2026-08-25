package com.sofajohnlee.eunhyo2.navigation

/**
 * Stable navigation identifiers used while migrating legacy Activities.
 * The enum deliberately represents user-facing feature groups rather than
 * legacy class names so implementation details can change without changing
 * the launcher contract.
 */
enum class LearningDestination {
    KOREAN,
    ENGLISH,
    MATH,
    SCHOOL,
    HANJA,
    GAMES,
    AI_CHAT,
    UTILITIES,
}
