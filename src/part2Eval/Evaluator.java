package part2Eval;

import part1Scan.Sexp;
import part1Scan.enums.SexpTypeEnum;
import part1Scan.exception.EvaluationException;
import part1Scan.utils.SexpUtil;
import part1Scan.utils.SymTableUtil;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */

public class Evaluator {
    private Sexp dlist;
    private Map<String, Sexp> symTable = SymTableUtil.getInstance();
    Evaluator(){
        dlist = symTable.get("NIL");
    }

    public Sexp getDlist() {
        return dlist;
    }

    public Sexp interpreter(Sexp x) throws EvaluationException {
        return eval(x, symTable.get("NIL"));
    }

    boolean atom(Sexp x) throws EvaluationException {
        if(x==null){throw new EvaluationException("wrong argument type --  null pointer");}
        if(x.getType() == SexpTypeEnum.NONATOM.getType()){
            return false;
        }
        else return true;
    }

    boolean lispNULL(Sexp x) throws EvaluationException {
        if(x==null){throw new EvaluationException("wrong argument type --  null pointer");}
        return eq(x, symTable.get("NIL"));
    }

    Sexp car(Sexp x) throws EvaluationException {
        if(x==null){throw new EvaluationException("wrong argument type --  null pointer");}
        if(atom(x)){
            throw new EvaluationException("wrong argument type -- CAR cannot be applied on atom");
        }
        return x.getLeft();
    }

    Sexp cdr(Sexp x) throws EvaluationException {
        if(x==null){throw new EvaluationException("wrong argument type --  null pointer");}
        if(atom(x)){
            throw new EvaluationException("wrong argument type -- CDR cannot be applied on atom" );
        }
        return x.getRight();
    }

    Sexp cons(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type --  null pointer");
        }
        return new Sexp(SexpTypeEnum.NONATOM.getType(), x, y);
    }

    boolean eq(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        int xType = x.getType();
        int yType = x.getType();
        if(xType == SexpTypeEnum.NUMERIC.getType()
                && yType == SexpTypeEnum.NUMERIC.getType()){
            return x.getValue() == y.getValue();
        }
        if(xType == SexpTypeEnum.SYMBOL.getType()
                && yType == SexpTypeEnum.SYMBOL.getType()){
            return x.getName().equals(y.getName());
        }
        return false;
    }

    Sexp plus(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        if(x.getType()!=SexpTypeEnum.NUMERIC.getType()
                || y.getType()!=SexpTypeEnum.NUMERIC.getType()){
            throw new EvaluationException("wrong argument type on PLUS");
        }
        int type = SexpTypeEnum.NUMERIC.getType();
        return new Sexp(type, x.getValue()+y.getValue());
    }

    Sexp minus(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        if(x.getType()!=SexpTypeEnum.NUMERIC.getType()
                || y.getType()!=SexpTypeEnum.NUMERIC.getType()){
            throw new EvaluationException("wrong argument type on MINUS");
        }
        int type = SexpTypeEnum.NUMERIC.getType();
        return new Sexp(type, x.getValue()-y.getValue());
    }

    Sexp times(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        if(x.getType()!=SexpTypeEnum.NUMERIC.getType()
                || y.getType()!=SexpTypeEnum.NUMERIC.getType()){
            throw new EvaluationException("wrong argument type on TIMES");
        }
        int type = SexpTypeEnum.NUMERIC.getType();
        return new Sexp(type, x.getValue()*y.getValue());
    }

    Sexp quotient(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        if(x.getType()!=SexpTypeEnum.NUMERIC.getType()
                || y.getType()!=SexpTypeEnum.NUMERIC.getType()){
            throw new EvaluationException("wrong argument type on QUOTEINT");
        }
        int type = SexpTypeEnum.NUMERIC.getType();
        return new Sexp(type, x.getValue()/y.getValue());
    }

    Sexp remainder(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        if(x.getType()!=SexpTypeEnum.NUMERIC.getType()
                || y.getType()!=SexpTypeEnum.NUMERIC.getType()){
            throw new EvaluationException("wrong argument type on REMAINDER");
        }
        int type = SexpTypeEnum.NUMERIC.getType();
        return new Sexp(type, x.getValue()%y.getValue());
    }

    Sexp less(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        if(x.getType()!=SexpTypeEnum.NUMERIC.getType()
                || y.getType()!=SexpTypeEnum.NUMERIC.getType()){
            throw new EvaluationException("wrong argument type on LESS");
        }
        return (x.getValue()<y.getValue())? symTable.get("T"): symTable.get("NIL");
    }

    Sexp greater(Sexp x, Sexp y) throws EvaluationException {
        if(x == null || y==null){
            throw new EvaluationException("wrong argument type -- null pointer");
        }
        if(x.getType()!=SexpTypeEnum.NUMERIC.getType()
                || y.getType()!=SexpTypeEnum.NUMERIC.getType()){
            throw new EvaluationException("wrong argument type on GREATER");
        }
        return (x.getValue()>y.getValue())? symTable.get("T"): symTable.get("NIL");
    }


    Sexp defun(Sexp x) throws EvaluationException {
        if(x==null){throw new EvaluationException("wrong argument type --  null pointer");}
        // x= (DEFUN SILLY (A B) (PLUS A B))
        Sexp f = cdr(x);//(SILLY (A B) (PLUS A B))
        Sexp dlistItem = cons(car(f), cons(car(cdr(f)), car(cdr(cdr(f)))));
        dlist = cons(dlistItem, dlist);
        Sexp funName = car(cdr(x));
        return symTable.get(funName.getName());
    }

    boolean lispInt(Sexp x) throws EvaluationException {
        if(x==null){throw new EvaluationException("wrong argument type --  null pointer");}
        return x.getType() == SexpTypeEnum.NUMERIC.getType();
    }

    boolean lispIn(Sexp atomSymbol, Sexp alist) throws EvaluationException {
        if(eq(alist, symTable.get("NIL"))){return false;}
        Sexp curPair = car(alist);
        if(eq(atomSymbol, car(curPair))){
            return true;
        }
        else{
            return lispIn(atomSymbol, cdr(alist));
        }
    }

    Sexp getVal(Sexp atomSymbol, Sexp list) throws EvaluationException{
        // pair(a.b)
        if(eq(list, symTable.get("NIL"))){
            throw new EvaluationException("undefine function name "
                    +(atomSymbol.getType() == SexpTypeEnum.NUMERIC.getType()? atomSymbol.getValue(): atomSymbol.getName())
                    + " in dlist");}
        Sexp curPair = car(list);
        if(eq(atomSymbol, car(curPair))){
            return cdr(curPair);
        }
        else{
            return getVal(atomSymbol, cdr(list));
        }
    }

    Sexp eval(Sexp x, Sexp alist) throws EvaluationException {
        if(atom(x)){
            if(lispInt(x)){
                return x;
            }
            else if(eq(x, symTable.get("T"))){
                return symTable.get("T");
            }
            else if(eq(x, symTable.get("NIL"))){
                return symTable.get("NIL");
            }
            else if(lispIn(x, alist)){
                return getVal(x, alist);
            }
            else {
                throw new EvaluationException("unbound variable " + x.getName() +" in alist");
            }
        }
        else if(atom(car(x))){
            if(eq(car(x), symTable.get("QUOTE"))){
                checkParamNum(cdr(x), 1);
                return car(cdr(x));
            }
            else if(eq(car(x), symTable.get("COND"))){
                return evcon(cdr(x), alist);
            }

            else if(eq(car(x), symTable.get("DEFUN"))){
                return defun(x);
            }
            else{
                Sexp fname = car(x);
                Sexp argValueList = evlis(cdr(x), alist);
                return apply(fname, argValueList, alist);
            }
        }
        else{
            throw new EvaluationException("first param must be nonatom");
        }
    }

    Sexp apply(Sexp f, Sexp x, Sexp alist) throws EvaluationException {
        if(atom(f)){
            if(eq(f, symTable.get("CAR"))){
                checkParamNum(x, 1);
                if(atom(car(x))){
                    throw new EvaluationException("CAR param must be a non-atom s-exp");
                }
                return car(car(x));
            }
            else if(eq(f, symTable.get("CDR"))){
                checkParamNum(x, 1);
                if(atom(car(x))){
                    throw new EvaluationException("CDR param must be a non-atom s-exp");
                }
                return cdr(car(x));
            }
            else if(eq(f, symTable.get("CONS"))){
                checkParamNum(x, 2);
                return cons(car(x), car(cdr(x)));
            }
            else if(eq(f, symTable.get("ATOM"))){
                checkParamNum(x, 1);
                return atom(car(x))? symTable.get("T"): symTable.get("NIL");
            }
            else if(eq(f, symTable.get("NULL"))){
                checkParamNum(x, 1);
                return lispNULL(car(x))? symTable.get("T"): symTable.get("NIL");
            }
            else if(eq(f, symTable.get("INT"))){
                checkParamNum(x, 1);
                return lispInt(car(x))? symTable.get("T"): symTable.get("NIL");
            }
            else if(eq(f, symTable.get("EQ"))){
                checkParamNum(x, 2);
                return eq(car(x), car(cdr(x)))? symTable.get("T"): symTable.get("NIL");
            }
            else if(eq(f, symTable.get("PLUS"))){
                checkParamNum(x, 2);
                return plus(car(x), car(cdr(x)));
            }
            else if(eq(f, symTable.get("MINUS"))){
                checkParamNum(x, 2);
                return minus(car(x), car(cdr(x)));
            }
            else if(eq(f, symTable.get("TIMES"))){
                checkParamNum(x, 2);
                return times(car(x), car(cdr(x)));
            }
            else if(eq(f, symTable.get("QUOTIENT"))){
                checkParamNum(x, 2);
                return quotient(car(x), car(cdr(x)));
            }
            else if(eq(f, symTable.get("REMAINDER"))){
                checkParamNum(x, 2);
                return remainder(car(x), car(cdr(x)));
            }
            else if(eq(f, symTable.get("LESS"))){
                checkParamNum(x, 2);
                return less(car(x), car(cdr(x)));
            }
            else if(eq(f, symTable.get("GREATER"))){
                checkParamNum(x, 2);
                return greater(car(x), car(cdr(x)));
            }
            else {
                Sexp fParamAndfBody = getVal(f, this.dlist);
                Sexp newAlist = addPairs(car(fParamAndfBody), x, alist);
                return eval(cdr(fParamAndfBody), newAlist);
            }
        }
        else{
            throw new EvaluationException("error: function name is not an atom");
        }
    }

    Sexp addPairs(Sexp paramList, Sexp argValueList, Sexp alist) throws EvaluationException {
        if(paramList == null){
            throw new EvaluationException("paramList definition" + SexpUtil.printSexp(paramList) +"is not a list notation");
        }
        if(lispNULL(paramList)){
            if(lispNULL(argValueList)){
                return alist;
            }
            else{
                throw new EvaluationException("# too much arguments");
            }
        }
        else if(lispNULL(argValueList)){
            throw new EvaluationException("# too few arguments");
        }
        else{
            Sexp param = car(paramList);
            Sexp arg = car(argValueList);
            Sexp alistItem = cons(param, arg);
            return addPairs(cdr(paramList), cdr(argValueList), cons(alistItem, alist));
        }
    }

    Sexp evcon(Sexp be, Sexp alist) throws EvaluationException {
        //
        if(be==null || lispNULL(be)){throw new EvaluationException("all bool expression are evaluating to be empty!!");}
        if(eq(eval(car(car(be)), alist), symTable.get("T"))){
            checkParamNum(car(be), 2);
            return eval( car(cdr(car(be))), alist);
        }
        return evcon(cdr(be), alist);
    }

    Sexp evlis(Sexp argList, Sexp alist) throws EvaluationException {
        if(atom(argList)){
            if(lispNULL(argList)){
                return symTable.get("NIL");
            }
            else throw new EvaluationException("argument input of function" + "must be in list notation");
        }

        Sexp temp1 = eval(car(argList), alist);
        Sexp temp2 = evlis(cdr(argList), alist);
        return cons(temp1, temp2);
    }

    void checkParamNum(Sexp parList, int definedParamNum) throws EvaluationException{
        int ans = 0;
        while(!lispNULL(parList)){
            ans++;
            parList = cdr(parList);
        }
        if(ans != definedParamNum){
            throw new EvaluationException("# param. unmatched");
        }
    }
}


