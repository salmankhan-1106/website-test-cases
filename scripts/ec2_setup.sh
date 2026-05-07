#!/usr/bin/env bash
set -euo pipefail

APP_DIR="${APP_DIR:-$(pwd)}"
VENV_DIR="${VENV_DIR:-$APP_DIR/.venv}"

cd "$APP_DIR"

if [ ! -d "$VENV_DIR" ]; then
  python3 -m venv "$VENV_DIR"
fi

"$VENV_DIR/bin/pip" install --upgrade pip
"$VENV_DIR/bin/pip" install -r requirements.txt
