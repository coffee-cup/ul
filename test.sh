#!/bin/bash

ACCEPT_FILES=./tests/accept/*.ul
REJECT_FILES=./tests/reject/*.ul

echo ""
echo "------ Running parser against accept files"
echo ""

# All of these files should be in the language
for f in $ACCEPT_FILES
do
    echo "Testing accept - $f"
    java Compiler $f
    if ! java Compiler $f; then
        echo "$f is not in the language!"
        echo ""
        echo "!!!!! ERROR !!!!!"
        exit 1
    fi
    echo ""
done

echo ""
echo "------ Running parser against reject files"
echo ""

# All of these files should not be in the language
for f in $REJECT_FILES
do
    echo ""
    echo "Testing reject - $f"
    if java Compiler $f; then
        echo "$f is in the language and shouldn't be!"
        echo ""
        echo "!!!!! ERROR !!!!!"
        exit 1
    fi
done

echo ""
echo "Tests ran successfully!"
echo ""
