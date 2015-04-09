name := "player"

version := "1.0"

scalaVersion := "2.11.6"

assemblySettings

libraryDependencies ++= Seq(
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