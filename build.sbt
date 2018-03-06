import scala.io.Source

organization in ThisBuild := "com.faberoh"
scalaVersion in ThisBuild := "2.12.4"
dependencyOverrides in ThisBuild += "org.scala-lang" % "scala-compiler" % scalaVersion.value

val V = new {
  val http4s = "0.18.0"
  val circe = "0.9.1"
}

val confDirectorySetting = settingKey[File]("obtains the file pointing to the root conf directory")
confDirectorySetting in ThisBuild := (baseDirectory in ThisBuild).value / "conf"

val devConfSettings = settingKey[Seq[String]]("obtains the settings to apply when starting up the JVM")
devConfSettings in ThisBuild := Source.fromFile(confDirectorySetting.value / "application.development.ini")
  .getLines().toSeq.map(line => if (line.startsWith("-J-")) line.drop(2) else line)

val commonSettings = Seq(
  cancelable := true,
  fork := true,
  logBuffered := false,
  scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-deprecation",
    "-feature",
    "-unchecked",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-Ypartial-unification"
  )
)

val dependenciesSettings = Seq(
  libraryDependencies ++= Seq(
    "org.http4s"           %% "http4s-core"               % V.http4s,
    "org.http4s"           %% "http4s-dsl"                % V.http4s,
    "org.http4s"           %% "http4s-blaze-server"       % V.http4s,
    "org.http4s"           %% "http4s-circe"              % V.http4s,
    "org.http4s"           %% "http4s-blaze-client"       % V.http4s,
    "io.circe"             %% "circe-core"                % V.circe,
    "io.circe"             %% "circe-generic"             % V.circe,
    "io.circe"             %% "circe-generic-extras"      % V.circe,
    "io.circe"             %% "circe-parser"              % V.circe,
    "io.circe"             %% "circe-java8"               % V.circe,
    "com.gilt"             %% "gfc-util"                  % "0.1.7",
    "com.gilt"             %% "gfc-concurrent"            % "0.3.8",
    "ch.qos.logback"        % "logback-classic"           % "1.2.3",
    "org.scalacheck"       %% "scalacheck"                % "1.13.5" % Test,
    "org.scalatest"        %% "scalatest"                 % "3.0.4"  % Test,
  )
)

lazy val orderApi = project.in(file("."))
  .settings(commonSettings ++ dependenciesSettings: _*)
  .settings(
    name := "order-api",
    javaOptions in run ++= devConfSettings.value,
    javaOptions in Runtime ++= devConfSettings.value,
    javaOptions in Test ++= devConfSettings.value,
    unmanagedResourceDirectories in Compile += confDirectorySetting.value,
    unmanagedResourceDirectories in Test += confDirectorySetting.value
  )
