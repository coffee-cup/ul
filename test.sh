#!/bin/bash

make clean; make

ACCEPT_FILES=../tests/accept
REJECT_FILES=../tests/reject

cd bin/

# Create tmp directory
mkdir -p ../tmp

echo ""
echo "------ Running compiler against accept files"
echo ""

# All of these files should be in the language
for f in $(find $ACCEPT_FILES -name "*.ul")
do
    TMP1="../tmp/$(basename ${f} .ul)_1.ul"
    TMP2="../tmp/$(basename ${f} .ul)_2.ul"

    echo "Testing accept - $f"
    if ! java Compiler -p 1 -o $TMP1 $f; then
        echo "$f did not compile"
        echo "!!!!! ERROR !!!!!"
        exit 1
    fi

    # Pretty print output of first to a second file and ensure
    # contents are the same
    if ! java Compiler -p 1 -o $TMP2 $TMP1; then
        echo "$tmp1 did not compile"
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
echo "------ Running compiler against reject files"
echo ""

# All of these files should not be in the language
for f in $(find $REJECT_FILES -name "*.ul")
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
