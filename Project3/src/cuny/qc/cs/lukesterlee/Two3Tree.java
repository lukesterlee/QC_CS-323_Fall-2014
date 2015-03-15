package cuny.qc.cs.lukesterlee;

/*
 * Created by Luke Lee on Fall 2014
 * Queens College CSCI-323 : Design and Analysis of Algorithms
 * Project 3 : 2-3 Trees
 *
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