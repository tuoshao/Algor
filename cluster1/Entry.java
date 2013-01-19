package cluster1;

class Entry implements Comparable<Entry>{
    int id;
    int adj;
    int cost; 

    Entry(int i, int a, int c){
        id = i;
        adj = a;
        cost = c;
    }
    int getId() {
        return id;
    }

    int getAdj() {
        return adj;
    }

    int getCost() {
        return cost;
    }

    public int compareTo(Entry e) {
        if(this.cost < e.cost) return -1;
        else if(this.cost == e.cost) return 0;
        else return 1;
    }
}
