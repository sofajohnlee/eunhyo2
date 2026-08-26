package com.sofajohnlee.eunhyo2.feature.links

data class EducationLink(
    val title: String,
    val url: String,
    val group: String,
)

object EducationLinkCatalog {
    val links: List<EducationLink> = listOf(
        EducationLink("사이버서당", "https://www.cyberseodang.or.kr/", "국어·한자"),

        // Legacy MainMathM10 curriculum links.
        EducationLink("소수(Prime numbers)", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/prime-numbers", "수학"),
        EducationLink("소인수분해", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/prime-factorization", "수학"),
        EducationLink("최소공배수", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/least-common-multiple-exercise", "수학"),
        EducationLink("최대공약수", "https://www.khanacademy.org/math/pre-algebra/pre-algebra-factors-multiples/modal/v/greatest-common-divisor-factor-exercise", "수학"),
        EducationLink("순환소수를 분수로", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/modal/a/writing-repeating-decimals-as-fractions-review", "수학"),
        EducationLink("제곱근", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-numbers-operations/modal/v/introduction-to-square-roots", "수학"),
        EducationLink("다항식 덧셈·뺄셈", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/adding-and-subtracting-polynomials-1", "수학"),
        EducationLink("곱셈공식", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/difference-of-squares-pattern-for-simple-binomials", "수학"),
        EducationLink("공통인수 묶기", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/factoring-linear-binomials", "수학"),
        EducationLink("이차식 인수분해", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/factoring-simple-quadratic-expression", "수학"),
        EducationLink("묶어 인수분해", "https://www.khanacademy.org/math/algebra-basics/alg-basics-quadratics-and-polynomials/modal/v/factor-by-grouping-and-factoring-completely", "수학"),
        EducationLink("일차방정식", "https://www.khanacademy.org/math/algebra-home/alg-basic-eq-ineq/modal/v/number-of-solutions-to-linear-equations", "수학"),
        EducationLink("일차부등식", "https://www.khanacademy.org/math/algebra-home/alg-basic-eq-ineq/modal/v/multi-step-inequalities-3", "수학"),
        EducationLink("연립일차방정식", "https://www.khanacademy.org/math/cc-eighth-grade-math/cc-8th-systems-topic/modal/v/trolls-tolls-and-systems-of-equations", "수학"),
        EducationLink("이차방정식과 영인수", "https://www.khanacademy.org/math/algebra/quadratics/modal/v/zero-product-property", "수학"),

        // Legacy MainEngM10 grammar entry points.
        EducationLink("영문법: 명사", "https://www.khanacademy.org/humanities/grammar/parts-of-speech-the-noun/", "영어"),
        EducationLink("영문법: 대명사", "https://www.khanacademy.org/humanities/grammar/parts-of-speech-the-pronoun/", "영어"),

        // Legacy MainKanMathElGeo elementary measurement/geometry entry points.
        EducationLink("초등 길이 문제", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/quiz/cc-2nd-length-word-problems-quiz", "초등 수학"),
        EducationLink("초등 그래프", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/quiz/cc-2nd-line-plots-quiz", "초등 수학"),
        EducationLink("초등 시계", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/cc-2nd-time", "초등 수학"),
        EducationLink("초등 도형", "https://www.khanacademy.org/math/cc-2nd-grade-math/cc-2nd-measurement-data/cc-2nd-shapes", "초등 수학"),
    )
}
