# Scala playground

[LinkedIn Learning - Scala Essential Training for Data Science](https://www.linkedin.com/learning/scala-essential-training-for-data-science)

## Chapter 1 - Introduction to scala

Entirely done on scala REPL.

## Chapter 2 - Parallel Processing in Scala

See `src/main/scala/MainPar.scala`

Parallel collections have been moved in Scala 2.13 to separate module `scala/scala-parallel-collection`.

```sbt
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
```

**Note:** the double percent "`%%`" indicates that the dependency will include the project scala version. 

## Chapter 3 - Using SQL in Scala

See `src/main/scala/MainSql.scala`

Instead of installing Postgresql database was hosted at [ElephantSQL](https://www.elephantsql.com/)

When running `MainSql.scala` remember to provide the password as a command line argument.

```sbt
libraryDependencies += "org.postgresql" % "postgresql" % "42.5.1"
```

## Chapter 4 - Scala and Spark RDDs

See `src/main/scala/MainSpark.scala`

No need to install spark. Just add the following dependencies.

```sbt
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.3.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.3.1"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.3.1"
```
**Take note that:**
 * on scala 2.13 you cannot `Random.shuffle` a `Range`. Use `Range.toList` and then shuffle it.
 * printing arrays doesnt work as in REPL. You need to use the `mkString` method. e.g(`array.mkString("Array(", ", ", ")")`).

