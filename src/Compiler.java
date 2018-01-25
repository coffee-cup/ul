
/*
 * Compiler.java
 */

import org.antlr.runtime.*;
import java.io.*;
import AST.*;

public class Compiler {
    private CompilerOptions options;

    public Compiler(CompilerOptions options) {
        this.options = options;
    }

    public void compile() throws RecognitionException, FileNotFoundException, Exception {
        ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(options.infile));

        // Lexer
        ulGrammarLexer lexer = new ulGrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Parser
        ulGrammarParser parser = new ulGrammarParser(tokens);

        Program p = parser.program();

        PrintStream outStream = System.out;
        if (options.outfile != "<stdout>") {
            outStream = new PrintStream(new File(options.outfile));
        }

        if (!options.silent) {
            if (options.dotFormat) {
                DotVisitor dotVisitor = new DotVisitor(outStream);
                p.accept(dotVisitor);
            } else {
                PrintVisitor printVisitor = new PrintVisitor(outStream);
                p.accept(printVisitor);
            }
        }
    }

	public static void main(String[] args) {

        CompilerOptions options = CompilerOptions.parseCompilerArgs(args);
        if (options == null) {
            System.exit(1);
        }

        Compiler compiler = new Compiler(options);

		try {
            compiler.compile();
		} catch (RecognitionException e) {
			System.exit(1);
		} catch (FileNotFoundException e) {
			System.out.println("File " + options.infile + " not found");
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
