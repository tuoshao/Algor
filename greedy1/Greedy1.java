package greedy1;

public class Greedy1 implements Comparable<Greedy1>{
    private int weight;
    private int length;

    public Greedy1(int w, int l){
        weight = w;
        length = l;
    }

    public int compareTo(Greedy1 g){
        if((float)this.weight/(float)this.length < (float)g.weight/(float)g.length) return -1;
        // if((this.weight-this.length) < (g.weight-g.length)) return -1;
        else if((float)this.weight/(float)this.length == (float)g.weight/(float)g.length) {
        // else if((this.weight-this.length) == (g.weight-g.length)) {
            if(this.weight < g.weight) return -1;
            else if(this.weight > g.weight) return 1;
            else return 0;
        }else return 1;
    }
    
    public int getWeight() {
        return weight;
    }
    public int getLength() {
        return length;
    }
}

