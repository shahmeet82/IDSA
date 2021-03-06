package mxs170043;

import java.io.File;
import java.util.Scanner;

import mxs170043.Graph.Vertex;
import mxs170043.Graph.GraphAlgorithm;
import mxs170043.Graph.Factory;

public class PERT extends GraphAlgorithm<PERT.PERTVertex> {
	//variable declaration numcritical, criticalPath
	public static class PERTVertex implements Factory {
		//variable declarartion slack,ec,lc,duration,boolean critical

		public PERTVertex(Vertex u) {
			//initialization of above variables
		}

		public PERTVertex make(Vertex u) {
			return new PERTVertex(u);
		}
	}

	public PERT(Graph g) {
		super(g, new PERTVertex(null));
	}//implementation

	//to implement
	public void setDuration(Vertex u, int d) {
		//this.get(u).duration=d;
	}

	//to implement
	public boolean pert() {
		return false;
	}

	public int ec(Vertex u) {
		return 0;
	}

	public int lc(Vertex u) {
		return 0;
	}

	public int slack(Vertex u) {
		return 0;
	}

	public int criticalPath() {
		return 0;
	}

	public boolean critical(Vertex u) {
		return false;
	}

	public int numCritical() {
		return 0;
	}

	// setDuration(u, duration[u.getIndex()]);
	//to implement
	public static PERT pert(Graph g, int[] duration) {
		return null;
	}

	public static void main(String[] args) throws Exception {
		String graph = "11 12   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1 0";
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(graph);
		Graph g = Graph.readDirectedGraph(in);
		g.printGraph(false);

		PERT p = new PERT(g);
		for (Vertex u : g) {
			p.setDuration(u, in.nextInt());
		}
		// Run PERT algorithm. Returns null if g is not a DAG
		if (p.pert()) {
			System.out.println("Invalid graph: not a DAG");
		} else {
			System.out.println("Number of critical vertices: " + p.numCritical());
			System.out.println("u\tEC\tLC\tSlack\tCritical");
			for (Vertex u : g) {
				System.out.println(u + "\t" + p.ec(u) + "\t" + p.lc(u) + "\t" + p.slack(u) + "\t" + p.critical(u));
			}
		}
	}
}