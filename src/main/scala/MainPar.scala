package com.hatebit

import scala.collection.parallel.CollectionConverters.ImmutableIterableIsParallelizable

object MainPar {
  def main(args: Array[String]): Unit = {
    val myRange = 1 to 100
    println(myRange)
    val myParRange = myRange.par
    println(myParRange)
  }
}
