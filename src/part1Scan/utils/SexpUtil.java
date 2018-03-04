package part1Scan.utils;

import part1Scan.Sexp;

/**
 * Created by Administrator on 2018/3/4.
 */
public class SexpUtil {
    public static String printSexp(Sexp sexp){
        if(sexp == null){
            return "";
        }
        return printAdvice(sexp);
    }
    private static String printAdvice(Sexp sexp){
        if(sexp.getType() == 3){
            String left = printAdvice(sexp.getLeft());
            String right = printAdvice(sexp.getRight());
            return "("+left+" . "+right+")";
        }
        else if(sexp.getType() ==2){
            return sexp.getName();
        }
        else if(sexp.getType() == 1){
            return String.valueOf(sexp.getValue());
        }
        else{
            return "error";
        }
    }
}
