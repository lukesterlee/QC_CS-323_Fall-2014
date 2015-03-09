package cuny.qc.cs.lukesterlee;

import java.util.*;
import java.io.*;

public class Project4 {

    public static int availProc(int processJob[]) {
        for (int i = 1; i <= processJob.length; i++) {
            if (processJob[i] <= 0) {
                return i;
            }
        }
        return 0;
    }

    public static void printdigit(int number) {
        if (number < 10) {
            System.out.print("0" + number);
        } else {
            System.out.print(number);
        }
    }

    public static void printProcessJob(int[] processJob) {
        System.out.print("Current Process Job Table : ");
        for (int i = 1; i < processJob.length; i++) {
            System.out.print(processJob[i] + " ");
        }
        System.out.println();
    }

    public static void printJobTime(int[] jobTime) {
        System.out.print("Current Job Time Table : ");
        for (int i = 1; i < jobTime.length; i++) {
            System.out.print(jobTime[i] + " ");
        }
        System.out.println();
    }

    public static void printJobStatus(int[] jobStatus) {
        System.out.print("Current Job Status Table : ");
        for (int i = 1; i < jobStatus.length; i++) {
            System.out.print(jobStatus[i] + " ");
        }
        System.out.println();
    }

    public static void printProcessTime(int[] processTime) {
        System.out.print("Current Process Time Table : ");
        for (int i = 1; i < processTime.length; i++) {
            System.out.print(processTime[i] + " ");
        }
        System.out.println();
    }

    public static void printParentCount(int[] parentCount) {
        System.out.print("Current Process Time Table : ");
        for (int i = 1; i < parentCount.length; i++) {
            System.out.print(parentCount[i] + " ");
        }
        System.out.println();
    }

    public static void printSchedule(int[][] schedule, int total) {
        System.out.println("Current Scheduling Table : ");
        for (int i = 1; schedule[i][0] != 0; i++) {
            for (int j = 0; j < total; j++) {
                if (schedule[i][j] == 0) {
                    System.out.print("  ");
                } else {
                    printdigit(schedule[i][j]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int job;
        int remain;
        Scanner inFile = new Scanner(new File(args[0]));
        Scanner inFile2 = new Scanner(new File(args[1]));
        remain = inFile.nextInt();
        job = inFile2.nextInt();
        job++;
        int[] processJob = new int[job];
        int[] processTime = new int[job];
        int[] parentCount = new int[job];
        int[] jobTime = new int[job];
        int[] jobStatus = new int[job];
        HashTable[] ht = new HashTable[job];
        for (int i = 0; i < job; i++) {
            ht[i] = new HashTable();
        }
        for (int i = 0; i < job; i++) {
            processJob[i] = 0;
            parentCount[i] = 0;
            jobStatus[i] = 2;
        }
        int jid, length;
        int total = 0;
        while (inFile2.hasNext()) {
            jid = inFile2.nextInt();
            length = inFile2.nextInt();

            processTime[jid] = length;
            jobTime[jid] = length;
            total += length;
        }
        int[][] schedule = new int[job][total];
        for (int i = 0; i < job; i++) {
            for (int j = 0; j < total; j++) {
                schedule[i][j] = 0;
            }
        }
        int jid2;
        while (inFile.hasNext()) {
            jid = inFile.nextInt();
            jid2 = inFile.nextInt();
            ht[jid].add(jid2);
            parentCount[jid2]++;
        }
        inFile.close();
        inFile2.close();
        int time = 0;
        while (remain > 0) {
            for (int i = 1; i < job; i++) {
                if (parentCount[i] == 0 && jobStatus[i] == 2) {
                    ht[0].add(i);
                }
            }
            while (!ht[0].isEmpty()) {
                jid = ht[0].remove();
                length = jobTime[jid];
                jid2 = availProc(processJob);
                processJob[jid2] = jid;
                jobStatus[jid] = 1;
                for (int j = time; j < (time + length); j++) {
                    schedule[jid2][j] = jid;
                }
            }
            time++;
            System.out.println("Current time is : " + time);
            printProcessJob(processJob);
            printJobTime(jobTime);
            printProcessTime(processTime);
            printParentCount(parentCount);
            printJobStatus(jobStatus);
            printSchedule(schedule, total);
            for (int i = 1; i < job; i++) {
                if (processJob[i] != 0) {
                    jid = processJob[i];
                    processTime[jid]--;
                    if (processTime[jid] == 0 && jobStatus[jid] == 1) {
                        processJob[i] = 0;
                        remain--;
                        jobStatus[jid] = 0;
                        while (!ht[jid].isEmpty()) {
                            jid2 = ht[jid].remove();
                            parentCount[jid2]--;
                        }
                    }
                }
            }
        }
        System.out.println("This is final output.");
        System.out.println("Total time was : " + time);
        printProcessJob(processJob);
        printJobTime(jobTime);
        printProcessTime(processTime);
        printParentCount(parentCount);
        printJobStatus(jobStatus);
        printSchedule(schedule, total);
    }
}