#!/bin/bash

make clean; make

ACCEPT_FILES=../tests/accept/*.ul
REJECT_FILES=../tests/reject/*.ul

cd bin/

# Create tmp directory
mkdir -p ../tmp

echo ""
echo "------ Running parser against accept files"
echo ""

# All of these files should be in the language
for f in $ACCEPT_FILES
do
    TMP1="../tmp/$(basename ${f} .ul)_1.ul"
    TMP2="../tmp/$(basename ${f} .ul)_2.ul"

    echo "Testing accept - $f"
    if ! java Compiler -o $TMP1 $f; then
        echo "$f is not in the language!"
        echo "!!!!! ERROR !!!!!"
        exit 1
    fi

    # Pretty print output of first to a second file and ensure
    # contents are the same
    if ! java Compiler -o $TMP2 $TMP1; then
        echo "$TMP1 is not in the language!"
        echo "!!!!! ERROR !!!!!"
        exit 1
    fi

    CMP=$(cmp $TMP1 $TMP2)
    if [ "$CMP" != "" ]
    then
        echo "$f - Pretty print of pretty print is not the same"
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
    if java Compiler -s 1 $f; then
        echo "$f is in the language and shouldn't be!"
        echo "!!!!! ERROR !!!!!"
        exit 1
    fi
done

# Remove tmp directory
cd ..
rm -rf tmp

echo ""
echo "Tests ran successfully!"
echo ""
