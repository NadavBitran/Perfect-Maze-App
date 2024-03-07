package com.hit.util;

import com.hit.undirectedGraph.UndirectedGraph;

public class UndirectedGraphCreator {
    /**
     * Creates a grid graph of size n x n.
     * @param n The size of the grid graph.
     * @return The created grid graph.
     */
    public static UndirectedGraph<Integer> createNxNGridGraph(int n)
    {
        UndirectedGraph<Integer> undirectedGraph = new UndirectedGraph<>();

        for(int row=0; row<=n-1;row++)
        {
            for(int col=0;col<=n-1;col++)
            {
                undirectedGraph.addNodeToGraph(row*n + col);
            }
        }

        for(int row=0; row<n-1; row++)
        {
            for(int col=0; col<n-1; col++)
            {
                int currentNode = row*n + col;
                undirectedGraph.addEdgeToGraph(currentNode , currentNode + 1);
                undirectedGraph.addEdgeToGraph(currentNode , currentNode + n);
            }
            undirectedGraph.addEdgeToGraph(row*n + n-1 , (row+1)*n + n-1);

        }

        for(int col=0; col<n-1; col++)
        {
            int currentNode = (n-1)*n + col;
            undirectedGraph.addEdgeToGraph(currentNode , currentNode + 1);
        }

        return undirectedGraph;
    }

    /**
     * Creates a tree graph with n nodes.
     * @param n The number of nodes in the tree graph.
     * @return The created tree graph.
     */
    public static UndirectedGraph<Integer> createTreeGraph(int n)
    {
        UndirectedGraph<Integer> undirectedGraph = new UndirectedGraph<>();

        for(int i=0; i<=n-1; i++)
        {
            undirectedGraph.addNodeToGraph(i);
        }

        for(int i=0; i<=n-2; i++)
        {
            undirectedGraph.addEdgeToGraph(i , i+1);
        }

        return undirectedGraph;
    }

    /**
     * Creates a forest graph with n nodes.
     * @param n The number of nodes in the forest graph.
     * @return The created forest graph.
     */
    public static UndirectedGraph<Integer> createForestGraph(int n, int connectedComponentAmount)
    {
        UndirectedGraph<Integer> undirectedGraph = new UndirectedGraph<>();

        for(int i=0; i<=n-1; i++)
        {
            undirectedGraph.addNodeToGraph(i);
        }

        for(int i=0; i<=n-connectedComponentAmount-1; i++)
        {
            undirectedGraph.addEdgeToGraph(i , i+1);
        }

        return undirectedGraph;
    }

    /**
     * Creates a circle graph with n nodes.
     * @param n The number of nodes in the circle graph.
     * @return The created circle graph.
     */
    public static UndirectedGraph<Integer> createCircleGraph(int n)
    {
        UndirectedGraph<Integer> undirectedGraph = new UndirectedGraph<>();

        for(int i=0; i<=n-1; i++)
        {
            undirectedGraph.addNodeToGraph(i);
        }

        for(int i=0; i<=n-2; i++)
        {
            undirectedGraph.addEdgeToGraph(i , i+1);
        }

        undirectedGraph.addEdgeToGraph(n-1, 0);

        return undirectedGraph;
    }

    /**
     * Creates a graph with empty edges and n nodes.
     * @param n The number of nodes in the graph.
     * @return The created graph with empty edges.
     */
    public static UndirectedGraph<Integer> createEmptyEdgesGraph(int n)
    {
        UndirectedGraph<Integer> undirectedGraph = new UndirectedGraph<>();

        for(int i=0; i<=n-1; i++)
        {
            undirectedGraph.addNodeToGraph(i);
        }

        return undirectedGraph;
    }
}
