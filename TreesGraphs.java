import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class Graph {
    HashMap<Integer, LinkedList<Integer>> graph = new HashMap();

    public void addEdge(int node, int adj){
        LinkedList<Integer> adjs = graph.getOrDefault(node, new LinkedList<Integer>());
        adjs.add(adj);
        graph.put(node, adjs);
    }

    public LinkedList<Integer> getAdj(int node){
        return graph.get(node);
    }
}

public class TreesGraphs {

    Graph graph;

    public void createGraph(){
        graph = new Graph();
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
    }

    public boolean isReachable(int start, int end){
//  Time Complexity: O(V+E), Space: O(V)
        Queue<Integer> queue = new LinkedList<Integer>();
        HashSet<Integer> visited = new HashSet();
        queue.add(start);
        visited.add(start);

        while(!queue.isEmpty()){
            int node = queue.poll();
            LinkedList<Integer> adjs = graph.getAdj(node);

            for(int adj : adjs){

                if(adj == end){
                    return true;
                }

                if(!visited.contains(adj)){
                    visited.add(adj);
                    queue.add(adj);
                }
            }
        }
        return false;
    }
}
