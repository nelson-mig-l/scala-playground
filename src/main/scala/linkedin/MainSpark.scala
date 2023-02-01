package com.hatebit
package linkedin

import org.apache.spark.SparkContext
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.sql.SparkSession

import scala.util.Random

object MainSpark {


  def main(args: Array[String]): Unit = {
    val bigRng = Random.shuffle(Range.inclusive(1, 100000).toList)
    println(bigRng)
    println(bigRng.length)

    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("Unnamed")
      .getOrCreate();
    val sc: SparkContext = spark.sparkContext
    sc.setLogLevel("ERROR")

    val bigPRng = sc.parallelize(bigRng)
    println(bigPRng.mean())
    println(bigPRng.popStdev())

    println(bigPRng.take(25).mkString("Array(", ", ", ")"))

    val bigPRng2 = bigPRng.map(_ * 2)
    println(bigPRng2.take(25).mkString("Array(", ", ", ")"))
    println(bigPRng2.mean())

    val bigBool = bigPRng2.map(div)
    println(bigBool.take(25).mkString("Array(", ", ", ")"))

    val republic = sc.textFile("data/pg1497.txt")
    republic.take(25).foreach(println)
    val linesWithSocrates = republic.filter(line => line.contains("Socrates"))
    linesWithSocrates.take(10).foreach(println)
    println(linesWithSocrates.toDebugString)

    val x = bigPRng2.takeSample(true, 1000)
    println(x.length)
    println(x.mkString("Array(", ", ", ")"))
    println(bigPRng2.stats())

    val series1 = Array.fill(100000)(Random.nextDouble)
    val series2 = Array.fill(100000)(Random.nextDouble)
    val pSeries1 = sc.parallelize(series1)
    val pSeries2 = sc.parallelize(series2)
    val myCorrelation: Double = Statistics.corr(pSeries1, pSeries2, "pearson")
    println(myCorrelation)
    val distTest = Statistics.kolmogorovSmirnovTest(pSeries1, "norm", 0, 1)
    println(distTest)
  }

  def div(x: Int): Boolean = (x % 3) == 0
}
