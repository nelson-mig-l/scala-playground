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
