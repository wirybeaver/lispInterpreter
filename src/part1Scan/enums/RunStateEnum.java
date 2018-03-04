package part1Scan.enums;

/**
 * Created by Administrator on 2018/3/3.
 */
public enum RunStateEnum {
    RESET(0, "reset"),
    SUCCEED(1, "succeed"),
    WAIT(2, "more input needed")
    ;
    private int stat;
    private String msg;
    RunStateEnum(int stat, String msg){
        this.stat = stat;
        this.msg = msg;
    }

    public int getStat() {
        return stat;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
