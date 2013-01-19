package greedy1;

import java.io.*;
import java.nio.charset.Charset;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Test{
    public static void main(String[] str){
        InputStream fis;
        BufferedReader br;
        String line;

        try{
            fis = new FileInputStream("jobs.txt");
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            List<Greedy1> list = new ArrayList<Greedy1>(); 

            try{
                br.readLine();

                int i = 0;
                while((line = br.readLine()) != null) {
                    int index = line.indexOf(' ');
                    String str1 = line.substring(0, index);
                    // System.out.println(str1);
                    int w = Integer.parseInt(str1);
                    String str2 = line.substring(index+1, line.length());
                    // System.out.println(str2);
                    // System.out.println();
                    int l = Integer.parseInt(str2);
                    list.add(new Greedy1(w, l));
                    i++;
                }
                
                List<Greedy1> result = QuickSort.quicksort(list);
                Iterator<Greedy1> it = result.iterator();
                long sum = 0;
                int length = 0;
                while(it.hasNext()) {
                    Greedy1 g = it.next();
                    //System.out.println(Integer.toString(g.getWeight()) + ' ' +  Integer.toString(g.getLength()));
                    length += g.getLength();
                    sum += g.getWeight() * length; 
                    // System.out.println(length);
                    // System.out.println(sum);
                    i++;
                }
                System.out.println(sum);
                System.out.println(i);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
