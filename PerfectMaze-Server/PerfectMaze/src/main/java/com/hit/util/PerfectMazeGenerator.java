package com.hit.util;

import com.hit.dm.PerfectMazeBoard;
import com.hit.undirectedGraph.Edge;
import com.hit.undirectedGraph.UndirectedGraph;

import java.util.Arrays;

/**
 * Utility class for generating perfect maze boards from spanning trees.
 */
public class PerfectMazeGenerator {

    private static final int MAZE_MIN_SIZE = 5;
    private static final int MAZE_MAX_SIZE = 50;

    /**
     * Generates a perfect maze board from the provided spanning tree and maze size.
     *
     * @param spanningTree The spanning tree representing the maze.
     * @param mazeSize     The size of the maze board to be generated.
     * @return A perfect maze board generated from the spanning tree, or {@code null} if the inputs are invalid.
     */
    public static PerfectMazeBoard generateMazeFromSpanningTree(UndirectedGraph<Integer> spanningTree, int mazeSize) {
        if (spanningTree == null || !isMazeSizeValid(mazeSize)) return null;

        int[][] perfectMaze = new int[2 * mazeSize - 1][2 * mazeSize - 1];

        for (int i = 0; i < 2 * mazeSize - 1; i++) {
            Arrays.fill(perfectMaze[i], 0);
        }

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                perfectMaze[2 * i][2 * j] = 1;

                int nodeIndex = i * mazeSize + j;

                if (spanningTree.getAdjacencyList(nodeIndex).isContainsEdge(new Edge(nodeIndex, nodeIndex + 1))) {
                    perfectMaze[2 * i][2 * j + 1] = 1;
                }

                if (spanningTree.getAdjacencyList(nodeIndex).isContainsEdge(new Edge(nodeIndex, nodeIndex + mazeSize))) {
                    perfectMaze[2 * i + 1][2 * j] = 1;
                }
            }
        }

        return new PerfectMazeBoard(perfectMaze, mazeSize);
    }

    /**
     * Checks if the provided maze size is within valid range.
     *
     * @param mazeSize The size of the maze to be checked.
     * @return {@code true} if the maze size is valid, {@code false} otherwise.
     */
    public static boolean isMazeSizeValid(int mazeSize) {
        return mazeSize >= MAZE_MIN_SIZE && mazeSize <= MAZE_MAX_SIZE;
    }
}
