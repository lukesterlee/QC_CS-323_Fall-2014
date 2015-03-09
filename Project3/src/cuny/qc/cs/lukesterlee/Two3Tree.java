package cuny.qc.cs.lukesterlee;

/**
 * Created by Luke on 3/9/2015.
 */
public class Two3Tree {
    public TreeNode dummy;
    public TreeNode root;

    public Two3Tree() {
        TreeNode d = new TreeNode(-1,-1,null,null,null,null);
        dummy = d;
        TreeNode r = new TreeNode(-1,-1,dummy,null,null,null);
        root = r;
    }
}