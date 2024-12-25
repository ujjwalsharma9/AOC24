public class Node {
    String id;
    int value = -1;
    Node node1;
    Node node2;
    Operation operation;

    public Node(String id) {
        this.id = id;
    }

    enum Operation{
        XOR, AND, OR
    }
}
