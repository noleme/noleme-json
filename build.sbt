name := "noleme-json"
organization := "com.noleme"
version := "0.6"

crossPaths := false
autoScalaLibrary := false
publishMavenStyle := false

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

val nolemeRepository = Resolver.sftp("Noleme Releases Repository", "beebop.no-ip.org", "releases/")(Resolver.ivyStylePatterns) as ("nolemesbt", "HsbZvT9eC8pPLv")
publishTo := Some(nolemeRepository)
resolvers += nolemeRepository

//JSON Parsing
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "[2.9,2.10)"
libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "[2.9,2.10)"
libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "[2.9,2.10)"
libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-afterburner" % "[2.9,2.10)"

//JUnit Testing
libraryDependencies += "org.junit.jupiter" % "junit-jupiter" % "5.5.2" % Test
//This one is only needed for SBT shenanigans
libraryDependencies += "net.aichler" % "jupiter-interface" % "0.8.3" % Test
