package vn.com.msb.homeloan.core.util;// DFS algorithm in Java

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph {

  private static LinkedList<Integer>[] adjLists;
  private static boolean[] visited;
  private static List<Integer> ways;

  // Graph creation
  public Graph(int vertices) {
    ways = new ArrayList<>();
    adjLists = new LinkedList[vertices];
    visited = new boolean[vertices];

    for (int i = 0; i < vertices; i++) {
      adjLists[i] = new LinkedList<>();
    }
  }

  // Add edges
  public void addEdge(int src, int dest) {
    adjLists[src].add(dest);
  }

  // DFS algorithm
  public void DFS(int vertex) {
    visited[vertex] = true;
    if (ways == null) {
      ways = new ArrayList<>();
    }
    ways.add(vertex);

    Iterator<Integer> ite = adjLists[vertex].listIterator();
    while (ite.hasNext()) {
      int adj = ite.next();
      if (!visited[adj]) {
        DFS(adj);
      }
    }
  }

  public List<Integer> getWays() {
    return ways;
  }

  public void setWays(List<Integer> ways) {
    this.ways = ways;
  }

  public static void main(String args[]) {

    Graph g = new Graph(7);
    g.addEdge(2, 4);
    g.addEdge(2, 3);
    g.addEdge(3, 5);
    g.addEdge(0, 1);
    g.addEdge(0, 2);
    g.addEdge(2, 6);

    System.out.println("Following is Depth First Traversal");

    g.DFS(0);
  }
}