package com.sofajohnlee.eunhyo2.feature.korean

import com.sofajohnlee.eunhyo2.domain.model.LearningCard

/**
 * Korean study content extracted from the legacy MainKGKorean flow.
 * The legacy screen contains a larger state table; migration continues in batches.
 */
object KoreanLearningRepository {
    val cards: List<LearningCard> = listOf(
        LearningCard("가", "가방", speechText = "가, 가방"),
        LearningCard("나", "나비", speechText = "나, 나비"),
        LearningCard("다", "다리", speechText = "다, 다리"),
        LearningCard("라", "라디오", speechText = "라, 라디오"),
        LearningCard("마", "마음", speechText = "마, 마음"),
        LearningCard("바", "바다", speechText = "바, 바다"),
        LearningCard("사", "사과", speechText = "사, 사과"),
        LearningCard("아", "아이", speechText = "아, 아이"),
        LearningCard("자", "자동차", speechText = "자, 자동차"),
        LearningCard("차", "차", speechText = "차"),
        LearningCard("카", "카메라", speechText = "카, 카메라"),
        LearningCard("타", "타조", speechText = "타, 타조"),
        LearningCard("파", "파도", speechText = "파, 파도"),
        LearningCard("하", "하늘", speechText = "하, 하늘"),
    )
}
