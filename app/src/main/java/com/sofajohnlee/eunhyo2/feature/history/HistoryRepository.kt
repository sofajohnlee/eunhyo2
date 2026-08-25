package com.sofajohnlee.eunhyo2.feature.history

/**
 * History material migrated from the legacy MainEschHistory screen.
 * Content is kept as application data instead of Activity-owned arrays.
 * Historical wording is preserved for behavioral migration and can be
 * editorially reviewed independently of the application architecture.
 */
object HistoryRepository {
    private val data: Map<HistoryEra, List<HistoryEntry>> = mapOf(
        HistoryEra.PALEOLITHIC to listOf(
            HistoryEntry("구석기 시대", "70만년 전", "뗀석기", "무리생활"),
            HistoryEntry("동굴", "전기"),
            HistoryEntry("막집", "후기"),
        ),
        HistoryEra.NEOLITHIC to listOf(
            HistoryEntry("신석기 시대", "8천년 전", "간석기"),
            HistoryEntry("빗살무늬토기"),
            HistoryEntry("움집"),
        ),
        HistoryEra.BRONZE to listOf(
            HistoryEntry("청동기 시대"),
            HistoryEntry("고인돌"),
        ),
        HistoryEra.GOJOSEON to listOf(
            HistoryEntry("고조선", "기원전 2333", "8조법"),
        ),
        HistoryEra.THREE_KINGDOMS to listOf(
            HistoryEntry("백제", "기원전 18 ~ 660"),
            HistoryEntry("백제 근초고왕", "4세기", "부흥기", politics = "고구려 고국원왕 전사"),
            HistoryEntry("황산벌 전투", "660", "계백"),
            HistoryEntry("고구려", "기원전 37 ~ 668"),
            HistoryEntry("을지문덕", "612", "살수대첩"),
            HistoryEntry("신라", "기원전 57 ~ 935"),
            HistoryEntry("김유신", "595~673", "황산벌 전투"),
            HistoryEntry("김춘추", "604~661", "신라 무열왕"),
        ),
        HistoryEra.UNIFIED_SILLA to listOf(
            HistoryEntry("통일신라", "668~892"),
            HistoryEntry("원효대사", "617~686"),
            HistoryEntry("의상", "625~702", "화엄종 시조", "당나라 유학"),
            HistoryEntry("왕오천축국전", "723~724", "혜초"),
            HistoryEntry("장보고", "787~846", "청해진 설치"),
        ),
        HistoryEra.LATER_THREE_KINGDOMS to listOf(
            HistoryEntry("후삼국 시대", "892~936"),
            HistoryEntry("후백제", "892~936", "견훤"),
            HistoryEntry("후고구려", "901~918", "궁예"),
            HistoryEntry("발해", "698~926", "대조영"),
        ),
        HistoryEra.GORYEO to listOf(
            HistoryEntry("고려 시대", "918~1392"),
            HistoryEntry("왕건", "918", "건국"),
            HistoryEntry("서희", "993", "강동6주", "거란 1차 침입"),
            HistoryEntry("강감찬", "1019", "귀주대첩", "거란 3차 침입"),
            HistoryEntry("팔만대장경", "1251", "대몽항쟁"),
            HistoryEntry("정몽주", "1338~1392", "단심가"),
        ),
        HistoryEra.JOSEON to listOf(
            HistoryEntry("조선 시대", "1392~1910"),
            HistoryEntry("건국", "1392", politics = "태조 이성계"),
            HistoryEntry("훈민정음 창제", "1443", politics = "세종대왕"),
            HistoryEntry("임진왜란", "1592~1598"),
            HistoryEntry("한산도대첩", "1592", politics = "이순신"),
            HistoryEntry("행주대첩", "1593", politics = "권율"),
            HistoryEntry("명량해전", "1597", politics = "이순신"),
            HistoryEntry("노량해전", "1598", "이순신 전사"),
            HistoryEntry("병자호란", "1636~1637"),
        ),
        HistoryEra.COLONIAL_PERIOD to listOf(
            HistoryEntry("일제강점기", "1910~1945"),
            HistoryEntry("대한민국임시정부 수립", "1919"),
            HistoryEntry("3·1운동", "1919.3.1.", "유관순 열사"),
            HistoryEntry("8·15광복", "1945.8.15."),
        ),
        HistoryEra.MODERN to listOf(
            HistoryEntry("현대 시대", "1945~현재"),
            HistoryEntry("6·25전쟁", "1950~1953"),
            HistoryEntry("5·18광주민주화운동", "1980.5.18."),
            HistoryEntry("15대 김대중 대통령", "1998~2002", "2000.6.15. 남북정상회담"),
            HistoryEntry("16대 노무현 대통령", "2003~2007", "2007.10.4. 남북정상회담"),
        ),
        HistoryEra.ANCIENT_STATES to listOf(
            HistoryEntry("고대 국가"),
            HistoryEntry("부여"),
            HistoryEntry("동맹"),
            HistoryEntry("변한"),
            HistoryEntry("진한"),
            HistoryEntry("마한"),
        ),
    )

    fun entries(era: HistoryEra): List<HistoryEntry> = data[era].orEmpty()
}
