package part1Scan.enums;

/**
 * Created by Administrator on 2018/3/4.
 */
public enum SexpTypeEnum {
    NUMERIC(1, "numeric"),
    SYMBOL(2, "symbol"),
    NONATOM(3, "non atom")
    ;
    private int type;
    private String msg;

    SexpTypeEnum(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
