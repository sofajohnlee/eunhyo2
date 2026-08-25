package com.sofajohnlee.eunhyo2.feature.school

import com.sofajohnlee.eunhyo2.GradeLevel

data class SchoolEntry(
    val section: SchoolSection,
    val title: String,
    val description: String,
)

object SchoolCatalog {
    fun entries(gradeLevel: GradeLevel): List<SchoolEntry> = when (gradeLevel) {
        GradeLevel.ELEMENTARY -> listOf(
            SchoolEntry(SchoolSection.KOREAN, "한글·문장·동요", "글자, 문장, 동요와 책 만들기"),
            SchoolEntry(SchoolSection.ENGLISH, "영어 기초", "알파벳, 단어, 문장 학습"),
            SchoolEntry(SchoolSection.MATH, "초등 수학", "사칙연산, 도형, 단위와 측정"),
            SchoolEntry(SchoolSection.HANJA, "한자", "급수별 한자와 부수 학습"),
            SchoolEntry(SchoolSection.HISTORY, "역사", "초등 역사 학습"),
            SchoolEntry(SchoolSection.UTILITIES, "학습 도구", "시계와 기타 학습 도구"),
        )
        GradeLevel.MIDDLE -> listOf(
            SchoolEntry(SchoolSection.KOREAN, "중등 국어", "문법, 관용 표현, 읽기"),
            SchoolEntry(SchoolSection.ENGLISH, "중등 영어", "문장과 어휘 학습"),
            SchoolEntry(SchoolSection.MATH, "중등 수학", "수와 연산, 도형, 그래프"),
            SchoolEntry(SchoolSection.HANJA, "한자", "한자 및 사자소학 학습"),
            SchoolEntry(SchoolSection.HISTORY, "역사", "한국사와 세계사 학습"),
        )
        GradeLevel.HIGH -> listOf(
            SchoolEntry(SchoolSection.KOREAN, "고등 국어", "문법과 읽기 학습"),
            SchoolEntry(SchoolSection.ENGLISH, "고등 영어", "고급 문장과 어휘 학습"),
            SchoolEntry(SchoolSection.MATH, "고등 수학", "수학 문제와 그래프 학습"),
            SchoolEntry(SchoolSection.HANJA, "한자", "고급 한자 학습"),
            SchoolEntry(SchoolSection.HISTORY, "역사", "역사 심화 학습"),
        )
    }
}
