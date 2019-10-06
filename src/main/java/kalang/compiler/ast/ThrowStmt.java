package kalang.compiler.ast;

import java.util.LinkedList;
import java.util.List;
public class ThrowStmt extends Statement{
    
    public ExprNode expr;
    
    public ThrowStmt(ExprNode expr){
            this.expr = expr;
    }
    
    public List<AstNode> getChildren(){
        List<AstNode> ls = new LinkedList();
        addChild(ls,expr);
        return ls;
    }

    @Override
    public String toString() {
        return "throw " + expr;
    }
}