package com.sofajohnlee.eunhyo2.data

import com.sofajohnlee.eunhyo2.domain.model.LearningCard
import com.sofajohnlee.eunhyo2.domain.model.StudyLanguage

/**
 * Initial extraction of the multilingual alphabet material embedded in the
 * legacy MainEng Activity. More levels are migrated into repositories in
 * later passes.
 */
class EnglishAlphabetRepository {
    private val english = listOf(
        LearningCard("A\na", "apple", "eng0", "A, apple"),
        LearningCard("B\nb", "bee", "eng1", "B, bee"),
        LearningCard("C\nc", "carrot", "eng2", "C, carrot"),
        LearningCard("D\nd", "dolphin", "eng3", "D, dolphin"),
        LearningCard("E\ne", "elephant", "eng4", "E, elephant"),
        LearningCard("F\nf", "frog", "eng5", "F, frog"),
    )

    private val french = listOf(
        LearningCard("A\na", "avion", "frn0", "A, avion"),
        LearningCard("B\nb", "bateau", "frn1", "B, bateau"),
        LearningCard("C\nc", "chien", "frn2", "C, chien"),
        LearningCard("D\nd", "dauphin", "eng3", "D, dauphin"),
        LearningCard("E\ne", "éléphant", "eng4", "E, éléphant"),
        LearningCard("F\nf", "fleur", "frn5", "F, fleur"),
    )

    private val chinese = listOf(
        LearningCard("a o[ō] e\ni(yi)[yī]\nu(wu)[wǔ]\nü(yu)[yú]", "啊 噢 呃 一 五 鱼", "cha1_1"),
        LearningCard("b[bo] p[po]\nm[mo] f[fo]\n[bàba] [pópo]\n[māma] [fūfù]", "卜 桲 摸 佛 爸爸 婆婆 妈妈 夫妇", "cha2_2"),
        LearningCard("d[de] t[te]\nn[ne] l[le]\n[dìdi] [tā]\n[nǚ] [lái]", "的 脦 呢 了 弟弟 他 女 来", "cha2_2"),
        LearningCard("g[ge] k[ke] h[he]\n[gēge] [kū] [hē]", "割 棵 喝 哥哥 哭 喝", "cha2_2"),
    )

    private val japanese = listOf(
        LearningCard("あいうえお\nアイウエオ"),
        LearningCard("かきくけこ\nカキクケコ"),
        LearningCard("さしすせそ\nサシスセソ"),
        LearningCard("たちつてと\nタチツテト"),
        LearningCard("なにぬねの\nナニヌネノ"),
        LearningCard("はひふへほ\nハヒフヘホ"),
    )

    private val italian = listOf(
        LearningCard("A\na", "aeroplano", "frn0", "A, aeroplano"),
        LearningCard("B\nb", "bicicletta", "itl1", "B, bicicletta"),
        LearningCard("C\nc", "cagnolino", "frn2", "C, cagnolino"),
        LearningCard("D\nd", "delfino", "eng3", "D, delfino"),
        LearningCard("E\ne", "elefante", "eng4", "E, elefante"),
        LearningCard("F\nf", "fiore", "frn5", "F, fiore"),
    )

    fun cards(language: StudyLanguage): List<LearningCard> = when (language) {
        StudyLanguage.ENGLISH -> english
        StudyLanguage.FRENCH -> french
        StudyLanguage.CHINESE -> chinese
        StudyLanguage.JAPANESE -> japanese
        StudyLanguage.ITALIAN -> italian
        StudyLanguage.KOREAN -> emptyList()
    }
}
