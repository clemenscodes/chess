#!/bin/sh

script_path="$(realpath "$0")"
scripts_path="$(dirname $script_path)"
project_path="$(dirname $scripts_path)"

cd $project_path || exit

zip -r ./docs.zip ./docs
