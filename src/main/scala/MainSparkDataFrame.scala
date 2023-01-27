package com.hatebit

import org.apache.spark.sql.SparkSession

object MainSparkDataFrame {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("Unnamed")
      .getOrCreate();
    spark.sparkContext.setLogLevel("ERROR")

    val dfEmps = spark.read.option("header", "true").csv("src/main/resources/employee.txt")
    println(dfEmps)
    println(dfEmps.take(10).mkString("Array(", ", ", ")"))
    dfEmps.printSchema()
    dfEmps.show()

    val dfCr = spark.read.option("header", "true").csv("src/main/resources/country_region.txt")
    dfCr.show()
    println(dfCr.collect().mkString("Array(", ", ", ")"))

    val dfDepDiv = spark.read.option("header", "true").csv("src/main/resources/dept_div.txt")
    dfDepDiv.show()

    dfEmps.createOrReplaceTempView("employees")
    val sqlDfEmps = spark.sql("SELECT * from employees")
    sqlDfEmps.show()
    val dfSqlEmpsByDep = spark.sql("SELECT department, count(*) FROM employees GROUP BY department")
    dfSqlEmpsByDep.show()
    val dfSqlEmpsByDepGender = spark.sql("SELECT department, gender, count(*) FROM employees GROUP BY department, gender")
    dfSqlEmpsByDepGender.show()
    val dfSqlDeps = spark.sql("SELECT DISTINCT department FROM employees")
    dfSqlDeps.show()
    val sqlDfEmps100 = spark.sql("SELECT * FROM employees WHERE id < 100")
    sqlDfEmps100.show()

    val dfJoin = dfEmps.join(dfCr, "region_id")
    println(dfJoin.columns.mkString("Array(", ", ", ")"))
    dfJoin.show()

    val dfJsonDepDiv = spark.read.json("src/main/resources/dept_div.json")
    dfJsonDepDiv.show()
  }

}
