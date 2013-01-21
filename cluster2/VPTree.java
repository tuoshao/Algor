package cluster2;

import java.util.Comparator;
import java.util.PriorityQueue;

public class VPTree{
    class Entry {
        int median;
        String str;
        public Entry(int m, String s){
            median = m;
            str = s;
        }
    }
    String[] tree;
    int[] m;
    boolean[] map;
    int size;
    Comparator<Entry> entryComparator1;
    Comparator<Entry> entryComparator2;

    public VPTree(int count) {

        tree = new String[count];
        m = new int[count];
        map = new boolean[count];
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
        PriorityQueue<Entry> left = new PriorityQueue<Entry>((end-beg)/2 + 1, entryComparator2); 
        PriorityQueue<Entry> right = new PriorityQueue<Entry>((end-beg)/2 + 1, entryComparator1); 
        int sizeL = 0;
        int sizeR = 0;
        Entry e;
        for(int i=beg; i <= end; i++) {
            if(i != median){
                m[i] = getHammingDistance(tree[median], tree[i]);
                Entry newEntry = new Entry(m[i], tree[i]);
                if(sizeL == 0 || entryComparator1.compare(left.peek(), newEntry) < 0){
                    left.add(newEntry);
                    // System.out.println("L:" + newEntry.str + ' ' + Integer.toString(newEntry.median));
                    sizeL ++;
                } else {
                    right.add(newEntry);
                    // System.out.println("R:" + newEntry.str + ' ' + Integer.toString(newEntry.median));
                    sizeR ++;
                }
                if(sizeL > sizeR + 1){
                    e = left.poll();
                    right.add(e);
                    // System.out.println("LtoR:" + e.str + ' ' + Integer.toString(e.median));
                    sizeL --; 
                    sizeR ++;
                }else if(sizeL < sizeR - 1){
                    e = right.poll();
                    left.add(e);
                    // System.out.println("RtoL:" + e.str + ' ' + Integer.toString(e.median));
                    sizeL ++;
                    sizeR --;
                }
                if(sizeL != 0 && sizeR != 0) {
                    while(entryComparator1.compare(left.peek(), right.peek()) > 0) {
                        e = left.poll();
                        right.add(e);
                        e = right.poll();
                        left.add(e);
                    }

                }
            }
        }
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
    public int getAdjNum(){
        int sum = 0;
        for(int i = 0; i < size; i++) {
            if(map[i] == true) sum++;
        }

        System.out.println(sum);

        return sum;
    }

    public int searchAdjNum(int beg, int end, int index, int dist) {
        String str = tree[index];
        int median = beg + (end-beg)/2;
        int distToM = getHammingDistance(str, tree[median]);
        // System.out.println(str);
        // System.out.println(tree[median]);
        // System.out.println(beg);
        // System.out.println(end);
        // // System.out.println(median);
        // System.out.println(m[median]);
        // System.out.println(distToM);
        // System.out.println(map[median]);
        int sum = -1;
        if(beg == end){
            if(distToM > dist) {
                // not found
                return -1;
            }else{
        // System.out.println(str);
        // System.out.println(tree[median]);
        // System.out.println(beg);
        // System.out.println(end);
        // // System.out.println(median);
        // System.out.println(m[median]);
        // System.out.println(distToM);
        // System.out.println(map[beg]);
                if(median != index){
                    if(map[median] == false){
                        map[median] = true;
                        System.out.println(1);
                        System.out.println(tree[median] + ' ' + Integer.toString(median) + "true");
                        return sum = 1;
                    }else{
                        return sum = 0;
                    }
                }else{
                    map[median] = true;
                    System.out.println(2);
                    System.out.println(tree[median] + ' ' + Integer.toString(median) + "true");
                    return sum = 1;
                }
            }
        }

        // if(m[median] < 2*dist) {
        // //    System.out.println(str);
            
            // return getAdjNum(beg, end, str, dist);
        // }else{

        int result;
                
            if(distToM <= dist) {
                if(map[median] == false){
                    map[median] = true;
                    map[index] = true;
                    System.out.println(3);
                    System.out.println(tree[median] + ' ' + Integer.toString(median) + "true");
                    System.out.println(tree[index] + ' ' + Integer.toString(index) + "true");
                    sum = 1;
                }else{
                    map[index] = true;
                    System.out.println(4);
                    System.out.println(tree[index] + ' ' + Integer.toString(index) + "true");
                    return sum = 0;
                }
            }
            if(m[median] < (distToM - dist)){
                if(end >= median+1) {
                    result =  searchAdjNum(median+1, end, index, dist);
                    sum &= (result >= 0? result:1);
                }
            }
            else if(m[median] > (distToM + dist)){
                if(beg <= median-1){
                    result =  searchAdjNum(beg, median-1, index, dist);
                    sum &= (result >= 0? result:1);
                }
            }
            else {
                if(beg <= median-1){
                    result =  searchAdjNum(beg, median-1, index, dist);
                    sum &= (result >= 0? result:1);
                }
                if(end >= median+1) {
                    result =  searchAdjNum(median+1, end, index, dist);
                    sum &= (result >= 0? result:1);
                }
            }
            if(str.equals("000101")){
                // System.out.println(sum);
            }
            return sum;
        // }
    }

    public int searchAdj(int dist) {
        int sum = 0;
        for(int i = 0; i < tree.length; i++) {
            if(map[i] == false){
                System.out.println();
                System.out.println(tree[i]);
                int tmp = searchAdjNum(0, size-1, i, dist);
                if(tmp > 0){
                    sum += tmp;
                }
                System.out.println(tmp);

            }
        }
        System.out.println(sum);
        return sum;
    }


    public void createTree() {
        sortTree(0, size-1);
    }
    public void printTree() {
        for(int i = 0; i < size; i++){
            System.out.println(tree[i] + ' ' + Integer.toString(m[i]));
        }

    }
}
