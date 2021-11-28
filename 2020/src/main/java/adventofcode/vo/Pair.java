package adventofcode.vo;

public class Pair<A, B> {
    A left;
    B right;

    public Pair() {
    }

    public Pair(A left, B right) {
        this.left = left;
        this.right = right;
    }

    public A getLeft() {
        return left;
    }


    public B getRight() {
        return right;
    }
}
