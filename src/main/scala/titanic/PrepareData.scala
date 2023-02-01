package com.hatebit
package titanic

import org.apache.spark.sql.{DataFrame, Row}
import org.apache.spark.sql.functions.{avg, udf}

object PrepareData {

  def prepareData(inputData: DataFrame): DataFrame = {

    val averageAge = inputData.select("Age")
      .agg(avg("Age"))
      .collect() match {
      case Array(Row(avg: Double)) => avg
      case _ => 0
    }

    val averageFare = inputData.select("Fare")
      .agg(avg("Fare"))
      .collect() match {
      case Array(Row(avg: Double)) => avg
      case _ => 0
    }

    def embarked = udf { str: String =>
      str match {
        case "" => "S"
        case null => "S"
        case str => str
      }
    }

    inputData.na.fill(Map("Fare" -> averageFare, "Age" -> averageAge))
      .withColumn("Embarked", embarked(inputData.col("Embarked")))
  }


}
