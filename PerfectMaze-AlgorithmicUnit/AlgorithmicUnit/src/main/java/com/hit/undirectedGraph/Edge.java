package com.hit.undirectedGraph;

public class Edge<T>{
    private T startingNodeIndex;
    private T endingNodeIndex;

    public Edge(T startingNode , T endingNode ){
        this.startingNodeIndex = startingNode;
        this.endingNodeIndex = endingNode;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj == this){
            return true;
        }

        if(obj.getClass() != this.getClass()){
            return false;
        }

        Edge otherEdge = (Edge) obj;

        return startingNodeIndex.equals(otherEdge.startingNodeIndex) && endingNodeIndex.equals(otherEdge.endingNodeIndex);
    }

    public T getStartingNodeIndex() {
        return startingNodeIndex;
    }

    public T getEndingNodeIndex() {
        return endingNodeIndex;
    }

}