#!/bin/bash

find . -name "*.class" | xargs rm -f;

rm -f out.mips
rm -rf bin