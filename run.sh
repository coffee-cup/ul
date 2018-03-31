##########################################
#                                        #
# Compile and run unnamed language file  #
#                                        #
##########################################

NAME=$(basename "$1" ".ul")
JNAME="$NAME.j"
java -cp "$CLASSPATH:./bin" Compiler "$1"
java jasmin.Main $JNAME
java $NAME
