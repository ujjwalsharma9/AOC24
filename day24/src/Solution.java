public class Solution {

    public void dfs(Node node){
        if(node.node1 == null || node.node2 == null){
            System.out.print(node.id);
            return;
        }

        System.out.print("(");
        dfs(node.node1);
        System.out.print(" " + node.operation.name() + " ");
        dfs(node.node2);
        System.out.print(")");
    }

    public StringBuilder dfsStr(Node node){
        if(node.node1 == null || node.node2 == null){
            return new StringBuilder(node.id);
        }

        StringBuilder sb1 = dfsStr(node.node1);
        StringBuilder sb2 = dfsStr(node.node2);

        if(sb1.compareTo(sb2) <= 0){
            sb1.append(" ").append(node.operation.name()).append(" ").append(sb2);
            return sb1;
        }
        else{
            sb2.append(" ").append(node.operation.name()).append(" ").append(sb1);
            return sb2;
        }

    }
}
