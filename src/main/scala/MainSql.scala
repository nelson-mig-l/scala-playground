package com.hatebit

import java.sql.DriverManager;
import java.sql.Connection;

// https://www.elephantsql.com/docs/java.html

object MainSql {
  // args(0) should contain password
  def main(args: Array[String]): Unit = {
    println("Hello world!")

    val driver = "org.postgresql.Driver"
    var url = "jdbc:postgresql://hattie.db.elephantsql.com/sbwayrso"
    val loaded = Class.forName(driver)
    println(loaded)
    //var connection: Connection = DriverManager.getConnection(url);
    var connection: Connection = DriverManager.getConnection(url, "sbwayrso", args(0));

    val statement = connection.createStatement()
    val resultSet = statement.executeQuery("SELECT * FROM emps LIMIT 5")
    println(resultSet.next())
    println(resultSet.getString("last_name"))

    val rs = statement.executeQuery("select * from company_divisions")
    while (rs.next()) {
      val dept = rs.getString("department")
      val compDiv = rs.getString("company_division")
      println(dept + " -> " + compDiv);
    }

    val query = "select * from company_regions where region_id > ?"
    val ps = connection.prepareStatement(query)
    ps.setInt(1, 5)
    val prs = ps.executeQuery()
    prs.next()
    println(prs.getInt("region_id"))
    println(prs.getString("company_regions"))
    println(prs.getString("country"))
  }
}