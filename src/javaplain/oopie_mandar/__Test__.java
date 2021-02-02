//import java.io.*;
//import org.antlr.runtime.*;
//import org.antlr.runtime.debug.DebugEventSocketProxy;
//
//import javaplain.*;
//
//
//public class __Test__ {
//
//    public static void main(String args[]) throws Exception {
//        JavaPlainLexer lex = new JavaPlainLexer(new ANTLRFileStream("C:\\Users\\Mayank Pidiha\\Documents\\MS CS SEM 3\\Software Maintenance\\Software Project\\oopie_mandar\\src\\javaplain\\oopie_mandar\\__Test___input.txt", "UTF8"));
//        CommonTokenStream tokens = new CommonTokenStream(lex);
//
//        JavaPlainParser g = new JavaPlainParser(tokens, 49100, null);
//        try {
//            g.compilationUnit();
//        } catch (RecognitionException e) {
//            e.printStackTrace();
//        }
//    }
//}