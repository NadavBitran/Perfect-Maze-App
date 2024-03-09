package com.hit.algorithm;

import com.hit.undirectedGraph.AdjacencyList;
import com.hit.undirectedGraph.Edge;
import com.hit.undirectedGraph.UndirectedGraph;

import java.util.*;

public class BFS<T> extends AbstractShortestPaths<T> implements IShortestPaths<T> {

    private Map<T, Integer> distance;

    public BFS(UndirectedGraph<T> undirectedGraph, T startingNodeIndex) {
        super(undirectedGraph);
        this.distance = new HashMap<>();
        this.startingNodeIndex = startingNodeIndex;
    }

    @Override
    public UndirectedGraph<T> run() {
        this.resetColors();
        this.resetParents();
        Queue<T> queue = new LinkedList<>();

        distance.put(startingNodeIndex, 0);
        this.getColors().put(startingNodeIndex, Color.GRAY);
        queue.add(startingNodeIndex);

        while (!queue.isEmpty()) {
            T currentNodeIndex = queue.poll();

            AdjacencyList<T> adjacencyList = this.getUndirectedGraph().getAdjacencyList(currentNodeIndex);

            if(adjacencyList != null)
            {
                List<Edge<T>> neighborList = adjacencyList.getEdgeList();
                for (Edge<T> edge : neighborList) {
                    T neighborIndex = edge.getEndingNodeIndex();
                    if (this.getColors().get(neighborIndex) == Color.WHITE) {
                        this.getColors().put(neighborIndex, Color.GRAY);
                        this.getParents().put(neighborIndex, currentNodeIndex);
                        distance.put(neighborIndex, distance.get(currentNodeIndex) + 1);
                        queue.add(neighborIndex);
                    }
                }
            }
            this.getColors().put(currentNodeIndex, Color.BLACK);
        }

        return createShortestPathsGraph();
    }
    @Override
    public int distance(T destinationNodeIndex) {
        return distance.getOrDefault(destinationNodeIndex, -1);
    }
}
