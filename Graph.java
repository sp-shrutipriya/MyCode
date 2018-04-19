import java.util.ArrayList;
import java.util.List;

// JAVA program to print all the paths from all the 0 in-degree nodes

// A directed graph using adjacency list representation
public class Graph {

    // No. of vertices in graph
    private int v;

    // adjacency list with out-degrees
    private ArrayList<Integer>[] adjListOutDegree;

    // adjacency list with in-degrees
    private ArrayList<Integer>[] adjListInDegree;

    //Constructor
    public Graph(int vertices){

        //initialise vertex count
        this.v = vertices;

        // initialise adjacency list
        initAdjList();
    }

    // utility method to initialise adjacency list
    @SuppressWarnings("unchecked")
    private void initAdjList() {

        adjListOutDegree = new ArrayList[v];
        adjListInDegree = new ArrayList[v];

        for(int i = 0; i < v; i++)
        {
            adjListOutDegree[i] = new ArrayList<>();
            adjListInDegree[i] = new ArrayList<>();
        }
    }

    // add edge from u to v
    public void addEdge(int u, int v) {

        adjListOutDegree[u].add(v);
        adjListInDegree[v].add(u);
    }

    // Prints all paths from 's' to 'd'
    public void printAllPathsBetweenTwoNodes(int s, int d) {

        boolean[] isVisited = new boolean[v];
        ArrayList<Integer> pathList = new ArrayList<>();

        //add source to path[]
        pathList.add(s);

        //Call recursive utility
        printAllPathsUtil(s, d, isVisited, pathList);
    }

    // print path list
    public String printPath(List<Integer> localPathList) {

        String path = localPathList.get(0).toString();
        for(int i=1; i<localPathList.size(); i++){
            path = path + " -> " + localPathList.get(i).toString();
        }
        return path;
    }

    // A recursive function to print all paths from 'u' to 'd'.
    // isVisited[] keeps track of vertices in current path.
    // localPathList<> stores actual vertices in the current path
    private void printAllPathsUtil(Integer u, Integer d, boolean[] isVisited, List<Integer> localPathList) {

        // Mark the current node
        isVisited[u] = true;

        if (u.equals(d))
        {
            System.out.println(printPath(localPathList));
        }

        // Recur for all the vertices adjacent to current vertex
        for (Integer i : adjListOutDegree[u])
        {
            if (!isVisited[i])
            {
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

        for(int i=0; i<v; i++){

            if(adjListInDegree[i].isEmpty()){

                System.out.println(i + " is 0 in-degree node");
                System.out.println("Following are all different paths from " + i);

                for(int j=0; j<v; j++){

                    if(adjListOutDegree[j].isEmpty()){
                        g.printAllPathsBetweenTwoNodes(i, j);
                    }
                }

                System.out.println();
            }
        }
    }

    // Driver program
    public static void main(String[] args)
    {
        // Create the graph
        Graph g = new Graph(7);
        g.addEdge(0,1);
        g.addEdge(0,2);
        g.addEdge(1,3);
        g.addEdge(1,5);
        g.addEdge(2,5);
        g.addEdge(6,2);

        System.out.println("Following are all different paths: ");
        System.out.println();
        g.printAllPaths(g);

    }
}
