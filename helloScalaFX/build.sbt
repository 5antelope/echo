name := "helloScala"

version := "1.0"

<<<<<<< HEAD

=======
>>>>>>> 81e048a8ef508fe5859444ab6b420ec75c0714a9
scalaVersion := "2.11.6"


libraryDependencies ++= Seq(
<<<<<<< HEAD
  "org.scalafx"            %% "scalafx"         % "8.0.40-R8",
  "org.scala-lang.modules" %% "scala-xml"       % "1.0.3",
  "com.typesafe.play"      %% "play-json"       % "2.3.0"
)


resolvers += Opts.resolver.sonatypeSnapshots


scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xlint")


unmanagedResourceDirectories in Compile <+= baseDirectory { _/"src/main/scala"}
=======
  "org.scalafx"       %% "scalafx"          % "8.0.40-R8"
)

lazy val root = (project in file(".")).enablePlugins(play.PlayScala)

// Sources should also be copied to output, so the sample code, for the viewer,
// can be loaded from the same file that is used to execute the example
unmanagedResourceDirectories in Compile <+= baseDirectory { _/"src/main/scala/gui"}
>>>>>>> 81e048a8ef508fe5859444ab6b420ec75c0714a9


shellPrompt := { state => System.getProperty("user.name") + ":" + Project.extract(state).currentRef.project + "> " }


fork := true

fork in Test := true

javaOptions += "-Dfile.encoding=UTF-8"
