package part1Scan;

import java.util.StringTokenizer;

/**
 * Created by Administrator on 2018/3/3.
 */
public class TokenHandler {
    StringTokenizer tokenizer;
    String token;

    TokenHandler(String input){
        tokenizer = new StringTokenizer(input, "(). \n\t", true);
    }

    public String ckNextToken(){
        if(token != null ){return token;}
        if(!tokenizer.hasMoreTokens()) return null;
        token = tokenizer.nextToken();
        while(isSpace(token.charAt(0))){
            if(tokenizer.hasMoreTokens()) {
                token = tokenizer.nextToken();
            }
            else{return null;}
        }
        return token;
    }
    public void skipToken(){
        token = null;
    }

    public boolean isSpace(char cur){
        char[] spaces = new char[]{' ', '\n', '\t'};
        for(char x : spaces){
            if(cur == x){return true;}
        }
        return false;
    }

}
