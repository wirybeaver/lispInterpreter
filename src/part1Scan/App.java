package part1Scan;

import part1Scan.exception.InvalidSexpException;
import part1Scan.exception.LispException;
import part1Scan.enums.RunStateEnum;
import part1Scan.utils.SexpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018/3/3.
 */
public class App {
    static StringBuilder sb;
    static Parser parser = new Parser();
    static RunStateEnum runStateEnum;
    static Sexp root;

    public static void reset(){
        runStateEnum = RunStateEnum.RESET;
        sb = new StringBuilder();
        root = null;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        reset();
        System.out.println("*** Lisp Interpreter Project1: Scan Mixed S-Expression ***");
        System.out.println("Manual:");
        System.out.println("1. Atom only consists of uppercase letters and integers");
        System.out.println("2. Enter a s-expression followed by a in a single $ or $$");
        System.out.println("3. It is allowed to appending a single or $ or $$ to any empty expression(only spaces)");
        System.out.println("4. After input a single $$, please press any key to exit");
        System.out.println("5. When it catches an error, the interpreter would ignore any input until meeting a single $ or $$");
        System.out.println("-------------- Lisp interpreter is running -------------->");
        while((input = rd.readLine())!=null){
            String line = input.trim();
            // in order to solve the corner case that $ appended to a sexp is still correct;
//            int x = line.indexOf("$");
//            int y = line.indexOf("$$");
//            if(x>-1){
//                if((y>-1 && line.length()>y+2) || line.length()>x+1){
//                    System.out.println("error: unexpected dollor");
//                }
//                else{
//                    sb.append(line.substring(0,x));
//                    parser.reset(sb.toString());
//                    try{
//
//                    }
//                    catch (){
//
//                    }
//                }
//                sb = new StringBuilder();
//            }
            if(line.length()==0){continue;}
            if(line.equals("$") || line.equals("$$")){
                if(runStateEnum.getStat() != RunStateEnum.RESET.getStat()){
                    if(runStateEnum.getStat()==RunStateEnum.SUCCEED.getStat()){
                        System.out.println("> "+ SexpUtil.printSexp(root));
                    }
                    else if(runStateEnum.getStat()==RunStateEnum.WAIT.getStat()){
                        System.out.println("> error: "+runStateEnum.getMsg());
                    }
                    // else runstate is ERROR, indicating the error message has been printed before, nothing to do
                    reset();
                }
                if(line.equals("$$")){break;}
            }
            else{
                if(runStateEnum.getStat()==RunStateEnum.SUCCEED.getStat()){
                    System.out.println("> error: redundant command line input: "+line);
                    reset();
                }
                else if(runStateEnum.getStat() == RunStateEnum.RESET.getStat()
                        || runStateEnum.getStat() == RunStateEnum.WAIT.getStat()){
                    sb.append(" ");
                    sb.append(line);
                    parser.reset(sb.toString());
                    try{
                        root = parser.startParsing();
                        runStateEnum = RunStateEnum.SUCCEED;
                    }
                    catch (LispException e){
                        if(e instanceof InvalidSexpException) {
                            System.out.println("> error: "+e.getMessage());
                            runStateEnum = RunStateEnum.Error;
                        }
                        else{// incompletenessException
                            runStateEnum = RunStateEnum.WAIT;
                            runStateEnum.setMsg(e.getMessage());
                        }
                    }
                }
            }
        }
        System.out.println("> Bye!");
        System.out.println("> Press any key to exit");
        while(rd.readLine()==null){
            ;
        }
    }
}
