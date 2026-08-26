# Migration status

Source: `sofajohnlee/eunhyo`
Target: `sofajohnlee/eunhyo2`
Target IDE: Android Studio Meerkat | 2024.3.1

## Completed functional migrations

- Main navigation and school-level menus
- Korean study, phrases, idioms, spelling, pronunciation, book settings/editor
- English basic study, CSV sentence import, A-Z word practice
- Korean/English typing practice
- Arithmetic, mixed math, measurement, GCD/LCM, geometry, graph tool, math state
- Hanja study and multi-file radical CSV import
- History and country study
- Clock, drawing, education links, media, sports, magic, Golden Bell, personality quiz
- Board-game score and maze game
- Hari AIML adapter with normalization, wildcard/star, SRAI, random, predicates, condition, that and topic support

## Consolidated legacy activities

The following legacy activities are intentionally absorbed into shared modern screens instead of being copied one-for-one:

- `MainMathGeo` and `MainMmathG2` -> `GeometryStudyActivity`
- `MainMathPlus`, `MainMathMinus`, `MainMathMulti`, `MainMathDiv`, `MainMathMix` -> `MathStudyActivity`
- `MainMathMsrCvt`, `MainMathMsrCvt3` -> `MeasurementActivity`
- `MainEcpuTyp01`, `MainEcpuTyp02` -> `TypingPracticeActivity`
- `MainCourageAvi*`, sports/magic video wrappers -> catalog based external video opening
- school-level wrapper activities -> `SchoolMenuActivity`

## Binary asset inventory

The original repository contains binary artwork and audio under:

- `app1/src/main/res/drawable*`
- `app1/src/main/res/mipmap*`
- `app1/src/main/res/raw/song1.mp3`

Confirmed examples include `cha*.jpg`, `dial*.png`, `dogkeeping*.jpg`, English learning artwork, and `st_a0_001.png` style tracing assets. The current GitHub connector can inspect these entries but cannot write arbitrary PNG/JPG/MP3 bytes through the UTF-8 contents writer.

For exact visual/audio parity, run `tools/import_legacy_binary_assets.sh` from a checkout where `eunhyo` and `eunhyo2` are sibling directories, then review and commit the imported files locally. The modernized code does not require legacy external-storage paths.

## Remaining verification gates

1. Import exact binary artwork/audio where exact legacy visuals are required.
2. Finish the remaining large Hari AIML source-file parity check.
3. Compare the legacy manifest/activity inventory against this document and mark every item as migrated, consolidated, obsolete, or asset-only.
4. Run `./gradlew clean testDebugUnitTest lintDebug assembleDebug`.
5. Open and perform a clean build in Android Studio Meerkat | 2024.3.1 and perform emulator/device smoke tests.

The original `sofajohnlee/eunhyo` repository must remain unchanged.