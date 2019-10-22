package kalang.compiler.function;

import kalang.compiler.ast.*;
import kalang.compiler.core.MethodDescriptor;
import kalang.compiler.core.Type;

import javax.annotation.Nonnull;
import java.util.*;

/**
 *
 * @author Kason Yang
 */
public class LambdaExpr extends ExprNode{

    private Type type;

    private MethodDescriptor interfaceMethod;

    private MethodNode implementationMethod;

    private List<ExprNode> captureArguments = new LinkedList<>();

    private final Map<String, VarObject> accessibleVarObjects = new HashMap();

    public LambdaExpr(Type type) {
        this.type = type;
    }

    public void putAccessibleVarObject(String name, VarObject var) {
        accessibleVarObjects.put(name, var);
    }

    public Map<String, VarObject> getAccessibleVarObjects() {
        return new HashMap(accessibleVarObjects);
    }

    @Override
    public List<AstNode> getChildren() {
        List<AstNode> list = new LinkedList(captureArguments);
        return list;
    }

    public MethodDescriptor getInterfaceMethod() {
        return interfaceMethod;
    }

    public void setInterfaceMethod(MethodDescriptor interfaceMethod) {
        this.interfaceMethod = interfaceMethod;
    }

    public MethodNode getImplementationMethod() {
        return implementationMethod;
    }

    public void setImplementationMethod(MethodNode implementationMethod) {
        this.implementationMethod = implementationMethod;
    }

    public List<ExprNode> getCaptureArguments() {
        return captureArguments;
    }

    public void setCaptureArguments(List<ExprNode> captureArguments) {
        this.captureArguments = captureArguments;
    }

    @Nonnull
    @Override
    public Type getType() {
        return type;
    }
}
