# Migration status

Source: `sofajohnlee/eunhyo`
Target: `sofajohnlee/eunhyo2`
Target IDE: Android Studio Meerkat | 2024.3.1

## Completed functional migrations

- Main navigation and school-level menus
- Korean study, phrases, idioms, spelling, pronunciation, book settings/editor, Korean song controller
- English basic study, CSV sentence import, A-Z word practice
- Korean/English typing practice
- Arithmetic, mixed math, measurement, GCD/LCM, geometry, graph tool, math state
- Hanja study and multi-file radical CSV import
- History and country study
- Clock, drawing, education links, media, sports, magic, Golden Bell, personality quiz
- Board-game score and maze game
- Hari AIML adapter with normalization, wildcard/star, SRAI, random, predicates, condition, that/topic matching, bot properties, date/size/vocabulary, runtime learn/learnf, eval, input/response and session learning support

## Consolidated legacy activities

The following legacy activities are intentionally absorbed into shared modern screens instead of being copied one-for-one:

- `MainMathGeo` and `MainMmathG2` -> `GeometryStudyActivity`
- `MainMathPlus`, `MainMathMinus`, `MainMathMulti`, `MainMathDiv`, `MainMathMix` -> `MathStudyActivity`
- `MainMathMsrCvt`, `MainMathMsrCvt3` -> `MeasurementActivity`
- `MainEcpuTyp01`, `MainEcpuTyp02` -> `TypingPracticeActivity`
- `MainCourageAvi*`, sports/magic video wrappers -> catalog based external video opening
- school-level wrapper activities -> `SchoolMenuActivity`

## Legacy data and binary assets

The original repository contains Hari AIML/config text plus binary artwork/audio under:

- `app1/src/main/assets/Hari`
- `app1/src/main/res/drawable*`
- `app1/src/main/res/mipmap*`
- `app1/src/main/res/raw/song1.mp3`

Confirmed examples include `cha*.jpg`, `dial*.png`, `dogkeeping*.jpg`, English learning artwork, `st_a0_001.png` style tracing assets and `song1.mp3`.

Exact import helpers are provided:

- `tools/import_legacy_text_assets.sh`
- `tools/import_legacy_binary_assets.sh`
- `tools/verify_legacy_asset_parity.sh`

The parity verifier compares imported AIML/config and supported PNG/JPG/WebP/MP3 resources byte-for-byte with the original checkout. The source repository is not anonymously accessible from GitHub Actions, so exact source/target parity verification is intentionally performed only in an authorized local checkout; the default CI remains independent of the private source repository.

## Automated verification

GitHub Actions continuously runs the modern source-only verification:

`clean + testDebugUnitTest + lintDebug + assembleDebug`

For an authorized checkout of the original repository, run:

```bash
bash tools/import_legacy_text_assets.sh ../eunhyo
bash tools/import_legacy_binary_assets.sh ../eunhyo/app1/src/main/res app/src/main/res
bash tools/verify_legacy_asset_parity.sh ../eunhyo app/src/main
./gradlew clean testDebugUnitTest lintDebug assembleDebug
```

This preserves the original `sofajohnlee/eunhyo` repository while providing a byte-for-byte asset acceptance gate in an environment that has access to it.

## Remaining final gate

The remaining non-automatable acceptance gate is to open the repository in Android Studio Meerkat | 2024.3.1 and perform emulator/device smoke tests using `docs/SMOKE_TEST_CHECKLIST.md`.

The original `sofajohnlee/eunhyo` repository must remain unchanged.
