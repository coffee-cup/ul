#!/bin/bash

# Exit on error
set -e

ACCEPT_FILES=./tests/accept/*.ul
REJECT_FILES=./tests/reject/*.ul

make clean; make;
echo  ""

# All of these files should be in the language
for f in $ACCEPT_FILES
do
    echo "Testing $f"
    java Compiler $f
    echo ""
done

# All of these files should not be in the language
