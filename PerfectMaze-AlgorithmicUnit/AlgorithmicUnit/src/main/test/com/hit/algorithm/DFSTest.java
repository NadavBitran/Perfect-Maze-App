package com.hit.algorithm;

import com.hit.undirectedGraph.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

public class DFSTest implements IShortestPathsTest{

    private UndirectedGraph<Integer> graph;
    private DFS<Integer> dfs;

    /**
     * Sets up the graph and DFS instance for testing with a specified starting node.
     * @param graphType The type of graph to create.
     * @param startingNodeIndex The index of the starting node for DFS.
     * @return The spanning forest obtained by running DFS.
     */
    @Override
    public UndirectedGraph<Integer> dynamicSetupWithStartingNode(UtilTest.graphType graphType, int startingNodeIndex) {
        graph = UtilTest.getGraphByType(graphType);
        dfs = new DFS<>(graph, startingNodeIndex);
        return dfs.run();
    }

    /**
     * Sets up the graph and DFS instance for testing without a specified starting node.
     * @param graphType The type of graph to create.
     * @return The spanning forest obtained by running DFS.
     */
    public UndirectedGraph<Integer> dynamicSetupWithoutStartingNode(UtilTest.graphType graphType) {
        graph = UtilTest.getGraphByType(graphType);
        dfs = new DFS<>(graph);
        return dfs.run();
    }

    /**
     * Tests the connectivity of a grid graph.
     */
    @Override
    @Test
    public void checkReturnedGraphConnectivityOfGrid() {
        UndirectedGraph<Integer> spanningTree = dynamicSetupWithoutStartingNode(UtilTest.graphType.GRID);

        Assert.assertEquals(UtilTest.GRID_GRAPH_TEST_LENGTH * UtilTest.GRID_GRAPH_TEST_LENGTH , spanningTree.getNodeCount());
        Assert.assertEquals(UtilTest.GRID_GRAPH_TEST_LENGTH * UtilTest.GRID_GRAPH_TEST_LENGTH - 1, spanningTree.getEdgeCount());
        Assert.assertEquals(true, UtilTest.isGraphConnected(dfs.getParents()));
    }

    /**
     * Tests the connectivity of a tree graph.
     */
    @Override
    @Test
    public void checkReturnedGraphConnectivityOfTree() {
        UndirectedGraph<Integer> spanningTree = dynamicSetupWithoutStartingNode(UtilTest.graphType.TREE);

        Assert.assertEquals(UtilTest.TREE_GRAPH_TEST_LENGTH , spanningTree.getNodeCount());
        Assert.assertEquals(UtilTest.TREE_GRAPH_TEST_LENGTH - 1, spanningTree.getEdgeCount());
        Assert.assertEquals(true, UtilTest.isGraphConnected(dfs.getParents()));
    }

    /**
     * Tests the connectivity of a forest graph.
     */
    @Override
    @Test
    public void checkReturnedGraphConnectivityOfForest() {
        UndirectedGraph<Integer> spanningForest = dynamicSetupWithoutStartingNode(UtilTest.graphType.FOREST);

        Assert.assertEquals(UtilTest.FOREST_GRAPH_TEST_LENGTH , spanningForest.getNodeCount());
        Assert.assertEquals(UtilTest.FOREST_GRAPH_TEST_LENGTH - UtilTest.MULTIPLE_CONNECTED_COMPONENT_TESTS_NUM, spanningForest.getEdgeCount());
        Assert.assertEquals(UtilTest.MULTIPLE_CONNECTED_COMPONENT_TESTS_NUM > 1 ? false : true , UtilTest.isGraphConnected(dfs.getParents()));
    }

    /**
     * Tests the connectivity of a circle graph.
     */
    @Override
    @Test
    public void checkReturnedGraphConnectivityOfCircle() {
        UndirectedGraph<Integer> spanningTree = dynamicSetupWithoutStartingNode(UtilTest.graphType.CIRCLE);

        Assert.assertEquals(UtilTest.CIRCLE_GRAPH_TEST_LENGTH , spanningTree.getNodeCount());
        Assert.assertEquals(UtilTest.CIRCLE_GRAPH_TEST_LENGTH - 1, spanningTree.getEdgeCount());
        Assert.assertEquals(true, UtilTest.isGraphConnected(dfs.getParents()));
    }

    /**
     * Tests the distances of one connected component graph.
     */
    @Override
    @Test
    public void checkDistanceOfOneConnectedComponentGraph() {
        dynamicSetupWithStartingNode(UtilTest.graphType.GRID, UtilTest.STARTING_NODE_INDEX);

        for(int i=0; i<UtilTest.GRID_GRAPH_TEST_LENGTH*UtilTest.GRID_GRAPH_TEST_LENGTH; i++)
        {
            Assert.assertNotEquals(-1, dfs.distance(i));
        }
    }

    /**
     * Tests the parents and colors of one connected component graph.
     */
    @Override
    @Test
    public void checkParentsAndColorsOfOneConnectedComponentGraph() {
        dynamicSetupWithStartingNode(UtilTest.graphType.GRID, UtilTest.STARTING_NODE_INDEX);

        for(int i=0; i<UtilTest.STARTING_NODE_INDEX; i++)
        {
            Assert.assertEquals(AbstractShortestPaths.Color.BLACK, dfs.getColors().get(i));
            Assert.assertNotEquals(null, dfs.getParents().get(i));
        }

        Assert.assertEquals(AbstractShortestPaths.Color.BLACK, dfs.getColors().get(UtilTest.STARTING_NODE_INDEX));
        Assert.assertEquals(null, dfs.getParents().get(UtilTest.STARTING_NODE_INDEX));

        for(int i=UtilTest.STARTING_NODE_INDEX+1; i<UtilTest.GRID_GRAPH_TEST_LENGTH*UtilTest.GRID_GRAPH_TEST_LENGTH; i++)
        {
            Assert.assertEquals(AbstractShortestPaths.Color.BLACK, dfs.getColors().get(i));
            Assert.assertNotEquals(null, dfs.getParents().get(i));
        }
    }

    /**
     * Tests the connectivity of a graph with empty edges.
     */
    @Override
    @Test
    public void checkReturnedGraphConnectivityOfEmptyEdgesGraph() {
        UndirectedGraph<Integer> emptyEdgesGraph = dynamicSetupWithoutStartingNode(UtilTest.graphType.EMPTY_EDGES);

        Assert.assertEquals(UtilTest.EMPTY_EDGES_GRAPH_TEST_LENGTH , emptyEdgesGraph.getNodeCount());
        Assert.assertEquals(0, emptyEdgesGraph.getEdgeCount());
    }

    /**
     * Tests the connectivity of a graph with one node.
     */
    @Override
    @Test
    public void checkReturnedGraphConnectivityOfGraphWithOneNode() {
        UndirectedGraph<Integer> oneNodeGraph = dynamicSetupWithoutStartingNode(UtilTest.graphType.ONE_NODE);

        Assert.assertEquals(1 , oneNodeGraph.getNodeCount());
        Assert.assertEquals(0, oneNodeGraph.getEdgeCount());
    }
}
