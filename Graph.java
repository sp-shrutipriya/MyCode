/*

Problem Statement:

You are given a DAG which may be disjointed (this represents courses in a university that must be taken in a particular order, but may represent different streams).
Identify all node with 0 in-degree.
For each such node, generate all possible paths that originate from that node e.g., in the following graph assume that all edges point downward
0 6 4
/ \ /
1 2
/ \ /
3 5
you should generate the following paths
0->1->3
0->1->5
0->2->5
6->2->5
4
For extra credit, here is a data file that describes the above graph where
• the first line is the total number of nodes
• every subsequent line represents an edge between the first and second node
7
0, 1
0, 2
1, 3
1, 5
2, 5
6, 2
Write the code to read in the data file and create the graph.

*/

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// JAVA program to print all the paths from all the 0 in-degree nodes

// A directed graph using adjacency list representation
public class Graph {

    // No. of vertices in graph
    private static int vertices;

    // adjacency list with out-degrees
    private ArrayList<Integer>[] adjListOutDegree;

    // adjacency list with in-degrees
    private ArrayList<Integer>[] adjListInDegree;

    public static List<String> readDataFile(String fileName) throws Exception {

        File file = new File(fileName);
        Scanner sc = new Scanner(file);

        List<String> nodeAndEdgesList = new ArrayList<>();

        while (sc.hasNextLine()) {
            nodeAndEdgesList.add(sc.nextLine());
        }

        return nodeAndEdgesList;
    }

    public void createGraph(List<String> nodeAndEdgesList) {

        for (int i = 0; i < nodeAndEdgesList.size(); i++) {

            String[] nodeAndEdges = nodeAndEdgesList.get(i).split(", ");

            if (i == 0) {

                //initialise vertex count
                vertices = Integer.parseInt(nodeAndEdges[0]);

                // initialise adjacency list
                initAdjList();
            } else {
                addEdge(Integer.parseInt(nodeAndEdges[0]), Integer.parseInt(nodeAndEdges[1]));
            }
        }
    }

    //Constructor
    public Graph(List<String> nodeAndEdgesList) {

        createGraph(nodeAndEdgesList);
    }

    // utility method to initialise adjacency list
    private void initAdjList() {

        adjListOutDegree = new ArrayList[vertices];
        adjListInDegree = new ArrayList[vertices];

        for (int i = 0; i < vertices; i++) {
            adjListOutDegree[i] = new ArrayList<>();
            adjListInDegree[i] = new ArrayList<>();
        }
    }

    // add edge from u to vertices
    public void addEdge(int u, int v) {

        adjListOutDegree[u].add(v);
        adjListInDegree[v].add(u);
    }

    // Prints all paths from 's' to 'd'
    public void printAllPathsBetweenTwoNodes(int s, int d) {

        boolean[] isVisited = new boolean[vertices];
        ArrayList<Integer> pathList = new ArrayList<>();

        //add source to path[]
        pathList.add(s);

        //Call recursive utility
        printAllPathsUtil(s, d, isVisited, pathList);
    }

    // print path list
    public String printPath(List<Integer> localPathList) {

        String path = localPathList.get(0).toString();
        for (int i = 1; i < localPathList.size(); i++) {
            path = path + " -> " + localPathList.get(i).toString();
        }
        return path;
    }

    // A recursive function to print all paths from 'u' to 'd'.
    // isVisited[] keeps track of vertices in current path.
    // localPathList<> stores actual vertices in the current path
    private void printAllPathsUtil(Integer u, Integer d, boolean[] isVisited,
                                   List<Integer> localPathList) {

        // Mark the current node
        isVisited[u] = true;

        if (u.equals(d)) {
            System.out.println(printPath(localPathList));
        }

        // Recur for all the vertices adjacent to current vertex
        for (Integer i : adjListOutDegree[u]) {
            if (!isVisited[i]) {
                // store current node in path[]
                localPathList.add(i);

                printAllPathsUtil(i, d, isVisited, localPathList);

                // remove current node in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }

    // find all the 0 in-degree nodes and then all different paths from that node
    public void printAllPaths(Graph g) {

        for (int i = 0; i < vertices; i++) {

            if (adjListInDegree[i].isEmpty()) {

                System.out.println(i + " is 0 in-degree node");
                System.out.println("Following are all different paths from " + i);

                for (int j = 0; j < vertices; j++) {

                    if (adjListOutDegree[j].isEmpty()) {
                        g.printAllPathsBetweenTwoNodes(i, j);
                    }
                }

                System.out.println();
            }
        }
    }

    // Driver program
    public static void main(String[] args) throws Exception {

        List<String> nodeAndEdgesList = readDataFile("C:\\.....\\Input.txt");
        // Create the graph
        Graph g = new Graph(nodeAndEdgesList);

        System.out.println("Following are all different paths: ");
        System.out.println();
        g.printAllPaths(g);
    }
}

/*

Input text file contents:
7
0, 1
0, 2
1, 3
1, 5
2, 5
6, 2

Output:

Following are all different paths:

0 is 0 in-degree node
Following are all different paths from 0
0 -> 1 -> 3
0 -> 1 -> 5
0 -> 2 -> 5

4 is 0 in-degree node
Following are all different paths from 4
4

6 is 0 in-degree node
Following are all different paths from 6
6 -> 2 -> 5

*/
