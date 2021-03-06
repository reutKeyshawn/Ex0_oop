package ex0;

import java.util.*;
import java.util.stream.Collectors;

public class Graph_Algo implements graph_algorithms {
    private ex0.graph graph;

    @Override

    public void init(graph g) {
        this.graph = g;
    }

    @Override

    public graph copy() {
        graph copyGraph = new Graph_DS();
        //add nodes to the new graph
        for (node_data node : graph.getV()) {
            copyGraph.addNode(new NodeData(node.getKey(), node.getTag()));
        }
        //add edges to the new graph by connect method
        for (node_data node : graph.getV()) {

            if (node.getNi().size() != 0) {
                for (node_data neighbor : node.getNi()) {
                    copyGraph.connect(node.getKey(), neighbor.getKey());
                }

            }
        }

        return copyGraph;
    }


    @Override
    public boolean isConnected() {
        if (graph.nodeSize() == 0) {
            return true;
        }
        Iterator<node_data> it = graph.getV().iterator();
        //start the BFS from arbitrary vertex
        BFS_Algo(it.next().getKey());

        //check if all the vertices are visited, if yes then graph is connected

        for (node_data nodeData : graph.getV()) {
            if (nodeData.getTag() != 1) {
                // reset the tags of all nodes back to 0 (unvisited) so it can be ready for the next method call.
                resetTags();
                return false;
            }

        }
        // reset the tags of all nodes back to 0 (unvisited) so it can be ready for the next method call.
        resetTags();
        return true;
    }

    public void resetTags() {
        for (node_data node : graph.getV()) {
            node.setTag(0);
        }
    }


    public void BFS_Algo(int node) {


        // Create a queue for BFS
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // Mark the current node as visited and enqueue it
        graph.getNode(node).setTag(1);
        queue.add(node);

        while (queue.size() != 0) {
            // Dequeue a vertex from queue and print it
            int tempNode = queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it
            for (node_data neighbor : graph.getNode(tempNode).getNi()) {
                if (neighbor.getTag() != 1) {
                    neighbor.setTag(1);

                    queue.add(neighbor.getKey());
                }
            }
        }
    }


    @Override
    public int shortestPathDist(int src, int dest) {

        HashMap<Integer, Integer> distance = new HashMap<>();

        // Initialize distances as -1
        for (node_data node : graph.getV()) {
            distance.put(node.getKey(), -1);
        }


        Queue<node_data> queue = new LinkedList<>();
        distance.put(src, 0);

        queue.add(graph.getNode(src));
        graph.getNode(src).setTag(1);
        while (!queue.isEmpty()) {
            node_data curr = queue.poll();

// iterate over the neighbors of curr and if it unvisited - calculate the distance between src to the neighbor and set it as visited
            for (node_data neighbor : curr.getNi()) {
                if (neighbor.getTag() == 1)
                    continue;

                // update distance for i

                distance.put(neighbor.getKey(), distance.get(curr.getKey()) + 1);
                queue.add(neighbor);
                neighbor.setTag(1);
            }
        }
        // reset the tags of all nodes back to 0 (unvisited) so it can be ready for the next method call.
        resetTags();
        return distance.get(dest);


    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        var route = shortestPathHelper(src, dest, new LinkedList<>());
        if (route != null) {
            route.add(dest);
        }

        return route != null ? route.stream().map(key -> graph.getNode(key)).collect(Collectors.toList()) : null;
    }


    /// helper methods
    public List<Integer> shortestPathHelper(Integer currNode, Integer destNode, List<Integer> route) {
        if (route.contains(currNode)) return null;

        if (currNode.equals(destNode)) {
            return route;
        }

        List<Integer> shortestRoute = null;
        route.add(currNode);

        for (var neighbor : graph.getNode(currNode).getNi()) {

            var shortestResult = shortestPathHelper(neighbor.getKey(), destNode, route);

            if (shortestResult != null && (shortestRoute == null || shortestResult.size() < shortestRoute.size())) {
                shortestRoute = new LinkedList<>(shortestResult);
            }
        }
        route.remove(currNode);

        return shortestRoute;
    }
}


