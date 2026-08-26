#!/usr/bin/env python3
from __future__ import annotations

import csv
import re
import sys
from pathlib import Path

if len(sys.argv) < 2:
    raise SystemExit("Usage: extract_legacy_phonics.py <legacy-root> [output.csv]")

legacy_root = Path(sys.argv[1])
source = legacy_root / "app1/src/main/java/com/example/foggy/eunhyo/MainEengAgent.java"
out = Path(sys.argv[2]) if len(sys.argv) > 2 else Path("app/src/main/assets/legacy_phonics.csv")

text = source.read_text(encoding="utf-8")
match = re.search(r"String\s+ColorVoc\s*\[\]\[\]\s*=\s*\{(.*?)\n\s*\};", text, re.S)
if not match:
    raise SystemExit(f"ColorVoc array not found in {source}")

rows = re.findall(r'\{\s*"([^"]+)"\s*,\s*"([^"]+)"\s*\}', match.group(1))
if not rows:
    raise SystemExit("No phonics rows extracted")

out.parent.mkdir(parents=True, exist_ok=True)
with out.open("w", encoding="utf-8", newline="") as f:
    writer = csv.writer(f)
    writer.writerow(["group", "word"])
    writer.writerows(rows)

print(f"Extracted {len(rows)} phonics rows to {out}")
