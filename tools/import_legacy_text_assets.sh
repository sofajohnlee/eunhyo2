#!/usr/bin/env bash
set -euo pipefail

# Usage:
#   ./tools/import_legacy_text_assets.sh ../eunhyo
#
# Copies the original Hari AIML/config text assets and extracts the embedded
# Hanja arrays from a local checkout of sofajohnlee/eunhyo without modifying it.

SOURCE_ROOT="${1:-../eunhyo}"
SOURCE_HARI="$SOURCE_ROOT/app1/src/main/assets/Hari"
TARGET_HARI="app/src/main/assets/Hari"

if [[ ! -d "$SOURCE_HARI" ]]; then
  echo "Legacy Hari directory not found: $SOURCE_HARI" >&2
  exit 1
fi

mkdir -p "$TARGET_HARI/aiml" "$TARGET_HARI/config"

find "$SOURCE_HARI/aiml" -maxdepth 1 -type f \
  \( -name '*.aiml' -o -name '*.txt' -o -name '*.save' \) \
  ! -name '*.bak' -print0 | while IFS= read -r -d '' file; do
    cp -p "$file" "$TARGET_HARI/aiml/$(basename "$file")"
  done

find "$SOURCE_HARI/config" -maxdepth 1 -type f -name '*.txt' -print0 | while IFS= read -r -d '' file; do
  cp -p "$file" "$TARGET_HARI/config/$(basename "$file")"
done

echo "Hari text assets copied from $SOURCE_HARI to $TARGET_HARI"

python3 tools/extract_legacy_hanja.py "$SOURCE_ROOT"

echo "Legacy text/data import complete"
echo "Review with: git diff -- app/src/main/assets/Hari app/src/main/assets/hanja"
