#!/usr/bin/env bash
set -euo pipefail

SOURCE_ROOT="${1:-../eunhyo/app1/src/main/res}"
TARGET_ROOT="${2:-app/src/main/res}"

if [[ ! -d "$SOURCE_ROOT" ]]; then
  echo "Legacy resource directory not found: $SOURCE_ROOT" >&2
  echo "Usage: $0 [legacy-res-dir] [target-res-dir]" >&2
  exit 1
fi

mkdir -p "$TARGET_ROOT"

copy_dir() {
  local name="$1"
  if [[ -d "$SOURCE_ROOT/$name" ]]; then
    mkdir -p "$TARGET_ROOT/$name"
    find "$SOURCE_ROOT/$name" -maxdepth 1 -type f \
      \( -iname '*.png' -o -iname '*.jpg' -o -iname '*.jpeg' -o -iname '*.webp' -o -iname '*.mp3' \) \
      -exec cp -p {} "$TARGET_ROOT/$name/" \;
  fi
}

copy_dir drawable
copy_dir drawable-hdpi
copy_dir drawable-mdpi
copy_dir drawable-ldpi
copy_dir mipmap-hdpi
copy_dir mipmap-mdpi
copy_dir mipmap-xhdpi
copy_dir mipmap-xxhdpi
copy_dir mipmap-xxxhdpi
copy_dir raw

echo "Legacy binary resources copied into $TARGET_ROOT"
echo "Review duplicates/unused resources, then run: ./gradlew clean testDebugUnitTest lintDebug assembleDebug"
