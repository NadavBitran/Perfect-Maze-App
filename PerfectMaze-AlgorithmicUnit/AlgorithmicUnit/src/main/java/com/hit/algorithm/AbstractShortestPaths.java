package com.hit.algorithm;

import com.hit.undirectedGraph.UndirectedGraph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @param <T> - Key Type Of UndirectedGraph's Node Values
 */
public abstract class AbstractShortestPaths<T>  {

    private Map<T , T> parents;
    private Map<T , Color> colors;
    private UndirectedGraph<T> undirectedGraph;
    protected T startingNodeIndex;

    /**
     *
     * @param undirectedGraph - The UndirectedGraph To Run The Algorithm
     */
    public AbstractShortestPaths(UndirectedGraph<T> undirectedGraph){
        this.undirectedGraph = undirectedGraph;
        this.parents = new HashMap<>();
        this.colors = new HashMap<>();
    }

    void resetParents()
    {
        List<T> nodesFromGraph = this.undirectedGraph.getNodeList();

        nodesFromGraph.forEach(tNode -> parents.put(tNode , null));
    }

    void resetColors()
    {
        List<T> nodesFromGraph = this.undirectedGraph.getNodeList();

        nodesFromGraph.forEach(tNode -> colors.put(tNode , Color.WHITE));
    }

    UndirectedGraph<T> createShortestPathsGraph()
    {
        UndirectedGraph<T> shortestPathsGraph = new UndirectedGraph<>();
        List<T> nodeList = this.getUndirectedGraph().getNodeList();

        for(T node : nodeList)
        {
            if(this.getColors().get(node) == Color.BLACK)
            {
                shortestPathsGraph.addNodeToGraph(node);
            }
        }

        List<T> nodeSpanningForestList = shortestPathsGraph.getNodeList();
        for(T node : nodeSpanningForestList)
        {
            if(this.getParents().get(node) != null)
            {
                shortestPathsGraph.addEdgeToGraph(this.getParents().get(node),node);
            }
        }

        return shortestPathsGraph;
    }

    Map<T, Color> getColors() {
        return colors;
    }

    UndirectedGraph<T> getUndirectedGraph() {
        return undirectedGraph;
    }

    public Map<T , T> getParents() {return this.parents;}

    public enum Color{
        WHITE,
        GRAY,
        BLACK
    }


}
