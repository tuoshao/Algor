package greedy2;


import java.io.*;
import java.nio.charset.Charset;

public class PrimTree{
    public static void main(String[] args) {
        InputStream fis;
        BufferedReader br;
        String line;

        try{
            fis = new FileInputStream("edges.txt");
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

            Graph g = new Graph(500);
            try{
                br.readLine();

                while((line = br.readLine()) != null) {
                    int index = line.indexOf(' ');
                    String str1 = line.substring(0, index);
                    int i = Integer.parseInt(str1);
                    String str2 = line.substring(index+1, line.length());
                    index = str2.indexOf(' ');
                    String str3 = str2.substring(0, index);
                    int a = Integer.parseInt(str3);
                    String str4 = str2.substring(index+1, str2.length());
                    int c = Integer.parseInt(str4);
                    g.insertEntry(i, a, c);
                    g.insertEntry(a, i, c);
                }

                g.primTree();
                System.out.println(g.getTreeLength());

                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }
}


