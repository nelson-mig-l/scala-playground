package com.hatebit
package playground

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object MainRdd {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("Unnamed")
      .getOrCreate();
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val a = sc.parallelize(List("A", "B", "C"), 3)
    val b = sc.parallelize(List("X", "Y"), 2)
    val c = a.cartesian(b)
    println(c.toDebugString)
    c.collect().foreach(println)
  }

}
