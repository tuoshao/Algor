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
            // fis = new FileInputStream("test2");
            br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

            try{
                line = br.readLine();
                int index = line.indexOf(' ');
                int count = Integer.parseInt(line.substring(0, index));
                int checksum = 0;
                // VPTree tree = new VPTree(count);
                Normal n = new Normal(count);

                while((line = br.readLine()) != null) {
                    StringBuilder str = new StringBuilder();
                    checksum = 0;
                    for(int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);
                        if(c != ' ') {
                            str.append(c);
                            checksum += c - '0';
                        }
                    }
                    // boolean check = tree.insertValue(str.toString());
                    n.insertValue(str.toString(), checksum);
                }
                // tree.createTree();
                // tree.printTree();
                // int num = tree.searchAdj(2);

                // System.out.println(count - tree.getAdjNum() + num);
                System.out.println(n.getClusters(2));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}


