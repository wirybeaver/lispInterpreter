package part1Scan.exception;

/**
 * Created by Administrator on 2018/3/3.
 */
public class InvalidSexpException extends LispException{
    public InvalidSexpException(String msg){
        super(msg);
    }
}
