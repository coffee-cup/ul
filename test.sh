#!/bin/bash

make clean; make

ACCEPT_FILES=../tests/accept
REJECT_FILES=../tests/reject
OUTPUT_FILES=../tests/output

cd bin/

# Create tmp directory
TMPDIR="../tmp"
mkdir -p $TMPDIR

ERROR_STR="!!!!! ERROR !!!!!"

compile_error () {
    echo "$1 did not compiler"
    echo $ERROR_STR
    exit 1
}

codegen_error () {
    echo "$1 did not codegen"
    echo $ERROR_STR
    exit 1
}

echo ""
echo "------ Running compiler against output files"
echo ""

# The .ul files in the output directory have an associated .txt file
# The .txt contains the correct output for the program
# In the meantime this must be run on linux.csc.uvic.ca
for f in $(find $OUTPUT_FILES -name "*.ul")
do
    NAME=$(basename ${f} .ul)
    IRTMP="$TMPDIR/$NAME.ir"
    JTMP="$TMPDIR/$NAME.j"
    OUTTMP="$TMPDIR/$NAME.out.txt"
    CORRECTOUT="$OUTPUT_FILES/$NAME.txt"

    echo "Testing output - $f"

    # Create the jasmine file
    if ! java Compiler -o $JTMP $f; then
        compile_error $f
    fi

    # Create the .class file
    if ! java jasmin.Main $JTMP -d $TMPDIR; then
        echo "Jasmin failed! - $JTMP"
        echo $ERROR_STR
        exit 1
    fi

    # Run the .class file
    if ! java -cp $TMPDIR $NAME > $OUTTMP; then
        echo "Java failed! - $NAME"
        echo $ERROR_STR
        exit 1
    fi

    CMP=$(cmp $OUTTMP $CORRECTOUT)
    if [ "$CMP" != "" ]; then
        echo "$NAME - Output is not correct"
        echo $ERROR_STR
        exit 1
    fi
    echo ""
done

echo ""
echo "------ Running compiler against accept files"
echo ""

# All of these files should be in the language
for f in $(find $ACCEPT_FILES -name "*.ul")
do
    TMP1="$TMPDIR/$(basename ${f} .ul)_1.ul"
    TMP2="$TMPDIR/$(basename ${f} .ul)_2.ul"

    echo "Testing accept - $f"
    if ! java Compiler -p 1 -ir 0 -o $TMP1 $f; then
        compile_error $f
    fi

    # Pretty print output of first to a second file and ensure
    # contents are the same
    if ! java Compiler -p 1 -ir 0 -o $TMP2 $TMP1; then
        compile_error $tmp1
    fi


    CMP=$(cmp $TMP1 $TMP2)
    if [ "$CMP" != "" ]
    then
        echo "$f - Pretty print of pretty print is not the same"
        echo $ERROR_STR
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
        echo $ERROR_STR
        exit 1
    fi
done

# Remove tmp directory
rm -rf $TMPDIR
cd ..

echo ""
echo "Tests ran successfully!"
echo ""
