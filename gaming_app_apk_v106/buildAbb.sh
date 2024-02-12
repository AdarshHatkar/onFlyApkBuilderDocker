#!/bin/bash


current_path=$(pwd)
echo "Current path is: $current_path"

cd appSourceCode

current_path=$(pwd)
echo "Current path is: $current_path"

exec gradle wrapper --daemon :app:bundleRelease