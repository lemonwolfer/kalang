package kalang.script;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import kalang.lang.Script;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Kason Yang
 */
public abstract class ShellScript extends Script {

    public final static String DEFAULT_ENCODING = "utf8";

    protected String readText(String file, String encoding) throws IOException {
        return FileUtils.readFileToString(new File(file), encoding);
    }

    protected String readText(String file) throws IOException {
        return readText(file, DEFAULT_ENCODING);
    }

    protected void writeText(String file, String data, String encoding) throws IOException {
        FileUtils.writeStringToFile(new File(file), data, encoding);
    }

    protected void writeText(String file, String data) throws IOException {
        writeText(file, data, DEFAULT_ENCODING);
    }

    protected int randomInt(int min, int max) {
        //TODO here never return max
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
    
    protected int randomInt() {
        return new Random().nextInt();
    }
    
    protected void deleteDirectory(String file) throws IOException {
        FileUtils.deleteDirectory(new File(file));
    }
    
    protected String baseName(String filename) {
        return FilenameUtils.getBaseName(filename);
    }
    
    protected String extension(String filename){
        return FilenameUtils.getExtension(filename);
    }

    protected int exec(String command, String... arguments) throws IOException {
        String[] list = new String[arguments.length + 1];
        list[0] = command;
        if (arguments.length > 0) {
            System.arraycopy(arguments, 0, list, 1, arguments.length);
        }
        ProcessBuilder pb = new ProcessBuilder(list);
        pb.inheritIO();
        Process p = pb.start();
        try {
            p.waitFor();
        } catch (InterruptedException ex) {

        }
        return p.exitValue();
    }

    protected int exec(String command) throws IOException {
        return exec(command, new String[0]);
    }

}