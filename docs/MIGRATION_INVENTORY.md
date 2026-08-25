# Eunhyo → Eunhyo2 migration inventory

## Preservation rule

- `sofajohnlee/eunhyo` is the immutable reference repository.
- All modernization work is performed only in `sofajohnlee/eunhyo2`.

## Baseline environment

- Android Studio Meerkat | 2024.3.1
- Android Gradle Plugin 8.9.2
- Gradle 8.11.1
- JDK 17
- compileSdk 35
- targetSdk 35
- minSdk 24

## Legacy source groups identified

### Application shell
- MainActivity
- MyApplication
- AppToast
- MainActivityFragment

### AI / chat
- MainAI
- ChatMessage
- ChatMessageAdapter
- Hari AIML assets

### Korean language
- MainKGKorean
- MainKoreanSen
- MainKoreanSong
- MainKoreanSen_avi
- MainKoreanBook
- Main_KoreanBook_env
- MainKoreanBookEdit
- MainEkorEdiom
- MainEkorRule
- MainEkorwRule

### English
- MainEng
- Main_EngExpert
- MainEngPic
- MainEngsen
- MainEengAbc01
- MainEengWd01
- MainEengSt01
- MainEengAgent

### School-level learning
- MainPschool
- MainELschool
- MainEMschool
- MainEHschool
- MainMschool
- MainHschool
- MainEschHistory

### Mathematics
- MainMathPainter
- MainMathPainter2
- MainMathPainter3
- MainMathPlus
- MainMathPlusSb
- MainMathPlusSbEmail
- MainMathMinus
- MainMathMulti
- MainMathDiv
- MainMathGeo
- MainMathGraph
- MainMathMix
- MainMathAvi
- MainMathState
- MainMathMsrCvt
- MainMathMsrCvt3
- MainMmathG2

### Hanja
- MainHanja
- MainHanjaBusu
- MainEhanja7

### Games / media / utilities
- MainBgame
- MainBgameCcs
- MzGame
- MzMenu
- MainMagic
- MainCourageAvi
- MainCourageAvi2
- MainCourageAvi3
- MainSports
- MainGbell
- MainGwrNat
- MainCtest
- MainEdusite
- MainClock
- MainVer
- Main_env
- Main_dogkeeping
- Main_pride
- Main_prnmarie
- Main_prnmarie_avi

## Legacy technical debt to remove

- Direct external-storage access via `Environment.getExternalStorageDirectory()`
- `READ_EXTERNAL_STORAGE` / `WRITE_EXTERNAL_STORAGE`
- `requestLegacyExternalStorage`
- `Uri.fromFile()`
- `startActivityForResult()`
- static mutable activity state
- XML `android:onClick`-style tight coupling where practical
- duplicated Material dependency declarations
- bundled APK/build outputs and JVM crash logs
- IDE-generated `.idea` state
- backup Gradle files
- direct local JAR usage where a maintained Maven replacement exists

## Migration order

1. Launcher/navigation shell
2. Environment/settings persistence
3. English and Korean top-level menus
4. Mathematics feature set
5. School-level learning features
6. Hanja
7. Games/media
8. AI/chat and AIML assets
9. Remaining utility screens
10. Regression pass against original application

## Completion criteria

A feature is considered migrated only when:

- its original user-visible behavior is represented in `eunhyo2`;
- deprecated platform APIs are removed or isolated behind a compatibility layer;
- permissions follow current Android requirements;
- resources are externalized where appropriate;
- the module compiles under the Meerkat toolchain;
- lint/test failures caused by the migration are resolved or explicitly documented.
