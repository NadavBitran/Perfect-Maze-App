package com.hit.algorithm;

import com.hit.undirectedGraph.UndirectedGraph;
import com.hit.util.UndirectedGraphCreator;
import org.junit.Assert;

import java.util.Map;

class UtilTest {
    /**
     * Starting node index for DFS algorithm testing.
     */
    static final int STARTING_NODE_INDEX = 0;

    /**
     * Test length for grid graph.
     */
    static final int GRID_GRAPH_TEST_LENGTH = 15;

    /**
     * Test length for tree graph.
     */
    static final int TREE_GRAPH_TEST_LENGTH = 10;

    /**
     * Test length for forest graph.
     */
    static final int FOREST_GRAPH_TEST_LENGTH = 10;

    /**
     * Test length for circle graph.
     */
    static final int CIRCLE_GRAPH_TEST_LENGTH = 10;

    /**
     * Test length for graph with empty edges.
     */
    static final int EMPTY_EDGES_GRAPH_TEST_LENGTH = 10;

    /**
     * Test number of multiple connected component.
     */
    static final int MULTIPLE_CONNECTED_COMPONENT_TESTS_NUM = 2;


    enum graphType
    {
        GRID,
        TREE,
        FOREST,
        CIRCLE,
        EMPTY_EDGES,
        ONE_NODE
    }

    /**
     * Retrieves true or false based on if the graph is connected
     * @param parentsOfGraph The parents map of the graph to check.
     * @return True if the graph is connected, false otherwise.
     */
    static boolean isGraphConnected(Map<Integer,Integer> parentsOfGraph)
    {
        int connectedComponents = 0;

        for(Map.Entry<Integer,Integer> entry : parentsOfGraph.entrySet())
        {
            if(entry.getValue() == null)
            {
                connectedComponents++;
            }
        }

        return connectedComponents == 1;
    }


    /**
            * Retrieves a graph of the specified type.
     * @param type The type of graph to create.
     * @return The created graph.
     */
    static UndirectedGraph<Integer> getGraphByType(graphType type)
    {
        switch (type)
        {
            case GRID:
                return UndirectedGraphCreator.createNxNGridGraph(GRID_GRAPH_TEST_LENGTH);
            case TREE:
                return UndirectedGraphCreator.createTreeGraph(TREE_GRAPH_TEST_LENGTH);
            case FOREST:
                return UndirectedGraphCreator.createForestGraph(FOREST_GRAPH_TEST_LENGTH, MULTIPLE_CONNECTED_COMPONENT_TESTS_NUM);
            case CIRCLE:
                return UndirectedGraphCreator.createCircleGraph(CIRCLE_GRAPH_TEST_LENGTH);
            case EMPTY_EDGES:
                return UndirectedGraphCreator.createEmptyEdgesGraph(EMPTY_EDGES_GRAPH_TEST_LENGTH);
            case ONE_NODE:
                return UndirectedGraphCreator.createEmptyEdgesGraph(1);
            default:
                return null;
        }
    }



}
