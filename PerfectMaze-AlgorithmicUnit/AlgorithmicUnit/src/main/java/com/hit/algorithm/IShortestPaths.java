package com.hit.algorithm;

import com.hit.undirectedGraph.UndirectedGraph;

/**
 *
 * @param <T> - Key Type Of UndirectedGraph's Node Values
 */
public interface IShortestPaths<T> {
    public UndirectedGraph<T> run();
    public int distance(T destinationNodeIndex);
}
