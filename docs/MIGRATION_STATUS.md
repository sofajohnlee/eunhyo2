# Migration status

Source: `sofajohnlee/eunhyo`
Target: `sofajohnlee/eunhyo2`
Target IDE: Android Studio Meerkat | 2024.3.1

## Status

**Legacy manifest functional audit is complete. No known unclassified functional Activity remains.**

Recent GitHub Actions runs, including run 381 and run 388, completed successfully with:

`clean + testDebugUnitTest + lintDebug + assembleDebug`

The migration uses shared modern Activities where multiple legacy screens duplicated the same behavior. Final acceptance still requires importing the original large text/binary assets in an authorized local checkout and running the Meerkat emulator/device smoke-test checklist.

## Completed functional migrations

- Main navigation and school-level menus
- Korean study, phrases, idioms, spelling, pronunciation, book settings/editor and Korean song controller
- Legacy `MainKGKorean` multilingual picture-learning path through an extractable CSV-backed modern screen with Korean/English/French text, original drawable lookup and TTS
- English basic study, alphabet tracing, phonics/color vocabulary, A-Z word practice, CSV sentence import and SAF-based PDF opening
- Korean/English typing practice
- Arithmetic, mixed math, measurement, GCD/LCM, geometry, graph tools, math state and progress
- Hanja study, embedded Hanja extraction and multi-file radical CSV import
- History and country study
- Clock and drawing practice
- Drawing persistence through SAF PNG export and reopening without legacy storage permissions
- Media catalog including Courage/Christmas/Wreck-It Ralph/Korean sentence/Marie-related legacy video entries
- Sports, magic, Golden Bell and personality quiz
- Board-game score and maze game
- Learning mail/notebook with SAF, speech recognition and TTS
- Settings plus SAF-based score/state import-export (`mscore.txt`, `yscore.txt`, `mstate.txt`)
- Hari AIML adapter with normalization, wildcard/star, SRAI, random, predicates, condition, that/topic matching, bot properties, date/size/vocabulary, runtime learn/learnf, eval, input/response and session learning support
- Khan Academy curriculum links discovered across math, English grammar and geometry legacy menus consolidated into one data-driven education-link catalog
- Legacy image-only materials exposed through reusable gallery/slideshow screens after binary asset import

## Consolidated legacy activities

The following legacy activities are intentionally absorbed into shared modern screens instead of being copied one-for-one:

- `MainMathGeo`, `MainMmathG2` -> `GeometryStudyActivity`
- `MainMathPlus`, `MainMathMinus`, `MainMathMulti`, `MainMathDiv`, `MainMathMix` -> `MathStudyActivity`
- `MainMathMsrCvt`, `MainMathMsrCvt3` -> `MeasurementActivity`
- `MainEcpuTyp01`, `MainEcpuTyp02` -> `TypingPracticeActivity`
- `MainMathM10/M11/M20/M21`, `MainEngM10/M11/M20/M21/M40/M41`, `MainKanMathMpx`, `MainKanMathElGeo*`, `MainKanMathHlGeo5` -> `EducationLinksActivity`
- `MainMathM12`, `MainEngPic`, tracing/image-only screens, pride/dog/Marie image materials -> reusable gallery/slideshow screens
- `MainCourageAvi*`, `MainMathAvi`, `Main_prnmarie_avi`, `MainKoreanSen_avi`, sports/magic video wrappers -> catalog-based external video opening
- `Main_EngExpert` -> SAF-based `PdfLibraryActivity`
- `Main_env` -> `SettingsActivity` + `DataTransferActivity`
- `MainMathPainter*` -> `DrawingPracticeActivity` with drawing, clear, PNG export and image reopening
- school-level wrapper activities -> `SchoolMenuActivity`

## Legacy data and binary assets

The original repository remains unchanged and contains Hari AIML/config text plus binary artwork/audio under:

- `app1/src/main/assets/Hari`
- `app1/src/main/res/drawable*`
- `app1/src/main/res/mipmap*`
- `app1/src/main/res/raw/song1.mp3`

Exact import/extraction helpers are provided:

- `tools/import_legacy_text_assets.sh`
- `tools/extract_legacy_hanja.py`
- `tools/extract_legacy_phonics.py`
- `tools/extract_legacy_kgkorean.py`
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

## Remaining final acceptance gates

1. Confirm the latest post-drawing-persistence CI succeeds.
2. In an authorized local checkout, import and verify the original Hari/image/audio assets.
3. Open `eunhyo2` in Android Studio Meerkat | 2024.3.1 and run `docs/SMOKE_TEST_CHECKLIST.md` on an emulator/device.

The original `sofajohnlee/eunhyo` repository must remain unchanged.
