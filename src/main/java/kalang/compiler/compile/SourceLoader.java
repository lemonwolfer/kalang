
package kalang.compiler.compile;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
/**
 *
 * @author Kason Yang 
 */
public interface SourceLoader {
    
    @Nullable
    KalangSource loadSource(@Nonnull String className);
    
}
