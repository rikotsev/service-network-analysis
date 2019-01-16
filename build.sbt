name := """ServicesNetworkAnalysis!!!"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
lazy val frms = (project in file("./modules/graphframes")).settings(
  organization              := "com.rikotsev.graphframes",
  name                      := "graphframes",
  version                   := "0.5.0-spark2.1-s_2.11",
  crossPaths                := false,  //don't add scala version to this artifacts in repo
  publishMavenStyle         := true,
  autoScalaLibrary          := false,  //don't attach scala libs as dependencies
  description               := "project for publishing dependency to maven repo, use 'sbt publishLocal' to install it",
  packageBin in Compile     := baseDirectory.value / s"${name.value}.jar"
)

scalaVersion := "2.11.7"

val sparkPack = "org.apache.spark"
val sparkVersion = "2.1.1"


resolvers += Resolver.url("SparkPackages", url("https://dl.bintray.com/spark-packages/maven/"))

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-core" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"
dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.7"

libraryDependencies ++= Seq(
  "org.codehaus.janino" % "janino" % "3.0.7",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "com.typesafe.scala-logging" %% "scala-logging-api" % "2.1.2",
  "org.postgresql" % "postgresql" % "42.2.2",
  "com.github.jbytecode" % "RCaller" % "3.0",
  javaJdbc,
  cache,
  javaWs
)
