#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd -P)"

ROOT_DIR=$(dirname $CURRENT_DIR)
RES_DIR=$ROOT_DIR/app/src/main/res/

convert $CURRENT_DIR/icon.png -resize 192x192 $RES_DIR/drawable-xxxhdpi/ic_launcher.png
convert $CURRENT_DIR/icon.png -resize 144x144 $RES_DIR/drawable-xxhdpi/ic_launcher.png
convert $CURRENT_DIR/icon.png -resize 144x144 $RES_DIR/drawable-xhdpi/ic_launcher.png
convert $CURRENT_DIR/icon.png -resize 72x72 $RES_DIR/drawable-hdpi/ic_launcher.png
convert $CURRENT_DIR/icon.png -resize 72x72 $RES_DIR/drawable-mdpi/ic_launcher.png
convert $CURRENT_DIR/icon.png -resize 36x36 $RES_DIR/drawable-ldpi/ic_launcher.png
