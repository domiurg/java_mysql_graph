package domiurg;


public class Pair<L, R> {
    private final L id;
    private final R val;

    public Pair(L id, R val){
        this.id = id;
        this.val = val;
    }

    public L getId() { return id; }
    public R getVal() { return val; }

    @Override
    public int hashCode() { return id.hashCode() ^ val.hashCode(); }

    @Override
    public boolean equals (Object o){
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.id.equals(pairo.getId()) &&
                this.val.equals(pairo.getVal());
    }
}
