import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import ParseCmd.ParseCmd;

public class CompilerOptions {
    public String infile;
    public String outfile = "<stdout>";
    public boolean irGenerate = false;
    public boolean prettyPrint = false;
    public boolean dotFormat = false;
    public boolean silent = false;

    public CompilerOptions(String infile, String outfile, boolean irGenerate, boolean prettyPrint, boolean dotFormat, boolean silent) {
        this.infile = infile;
        this.outfile = outfile;
        this.irGenerate = irGenerate;
        this.prettyPrint = prettyPrint;
        this.dotFormat = dotFormat;
        this.silent = silent;
    }

    public static CompilerOptions parseCompilerArgs(String[] args) {
        String filename;
        String[] optionsArgs;

        String usage = "usage: [-o outfile] filename";
        ParseCmd cmd = new ParseCmd.Builder()
            .help(usage)
            .parm("-o", "<stdout>")
            .parm("-p", "0")
                .rex("^[01]{1}$")
                .msg("enter 0 or 1; other values are invalid")
            .parm("-s", "0")
                .rex("^[01]{1}$")
                .msg("enter 0 or 1; other values are invalid")
            .parm("-ir", "0")
                .rex("^[01]{1}$")
                .msg("enter 0 or 1; other values are invalid")
            .parm("-d", "0")
                .rex("^[01]{1}$")
                .msg("enter 0 or 1; other values are invalid")
            .build();

        if (args.length == 0) {
            System.out.println(usage);
            return null;
        } else {
            filename = args[args.length - 1];
            optionsArgs = Arrays.copyOfRange(args, 0, args.length - 1);
        }

        Map<String, String> R = new HashMap<String, String>();
        String parseError = cmd.validate(optionsArgs);
        if (cmd.isValid(optionsArgs)) {
            R = cmd.parse(optionsArgs);
        } else {
            System.out.println(parseError);
            return null;
        }

        String outfile = R.get("-o");
        boolean irGenerate = R.get("-ir").equals("1");
        boolean prettyPrint = R.get("-p").equals("1");
        boolean dot = R.get("-d").equals("1");
        boolean silent = R.get("-s").equals("1");

        return new CompilerOptions(filename, outfile, irGenerate, prettyPrint, dot, silent);
    }
}
