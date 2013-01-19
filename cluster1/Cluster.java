package cluster1;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Cluster{

    static int vertices[]; 
    static PriorityQueue<Entry> heap;
    static List<ArrayList<Integer>> clusters;

    public static void mergeCluster(ArrayList<Integer> a1, ArrayList<Integer> a2) {
        Iterator it = a2.iterator();
        int leader = a1.get(0);
        int length = a2.size();
        for(int i=0; i<length; i++){
            vertices[a2.get(i).intValue()] = leader;
        }
        a1.addAll(a2);
        a2.clear();

    }

    public static void main(String[] args) {
        InputStream fis;
        BufferedReader br;
        String line;

        try{
            fis = new FileInputStream("clustering1.txt");
            // fis = new FileInputStream("test");
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

            try{
                line = br.readLine();
                int index;
                int size = Integer.parseInt(line);
                int clustersize = size;
                int verticesize = 0;

                heap = new PriorityQueue<Entry>();
                clusters = new ArrayList<ArrayList<Integer>>(size); 
                vertices = new int[size];
                for(int i = 0; i < size; i++) {
                    clusters.add(new ArrayList<Integer> ());
                    vertices[i] = -1;
                }

                while((line = br.readLine()) != null) {
                    index = line.indexOf(' ');
                    String str1 = line.substring(0, index);
                    int i = Integer.parseInt(str1);
                    String str2 = line.substring(index+1, line.length());
                    index = str2.indexOf(' ');
                    String str3 = str2.substring(0, index);
                    int a = Integer.parseInt(str3);
                    String str4 = str2.substring(index+1, str2.length());
                    int c = Integer.parseInt(str4);
                    // System.out.println(Integer.toString(i) + ' ' + Integer.toString(a) + ' ' + Integer.toString(c));
                    Entry entry = new Entry(i-1, a-1, c);
                    heap.add(entry);
                }

                Entry e;
                while((e = heap.poll()) != null && (clustersize > 4 )) {
                    int idL = vertices[e.getId()];
                    int adjL = vertices[e.getAdj()];
                    if(idL != -1 && adjL!= -1) {
                        if(idL < 0) idL = e.getId();
                        if(adjL < 0) adjL = e.getAdj();
                        if(idL != adjL) {
                            if(-vertices[idL] > -vertices[adjL]) {
                                vertices[idL] -= clusters.get(adjL).size();
                                mergeCluster(clusters.get(idL), clusters.get(adjL));
                            }else{
                                vertices[adjL] -= clusters.get(idL).size();
                                mergeCluster(clusters.get(adjL), clusters.get(idL));
                            }
                            clustersize --;
                        }
                    }else if(idL == -1 && adjL == -1) {
                        idL = e.getId();
                        adjL = e.getAdj();
                        vertices[idL] -=1;
                        vertices[adjL] = idL;
                        clusters.get(idL).add(idL);
                        clusters.get(idL).add(adjL);
                        clustersize --;
                        verticesize +=2;
                    }else if(idL == -1) {
                        if(adjL < 0) adjL = e.getAdj();
                        idL = e.getId();
                        vertices[idL] = adjL;
                        clusters.get(adjL).add(idL);
                        clustersize --;
                        verticesize ++;
                    }else if(adjL == -1) {
                        if(idL < 0) idL = e.getId();
                        adjL = e.getAdj();
                        vertices[adjL] = idL;
                        clusters.get(idL).add(adjL);
                        clustersize --;
                        verticesize ++;
                    }
                    System.out.println(Integer.toString(e.getId()) + ' ' + Integer.toString(e.getAdj()) + ' ' + Integer.toString(e.getCost()) + ' ' + Integer.toString(clustersize) + ' ' + Integer.toString(verticesize));

                }
                while((e = heap.poll()) != null){ 
                    if( vertices[e.getId()] == -1 || vertices[e.getAdj()] == -1) break;
                }
                System.out.println(e.getId());
                System.out.println(e.getAdj());
                System.out.println(e.getCost());
                                
                            
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }



}
