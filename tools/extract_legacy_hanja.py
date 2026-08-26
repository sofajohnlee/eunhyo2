#!/usr/bin/env python3
"""Extract embedded Hanja arrays from the legacy MainEhanja7.java source.

Usage:
    python3 tools/extract_legacy_hanja.py ../eunhyo

The script reads the original repository without modifying it and writes:
    app/src/main/assets/hanja/legacy_embedded.csv
    app/src/main/assets/hanja/legacy_embedded_meta.csv

The main CSV is intentionally 3 columns (character, reading, meaning) so it is
compatible with HanjaRepository.parseCsv(). The metadata CSV keeps the legacy
rank field when present.
"""

from __future__ import annotations

import csv
import re
import sys
from pathlib import Path

ROW_RE = re.compile(
    r"\{\s*\"((?:\\.|[^\"\\])*)\"\s*,\s*"
    r"\"((?:\\.|[^\"\\])*)\"\s*,\s*"
    r"\"((?:\\.|[^\"\\])*)\""
    r"(?:\s*,\s*\"((?:\\.|[^\"\\])*)\")?\s*\}"
)


def java_unescape(value: str) -> str:
    return (
        value.replace(r"\\", "\0")
        .replace(r"\n", "\n")
        .replace(r"\t", "\t")
        .replace(r'\"', '"')
        .replace("\0", "\\")
        .strip()
    )


def extract_block(text: str, marker: str) -> str:
    start = text.find(marker)
    if start < 0:
        return ""
    brace = text.find("{", start)
    if brace < 0:
        return ""
    # Java array initializers here end at the first top-level `};` after marker.
    end = text.find("};", brace)
    return text[brace : end + 1] if end >= 0 else text[brace:]


def main() -> int:
    source_root = Path(sys.argv[1] if len(sys.argv) > 1 else "../eunhyo")
    source = source_root / "app1/src/main/java/com/example/foggy/eunhyo/MainEhanja7.java"
    if not source.is_file():
        print(f"Legacy source not found: {source}", file=sys.stderr)
        return 1

    text = source.read_text(encoding="utf-8", errors="replace")
    blocks = [
        ("hanja7", extract_block(text, "String hanja7[][]")),
        ("hanja3", extract_block(text, "String hanja3[][]")),
    ]

    rows: list[tuple[str, str, str, str, str]] = []
    seen: set[tuple[str, str, str]] = set()
    for source_name, block in blocks:
        for match in ROW_RE.finditer(block):
            character, reading, meaning, rank = (java_unescape(v or "") for v in match.groups())
            if not character:
                continue
            key = (character, reading, meaning)
            if key in seen:
                continue
            seen.add(key)
            rows.append((character, reading, meaning, rank, source_name))

    if not rows:
        print("No embedded Hanja rows found", file=sys.stderr)
        return 2

    target_dir = Path("app/src/main/assets/hanja")
    target_dir.mkdir(parents=True, exist_ok=True)

    main_csv = target_dir / "legacy_embedded.csv"
    with main_csv.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.writer(handle)
        for character, reading, meaning, _rank, _source in rows:
            writer.writerow([character, reading, meaning])

    meta_csv = target_dir / "legacy_embedded_meta.csv"
    with meta_csv.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.writer(handle)
        writer.writerow(["character", "reading", "meaning", "rank", "source"])
        writer.writerows(rows)

    print(f"Extracted {len(rows)} unique Hanja rows")
    print(f"Wrote {main_csv}")
    print(f"Wrote {meta_csv}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
