organization := "com.github.tpuronen"

name := "scsv"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "1.9.1" % "test"
)

publishMavenStyle := true

publishArtifact in Test := false

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/tpuronen/scsv</url>
  <licenses>
    <license>
      <name>ASL</name>
      <url>http://github.com/tpuronen/scsv/raw/HEAD/LICENSE</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:tpuronen/scsv.git</url>
    <connection>scm:git:git@github.com:tpuronen/scsv.git</connection>
  </scm>
  <developers>
    <developer>
      <id>tpuronen</id>
      <name>Timo Puronen</name>
      <url>http://github.com/tpuronen</url>
    </developer>
  </developers>)