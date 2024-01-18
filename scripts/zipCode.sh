#!/bin/sh

script_path="$(realpath "$0")"
scripts_path="$(dirname $script_path)"
project_path="$(dirname $scripts_path)"
super_path="$(dirname $project_path)"

cd $super_path || exit

zip \
    -r ./chess/chess.zip \
    ./chess \
    -x "./chess/.git/*" \
    -x "./chess/.nx/*" \
    -x "./chess/.idea/*" \
    -x "./chess/.vscode/*" \
    -x "./chess/node_modules/*" \
    -x "./chess/docs/*" \
    -x "./chess/coverage/*" \
    -x "./chess/dist/*"

