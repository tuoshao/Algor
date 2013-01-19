package cluster2;


public class BKTree{
    public class BKTreeNode{
        String str;
        int dup;
        BKTreeNode[] children;

        public BKTreeNode(String s, int size) {
            str = s;
            dup = 1;
            children = new BKTreeNode[size];
            // for(int i = 0; i < size; i++){
                // children[i] = null;
            // }
        }

        public void setDup(int d) {
            dup = d;
        }
    }
    BKTreeNode root;
    int childrenNum;

    public BKTree(String s, int c) {
        root = new BKTreeNode(s, c);
        childrenNum = c;
    }

    public BKTreeNode getRoot(){
        return root;
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

        if(count < 3){

            System.out.println(count);
        }
        return count;
    }
    public boolean insertNode(String s) {
        BKTreeNode tmp = root;
        int d;
        while(tmp != null){
            d = this.getHammingDistance(s, tmp.str);
            if(d == 0) {
                System.out.println(s + ' ' + tmp.str);
                tmp.dup += 1; 
                return true;
            }
            if(d > childrenNum) return false;
            if(tmp.children[d-1] == null) {
                tmp.children[d-1] = new BKTreeNode(s, childrenNum);
                break;
            } else {
                tmp = tmp.children[d-1];
            }
        }
        return true;
    }

    public int getSubTreeNodeNum(BKTreeNode node){
        int count = 0;
        System.out.println(node.str);
        for(int i = 0; i < childrenNum; i++) {
            if(node.children[i] != null) count += getSubTreeNodeNum(node.children[i]);
        }
        return count+node.dup;
    }

    public int getAdjNum(BKTreeNode node, int dist) {
        int count = 0;
        int countself = 0;
        for(int i = 0; i < childrenNum; i++) {
            if(node.children[i] != null) {
                if(i < dist) {
                    System.out.println();
                    System.out.println(node.str);
                    count += getSubTreeNodeNum(node.children[i]);
                    countself = 1;
                }
                else count += getAdjNum(node.children[i], dist);
            }
        }
        return count + (countself == 1? node.dup:0);
    }

    public int getAdjNumber(int dist) {
        int result = getAdjNum(root, dist);
        System.out.println(result);

        return result;
    }


}
