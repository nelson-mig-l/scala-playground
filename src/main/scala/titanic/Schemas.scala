package com.hatebit
package titanic

import org.apache.spark.sql.types.{FloatType, IntegerType, StringType, StructType}

object Schemas {
  val trainSchema = (new StructType)
    .add("PassengerId", IntegerType)
    .add("Survived", IntegerType)
    .add("Pclass", IntegerType)
    .add("Name", StringType)
    .add("Sex", StringType)
    .add("Age", FloatType)
    .add("SibSp", IntegerType)
    .add("Parch", IntegerType)
    .add("Ticket", StringType)
    .add("Fare", FloatType)
    .add("Cabin", StringType)
    .add("Embarked", StringType)
}
