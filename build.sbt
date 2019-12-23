name := "itto-csv"

version := "0.0.1"
organization := "com.github.gekomad"
scalaVersion := "0.21.0-RC1"

//cats
//libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"

//shapeless
//libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3"

//scala-regex-collection
libraryDependencies += "com.github.gekomad" %% "scala-regex-collection" % "0.0.1-dotty"

//libraryDependencies += "com.storm-enroute"  %% "scalameter" % "0.19"   % Test
//libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0-RC2" % Test
libraryDependencies += "org.apache.commons" % "commons-csv" % "1.7"          % Test
//libraryDependencies += "org.scalacheck"     %% "scalacheck" % "1.14.0"     % Test
//testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-minSuccessfulTests", "1000")

//publishTo := sonatypePublishTo.value
