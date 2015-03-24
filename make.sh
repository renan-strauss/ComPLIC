#!/bin/bash

mkdir bin

find . -name "*.java" | xargs javac -d bin;