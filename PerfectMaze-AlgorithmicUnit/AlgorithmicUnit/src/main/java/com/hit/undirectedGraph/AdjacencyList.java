package com.hit.undirectedGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AdjacencyList<T>{

    private List<Edge<T>> edgeList;

    public AdjacencyList(){
        this.edgeList = new ArrayList<>();
    }
    public AdjacencyList(List<Edge<T>> adjancencyList){
        this.edgeList = adjancencyList;
    }
    public List<Edge<T>> getEdgeList() {
        Collections.shuffle(this.edgeList);
        return this.edgeList;}

    public boolean addEdge(Edge<T> edge){
        if(isContainsEdge(edge)) return false;

        this.edgeList.add(edge);

        return true;
    }
    public boolean removeEdge(T nodeStartingIndex, T nodeEndingIndex) {

        List<Edge<T>> filteredEdgeList = this.edgeList.stream().filter(tedge -> (!tedge.getStartingNodeIndex().equals(nodeStartingIndex)) || (!tedge.getEndingNodeIndex().equals(nodeEndingIndex))).collect(Collectors.toList());

        boolean isRemoved = filteredEdgeList.size() < this.edgeList.size();

        this.edgeList = filteredEdgeList;

        return isRemoved;
    }
    public boolean removeEdgeByStartingNode(T startingNodeIndex){

        List<Edge<T>> filteredEdgeList = this.edgeList.stream().filter(tedge -> (!tedge.getStartingNodeIndex().equals(startingNodeIndex))).collect(Collectors.toList());

        boolean isRemoved = filteredEdgeList.size() < this.edgeList.size();

        this.edgeList = filteredEdgeList;

        return isRemoved;
    }
    public boolean removeEdgeByEndingNode(T endingNodeIndex){

        List<Edge<T>> filteredEdgeList = this.edgeList.stream().filter(tedge -> (!tedge.getEndingNodeIndex().equals(endingNodeIndex))).collect(Collectors.toList());

        boolean isRemoved = filteredEdgeList.size() < this.edgeList.size();

        this.edgeList = filteredEdgeList;

        return isRemoved;
    }
    public boolean removeEdgeByNodeIndex(T containedNodeIndex){

        List<Edge<T>> filteredEdgeList = this.edgeList.stream().filter(tedge -> (!tedge.getStartingNodeIndex().equals(containedNodeIndex)) && (!tedge.getEndingNodeIndex().equals(containedNodeIndex))).collect(Collectors.toList());

        boolean isRemoved = filteredEdgeList.size() < this.edgeList.size();

        this.edgeList = filteredEdgeList;

        return isRemoved;
    }

    public boolean isContainsEdge (Edge<T> searchedEdge){
        for(Edge<T> currentEdge : this.edgeList){
            if(currentEdge.equals(searchedEdge)){
                return true;
            }
        }
        return false;
    }

}