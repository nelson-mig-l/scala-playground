ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "untitled",
    idePackagePrefix := Some("com.hatebit")
  )

// Chapter 2
// By using the double percent "%%" sbt will add your project’s binary Scala version to the artifact name
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"
// Chapter 3
libraryDependencies += "org.postgresql" % "postgresql" % "42.5.1"
// Chapter 4
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.3.1"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.3.1"
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "3.3.1"

resolvers += "osgeo" at "https://repo.osgeo.org/repository/release/"
libraryDependencies += "org.geotools" % "gt-graph" % "25.2"

