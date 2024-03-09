package com.hit.algorithm;

import com.hit.undirectedGraph.UndirectedGraph;
import org.junit.Assert;
import org.junit.Test;

public class BFSTest implements IShortestPathsTest{

    private UndirectedGraph<Integer> graph;
    private BFS<Integer> bfs;

    @Override
    public UndirectedGraph<Integer> dynamicSetupWithStartingNode(UtilTest.graphType graphType, int startingNodeIndex) {
        graph = UtilTest.getGraphByType(graphType);
        bfs = new BFS<>(graph, startingNodeIndex);
        return bfs.run();
    }

    @Override
    @Test
    public void checkReturnedGraphConnectivityOfGrid() {
        UndirectedGraph<Integer> spanningTree = dynamicSetupWithStartingNode(UtilTest.graphType.GRID, UtilTest.STARTING_NODE_INDEX);

        Assert.assertEquals(UtilTest.GRID_GRAPH_TEST_LENGTH * UtilTest.GRID_GRAPH_TEST_LENGTH , spanningTree.getNodeCount());
        Assert.assertEquals(UtilTest.GRID_GRAPH_TEST_LENGTH * UtilTest.GRID_GRAPH_TEST_LENGTH - 1, spanningTree.getEdgeCount());
        Assert.assertEquals(true, UtilTest.isGraphConnected(bfs.getParents()));
    }

    @Override
    @Test
    public void checkReturnedGraphConnectivityOfTree() {
        UndirectedGraph<Integer> spanningTree = dynamicSetupWithStartingNode(UtilTest.graphType.TREE, UtilTest.STARTING_NODE_INDEX);

        Assert.assertEquals(UtilTest.TREE_GRAPH_TEST_LENGTH , spanningTree.getNodeCount());
        Assert.assertEquals(UtilTest.TREE_GRAPH_TEST_LENGTH - 1, spanningTree.getEdgeCount());
        Assert.assertEquals(true, UtilTest.isGraphConnected(bfs.getParents()));
    }

    @Override
    @Test
    public void checkReturnedGraphConnectivityOfForest() {
        UndirectedGraph<Integer> spanningTree = dynamicSetupWithStartingNode(UtilTest.graphType.FOREST, UtilTest.STARTING_NODE_INDEX);

        Assert.assertEquals(spanningTree.getNodeCount() - 1, spanningTree.getEdgeCount());
        Assert.assertEquals(spanningTree.getNodeCount() == UtilTest.TREE_GRAPH_TEST_LENGTH ? true : false, UtilTest.isGraphConnected(bfs.getParents()));
    }

    @Override
    @Test
    public void checkReturnedGraphConnectivityOfCircle() {
        UndirectedGraph<Integer> spanningTree = dynamicSetupWithStartingNode(UtilTest.graphType.CIRCLE, UtilTest.STARTING_NODE_INDEX);

        Assert.assertEquals(UtilTest.CIRCLE_GRAPH_TEST_LENGTH , spanningTree.getNodeCount());
        Assert.assertEquals(UtilTest.CIRCLE_GRAPH_TEST_LENGTH - 1, spanningTree.getEdgeCount());
        Assert.assertEquals(true, UtilTest.isGraphConnected(bfs.getParents()));
    }

    @Override
    @Test
    public void checkDistanceOfOneConnectedComponentGraph() {
        dynamicSetupWithStartingNode(UtilTest.graphType.GRID, UtilTest.STARTING_NODE_INDEX);

        for(int i=0; i<UtilTest.GRID_GRAPH_TEST_LENGTH*UtilTest.GRID_GRAPH_TEST_LENGTH; i++)
        {
            Assert.assertNotEquals(-1, bfs.distance(i));
        }
    }

    @Override
    @Test
    public void checkParentsAndColorsOfOneConnectedComponentGraph() {
        dynamicSetupWithStartingNode(UtilTest.graphType.GRID, UtilTest.STARTING_NODE_INDEX);

        for(int i=0; i<UtilTest.STARTING_NODE_INDEX; i++)
        {
            Assert.assertEquals(AbstractShortestPaths.Color.BLACK, bfs.getColors().get(i));
            Assert.assertNotEquals(null, bfs.getParents().get(i));
        }

        Assert.assertEquals(AbstractShortestPaths.Color.BLACK, bfs.getColors().get(UtilTest.STARTING_NODE_INDEX));
        Assert.assertEquals(null, bfs.getParents().get(UtilTest.STARTING_NODE_INDEX));

        for(int i=UtilTest.STARTING_NODE_INDEX+1; i<UtilTest.GRID_GRAPH_TEST_LENGTH*UtilTest.GRID_GRAPH_TEST_LENGTH; i++)
        {
            Assert.assertEquals(AbstractShortestPaths.Color.BLACK, bfs.getColors().get(i));
            Assert.assertNotEquals(null, bfs.getParents().get(i));
        }
    }

    @Override
    @Test
    public void checkReturnedGraphConnectivityOfEmptyEdgesGraph() {
        UndirectedGraph<Integer> emptyEdgesGraph = dynamicSetupWithStartingNode(UtilTest.graphType.EMPTY_EDGES, UtilTest.STARTING_NODE_INDEX);

        Assert.assertEquals(0, emptyEdgesGraph.getEdgeCount());
        Assert.assertEquals(1, emptyEdgesGraph.getNodeCount());
    }

    @Override
    @Test
    public void checkReturnedGraphConnectivityOfGraphWithOneNode() {
        UndirectedGraph<Integer> oneNodeGraph = dynamicSetupWithStartingNode(UtilTest.graphType.ONE_NODE, UtilTest.STARTING_NODE_INDEX);

        Assert.assertEquals(0, oneNodeGraph.getEdgeCount());
        Assert.assertEquals(1, oneNodeGraph.getNodeCount());
    }

}
