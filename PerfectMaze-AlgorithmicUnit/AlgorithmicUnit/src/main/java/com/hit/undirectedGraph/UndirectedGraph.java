package com.hit.undirectedGraph;


import java.util.*;

public class UndirectedGraph<K>{
    private Map<K,Node<K>> undirectedGraph;
    public UndirectedGraph() {this.undirectedGraph = new HashMap<>();}
    public Map<K,Node<K>> getDirectedGraph() {
        return undirectedGraph;
    }
    public List<K> getNodeList() {
        List<K> nodeList = new ArrayList<>(this.undirectedGraph.keySet().stream().toList());
        Collections.shuffle(nodeList);
        return nodeList;}
    public boolean addNodeToGraph(K nodeIndex){
        return undirectedGraph.put(nodeIndex , new Node(nodeIndex)) != null;
    }
    public boolean addEdgeToGraph(K nodeStartingIndex , K nodeEndingIndex){

        if(!isContainsNodeFromGraph(nodeStartingIndex) || !isContainsNodeFromGraph(nodeEndingIndex)) return false;

        return undirectedGraph.get(nodeStartingIndex).getAdjacencyList().addEdge(new Edge(nodeStartingIndex , nodeEndingIndex))
              && undirectedGraph.get(nodeEndingIndex).getAdjacencyList().addEdge(new Edge(nodeEndingIndex, nodeStartingIndex));
    }
    public boolean removeNodeFromGraph(K nodeIndex){

        if(undirectedGraph.remove(nodeIndex) == null) return false;

        undirectedGraph.forEach((tNodeIndex , tNode) -> tNode.getAdjacencyList().removeEdgeByNodeIndex(nodeIndex));

        return true;
    }
    public boolean removeEdgeFromGraph(K nodeStartingIndex, K nodeEndingIndex){

        if(!isContainsNodeFromGraph(nodeStartingIndex) || !isContainsNodeFromGraph(nodeEndingIndex)) return false;

        return undirectedGraph.get(nodeStartingIndex).getAdjacencyList().removeEdge(nodeStartingIndex , nodeEndingIndex)
              && undirectedGraph.get(nodeEndingIndex).getAdjacencyList().removeEdge(nodeEndingIndex , nodeStartingIndex);
    }
    public AdjacencyList getAdjacencyList(K nodeIndex){

        if(!isContainsNodeFromGraph(nodeIndex)) return null;

        return undirectedGraph.get(nodeIndex).getAdjacencyList();
    }
    private boolean isContainsNodeFromGraph(K nodeIndex){
        return undirectedGraph.get(nodeIndex) != null;
    }
    public int getNodeCount() {return this.undirectedGraph.size();}
    public int getEdgeCount() {return undirectedGraph.values().stream().mapToInt(Node::getEdgeCount).reduce(0 , Integer::sum) / 2;}
    public void printUndirectedGraph(){
        undirectedGraph.forEach((tNodeIndex , tNode) -> {
            System.out.println("=============================================");
            System.out.println("NODE: " + tNodeIndex);

            if(tNode.getAdjacencyList().getEdgeList().size() == 0 ){
                System.out.println("NO EDGES");
            }
            else{
                List<Edge<K>> edgeList = tNode.getAdjacencyList().getEdgeList();
                for(Edge edge : edgeList){
                    System.out.println("( " + edge.getStartingNodeIndex() + " , " + edge.getEndingNodeIndex() + " )");
                }
                System.out.println("=============================================");
            }
        });

        System.out.println("Graph's Node Count: " + getNodeCount());
        System.out.println("Graph's Edges Count: " + getEdgeCount());
    }
}
