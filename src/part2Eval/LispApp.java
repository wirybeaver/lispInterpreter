package part2Eval;

import part1Scan.Parser;
import part1Scan.Sexp;
import part1Scan.exception.EvaluationException;
import part1Scan.exception.LispException;
import part1Scan.utils.SexpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018/3/17.
 */
public class LispApp {
    public static void main(String[] args) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        String input = null;
        StringBuilder sb = new StringBuilder();
        Parser parser = new Parser();
        Evaluator evaluator = new Evaluator();

        printStartInfo();
        while((input = rd.readLine())!=null){
            String line = input.trim();
            if(line.length()==0){
                continue;
            }
            else if(line.equals("$") || line.equals("$$")){
                if(sb.length()==0){
                    if(line.equals("$")) {continue;}
                    else{break;}
                }
                parser.reset(sb.toString());
                try {
                    Sexp exp = parser.startParsing();
                    Sexp evalResult = evaluator.interpreter(exp);
                    System.out.println("> " + SexpUtil.printSexp(evalResult));
                    if(line.equals("$$")){
                        break;
                    }
                } catch (LispException e) {
                    if(e instanceof EvaluationException){
                        System.out.println("> Evaluation error: " + e.getMessage());
                    }
                    else{
                        System.out.println("> Sexp error: " + e.getMessage());
                    }
                }
                sb = new StringBuilder();
            }
            else{
                sb.append(" "+line);
            }
        }

        printEndInfo();
        while (rd.readLine()==null){
            continue;
        }
    }

    public static void printStartInfo(){
        System.out.println("*** Lisp Interpreter Project1: Scan Mixed S-Expression ***");
        System.out.println("Manual:");
        System.out.println("1. Atom only consists of uppercase letters and integers");
        System.out.println("2. Enter a s-expression followed by a in a single $ or $$");
        System.out.println("3. It is allowed to appending a single or $ or $$ to any empty expression(only spaces)");
        System.out.println("4. After input a single $$, please press any key to exit");
        System.out.println("-------------- Lisp interpreter is running -------------->");
    }

    public static void printEndInfo(){
        System.out.println("> Bye!");
        System.out.println("> Press any key to exit");
    }

}
