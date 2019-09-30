name := "noleme-json"
organization := "com.noleme"
version := "0.3"

crossPaths := false
autoScalaLibrary := false
publishMavenStyle := false

val nolemeRepository = Resolver.sftp("Noleme Releases Repository", "beebop.no-ip.org", "releases/")(Resolver.ivyStylePatterns) as ("nolemesbt", "HsbZvT9eC8pPLv")
publishTo := Some(nolemeRepository)
resolvers += nolemeRepository

//JSON Parsing
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "[2.9,2.10)"
libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "[2.9,2.10)"
libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "[2.9,2.10)"
