package com.sofajohnlee.eunhyo2.feature.world

data class CountryEntry(
    val name: String,
    val region: String,
    val capital: String,
)

object CountryRepository {
    val countries: List<CountryEntry> = listOf(
        CountryEntry("대한민국", "동아시아", "서울"),
        CountryEntry("일본", "동아시아", "도쿄"),
        CountryEntry("중국", "동아시아", "베이징"),
        CountryEntry("몽골", "동아시아", "울란바토르"),
        CountryEntry("인도", "남아시아", "뉴델리"),
        CountryEntry("네팔", "남아시아", "카트만두"),
        CountryEntry("태국", "동남아시아", "방콕"),
        CountryEntry("베트남", "동남아시아", "하노이"),
        CountryEntry("싱가포르", "동남아시아", "싱가포르"),
        CountryEntry("인도네시아", "동남아시아", "자카르타"),
        CountryEntry("호주", "오세아니아", "캔버라"),
        CountryEntry("뉴질랜드", "오세아니아", "웰링턴"),
        CountryEntry("미국", "북아메리카", "워싱턴 D.C."),
        CountryEntry("캐나다", "북아메리카", "오타와"),
        CountryEntry("멕시코", "북아메리카", "멕시코시티"),
        CountryEntry("브라질", "남아메리카", "브라질리아"),
        CountryEntry("아르헨티나", "남아메리카", "부에노스아이레스"),
        CountryEntry("칠레", "남아메리카", "산티아고"),
        CountryEntry("영국", "유럽", "런던"),
        CountryEntry("프랑스", "유럽", "파리"),
        CountryEntry("독일", "유럽", "베를린"),
        CountryEntry("이탈리아", "유럽", "로마"),
        CountryEntry("스페인", "유럽", "마드리드"),
        CountryEntry("스위스", "유럽", "베른"),
        CountryEntry("이집트", "북아프리카", "카이로"),
        CountryEntry("남아프리카 공화국", "남아프리카", "프리토리아"),
        CountryEntry("케냐", "동아프리카", "나이로비"),
        CountryEntry("나이지리아", "서아프리카", "아부자"),
        CountryEntry("사우디아라비아", "서아시아", "리야드"),
        CountryEntry("튀르키예", "서아시아·유럽", "앙카라"),
    )

    fun byRegion(region: String): List<CountryEntry> =
        countries.filter { it.region.contains(region, ignoreCase = true) }

    fun find(query: String): List<CountryEntry> {
        val normalized = query.trim()
        if (normalized.isBlank()) return countries
        return countries.filter {
            it.name.contains(normalized, ignoreCase = true) ||
                it.region.contains(normalized, ignoreCase = true) ||
                it.capital.contains(normalized, ignoreCase = true)
        }
    }
}
