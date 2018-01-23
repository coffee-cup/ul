
/*
 * Compiler.java
 */

import org.antlr.runtime.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import ParseCmd.ParseCmd;
import AST.*;

public class Compiler {
	public static void main(String[] args) {
		ANTLRInputStream input;
		String filename;
		String[] optionsArgs;

		String usage = "usage: [-o outfile -s 0] filename";
		ParseCmd cmd = new ParseCmd.Builder()
                .help(usage)
                .parm("-o", "<stdout>")
                .parm("-s", "0")
                    .rex("^[01]{1}$")
                    .msg("enter 0 or 1; other values are invalid")
                .parm("-d", "0")
                    .rex("^[01]{1}$")
                    .msg("enter 0 or 1; other values are invalid")
                .build();

		if (args.length == 0) {
			System.out.println(usage);
			return;
		} else {
			filename = args[args.length - 1];
			optionsArgs = Arrays.copyOfRange(args, 0, args.length - 1);
		}

		try {
			input = new ANTLRInputStream(new FileInputStream(filename));

			Map<String, String> R = new HashMap<String, String>();
			String parseError = cmd.validate(optionsArgs);
			if (cmd.isValid(optionsArgs)) {
				R = cmd.parse(optionsArgs);
                // System.out.println(cmd.displayMap(R));
			} else {
				System.out.println(parseError);
				System.exit(1);
			}

			String outfile = R.get("-o");
            boolean silent = R.get("-s").equals("1");
            boolean dot = R.get("-d").equals("1");

			// System.out.println("filename: " + filename);
			// System.out.println("outfile: " + outfile);
            // System.out.println("silent: " + silent);

			// The name of the grammar here is "ulGrammar",
			// so ANTLR generates ulGrammarLexer and ulGrammarParser
			ulGrammarLexer lexer = new ulGrammarLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			ulGrammarParser parser = new ulGrammarParser(tokens);

			Program p = parser.program();

            PrintStream outStream = System.out;
            if (outfile != "<stdout>") {
                outStream = new PrintStream(new File(outfile));
            }

            if (!silent) {
                if (dot) {
                    DotVisitor dotVisitor = new DotVisitor(outStream);
                    p.accept(dotVisitor);
                } else {
                    PrintVisitor printVisitor = new PrintVisitor(outStream);
                    p.accept(printVisitor);
                }
            }
		} catch (RecognitionException e) {
			// A lexical or parsing error occurred.
			// ANTLR will have already printed information on the
			// console due to code added to the grammar. So there is
			// nothing to do here.
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.out.println("File " + filename + " not found");
			System.exit(1);
		} catch (IOException e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(1);
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
