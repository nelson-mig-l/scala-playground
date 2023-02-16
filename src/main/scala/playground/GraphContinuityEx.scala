package com.hatebit
package playground

import org.geotools.graph.build.basic.BasicGraphBuilder
import org.geotools.graph.structure.Graph
import org.geotools.graph.structure.basic.BasicEdge
import org.geotools.graph.util.graph.GraphPartitioner

object GraphContinuityEx {

  def getConnectivityComponentCount(arrRoadSegmentIds: Array[Int], arrRightIds: Array[Int], fullGraph: Boolean): Int = {
    if (arrRoadSegmentIds.length == arrRightIds.length) {
      val mapRoadSegments = arrRoadSegmentIds.foldLeft(Map[Int, ContinuityNode]()) { (m, t) => m + (t-> new ContinuityNode(t)) }
      val mapRights = arrRightIds.foldLeft(Map[Int, ContinuityNode]()) { (m, t) => m + (t-> new ContinuityNode(t)) }
      val arrEdges = arrRoadSegmentIds
        .zip(arrRightIds)
        .map { e =>
          new BasicEdge(
            mapRoadSegments(e._1),
            mapRights(e._2)
          )
        }

      if (fullGraph) {
        val nodes = mapRoadSegments.values.concat(mapRights.values).toArray
        getNumberOfConnectedComponents2(arrEdges, nodes)
      } else {
        val subEdges = arrEdges.filter(e =>
          mapRoadSegments.contains(e.getNodeA.getObject.asInstanceOf[Int]) &&
            mapRoadSegments.contains(e.getNodeB.getObject.asInstanceOf[Int])
        )
        getNumberOfConnectedComponents2(subEdges, mapRoadSegments.values.toArray)
      }
    } else -1
  }

  def getNumberOfConnectedComponents2(
                                      edges: Array[BasicEdge],
                                      nodes: Array[ContinuityNode],
                                    ): Int = {
    // val graph = buildGraph(edges, useFullGraph)
    val builder = new BasicGraphBuilder

    nodes.foreach( node => builder.addNode(node))
    edges.foreach { edge => builder.addEdge(edge) }

    val graph = builder.getGraph
    getNumberOfConnectedComponents(graph)
  }

  def getNumberOfConnectedComponents(graph: Graph): Int = {
    println("Graph with " + graph.getNodes.size() + " nodes and " + graph.getEdges.size() + " edges")
    val partitioner = new GraphPartitioner(graph)
    if (partitioner.partition) partitioner.getPartitions.size
    else throw new RuntimeException("Failed to get connected components of graph.")
  }

}
