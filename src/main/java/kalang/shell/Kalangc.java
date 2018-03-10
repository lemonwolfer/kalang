package kalang.shell;

import java.io.File;
import java.io.IOException;
import kalang.compiler.Diagnosis;
import kalang.compiler.DiagnosisHandler;
import kalang.tool.AstWriter;
import kalang.tool.ClassWriter;
import kalang.tool.FileSystemOutputManager;
import kalang.tool.JointFileSystemCompiler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 *
 * @author Kason Yang
 */
public class Kalangc extends ShellBase {

    protected boolean hasError = false;

    public final static String SYNTAX = "kalangc";

    public static void main(String[] args) {
        new Kalangc().run(args);
    }

    private void run(String[] args) {
        Options ops = new Options();
        ops.addOption("o", true, "output directory");
        ops.addOption("f", true, "output format");
        CommandLine cli = this.parseArgs(ops, args);
        if (cli == null || cli.hasOption("help")) {
            printUsage(SYNTAX, ops);
            return;
        }
        //TODO use classLoader and config
        JointFileSystemCompiler fsc = new JointFileSystemCompiler();
        DiagnosisHandler oldHandler = fsc.getDiagnosisHandler();
        fsc.setDiagnosisHandler(new DiagnosisHandler() {
            @Override
            public void handleDiagnosis(Diagnosis diagnosis) {
                oldHandler.handleDiagnosis(diagnosis);
                if (diagnosis.getKind().isError()) {
                    Kalangc.this.hasError = true;
                }
            }
        });
        File[] cps = parseClassPath(cli);
        if (cps != null) {
            for (File cp : cps) {
                fsc.addClassPath(cp);
            }
        }
        String outPath = ".";
        if (cli.hasOption("s")) {
            String srcPath = cli.getOptionValue("s");
            try {
                fsc.addKalangAndJavaSourceDir(new File(srcPath));
            } catch (IOException ex) {
                //TODO show exception message
            }
        }
        if (cli.hasOption("o")) {
            outPath = cli.getOptionValue("o");
        }
        String outputFormat = "class";
        if (cli.hasOption("f")) {
            outputFormat = cli.getOptionValue("f");
        }
        FileSystemOutputManager outputManager = new FileSystemOutputManager(new File(outPath), outputFormat);
        switch (outputFormat) {
            case "class":
                fsc.setJavaOutputManager(outputManager);
                fsc.setCodeGenerator(new ClassWriter(outputManager));
                break;
            case "ast":
                fsc.setCodeGenerator(new AstWriter(outputManager));
                break;
            default:
                throw new UnsupportedOperationException("unknown format:" + outputFormat);
        }
        File currentDir = new File(".");
        fsc.addJavaSourcePath(currentDir);
        fsc.addSourcePath(currentDir);
        String[] srcs = cli.getArgs();
        for (String s : srcs) {
            File srcFile = new File(s);
            if (srcFile.isDirectory()) {
                try {
                    fsc.addKalangAndJavaSourceDir(srcFile);
                } catch (IOException ex) {
                    //TODO show exception message
                }
            } else {
                try {
                    //TODO here should be currenDir?
                    fsc.addKalangOrJavaSource(currentDir, srcFile);
                } catch (IOException ex) {
                    //TODO show exception message
                }
            }
        }
        hasError = false;
        fsc.compile();
    }

}