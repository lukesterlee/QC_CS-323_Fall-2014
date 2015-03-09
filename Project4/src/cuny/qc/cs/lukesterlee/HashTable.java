package cuny.qc.cs.lukesterlee;

/**
 * Created by Luke on 3/9/2015.
 */
public class HashTable {
    Node n;
    Node dummy;
    Node back;
    int size;

    void add(int jid) {
        Node n = new Node();
        n.jobId = jid;
        back.next = n;
        back = n;
        size++;
    }

    int remove() {
        int jid = dummy.next.jobId;
        if (dummy.next.next == null) {
            dummy.next = null;
            back = dummy;
        } else {
            dummy.next = dummy.next.next;
        }
        size--;
        return jid;
    }

    boolean isEmpty() {
        if (dummy.next == null) {
            return true;
        } else {
            return false;
        }
    }

    HashTable() {
        n = new Node();
        dummy = n;
        back = n;
        size = 0;
    }
}
