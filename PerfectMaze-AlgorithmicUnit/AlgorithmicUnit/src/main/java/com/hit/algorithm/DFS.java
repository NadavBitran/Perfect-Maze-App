package com.hit.algorithm;

import com.hit.undirectedGraph.Edge;
import com.hit.undirectedGraph.UndirectedGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFS<T> extends AbstractShortestPaths<T> implements IShortestPaths<T> {

    private Map<T , Integer> begin;
    private Map<T , Integer> finish;
    private int time;

    public DFS(UndirectedGraph<T> undirectedGraph)
    {
        super(undirectedGraph);
        this.begin = new HashMap<>();
        this.finish = new HashMap<>();
        this.startingNodeIndex = null;
    }
    public DFS(UndirectedGraph<T> undirectedGraph , T startingNodeIndex)
    {
        super(undirectedGraph);
        this.begin = new HashMap<>();
        this.finish = new HashMap<>();
        this.startingNodeIndex = startingNodeIndex;
    }


    @Override
    public UndirectedGraph<T> run() {
        this.resetColors();
        this.resetParents();
        this.time = 0;

        if(startingNodeIndex != null)
        {
            DFS_Visit(startingNodeIndex);
        }

        List<T> nodeList = this.getUndirectedGraph().getNodeList();
        for(T node : nodeList)
        {
            if(this.getColors().get(node) == Color.WHITE)
            {
                DFS_Visit(node);
            }
        }

        return createShortestPathsGraph();
    }

    private void DFS_Visit(T visitNodeIndex)
    {
        this.getColors().put(visitNodeIndex , Color.GRAY);
        this.time++;
        this.begin.put(visitNodeIndex , time);

        List<Edge<T>> neighborList = this.getUndirectedGraph().getAdjacencyList(visitNodeIndex).getEdgeList();
        for(Edge<T> edgeToNeighbor : neighborList)
        {
            T neighbor = edgeToNeighbor.getEndingNodeIndex();
            if(this.getColors().get(neighbor) == Color.WHITE)
            {
                this.getParents().put(neighbor , visitNodeIndex);
                DFS_Visit(neighbor);
            }
        }
        this.getColors().put(visitNodeIndex , Color.BLACK);
        this.time++;
        this.finish.put(visitNodeIndex , time);
    }

    @Override
    public int distance(T destinationToNodeIndex) {

        int currentdistance = 0;
        T currentNodeIndex = destinationToNodeIndex;

        while(currentNodeIndex != null && currentNodeIndex != startingNodeIndex)
        {
            currentNodeIndex = this.getParents().get(currentNodeIndex);
            currentdistance++;
        }

        if(currentNodeIndex == null)
        {
            return -1;
        }
        else
        {
            return currentdistance;
        }
    }

}
