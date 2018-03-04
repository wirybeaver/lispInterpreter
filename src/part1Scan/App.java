package part1Scan;

import part1Scan.exception.InvalidSexpException;
import part1Scan.exception.LispException;
import part1Scan.enums.RunStateEnum;
import part1Scan.utils.SexpUtil;

import java.util.Scanner;

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

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        reset();
        while(sc.hasNextLine()){
            String line = sc.nextLine().trim();
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
            if(line.contains("$")){
                if(line.equals("$") || line.equals("$$")){
                    if(runStateEnum.getStat()==RunStateEnum.SUCCEED.getStat()){
                        System.out.println("> "+ SexpUtil.printSexp(root));
                    }
                    else if(runStateEnum.getStat()==RunStateEnum.RESET.getStat()){
                        if(line.equals("$")){System.out.println("> warn: empty expression");continue;}
                        else {break;}
                    }
                    else{
                        System.out.println("> error: "+runStateEnum.getMsg());
                    }
                }
                else{
                    System.out.println("> error: unexpected dollar");
                }
                reset();
            }
            else{
                if(runStateEnum.getStat()==RunStateEnum.SUCCEED.getStat()){
                    System.out.println("> error: redundant command line input: "+line);
                    reset();
                }
                else{
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
                            reset();
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
        while(!sc.hasNextLine()){
            ;
        }
    }
}
