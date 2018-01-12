
/*
 * Compiler.java
 *
 * A starting place for the unamed language compiler for CSC 435/535
 *
 */

import org.antlr.runtime.*;
import java.io.*;

public class Compiler {
	public static void main(String[] args) throws Exception {
		ANTLRInputStream input;
    String filename;

		if (args.length == 0) {
			System.out.println("Usage: Test filename.ul");
			return;
		} else {
        filename = args[0];
        input = new ANTLRInputStream(new FileInputStream(filename));
		}

		// The name of the grammar here is "ulGrammar",
		// so ANTLR generates ulGrammarLexer and ulGrammarParser
		ulGrammarLexer lexer = new ulGrammarLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ulGrammarParser parser = new ulGrammarParser(tokens);

		try {
			parser.program();
      System.out.println(filename + " is in the language");
    } catch (RecognitionException e) {
        // A lexical or parsing error occurred.
        // ANTLR will have already printed information on the
        // console due to code added to the grammar. So there is
        // nothing to do here.
        System.out.println(filename + " is not in the language :(\n");
        System.exit(1);
		} catch (Exception e) {
			System.out.println(e);
      System.out.println("\n" + filename + " is not in the language :(\n");
			e.printStackTrace();

      System.exit(1);
		}
	}
}
