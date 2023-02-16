package com.hatebit
package playground

object GraphMain {


  def main(args: Array[String]): Unit = {
    val roadSegmentIds = Array(10, 10, 90)
    val rightIds = Array(20, 40, 40)

    val fg = GraphContinuityEx.getConnectivityComponentCount(roadSegmentIds, rightIds, true)
    val sg = GraphContinuityEx.getConnectivityComponentCount(roadSegmentIds, rightIds, false)
    println(fg)
    println(sg)

  }

}
