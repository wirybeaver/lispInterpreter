package part1Scan;

/**
 * Created by Administrator on 2018/3/3.
 */
public class Sexp {
    private int type;//1:integer 2:symbol 3: non-atom
    private int value;
    private String name;
    private Sexp left, right;

    public Sexp(int type, int value) {
        this.type = type;
        this.value = value;
    }

    public Sexp(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public Sexp(int type, Sexp left, Sexp right) {
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sexp getLeft() {
        return left;
    }

    public void setLeft(Sexp left) {
        this.left = left;
    }

    public Sexp getRight() {
        return right;
    }

    public void setRight(Sexp right) {
        this.right = right;
    }

}
