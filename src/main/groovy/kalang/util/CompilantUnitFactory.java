
package kalang.util;
import java.io.*;
import java.nio.*;
import java.net.*;
import java.util.*;
import kalang.antlr.KalangLexer;
import kalang.antlr.KalangParser;
import kalang.compiler.CompilantUnit;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
/**
 *
 * @author Kason Yang <i@kasonyang.com>
 */
public class CompilantUnitFactory {
    
    public static CompilantUnit createCompilantUnit(String clsName,KalangLexer lexer){
        return CompilantUnitFactory.createCompilantUnit(
                clsName
                ,TokenStreamFactory.createTokenStream(lexer)
        );
    }
    
    public static CompilantUnit createCompilantUnit(String clsName,String source){
        return CompilantUnitFactory.createCompilantUnit(
                clsName
                ,TokenStreamFactory.createTokenStream(source)
        );
    }
        
    public static CompilantUnit createCompilantUnit(String clsName,TokenStream tokens){
        KalangParser p = new KalangParser(tokens);
        CompilantUnit sp = new CompilantUnit(clsName, p);
        p.setErrorHandler(new DefaultErrorStrategy() {

            @Override
            public void reportError(Parser recognizer, RecognitionException e) {
                String msg = AntlrErrorString.exceptionString(recognizer, e);
//                RuleContext ctx = e.getCtx();
//                while(ctx!=null && !(ctx instanceof ParserRuleContext)){
//                    ctx = ctx.getParent();
//                }
                sp.reportError(msg, e.getOffendingToken());
//                if(ctx!=null){
//                    sp.reportError(msg, e.getOffendingToken(), (ParserRuleContext) ctx);
//                }else{
//                    sp.reportError(msg, e.getOffendingToken(),null);
//                }
            }
        });
        return sp;
    }

}
