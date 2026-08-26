# Android Studio Meerkat smoke test checklist

Target IDE: Android Studio Meerkat | 2024.3.1

## Clean build

- Open `sofajohnlee/eunhyo2` from the repository root.
- Use JDK 17.
- Sync Gradle successfully.
- Run `clean`, `testDebugUnitTest`, `lintDebug`, and `assembleDebug`.
- Install the debug APK on at least one emulator or physical Android device.

## Navigation

- Launch the app from the launcher icon.
- Open each school-level menu and return to the main screen.
- Verify rotation/background-resume does not crash the current screen.

## Korean

- Korean study and phrase navigation.
- Idiom/proverb previous-next and meaning reveal.
- Spelling rule previous-next and answer reveal.
- Pronunciation 45-case navigation.
- Korean book character/plot/language changes, persistence, editor preview and TTS.
- Korean song play/pause/stop after importing `song1.mp3`.

## English and typing

- Basic English study.
- A-Z word practice and upper/lower-case display.
- Import a legacy-format sentence CSV through the system file picker; relaunch and confirm persistence.
- Korean and English typing modes, level changes, typing accuracy and reset.

## Mathematics

- Normal arithmetic and mixed arithmetic across difficulty levels.
- Correct/incorrect scoring and score persistence/reset.
- Measurement conversions including m/cm, hour/minute, day/hour, week/day and year/month.
- GCD/LCM calculations.
- Geometry interactions and graph tools.
- Math-state persistence.

## Hanja

- Built-in Hanja navigation.
- Import legacy `hsrc600.csv` format and verify persistence.
- Multi-select legacy `hbusu1.csv` through `hbusu14.csv`; verify group recognition and persistence.
- Restore built-in Hanja/radical data.

## Other study/utilities

- History and country search.
- Clock and drawing practice.
- Education links and media/sports/magic external intents.
- Golden Bell O/X scoring and TTS.
- Personality quiz result flow.
- Maze movement and completion on all three levels.
- Board-game score controls.

## Hari AI

- Exact response category.
- `*` and `_` wildcard capture.
- `<srai>` redirects and `<random>` output.
- `<set>/<get>` and `<condition>` predicates.
- `<that>` context conversation.
- Topic-based category after changing the topic predicate.
- `<bot>`, `<date>`, `<size>`, `<vocabulary>` output.
- Runtime learning through a supported `<learnf>` rule, then confirm the learned pattern in the same app session.
- Confirm malformed or unsupported AIML does not crash the app and falls back to the local rule engine.

## Exact legacy-asset acceptance

Before visual/audio parity testing, run from `eunhyo2` with sibling original checkout `../eunhyo`:

```bash
bash tools/import_legacy_text_assets.sh ../eunhyo
bash tools/import_legacy_binary_assets.sh ../eunhyo/app1/src/main/res app/src/main/res
bash tools/verify_legacy_asset_parity.sh ../eunhyo app/src/main
```

Then repeat the clean build and install. Check representative original images, tracing artwork and `song1.mp3` playback.

## Acceptance criterion

The migration is accepted when both GitHub Actions jobs pass and all relevant smoke-test items above pass on Android Studio Meerkat | 2024.3.1 without modifying the original `sofajohnlee/eunhyo` repository.
