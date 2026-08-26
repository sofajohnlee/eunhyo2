# eunhyo2

Modernized Android application migrated from `sofajohnlee/eunhyo`.

Target development environment: Android Studio Meerkat | 2024.3.1.

The original `sofajohnlee/eunhyo` repository is preserved unchanged as the reference implementation.

## Migration and verification

- Migration status and remaining verification gates: `docs/MIGRATION_STATUS.md`
- Optional exact legacy PNG/JPG/MP3 import from a sibling checkout: `tools/import_legacy_binary_assets.sh`
- CI verification target: `./gradlew clean testDebugUnitTest lintDebug assembleDebug`
