package part1Scan.utils;

import part1Scan.Sexp;
import part1Scan.enums.PrimitiveEnum;
import part1Scan.enums.SexpTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */
public class SymTableUtil {
    //Design Pattern: Singleton
    public volatile static Map<String, Sexp> symTable;
    private SymTableUtil(){}
    public static Map<String, Sexp> getInstance(){
        if(symTable == null){
            synchronized (SymTableUtil.class){
                if(symTable == null){
                    symTable= new HashMap<String, Sexp>();
                    for(PrimitiveEnum constant: PrimitiveEnum.values()){
                        symTable.put(constant.getName(), new Sexp(SexpTypeEnum.SYMBOL.getType(), constant.getName()));
                    }
                }
            }
        }
        return symTable;
    }
}
