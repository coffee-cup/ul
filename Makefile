GNAME= ulGrammar
SRCDIR= src/
SRCENTRY= Compiler
OUTDIR= bin/
GSRC= $(SRCDIR)$(GNAME).g

all: grammar compiler

grammar: $(GSRCS)
	java org.antlr.Tool -fo $(SRCDIR) $(GSRC)

compiler:
	javac -d $(OUTDIR) -sourcepath $(SRCDIR) $(SRCDIR)$(SRCENTRY).java

clean:
	rm $(OUTDIR)/*.class rm $(OUTDIR)**/*.class $(SRCDIR)$(GNAME)*.java $(SRCDIR)$(GNAME)__.g $(SRCDIR)$(GNAME).tokens

