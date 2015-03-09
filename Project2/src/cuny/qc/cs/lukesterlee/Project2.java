package cuny.qc.cs.lukesterlee;

import java.io.*;
import java.util.*;
import java.lang.Math;

public class Project2 {

    public static void printmap(int[][] table, int[][] map, int[][] centroid) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = 0;
            }
        }
        for (int i = 0; i < table.length; i++) {
            map[table[i][0]][table[i][1]] = table[i][2];
        }
        for (int i = 0; i < centroid.length; i++) {
            map[centroid[i][0]][centroid[i][1]] = 91 + i;
        }
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
    }
    public static void centroidupdate(int[][] table, int[][] centroid, int k) {
        int[] num = new int[k];
        int[][] centroidxy = new int[k][2];
        for (int i = 0; i < k; i++) {
            num[i] = 0;
            centroidxy[i][0] = 0;
            centroidxy[i][1] = 0;
        }
        for (int i = 0; i < table.length; i++) {
            for (int j = 1; j <= k; j++) {
                if (table[i][2] == j) {
                    num[j - 1]++;
                    centroidxy[j - 1][0] += table[i][0];
                    centroidxy[j - 1][1] += table[i][1];
                }
            }
        }
        for (int i = 0; i < k; i++) {
            if (num[i] == 0) {
                System.out.println("Centroid " + (i + 1)
                        + " does not exist at this moment.");
            } else {
                centroid[i][0] = centroidxy[i][0] / num[i];
                centroid[i][1] = centroidxy[i][1] / num[i];
                System.out.println("Centroid " + (i + 1) + " : ("
                        + centroid[i][0] + "," + centroid[i][1] + ")");
            }
        }
    }
    public static int distance(int[][] centroid, int row, int col, int k) {

        int label = 0;
        double lowest = 80 * 80 + 80 * 80;
        double[] d = new double[k];
        double[][] dxy = new double[k][2];
        for (int i = 0; i < k; i++) {
            dxy[i][0] = centroid[i][0] - row;
            dxy[i][1] = centroid[i][1] - col;
            d[i] = Math.sqrt(dxy[i][0] * dxy[i][0] + dxy[i][1] * dxy[i][1]);
        }
        for (int i = 0; i < k; i++) {
            if (d[i] < lowest)
                lowest = d[i];
        }
        for (int i = 0; i < k; i++) {
            if (d[i] == lowest) {
                label = i + 1;
                break;
            }
        }
        return label;
    }
    public static void main(String[] args) throws FileNotFoundException {

        int row, col, dots, kcounter;
        int total = 0;
        int k = Integer.parseInt(args[1]);

        Scanner inFile0 = new Scanner(new File(args[0]));
        while (inFile0.hasNextInt()) {
            kcounter = inFile0.nextInt();
            total++;
        }
        dots = (total - 2) / 2;
        inFile0.close();

        int[][] table = new int[dots][3];

        Scanner inFile = new Scanner(new File(args[0]));
        row = inFile.nextInt();
        col = inFile.nextInt();

        kcounter = 0;
        int[] ksub = new int[k];
        for (int i = 0; i < k; i++) {
            ksub[i] = 0;
        }
        for (int i = 0; i < dots; i++) {
            table[i][0] = inFile.nextInt();
            table[i][1] = inFile.nextInt();
            if (ksub[kcounter] < dots / k) {
                table[i][2] = kcounter + 1;
                ksub[kcounter]++;
            } else if (kcounter == k - 1) {
                table[i][2] = k;
            } else {
                kcounter++;
            }
        }

        System.out.println("Row Col");
        System.out.println(row + "  " + col);
        System.out.println("X  Y  Label(k)");

        for (int i = 0; i < dots; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println("");
        }
        inFile.close();
        int[][] map = new int[row][col];
        int[][] centroid = new int[k][2];
        centroidupdate(table, centroid, k);
        printmap(table, map, centroid);

        int move = 0;
        while (true) {
            for (int i = 0; i < table.length; i++) {
                if (table[i][2] != distance(centroid, table[i][0], table[i][1],
                        k)) {
                    move++;
                    table[i][2] = distance(centroid, table[i][0], table[i][1],
                            k);
                }
            }
            centroidupdate(table, centroid, k);
            if (move != 0) {
                System.out.println("Cluster Move : " + move + " times.");
                System.out.println("Current map is : ");
                printmap(table, map, centroid);
                move = 0;
            } else {
                break;
            }
        }
        System.out.println("Cluster Move : 0 times.");
        System.out.println("Final map is : ");
        printmap(table, map, centroid);
    }
}
