# Scala playground

[LinkedIn Learning - Scala Essential Training for Data Science](https://www.linkedin.com/learning/scala-essential-training-for-data-science)

## Chapter 2 - Parallel Processing in Scala

Parallel collections have been moved in Scala 2.13 to separate module scala/scala-parallel-collection
```sbt
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
```

## Chapter 3 - Using SQL in Scala

Database hosted at [ElephantSQL](https://www.elephantsql.com/)

```sbt
libraryDependencies += "org.postgresql" % "postgresql" % "42.5.1"
```