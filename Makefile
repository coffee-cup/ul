GNAME= ulGrammar
SRCDIR= src/
GSRC= $(SRCDIR)$(GNAME).g

all: grammar compiler

grammar: $(GSRCS)
	java org.antlr.Tool -fo $(SRCDIR) $(GSRC)

compiler:
	javac $(SRCDIR)*.java

clean:
	rm $(SRCDIR)*.class $(SRCDIR)$(GNAME)*.java $(SRCDIR)$(GNAME)__.g $(SRCDIR)$(GNAME).tokens

