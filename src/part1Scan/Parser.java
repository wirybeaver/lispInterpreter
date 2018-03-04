package part1Scan;

import part1Scan.exception.IncompletenessException;
import part1Scan.exception.InvalidSexpException;
import part1Scan.exception.LispException;
import part1Scan.enums.PrimitiveEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/3.
 */
public class Parser {
    Map<String, Sexp> symTable;
    TokenHandler tokenHandler;
    Sexp root;
    public Parser(){
        symTable= new HashMap<String, Sexp>();
        for(PrimitiveEnum constant: PrimitiveEnum.values()){
            symTable.put(constant.getName(), new Sexp(2, constant.getName()));
        }
    }
    public void reset(String input){
        tokenHandler = new TokenHandler(input);
        root = null;
    }

    public Sexp startParsing() throws LispException {
        Sexp ans= input();
        if(tokenHandler.ckNextToken()!=null){
            throw new InvalidSexpException("redundant symbols starting from "+tokenHandler.ckNextToken());
        }
        return ans;
    }
    public Sexp input() throws LispException{
        String token = tokenHandler.ckNextToken();
        if(token == null){throw new IncompletenessException("absence of right parenthesis");}
        if(token.equals("(")){
            tokenHandler.skipToken();
            if(tokenHandler.ckNextToken().equals(")")){
                tokenHandler.skipToken();
                return symTable.get("NIL");
            }
            Sexp left=null, right=null;
            left = input();
            String token2 = tokenHandler.ckNextToken();
            if(token2 == null){
                throw new IncompletenessException("abesence of right parenthesis");
            }
            else if(token2.equals(".")){
                tokenHandler.skipToken();
                right = input();
                String token3 = tokenHandler.ckNextToken();
                if(token3==null || !token3.equals(")")){
                    throw new IncompletenessException("absence of right parenthesis for closing dot notation");
                }
                tokenHandler.skipToken();
            }
            else{
                right = input2();
            }
            return new Sexp(3, left, right);
        }
        else if(token.equals(")")){
            throw new InvalidSexpException("unexpected right parenthesis");
        }
        else if(token.equals(".")){
            throw new InvalidSexpException("unexpected dot");
        }
        else if(isInteger(token)){
            //sexpression type is integer
            Sexp result = new Sexp(1, Integer.parseInt(token));
            tokenHandler.skipToken();
            return result;
        }
        else{
            //sexpression type is symbol
            if(!validSymbol(token)){throw new InvalidSexpException("illegal symbol name");}
            if(!symTable.containsKey(token)){
                symTable.put(token, new Sexp(2, token));
            }
            tokenHandler.skipToken();
            return symTable.get(token);
        }
    }

    public Sexp input2() throws LispException{
        String token = tokenHandler.ckNextToken();
        if(token == null){
            throw new IncompletenessException("absence of right parenthesis");
        }
        if(token.equals(")")){
            tokenHandler.skipToken();
            return symTable.get("NIL");
        }
        else{
            Sexp left=null, right=null;
            left = input();
            right = input2();
            return new Sexp(3, left, right);
        }
    }

    public boolean isInteger(String x) {
        char[] chars = x.toCharArray();
        for(int i = 0; i<chars.length; i++){
            if(i==0 && (chars[0] == '+' || chars[0] == '-')){
                    continue;
            }
            if(chars[i] >= '0' && chars[i] <= '9'){continue;}
            return false;
        }
        if(chars.length==1 && (chars[0]=='+'||chars[0]=='-')){
            return false;
        }
        return true;
    }

    public boolean validSymbol(String x) {
        char[] chars = x.toCharArray();
        if(Character.isDigit(chars[0])){
            return false;
        }
        for(int i =0; i<chars.length; i++){
            if(Character.isLetterOrDigit(chars[i])){
                continue;
            }
            return false;
        }
        return true;
    }
}
