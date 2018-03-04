package part1Scan.enums;

/**
 * Created by Administrator on 2018/3/4.
 */
public enum PrimitiveEnum {
    NIL("NIL"),
    T("T"),
    ;
    private String name;
    PrimitiveEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
