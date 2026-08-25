package com.sofajohnlee.eunhyo2.feature.koreanbook

data class KoreanBookStory(
    val characterId: Int,
    val plot: StoryPlot,
    val language: StoryLanguage,
    val text: String,
    val imageKey: String? = null,
)

class KoreanBookStoryRepository {
    private val koreanCharacterNames = mapOf(
        1 to "유진",
        2 to "푸른아리 선생님",
        3 to "미아",
        4 to "스테파니",
        5 to "올리비아",
        6 to "엠마",
        7 to "안드레아",
        8 to "쥬쥬",
        9 to "로사",
        10 to "아이린",
        11 to "바넬로피",
        12 to "개구리",
        13 to "개구리",
    )

    private val englishCharacterNames = mapOf(
        1 to "Yujin Princess",
        2 to "White Princess",
        3 to "Aurora Princess",
        4 to "Cinderella Princess",
        5 to "Rapunzel Princess",
        6 to "Elsa Princess",
        7 to "Anna Princess",
        8 to "Princess Marie",
        9 to "Princess Marie's mom",
        10 to "Fairy",
        11 to "Vanellope",
        12 to "The Frog Prince",
        13 to "The Frog Prince",
    )

    fun story(settings: KoreanBookSettings): KoreanBookStory {
        val plot = StoryPlot.entries.firstOrNull { it.legacyValue == settings.plot } ?: StoryPlot.HAPPY
        val language = StoryLanguage.entries.firstOrNull { it.legacyValue == settings.language } ?: StoryLanguage.KOREAN
        val characterId = settings.character.coerceIn(1, 13)
        val text = when (language) {
            StoryLanguage.KOREAN -> koreanText(characterId, plot)
            StoryLanguage.ENGLISH -> englishText(characterId, plot)
        }
        return KoreanBookStory(characterId, plot, language, text, imageKey(characterId))
    }

    private fun koreanText(characterId: Int, plot: StoryPlot): String {
        if (characterId == 1 && plot == StoryPlot.HAPPY) {
            return "요즘 유진이는 그리기가 너무 좋아요. 그리고 그 날은 푸른아리선생님을 볼 수 있기 때문이죠. 선생님, 사랑해요. 엄마, 아빠도요^^"
        }
        if (characterId == 2 && plot == StoryPlot.HAPPY) {
            return "푸른아리 선생님은 매주 화요일마다 유진이를 만나서 너무 행복해요. 매일 만나고 싶어서 유진이가 푸른아리반으로 왔으면 좋겠어요. 선생님이 유진이를 사랑하고 있나봐요."
        }
        if (characterId == 1 && plot == StoryPlot.FUNNY) {
            return "유진이는 어제 자면서 발차기를 했어요. 축구하는 꿈을 꿨을까요? 아니면 혹시 ..."
        }
        if (characterId == 2 && plot == StoryPlot.FUNNY) {
            return "푸른아리선생님은 요즘 소리내어 웃는 일이 많아요. 그건 바로 ..."
        }
        if (characterId == 1 && plot == StoryPlot.MOVING) {
            return "유진이는 푸른아리선생님이 인사를 하면 너무 가슴이 두근두근 뛰고 감동이 밀려온대요. 어떻게 된 일일까요?"
        }
        if (characterId == 2 && plot == StoryPlot.MOVING) {
            return "푸른아리선생님은 화요일마다 유진이의 인사를 받으면 너무 감동이 밀려온답니다. 유진이가 너무 좋은데 어떡하죠?"
        }
        val name = koreanCharacterNames.getValue(characterId)
        return when (plot) {
            StoryPlot.HAPPY -> "${name}${if (name.endsWith("는") || name.endsWith("은")) "" else "는"} 유진이를 만나서 행복했어요."
            StoryPlot.FUNNY -> "${name}${if (name.endsWith("는") || name.endsWith("은")) "" else "는"} 유진이를 만나서 웃음이 나왔어요."
            StoryPlot.MOVING -> "${name}에게 어떤 감동적인 이야기가 이어질까요?"
        }
    }

    private fun englishText(characterId: Int, plot: StoryPlot): String {
        if (characterId == 1 && plot == StoryPlot.HAPPY) {
            return "Once upon a time, there was a Yujin Princess. Someday she had a stomachache. But she was brave and intelligent. The next day, she went to the children's hospital, took medicine, and soon felt better. She was happy because she was thinking of having fun with her daddy."
        }
        val name = englishCharacterNames.getValue(characterId)
        return when (plot) {
            StoryPlot.HAPPY -> "Once upon a time, there was $name. Which happy story is there?"
            StoryPlot.FUNNY -> "Once upon a time, there was $name. Which funny story is there?"
            StoryPlot.MOVING -> "Once upon a time, there was $name. Which moving story is there?"
        }
    }

    private fun imageKey(characterId: Int): String = when (characterId) {
        1 -> "prc_yujin2"
        2 -> "kb_2"
        3 -> "kb_lf01"
        4 -> "kb_lf02"
        5 -> "kb_lf03"
        6 -> "kb_lf04"
        7 -> "kb_lf05"
        8 -> "kb_sj01"
        9 -> "kb_sj02"
        10 -> "kb_sj03"
        11 -> "kb_sj04"
        else -> "prc_yujin3"
    }
}
