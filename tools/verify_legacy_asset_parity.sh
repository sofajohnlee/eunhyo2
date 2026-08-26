#!/usr/bin/env bash
set -euo pipefail

SOURCE_ROOT="${1:-../eunhyo}"
TARGET_ROOT="${2:-app/src/main}"

SOURCE_HARI="$SOURCE_ROOT/app1/src/main/assets/Hari"
TARGET_HARI="$TARGET_ROOT/assets/Hari"
SOURCE_RES="$SOURCE_ROOT/app1/src/main/res"
TARGET_RES="$TARGET_ROOT/res"

fail=0

check_equal() {
  local source="$1"
  local target="$2"
  if [[ ! -f "$target" ]]; then
    echo "MISSING: $target (source: $source)" >&2
    fail=1
    return
  fi
  if ! cmp -s "$source" "$target"; then
    echo "DIFFERS: $target (source: $source)" >&2
    fail=1
  fi
}

if [[ -d "$SOURCE_HARI/aiml" ]]; then
  while IFS= read -r -d '' source; do
    name="$(basename "$source")"
    check_equal "$source" "$TARGET_HARI/aiml/$name"
  done < <(find "$SOURCE_HARI/aiml" -maxdepth 1 -type f \
    \( -name '*.aiml' -o -name '*.txt' -o -name '*.save' \) \
    ! -name '*.bak' -print0)
fi

if [[ -d "$SOURCE_HARI/config" ]]; then
  while IFS= read -r -d '' source; do
    name="$(basename "$source")"
    check_equal "$source" "$TARGET_HARI/config/$name"
  done < <(find "$SOURCE_HARI/config" -maxdepth 1 -type f -name '*.txt' -print0)
fi

for dir in drawable drawable-hdpi drawable-mdpi drawable-ldpi mipmap-hdpi mipmap-mdpi mipmap-xhdpi mipmap-xxhdpi mipmap-xxxhdpi raw; do
  [[ -d "$SOURCE_RES/$dir" ]] || continue
  while IFS= read -r -d '' source; do
    name="$(basename "$source")"
    check_equal "$source" "$TARGET_RES/$dir/$name"
  done < <(find "$SOURCE_RES/$dir" -maxdepth 1 -type f \
    \( -iname '*.png' -o -iname '*.jpg' -o -iname '*.jpeg' -o -iname '*.webp' -o -iname '*.mp3' \) -print0)
done

if [[ "$fail" -ne 0 ]]; then
  echo "Legacy asset parity verification FAILED." >&2
  exit 1
fi

echo "Legacy AIML/config and binary resource parity verified byte-for-byte."
