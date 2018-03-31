import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;

import AST.DotVisitor;
import AST.PrintVisitor;
import AST.Program;
import IR.IRPrintVisitor;
import IR.IRProgram;
import IR.IRVisitor;
import IR.Exceptions.IRException;
import Semantics.TypeCheckVisitor;
import Semantics.Exceptions.SemanticException;
import Codegen.CodegenIRVisitor;

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

        // Get just the filename without path or extension
        String nameExt = new File(options.infile).getName();
        String name = nameExt.substring(0, nameExt.lastIndexOf('.'));

        PrintStream outStream = System.out;
        if (options.outfile == "<file>") {
            String ext = "j";
            if (options.irGenerate) ext = "ir";
            else if (options.prettyPrint) ext = "_ul";
            else if (options.dotFormat) ext = "dot";

            outStream = new PrintStream(name + "." + ext);
        } else if (options.outfile != "<stdout>") {
            outStream = new PrintStream(new File(options.outfile));
        }

        // Type checker
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
        p.accept(typeCheckVisitor);

        // IR generator
        IRVisitor irVisitor = new IRVisitor(name);
        p.accept(irVisitor);
        IRProgram irProgram = irVisitor.getIRProgram();

        // If silent, we just want to compile without producing any output
        if (!options.silent) {
            if (options.irGenerate) {
                // IR pretty printer
                IRPrintVisitor irPrintVisitor = new IRPrintVisitor(outStream);
                irProgram.accept(irPrintVisitor);
            } else if (options.prettyPrint) {
                // Pretty printer
                PrintVisitor printVisitor = new PrintVisitor(outStream);
                p.accept(printVisitor);
            } else if (options.dotFormat) {
                // Dot printer
                DotVisitor dotVisitor = new DotVisitor(outStream);
                p.accept(dotVisitor);
            } else {
                // Codegen generator
                CodegenIRVisitor codegenIRVisitor = new CodegenIRVisitor(outStream, nameExt);
                irProgram.accept(codegenIRVisitor);
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
        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (IRException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
