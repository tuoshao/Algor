package greedy2;

import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Collections;

public class Graph{
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

    List<ArrayList<Entry>> vertices;
    ArrayList<Entry> finalTree;
    int size;

    Graph(int size) {
        vertices =  new ArrayList<ArrayList<Entry>> (size); 
        for(int i = 0; i < size; i++) {
            vertices.add(new ArrayList<Entry> ());
        }
        finalTree = new ArrayList<Entry> (size);
        this.size = size;
    }

    public boolean insertEntry(int id, int adj, int cost) {
        if(id > size || adj > size) return false;
        ArrayList<Entry> vertex = vertices.get(id-1);
        Iterator it = vertex.iterator();
        while(it.hasNext()) {
            Entry e = (Entry)it.next();
            if(e.getAdj() == adj) return false;
        }
        Entry newEntry = new Entry(id, adj, cost);
        vertex.add(newEntry);
        return true;
    }
    
    public boolean primTree() {
        finalTree.clear();

        PriorityQueue<Entry> heap = new PriorityQueue<Entry> (size);

        ArrayList<Entry> vertex = vertices.get(0);
        ArrayList<Entry> map = new ArrayList<Entry> (size);
        for(int i = 0; i < size; i++) {
            finalTree.add(null);
            map.add(null);
        }
        Iterator it = vertex.iterator();
        while(it.hasNext()) {
            Entry tmp = (Entry)it.next();
            Entry newEntry = new Entry(tmp.getAdj(), tmp.getId(), tmp.getCost());
            heap.add(newEntry);
            //map.add(newEntry.getId() - 1, newEntry);
            map.set(newEntry.getId() - 1, newEntry);
        }
        
        Entry e; 

        while((e = heap.poll()) != null) {
            finalTree.set(e.getId()-1, e);
            System.out.println("pull");
            System.out.println(Integer.toString(e.getId()) + ' ' + Integer.toString(e.getAdj()) + ' ' + Integer.toString(e.getCost()));
            System.out.println();

            vertex = vertices.get(e.getId()-1);
            it = vertex.iterator();
            while(it.hasNext()) {
                Entry tmp = (Entry)it.next();
                //System.out.println(Integer.toString(tmp.getId()) + ' ' + Integer.toString(tmp.getAdj()) + ' ' + Integer.toString(tmp.getCost()));
                Entry old = map.get(tmp.getAdj()-1); 
                if(old == null && tmp.getAdj() != 1) {
                    Entry newEntry = new Entry(tmp.getAdj(), tmp.getId(), tmp.getCost());
                    //System.out.println("change map" + Integer.toString(newEntry.getId()) + ' ' + Integer.toString(newEntry.getAdj()) + ' ' + Integer.toString(newEntry.getCost()));
                    //map.add(newEntry.getId() - 1, newEntry);
                    heap.add(newEntry);
                    map.set(newEntry.getId() - 1, newEntry);
                }else{
                    if(!(finalTree.get(tmp.getId() - 1) != null && (finalTree.get(tmp.getAdj() - 1) != null || tmp.getAdj() == 1))) {
                        if(old.compareTo(tmp) > 0) {
                            //map.remove(old.getId() - 1);
                            Entry newEntry = new Entry(tmp.getAdj(), tmp.getId(), tmp.getCost());
                            //System.out.println("change map" + Integer.toString(newEntry.getId()) + ' ' + Integer.toString(newEntry.getAdj()) + ' ' + Integer.toString(newEntry.getCost()));
                            heap.remove(old);
                            heap.add(newEntry);
                            //map.add(newEntry.getId() - 1, newEntry);
                            map.set(newEntry.getId() - 1, newEntry);
                        }
                    }
                }
            }
            
        }
        return true;
    }
    public int getTreeLength() {
        Iterator it = finalTree.iterator();
        int sum = 0;
        int i = 0;
        while(it.hasNext()) {
            Entry tmp = (Entry)it.next();
            if(tmp != null){
                //System.out.println(Integer.toString(tmp.getId()) + ' ' + Integer.toString(tmp.getAdj()) + ' ' + Integer.toString(tmp.getCost()));
                sum += tmp.getCost();
                i++;
            }
        }
        System.out.println(i);
        return sum;

    }


}
