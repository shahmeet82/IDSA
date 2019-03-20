package mxs170043;

import rbk.Graph;
import rbk.Graph.Vertex;
import rbk.Graph.Edge;
import rbk.Graph.GraphAlgorithm;
import rbk.Graph.Factory;

import java.io.File;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
    LinkedList<Vertex> finishList;
    int topNum;
    boolean cycleSeenFlag;
    int cNO;

    public static class DFSVertex implements Factory {
        int cno;
        boolean seen;
        Vertex parent;
        int top;

        public DFSVertex(Vertex u) {
            this.seen = false;
            this.top = 0;
            this.parent = null;
            this.cno=-1;
        }
        public DFSVertex make(Vertex u) { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        finishList = new LinkedList<>();
        topNum = g.size();
        cycleSeenFlag = false;
    }
    /**
     * performs  depth first search in order of vertices given by iterator
     * @param iterable order of vertices in which dfs should run
     */
    public void dfs(Iterable<Vertex> iterable){
        topNum = g.size();
        finishList = new LinkedList<Vertex>();
        cycleSeenFlag = false;

        for(Vertex u: g) {
            get(u).seen = false;
            get(u).parent = null;
            get(u).top = 0;
        }
        cNO = 0;

        for(Vertex u: iterable){
            if(!get(u).seen){
                dfsVisit(u,++cNO);
            }
        }
    }

    /**
     * Traverse vertex u in depth first order.
     * @param u vertex to start depth first travel
     * @param cNO assigns the component number to any given vertex u
     */
    public void dfsVisit(Vertex u, int cNO){
        get(u).seen = true;
        get(u).cno = cNO;
        for(Edge e: g.incident(u)){
            Vertex v = e.otherEnd(u);
            if(!get(v).seen){
                get(v).parent = u;
                dfsVisit(v,cNO);
            }else if (get(v).top == 0){ // back edge detected (only applicable to directed graph)
                cycleSeenFlag = true;
            }
        }
        get(u).top = topNum -- ;
        finishList.addFirst(u);
    }
    /**
     * return DFS instance on given graph
     * @param g graph to run depth first search
     * @return instance of DFS on graph g
     */
    public static DFS depthFirstSearch(Iterable<Vertex> g) {
        DFS d = new DFS((Graph) g);
        d.dfs(g);
        return d;
    }

    /**
     * Member function to find topological order
     * Pre-condition: graph must be directed
     * @return List of vertices in topological order if graph does not contain any cycle otherwise null
     */
    public List<Vertex> topologicalOrder1() {
        if(!g.isDirected())
        {
            return null;
        }
        this.dfs(g);
        if (cycleSeenFlag)
        {
            return null;
        }
        else {
            return finishList;
        }
    }

    // Find the number of connected components of the graph g by running dfs.
    // Enter the component number of each vertex u in u.cno.
    // Note that the graph g is available as a class field via GraphAlgorithm.
    /**
     * find the number of connected components in graph
     * @return number of connected components
     */
    public int connectedComponents() {
        dfs(g);
        return cNO;
    }
    /**
     * Find strongly connected components in graph
     * @param g finds strongly connected component by running dfs on given graph
     * @return DFS object containing information about strongly connected components
     */

    public static DFS stronglyConnectedComponents(Graph g) {
        DFS d = new DFS(g);
        d.dfs(g);
        List<Vertex> list=d.finishList;
        g.reverseGraph();
        d.dfs(list);
        g.reverseGraph();
        return d;
    }
    // After running the connected components algorithm, the component no of each vertex can be queried.
    /**
     * @param u
     * @return component number of vertex u
     */
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    /** Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
     * @return list of vertices in topological order
     */
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        String string="11 17    1 11 2  2 3 4   2 7 8   3 10 5  4 1 3   4 9 8   5 4 2   5 7 9   5 8 10  6 3 4   7 8 6   8 2 3   9 11 3  10 6 5  11 4 6  11 3 4  11 3 5 0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph(in,true);
        g.printGraph(false);


        DFS d = stronglyConnectedComponents(g);
        System.out.println("Number of Strongly connected components: " + d.cNO + "\nVertex:u\tcomponent");
        for(Graph.Vertex u: g) {
            System.out.println("\t"+u + "\t\t\t" + d.cno(u));
        }

    }
}