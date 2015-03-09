package cuny.qc.cs.lukesterlee;

/**
 * Created by Luke on 3/9/2015.
 */

public class TreeNode {

    public int key1;
    public int key2;
    public TreeNode parent;
    public TreeNode kid1;
    public TreeNode kid2;
    public TreeNode kid3;

    public TreeNode(int k1, int k2, TreeNode father, TreeNode c1, TreeNode c2, TreeNode c3) {
        key1 = k1;
        key2 = k2;
        parent = father;
        kid1 = c1;
        kid2 = c2;
        kid3 = c3;
    }


}
