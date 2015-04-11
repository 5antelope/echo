name := "akka-streaming"

version := "1.0"

scalaVersion := "2.11.6"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-contrib" % "2.3.4",

  "com.typesafe.akka" %% "akka-actor" % "2.3.2",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.2",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.2",
  "ch.qos.logback" % "logback-classic" % "1.0.9",
  "com.typesafe.akka" %% "akka-cluster" % "2.3.2",
  "com.typesafe.akka" %% "akka-remote" % "2.3.2",
  "org.scalatest" %% "scalatest" % "2.1.5",


  "com.typesafe.akka" % "akka-stream-experimental_2.11" % "1.0-M2",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",

  "org.scalafx"            %% "scalafx"          % "8.0.31-R7",
  "org.scala-lang.modules" %% "scala-xml"        % "1.0.3",
  "com.typesafe.play"      %% "play-json"        % "2.3.0"
)

resolvers += Opts.resolver.sonatypeSnapshots


scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xlint")


unmanagedResourceDirectories in Compile <+= baseDirectory { _/"src/main/scala"}


shellPrompt := { state => System.getProperty("user.name") + ":" + Project.extract(state).currentRef.project + "> " }


fork := true

fork in Test := true