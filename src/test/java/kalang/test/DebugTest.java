package kalang.test;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 *
 * @author Kason Yang
 */
public class DebugTest extends JointCompilerTestCase {
    
    public DebugTest() {
    }
    
    @Test
    public void test() throws IOException{
        File debugDir = new File("debug");
        if(debugDir.exists()){
            this.addKalangAndJavaSourceDir(debugDir);
            compile();
        }
    }
    
}