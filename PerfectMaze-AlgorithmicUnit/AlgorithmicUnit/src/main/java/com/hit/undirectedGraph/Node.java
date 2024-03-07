package com.hit.undirectedGraph;

class Node<T>{
    private T nodeIndex;
    private AdjacencyList<T> adjacencyList;

    public Node(T nodeIndex){
        this.nodeIndex = nodeIndex;
        this.adjacencyList = new AdjacencyList<>();
    }
    public Node(T nodeIndex, AdjacencyList adjacencyList){
        this.nodeIndex = nodeIndex;
        this.adjacencyList = adjacencyList;
    }

    public T getNodeIndex(){return this.nodeIndex;}
    public AdjacencyList<T> getAdjacencyList() {return this.adjacencyList;}
    public int getEdgeCount() {return this.adjacencyList.getEdgeList().size();}

    public boolean equals(Node<T> otherNode) {
        return nodeIndex == otherNode.nodeIndex;
    }
}