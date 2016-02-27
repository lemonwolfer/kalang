
package kalang.ast;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import javax.annotation.Nonnull;
import kalang.core.Type;
import kalang.core.Types;
/**
 *
 * @author Kason Yang <i@kasonyang.com>
 */
public class ArrayLengthExpr extends ExprNode{

    @Nonnull
    public ExprNode arrayExpr;

    public ArrayLengthExpr(@Nonnull ExprNode arrayExpr) {
        this.arrayExpr = arrayExpr;
    }
    
    @Override
    public Type getType() {
        return Types.INT_TYPE;
    }

}
