package com.hatebit
package playground

import org.geotools.graph.build.basic.BasicGraphBuilder
import org.geotools.graph.structure.Graph
import org.geotools.graph.structure.basic.{BasicEdge, BasicNode}
import org.geotools.graph.util.graph.GraphPartitioner

object GraphContinuity {

  def getConnectivityComponentCount(a: Array[Int], b: Array[Int], c: Boolean): Int =
    if (a.length == b.length) {
      val nodes: collection.mutable.Map[Long, ContinuityNode] = collection.mutable.Map()
      val edges = a
        .zip(b)
        .map { e =>
          val nodeA = nodes.getOrElseUpdate(e._1, new ContinuityNode(e._1))
          val nodeB = nodes.getOrElseUpdate(e._2, new ContinuityNode(e._2))
          new BasicEdge(nodeA, nodeB)
        }
      getNumberOfConnectedComponents(edges, nodes, c)
    } else -1

  def getNumberOfConnectedComponents(
                                      edges: Array[BasicEdge],
                                      nodes: collection.mutable.Map[Long, ContinuityNode],
                                      useFullGraph: Boolean
                                    ): Int = {
    // val graph = buildGraph(edges, useFullGraph)
    val builder = new BasicGraphBuilder
    edges.foreach { edge =>
      builder.addNode(nodes(edge.getNodeA.getObject.asInstanceOf[Long]))
      if (useFullGraph){ builder.addNode(nodes(edge.getNodeB.getObject.asInstanceOf[Long]))
      builder.addEdge(edge) }
    }

    val graph = builder.getGraph
    getNumberOfConnectedComponents(graph)
  }

  def getNumberOfConnectedComponents(graph: Graph): Int = {
    println("Graph with "+graph.getNodes.size()+" nodes and "+graph.getEdges.size()+" edges")
    val partitioner = new GraphPartitioner(graph)
    if (partitioner.partition) partitioner.getPartitions.size
    else throw new RuntimeException("Failed to get connected components of graph.")
  }

}
