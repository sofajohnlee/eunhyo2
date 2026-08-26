# Migration status

Source: `sofajohnlee/eunhyo`
Target: `sofajohnlee/eunhyo2`
Target IDE: Android Studio Meerkat | 2024.3.1

## Status

**Final legacy-manifest audit is in progress.**

Android CI run 323 and run 324 completed successfully with:

`clean + testDebugUnitTest + lintDebug + assembleDebug`

During the final manifest audit, additional legacy curriculum-link and image-only learning activities were identified. These are now being consolidated into `EducationLinksActivity` and `LegacyLearningGalleryActivity` rather than copied one-for-one. Therefore the migration is not marked final until the remaining manifest entries have been classified and the latest post-audit CI is green.

## Completed functional migrations

- Main navigation and school-level menus
- Korean study, phrases, idioms, spelling, pronunciation, book settings/editor, Korean song controller
- English basic study, CSV sentence import, A-Z word practice
- Korean/English typing practice
- Arithmetic, mixed math, measurement, GCD/LCM, geometry, graph tool, math state
- Hanja study and multi-file radical CSV import
- History and country study
- Clock, drawing, media, sports, magic, Golden Bell, personality quiz
- Board-game score and maze game
- Hari AIML adapter with normalization, wildcard/star, SRAI, random, predicates, condition, that/topic matching, bot properties, date/size/vocabulary, runtime learn/learnf, eval, input/response and session learning support
- Legacy Khan Academy curriculum links discovered in `MainMathM10/M11/M20/M21`, `MainEngM10/M11`, and elementary geometry menus are being consolidated into the education-link catalog
- Legacy image-only materials such as `math_m12` and `st_a0_001` through `st_c1_001` are exposed through a zoomable gallery after binary asset import

## Consolidated legacy activities

The following legacy activities are intentionally absorbed into shared modern screens instead of being copied one-for-one:

- `MainMathGeo` and `MainMmathG2` -> `GeometryStudyActivity`
- `MainMathPlus`, `MainMathMinus`, `MainMathMulti`, `MainMathDiv`, `MainMathMix` -> `MathStudyActivity`
- `MainMathMsrCvt`, `MainMathMsrCvt3` -> `MeasurementActivity`
- `MainEcpuTyp01`, `MainEcpuTyp02` -> `TypingPracticeActivity`
- `MainMathM10/M11/M20/M21`, `MainEngM10/M11`, `MainKanMathElGeo*` link menus -> `EducationLinksActivity`
- `MainMathM12` and selected tracing/image-only screens -> `LegacyLearningGalleryActivity`
- `MainCourageAvi*`, sports/magic video wrappers -> catalog based external video opening
- school-level wrapper activities -> `SchoolMenuActivity`

## Legacy data and binary assets

The original repository contains Hari AIML/config text plus binary artwork/audio under:

- `app1/src/main/assets/Hari`
- `app1/src/main/res/drawable*`
- `app1/src/main/res/mipmap*`
- `app1/src/main/res/raw/song1.mp3`

Exact import helpers are provided:

- `tools/import_legacy_text_assets.sh`
- `tools/import_legacy_binary_assets.sh`
- `tools/verify_legacy_asset_parity.sh`

The parity verifier compares imported AIML/config and supported PNG/JPG/WebP/MP3 resources byte-for-byte with the original checkout. Exact source/target parity verification is performed in an authorized local checkout; the default CI remains independent of the source repository.

## Automated verification

GitHub Actions continuously runs:

`clean + testDebugUnitTest + lintDebug + assembleDebug`

For an authorized checkout of the original repository, run:

```bash
bash tools/import_legacy_text_assets.sh ../eunhyo
bash tools/import_legacy_binary_assets.sh ../eunhyo/app1/src/main/res app/src/main/res
bash tools/verify_legacy_asset_parity.sh ../eunhyo app/src/main
./gradlew clean testDebugUnitTest lintDebug assembleDebug
```

## Remaining final gates

1. Finish classifying every remaining activity in the original manifest as migrated, consolidated, obsolete, or asset-only.
2. Confirm the latest post-audit CI succeeds.
3. In an authorized checkout, import and verify the original Hari/image/audio assets.
4. Open `eunhyo2` in Android Studio Meerkat | 2024.3.1 and run `docs/SMOKE_TEST_CHECKLIST.md` on an emulator/device.

The original `sofajohnlee/eunhyo` repository must remain unchanged.
