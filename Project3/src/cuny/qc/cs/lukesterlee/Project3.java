package cuny.qc.cs.lukesterlee;

/*
 * Created by Luke Lee on Fall 2014
 * Queens College CSCI-323 : Design and Analysis of Algorithms
 * Project 3 : 2-3 Trees
 *
 */
import java.util.Scanner;
import java.io.*;

public class Project3 {
    public static int findSmall(TreeNode spot) {
        if(spot.kid1==null) {
            return spot.key1;
        }
        return findSmall(spot.kid1);
    }
    public static int findKey1(TreeNode spot) {
        return findSmall(spot.kid2);
    }
    public static int findKey2(TreeNode spot) {
        if(spot.kid3==null) {
            return -1;
        }
        else {
            return findSmall(spot.kid3);
        }
    }
    public static TreeNode findSpot(TreeNode spot, int data) {

        if(data==findSmall(spot)) {
            return null;
        }

        if(spot.kid1==null || spot.kid1.kid1==null) {
            return spot;
        }

        else {
            if(data==spot.key1 || data==spot.key2) {
                return null;
            }
            else if(data < spot.key1) {
                return findSpot(spot.kid1,data);
            }
            else if(spot.key2==-1 || (data < spot.key2)) {
                return findSpot(spot.kid2,data);
            }
            else {
                return findSpot(spot.kid3,data);
            }
        }
    }

    public static void update(Two3Tree tree, TreeNode spot) {
        if(spot==tree.dummy) {
            return;
        }
        spot.key1 = findKey1(spot);
        spot.key2 = findKey2(spot);

        update(tree, spot.parent);
    }
    public static void leafInsert(Two3Tree tree, TreeNode spot,TreeNode leaf) {
        int kid;
        if(spot.kid1==null) {
            kid = 0;
        }
        else if(spot.kid2==null) {
            kid = 1;
        }
        else if(spot.kid3==null) {
            kid = 2;
        }
        else {
            kid = 3;
        }
        switch(kid)
        {
            case 0:
                spot.kid1 = leaf;
                break;
            case 1:
                if(leaf.key1 < spot.kid1.key1) {
                    spot.kid2 = spot.kid1;
                    spot.kid1 = leaf;
                }
                else {
                    spot.kid2 = leaf;
                }
                update(tree,spot);
                break;
            case 2:
                if(leaf.key1 < spot.kid1.key1) {
                    spot.kid3 = spot.kid2;
                    spot.kid2 = spot.kid1;
                    spot.kid1 = leaf;
                }
                else if(leaf.key1 < spot.kid2.key1) {
                    spot.kid3 = spot.kid2;
                    spot.kid2 = leaf;
                }
                else {
                    spot.kid3 = leaf;
                }
                update(tree,spot);
                break;
            case 3:
                TreeNode sibling = new TreeNode(-1,-1,spot.parent,null,null,null);
                if(leaf.key1 < spot.kid1.key1) {
                    sibling.kid2 = spot.kid3;
                    spot.kid3.parent = sibling;
                    sibling.kid1 = spot.kid2;
                    spot.kid2.parent = sibling;
                    spot.kid2 = spot.kid1;
                    spot.kid1 = leaf;
                    spot.kid3 = null;
                }
                else if(leaf.key1 < spot.kid2.key1) {
                    sibling.kid2 = spot.kid3;
                    spot.kid3.parent = sibling;
                    sibling.kid1 = spot.kid2;
                    spot.kid2.parent = sibling;
                    spot.kid2 = leaf;
                    spot.kid3 = null;
                }
                else if(leaf.key1 < spot.kid3.key1) {
                    sibling.kid2 = spot.kid3;
                    spot.kid3.parent = sibling;
                    sibling.kid1 = leaf;
                    leaf.parent = sibling;
                    spot.kid3 = null;
                }
                else {
                    sibling.kid2 = leaf;
                    leaf.parent = sibling;
                    sibling.kid1 = spot.kid3;
                    spot.kid3.parent = sibling;
                    spot.kid3 = null;
                }
                update(tree,spot);
                update(tree,sibling);
                if(spot.parent==tree.dummy) {
                    TreeNode newroot = new TreeNode(-1,-1,tree.dummy,spot,sibling,null);
                    spot.parent = newroot;
                    sibling.parent = newroot;
                    tree.root = newroot;
                    update(tree,tree.root);
                }
                else {
                    leafInsert(tree, spot.parent,sibling);
                }
                break;
        }
    }

    public static void nullprint(TreeNode spot) {
        if(spot==null) {
            System.out.print("NULL");
        }
        else {
            System.out.print(spot.key1);
        }
    }

    public static void print(TreeNode spot) {
        if(spot==null) {
            return;
        }
        else {
            System.out.print("<");
            nullprint(spot);
            System.out.print(", " + spot.key2 + ", ");
            nullprint(spot.parent);
            System.out.print(", ");
            nullprint(spot.kid1);
            System.out.print(", ");
            nullprint(spot.kid2);
            System.out.print(", ");
            nullprint(spot.kid3);
            System.out.println(">");

            print(spot.kid1);
            print(spot.kid2);
            print(spot.kid3);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {

        Scanner inFile = new Scanner(new File(args[0]));
        Two3Tree tree = new Two3Tree();
        char op;
        int data;

        while(inFile.hasNext()) {
            op = inFile.next().charAt(0);
            if (op=='+') {
                System.out.print(op);
                data = inFile.nextInt();
                System.out.println(" " + data);
                TreeNode spot = findSpot(tree.root,data);

                if(spot==null) {
                    System.out.println("The data is already in the database!");
                } else {
                    TreeNode leaf = new TreeNode(data,-1,spot,null,null,null);
                    leafInsert(tree,spot,leaf);
                }
            }
            else if(op=='-') {
                System.out.print(op);
                data = inFile.nextInt();
                System.out.println(" " + data);
                deletion();
            }
            // Print the entire 2-3 tree.
            else if (op=='p') {
                System.out.println(op);
                print(tree.root);
            }
        }
        inFile.close();
    }
}
