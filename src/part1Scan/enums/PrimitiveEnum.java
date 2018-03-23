package part1Scan.enums;

/**
 * Created by Administrator on 2018/3/4.
 */
public enum PrimitiveEnum {
    NIL("NIL"),
    T("T"),
    CAR("CAR"),
    CDR("CDR"),
    CONS("CONS"),
    ATOM("ATOM"),
    NULL("NULL"),
    EQ("EQ"),
    PLUS("PLUS"),
    MINUS("MINUS"),
    QUOTE("QUOTE"),
    COND("COND"),
    DEFUN("DEFUN")
    ;
    private String name;
    PrimitiveEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
