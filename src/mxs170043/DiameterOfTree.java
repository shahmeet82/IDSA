package mxs170043;

import java.io.File;
import java.util.Scanner;

import mxs170043.Graph;
import mxs170043.Graph.Vertex;

public class DiameterOfTree {
    public static final int INFINITY = Integer.MAX_VALUE;
    Vertex src;
    Vertex maxVertex;
    public int diameter(Graph g) {
        int max=0;
        src = g.getVertex(1);
        //int count = 0;
        BFSOO b = BFSOO.breadthFirstSearch(g, src);
        for(Vertex u: g) {
            if(b.getDistance(u) == INFINITY) {
                continue;
            } else if(b.getDistance(u)>max) {
                maxVertex=u;
                max=b.getDistance(u);
            }
        }
        BFSOO bMax = BFSOO.breadthFirstSearch(g, maxVertex);
        for(Vertex u: g) {
            if(bMax.getDistance(u) == INFINITY) {
                continue;
            } else if(bMax.getDistance(u)>max) {
                maxVertex=u;
                max=bMax.getDistance(u);
            }
        }



        return max;
    }

    public static void main(String[] args) throws Exception {
        //String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 -7   6 7 -1   7 6 -1 1";
        //String string = "10 9  1 2 1  1 3 1  2 4 2  2 5 3  3 6 1  3 7 2  4 8 6  7 9 1  7 10 2 0 ";
        String string = "5 4  1 2 2   1 3 3   2 4 5  4 5 1 0 ";

        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in =  new Scanner(string);
        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        int s = in.nextInt();
        g.printGraph(false);
        DiameterOfTree dia = new DiameterOfTree();
        System.out.println(dia.diameter(g));

    }

}
