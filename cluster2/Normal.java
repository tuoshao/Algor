package cluster2;

import java.io.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class Normal{

    class Entry{
        String str;
        int id;
        int checksum;
        public Entry(String Str, int Id, int Checksum){
            str = Str;
            id = Id;
            checksum = Checksum;
        }
    }

    ArrayList<Entry> nodes;
    int vertices[];
    int count;
    List<ArrayList<Integer>> clusters;

    public Normal(int size) {
        nodes = new ArrayList<Entry>(size);
        vertices = new int[size];
        count = 0;
        clusters = new ArrayList<ArrayList<Integer>>(size); 
        for(int i = 0; i < size; i++) {
            clusters.add(new ArrayList<Integer> ());
            vertices[i] = -1;
        }
    }

    public boolean insertValue(String str, int checksum){
        Entry newEntry = new Entry(str, count, checksum);
        nodes.add(newEntry);
        count ++;
        return true;
    }

    public void mergeCluster(ArrayList<Integer> a1, ArrayList<Integer> a2) {
        Iterator it = a2.iterator();
        int leader = a1.get(0);
        int length = a2.size();
        for(int i=0; i<length; i++){
            vertices[a2.get(i).intValue()] = leader;
        }
        a1.addAll(a2);
        a2.clear();
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

    public int getClusters(int dist){
        int clustersize = count;
        for(int i = 0; i < count; i++) {
            Entry nodeI = nodes.get(i);
            for(int j = i+1; j < count; j++) {
                Entry nodeJ = nodes.get(j);
                if((nodeI.checksum - nodeJ.checksum) <= dist && 
                        (nodeI.checksum - nodeJ.checksum) >= -dist) {
                    if(getHammingDistance(nodeI.str, nodeJ.str) <= dist) {
                        int idL = vertices[nodeI.id];
                        int adjL = vertices[nodeJ.id];
                        if(idL != -1 && adjL!= -1) {
                            if(idL < 0) idL = nodeI.id;
                            if(adjL < 0) adjL = nodeJ.id; 
                            if(idL != adjL) {
                                if(-vertices[idL] > -vertices[adjL]) {
                                    vertices[idL] -= clusters.get(adjL).size();
                                    mergeCluster(clusters.get(idL), clusters.get(adjL));
                                }else{
                                    vertices[adjL] -= clusters.get(idL).size();
                                    mergeCluster(clusters.get(adjL), clusters.get(idL));
                                }
                                System.out.println("merge " + Integer.toString(idL) + ' ' + Integer.toString(adjL));
                                clustersize --;
                            }
                        }else if(idL == -1 && adjL == -1) {
                            idL = nodeI.id; 
                            adjL = nodeJ.id;
                            vertices[idL] -=1;
                            vertices[adjL] = idL;
                            clusters.get(idL).add(idL);
                            clusters.get(idL).add(adjL);
                            System.out.println("merge " + Integer.toString(idL) + ' ' + Integer.toString(adjL));
                            clustersize --;
                        }else if(idL == -1) {
                            if(adjL < 0) adjL = nodeJ.id; 
                            idL = nodeI.id;
                            vertices[idL] = adjL;
                            clusters.get(adjL).add(idL);
                            System.out.println("merge " + Integer.toString(idL) + ' ' + Integer.toString(adjL));
                            clustersize --;
                        }else if(adjL == -1) {
                            if(idL < 0) idL = nodeI.id;
                            adjL = nodeJ.id;
                            vertices[adjL] = idL;
                            clusters.get(idL).add(adjL);
                            System.out.println("merge " + Integer.toString(idL) + ' ' + Integer.toString(adjL));
                            clustersize --;
                        }
                    }
                }

            }

        }
        return clustersize;

    }

}
