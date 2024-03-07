package com.hit.algorithm;

import com.hit.undirectedGraph.UndirectedGraph;

/**
 * Interface defining test methods for shortest paths algorithms.
 */
public interface IShortestPathsTest
{
    /**
     * Sets up the graph and DFS instance for testing with a specified starting node.
     * @param graphType The type of graph to create.
     * @param startingNodeIndex The index of the starting node for DFS.
     * @return The spanning forest obtained by running DFS.
     */
    UndirectedGraph<Integer> dynamicSetupWithStartingNode(UtilTest.graphType graphType, int startingNodeIndex);

    /**
     * Tests the connectivity of a grid graph.
     */
    void checkReturnedGraphConnectivityOfGrid();

    /**
     * Tests the connectivity of a tree graph.
     */
    void checkReturnedGraphConnectivityOfTree();

    /**
     * Tests the connectivity of a forest graph.
     */
    void checkReturnedGraphConnectivityOfForest();

    /**
     * Tests the connectivity of a circle graph.
     */
    void checkReturnedGraphConnectivityOfCircle();

    /**
     * Tests the distance of one connected component graph.
     */
    void checkDistanceOfOneConnectedComponentGraph();

    /**
     * Tests the parents and colors of one connected component graph.
     */
    void checkParentsAndColorsOfOneConnectedComponentGraph();

    /**
     * Tests the connectivity of a graph with empty edges.
     */
    void checkReturnedGraphConnectivityOfEmptyEdgesGraph();

    /**
     * Tests the connectivity of a graph with one node.
     */
    void checkReturnedGraphConnectivityOfGraphWithOneNode();



}
