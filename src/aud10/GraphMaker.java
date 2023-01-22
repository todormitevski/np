package aud10;

import java.io.BufferedReader;
import java.util.Stack;
import java.io.InputStreamReader;
import java.io.IOException;

class Graph<E> {

    E nodes[];
    int brNodes;
    int[][] adjacentMatrix;
    @SuppressWarnings("unchecked")
    public Graph(int num_nodes) {
        this.brNodes = num_nodes;
        nodes = (E[]) new Object[num_nodes];
        adjacentMatrix = new int[num_nodes][num_nodes];
        for(int i = 0; i<this.brNodes; i++)
            for(int j = 0; j<this.brNodes; j++)
                adjacentMatrix[i][j]=0;
    }

    public Graph(int num_nodes, E[] nodes) {
        this.brNodes = num_nodes;
        this.nodes = nodes;
        adjacentMatrix = new int[num_nodes][num_nodes];

        for(int i = 0; i<this.brNodes; i++)
            for(int j = 0; j<this.brNodes; j++)
                adjacentMatrix[i][j]=0;
    }
    void addEdge(int x,int y) {
        adjacentMatrix[x][y] = 1;
        adjacentMatrix[y][x] = 1;
    }
    void deleteEdge(int x,int y) {
        adjacentMatrix[x][y]=0;
        adjacentMatrix[y][x]=0;
    }

    int adjacent(int x,int y) {
        if(adjacentMatrix[x][y]!=0) return 1;
        else return 0;
    }
    void setNodeVal(int x, E a) {
        nodes[x]=a;
    }

    E getNodeVal(int x) {
        return nodes[x];
    }

    public int getBrNodes() {
        return brNodes;
    }
    public void setBrNodes(int brNodes) {
        this.brNodes = brNodes;
    }


    void recursive(int node, boolean visited[]) {
        visited[node] = true;
        System.out.println(node + ": " + nodes[node]);

        for (int i = 0; i < this.brNodes; i++) {
            if(adjacent(node, i)==1){
                if (!visited[i])
                    recursive(i, visited);
            }
        }
    }
    void search(int node) {
        boolean visitedNode[] = new boolean[brNodes];
        for (int i = 0; i < brNodes; i++)
            visitedNode[i] = false;
        recursive(node, visitedNode);
    }



    void nonrecursive(int node) {
        boolean visitedNode[] = new boolean[brNodes];
        for (int i = 0; i < brNodes; i++)
            visitedNode[i] = false;
        visitedNode[node] = true;
        System.out.println(node + ": " + nodes[node]);
        Stack<Integer> s = new Stack<Integer>();
        s.push(node);
        int tmp;
        while (!s.isEmpty()) {
            tmp = s.peek();
            int tmp1 = tmp;
            for (int i = 0; i < brNodes; i++) {
                if(adjacent(tmp,i)==1){
                    tmp1 = i;
                    if(!visitedNode[i])
                        break;
                }
            }

            if(!visitedNode[tmp1]){
                visitedNode[tmp1] = true;
                System.out.println(tmp1 + ": " + nodes[tmp1]);
                s.push(tmp1);
            } else s.pop();
        }
    }


    @Override
    public String toString() {
        String ret = "  ";
        for(int i=0;i<brNodes;i++)
            ret += nodes[i] + " ";
        ret += "\n";
        for(int i=0;i<brNodes;i++){
            ret += nodes[i] + " ";
            for(int j=0;j<brNodes;j++)
                ret += adjacentMatrix[i][j] + " ";
            ret += "\n";
        }
        return ret;
    }


}


public class GraphMaker {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(bf.readLine());
        Graph<Character> newGraph = null;
        for(int i=0;i<n;i++){

            String[] line = bf.readLine().split("\\s+");
            if(line[0].equals("CREATE")){
                Character[] letters = new Character[Integer.parseInt(line[1])];
                for(int j=0;j<letters.length;j++){
                    letters[j] = (char)('A'+j);
                }
                newGraph = new Graph<Character>(Integer.parseInt(line[1]),letters);
            }

            if(line[0].equals("ADDEDGE")){
                newGraph.addEdge(Integer.parseInt(line[1]),Integer.parseInt(line[2]));
            }

            if(line[0].equals("DELETEEDGE")){
                newGraph.deleteEdge(Integer.parseInt(line[1]),Integer.parseInt(line[2]));
            }

            if(line[0].equals("ADJACENT")){
                System.out.println(newGraph.adjacent(Integer.parseInt(line[1]),Integer.parseInt(line[2])));
            }

            if(line[0].equals("PRINTNODE")){
                System.out.println(newGraph.getNodeVal(Integer.parseInt(line[1])));
            }

            if(line[0].equals("PRINTMATRIX")){
                for(int j=0;j<newGraph.adjacentMatrix.length;j++){
                    for(int k=0;k<newGraph.adjacentMatrix.length;k++){
                        System.out.print(newGraph.adjacentMatrix[j][k] + " ");
                    }
                    System.out.println();
                }
            }

        }
    }
}
