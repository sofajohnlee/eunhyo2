package com.sofajohnlee.eunhyo2.feature.links

data class EducationLink(
    val title: String,
    val url: String,
    val group: String,
)

object EducationLinkCatalog {
    val links: List<EducationLink> = listOf(
        EducationLink("사이버서당", "https://www.cyberseodang.or.kr/", "국어·한자"),

        // Legacy MainMathM10 concept/video links.
        EducationLink("소수(개념)", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/prime-numbers", "수학 개념"),
        EducationLink("소인수분해(개념)", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/prime-factorization", "수학 개념"),
        EducationLink("최소공배수(개념)", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/least-common-multiple-exercise", "수학 개념"),
        EducationLink("최대공약수(개념)", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/greatest-common-divisor-factor-exercise", "수학 개념"),
        EducationLink("순환소수를 분수로", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/modal/a/writing-repeating-decimals-as-fractions-review", "수학 개념"),
        EducationLink("제곱근", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/modal/v/introduction-to-square-roots", "수학 개념"),
        EducationLink("다항식 덧셈·뺄셈", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/adding-and-subtracting-polynomials-1", "수학 개념"),
        EducationLink("곱셈공식", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/difference-of-squares-pattern-for-simple-binomials", "수학 개념"),
        EducationLink("공통인수 묶기", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/factoring-linear-binomials", "수학 개념"),
        EducationLink("이차식 인수분해", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/factoring-simple-quadratic-expression", "수학 개념"),
        EducationLink("묶어 인수분해", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/factor-by-grouping-and-factoring-completely", "수학 개념"),
        EducationLink("일차방정식", "https://www.khanacademy.org/math/algebra-home/alg-basic-eq-ineq/modal/v/number-of-solutions-to-linear-equations", "수학 개념"),
        EducationLink("일차부등식", "https://www.khanacademy.org/math/algebra-home/alg-basic-eq-ineq/modal/v/multi-step-inequalities-3", "수학 개념"),
        EducationLink("연립일차방정식", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-systems-topic/modal/v/trolls-tolls-and-systems-of-equations", "수학 개념"),
        EducationLink("이차방정식과 영인수", "https://www.khanacademy.org/math/algebra/quadratics/modal/v/zero-product-property", "수학 개념"),

        // Legacy MainMathM11 exercise links.
        EducationLink("소수 문제풀이", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/e/prime_numbers", "수학 문제풀이"),
        EducationLink("소인수분해 문제풀이", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/e/prime_factorization", "수학 문제풀이"),
        EducationLink("최소공배수 문제풀이", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/e/least_common_multiple", "수학 문제풀이"),
        EducationLink("최대공약수 문제풀이", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/e/greatest_common_divisor", "수학 문제풀이"),
        EducationLink("순환소수 문제풀이", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/modal/e/writing-fractions-as-repeating-decimals", "수학 문제풀이"),
        EducationLink("제곱근 문제풀이", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/modal/e/square_roots", "수학 문제풀이"),
        EducationLink("다항식 문제풀이", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/e/adding_and_subtracting_polynomials", "수학 문제풀이"),

        // Legacy MainMathM20/21 geometry concept/exercise links.
        EducationLink("기본도형(개념)", "https://www.khanacademy.org/math/basic-geo/basic-geometry-shapes", "기하"),
        EducationLink("작도와 합동(개념)", "https://www.khanacademy.org/math/basic-geo/basic-geo-transformations-congruence/modal/v/testing-congruence-by-transformations-example", "기하"),
        EducationLink("삼각형의 성질", "https://www.khanacademy.org/math/geometry-home/triangle-properties/modal/v/proof-sum-of-measures-of-angles-in-a-triangle-are-180", "기하"),
        EducationLink("사각형의 성질", "https://www.khanacademy.org/math/basic-geo/basic-geometry-shapes/modal/a/quadrilaterals-review", "기하"),
        EducationLink("도형의 닮음", "https://www.khanacademy.org/math/basic-geo/basic-geo-transformations-congruence/modal/v/testing-similarity-through-transformations", "기하"),
        EducationLink("피타고라스 정리", "https://www.khanacademy.org/math/basic-geo/basic-geometry-pythagorean-theorem/modal/v/the-pythagorean-theorem", "기하"),
        EducationLink("삼각비", "https://www.khanacademy.org/math/geometry/hs-geo-trig/modal/a/laws-of-sines-and-cosines-review", "기하"),
        EducationLink("기본도형 문제풀이", "https://www.khanacademy.org/math/basic-geo/basic-geometry-shapes/basic-geo-properties-shapes/e/compare-shapes", "기하 문제풀이"),
        EducationLink("합동 문제풀이", "https://www.khanacademy.org/math/basic-geo/basic-geo-transformations-congruence/modal/e/exploring-rigid-transformations-and-congruence", "기하 문제풀이"),
        EducationLink("삼각형 각 문제풀이", "https://www.khanacademy.org/math/geometry-home/triangle-properties/modal/e/triangle_angles_1", "기하 문제풀이"),
        EducationLink("사각형 문제풀이", "https://www.khanacademy.org/math/basic-geo/basic-geometry-shapes/modal/e/identify-quadrilaterals", "기하 문제풀이"),
        EducationLink("닮음 문제풀이", "https://www.khanacademy.org/math/basic-geo/basic-geo-transformations-congruence/modal/e/exploring-angle-preserving-transformations-and-similarity", "기하 문제풀이"),
        EducationLink("피타고라스 문제풀이", "https://www.khanacademy.org/math/basic-geo/basic-geometry-pythagorean-theorem/modal/e/pythagorean_theorem_1", "기하 문제풀이"),
        EducationLink("삼각비 문제풀이", "https://www.khanacademy.org/math/geometry/hs-geo-trig/modal/e/law-of-sines-and-cosines-word-problems", "기하 문제풀이"),

        // Legacy MainEngM10/M11/M20/M21/M40/M41 grammar entry points.
        EducationLink("영문법: 명사", "https://www.khanacademy.org/humanities/grammar/parts-of-speech-the-noun/", "영어 문법"),
        EducationLink("영문법: 대명사", "https://www.khanacademy.org/humanities/grammar/parts-of-speech-the-pronoun/", "영어 문법"),
        EducationLink("영문법: 동사·시제·조동사", "https://www.khanacademy.org/humanities/grammar/parts-of-speech-the-verb/", "영어 문법"),
        EducationLink("영문법: 형용사·부사·관사", "https://www.khanacademy.org/humanities/grammar/parts-of-speech-the-modifier/", "영어 문법"),
        EducationLink("영문법: 전치사·접속사", "https://www.khanacademy.org/humanities/grammar/parts-of-speech-the-preposition-and-the-conjunction/", "영어 문법"),

        // Legacy MainKanMathElGeo elementary measurement/geometry entry points.
        EducationLink("초등 길이 문제", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/quiz/cc-2nd-length-word-problems-quiz", "초등 수학"),
        EducationLink("초등 그래프", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/quiz/cc-2nd-line-plots-quiz", "초등 수학"),
        EducationLink("초등 시계", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/cc-2nd-time", "초등 수학"),
        EducationLink("초등 도형", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/cc-2nd-shapes", "초등 수학"),
    ) + LegacyGeometryLinkCatalog.links
}
