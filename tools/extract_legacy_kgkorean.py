#!/usr/bin/env python3
import csv
import re
import sys
from pathlib import Path

root = Path(sys.argv[1] if len(sys.argv) > 1 else '../eunhyo')
src = root / 'app1/src/main/java/com/example/foggy/eunhyo/MainKGKorean.java'
out = Path('app/src/main/assets/korean/legacy_kgkorean.csv')
text = src.read_text(encoding='utf-8')

case_re = re.compile(r'case\s+(\d+)\s*:(.*?)(?=\n\s*case\s+\d+\s*:|\n\s*break;\s*\n\s*\}|\Z)', re.S)
rows = []
for m in case_re.finditer(text):
    idx, block = int(m.group(1)), m.group(2)
    vals = re.findall(r'tv\.setText\("((?:\\.|[^"\\])*)"\)', block)
    images = re.findall(r'iv\.setImageResource\(R\.drawable\.([A-Za-z0-9_]+)\)', block)
    if not vals:
        continue
    decoded = [bytes(v, 'utf-8').decode('unicode_escape') if '\\u' in v else v.replace('\\n', '\n') for v in vals]
    korean = decoded[0] if decoded else ''
    english = next((v for v in decoded[1:] if re.search(r'[A-Za-z]', v)), '')
    french = decoded[-1] if len(decoded) >= 3 else ''
    image = next((i for i in images if i != 'koremp'), images[0] if images else '')
    rows.append((idx, korean, english, french, image))

out.parent.mkdir(parents=True, exist_ok=True)
with out.open('w', encoding='utf-8', newline='') as f:
    w = csv.writer(f)
    w.writerow(['index','korean','english','french','image'])
    w.writerows(sorted({r[0]: r for r in rows}.values()))
print(f'wrote {len(rows)} candidate cards to {out}')
