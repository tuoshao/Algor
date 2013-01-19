package cluster2;

import java.io.*;
import java.nio.charset.Charset;

public class Cluster{
    public static void main(String[] args) {
        InputStream fis;
        BufferedReader br;
        String line;

        try{
            fis = new FileInputStream("clustering2.txt");
            // fis = new FileInputStream("test");
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

            try{
                line = br.readLine();
                int index = line.indexOf(' ');
                int count = Integer.parseInt(line.substring(0, index));
                VPTree tree = new VPTree(count);

                while((line = br.readLine()) != null) {
                    StringBuilder str = new StringBuilder();
                    for(int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);
                        if(c != ' ') {
                            str.append(c);
                        }
                    }
                    boolean check = tree.insertValue(str.toString());
                }
                tree.createTree();
                int num = tree.searchAdj(2);

                System.out.println(count-num+1);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


