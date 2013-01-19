package cluster2;

import java.util.Comparator;
import java.util.PriorityQueue;

public class VPTree{
    class Entry {
        int index;
        int median;
        String str;
        public Entry(int i, int m, String s){
            index = i;
            median = m;
            str = s;
        }
    }
    String[] tree;
    int[] m;
    int size;
    Comparator<Entry> entryComparator1;
    Comparator<Entry> entryComparator2;

    public VPTree(int count) {

        tree = new String[count];
        m = new int[count];
        size = 0;
        entryComparator1 = new Comparator<Entry>() {
            public int compare(Entry e1, Entry e2) {
                int result = 0;
                if(e1.median > e2.median) result = 1;
                else if(e1.median == e2.median) result = 0;
                else if(e1.median < e2.median) result = -1;
                return result;
            }
        };
        entryComparator2 = new Comparator<Entry>() {
            public int compare(Entry e1, Entry e2) {
                int result = 0;
                if(e1.median < e2.median) result = 1;
                else if(e1.median == e2.median) result = 0;
                else if(e1.median > e2.median) result = -1;
                return result;
            }
        };
    }

    public boolean insertValue(String str) {
        if(size == tree.length) return false;
        tree[size] = str;
        size ++;
        return true;
    }

    public int getHammingDistance(String s1, String s2) {
        int l1 = s1.length();
        int l2 = s2.length();
        int min = l1 < l2 ? l1 : l2;
        int count = 0;
        for(int i = 0; i < min; i++) {
            if(s1.charAt(i) != s2.charAt(i)) {
                count++;
            }
        }
        count += (l1 < l2 ? (l2-min) : (l1-min));

        // if(count < 3){

            // System.out.println(count);
        // }
        return count;
    }
   

    public void sortTree(int beg, int end){
        if(beg == end) {
            m[beg] = 0;
            return;
        }
        int median = beg + (end-beg)/2;
        PriorityQueue<Entry> left = new PriorityQueue<Entry>((end-beg)/2 + 1, entryComparator1); 
        PriorityQueue<Entry> right = new PriorityQueue<Entry>((end-beg)/2 + 1, entryComparator2); 
        int sizeL = 0;
        int sizeR = 0;
        for(int i=beg; i <= end; i++) {
            if(i != median){
                m[i] = getHammingDistance(tree[median], tree[i]);
                Entry newEntry = new Entry(i, m[i], tree[i]);
                if(sizeL >= sizeR){
                    right.add(newEntry);
                    sizeR ++;
                }else{
                    left.add(newEntry);
                    sizeL ++;
                }
            }
        }
        Entry e;
        if(sizeL > sizeR){
            e = left.poll();
            right.add(e);
        }
        int i = 0;
        while((e = left.poll()) != null){
            tree[beg+i] = e.str;
            i++;
        }
        e = right.poll();
        m[beg+i] = e.median;
        tree[beg+i+1] = e.str;
        i+=2;
        while((e = right.poll()) != null){
            tree[beg+i] = e.str;
            i++;
        }
        if(beg <= median-1){
            sortTree(beg, median-1);
        }
        if(end >= median+1){
            sortTree(median+1, end);
        }

    }
    public int getAdjNum(int beg, int end, String str, int dist){
        int count = 0;
        for(int i = beg; i < end; i++) {
            int distToM = getHammingDistance(str, tree[i]);
            if(distToM <= dist){
                System.out.println(i);
                System.out.println(m[i]);
                System.out.println(distToM);
                count +=1;
            }
        }
        return count;
    }

    public int searchAdjNum(int beg, int end, String str, int dist) {
        int median = beg + (end-beg)/2;
        System.out.println(str);
        System.out.println(tree[median]);
        int distToM = getHammingDistance(str, tree[median]);
        System.out.println(beg);
        System.out.println(end);
        // System.out.println(median);
        System.out.println(m[median]);
        System.out.println(distToM);
        if(beg == end && distToM > dist) return 0;
        else return 1;

        if(m[median] < 2*dist) {
        //    System.out.println(str);
            
            return getAdjNum(beg, end, str, dist);
        }else{
                
            int sum = 0;
            if(m[median] < (distToM - dist)){
                // System.out.println(1);
                if(end >= median+1) sum+= searchAdjNum(median+1, end, str, dist);
                return sum;
            }
            else if(m[median] > (distToM + dist)){
                // System.out.println(2);
                if(beg <= median-1) sum+= searchAdjNum(beg, median-1, str, dist);
                return sum;
            }
            else {
                // System.out.println(3);
                if(end >= median+1) sum+= searchAdjNum(median+1, end, str, dist);
                if(beg <= median-1) sum+= searchAdjNum(beg, median-1, str, dist);
                return sum;
            }
        }
    }

    public int searchAdj(int dist) {
        int sum = 0;
        for(int i = 0; i < tree.length; i++) {
            int tmp = searchAdjNum(0, size-1, tree[i], dist);
            if(tmp > 0){
                System.out.println();
                System.out.println(tree[i]);
                System.out.println(tmp);
            sum += (tmp-1);
            }


        }
        return (sum)/2;
    }

    public void createTree() {
        sortTree(0, size-1);

    }
}
