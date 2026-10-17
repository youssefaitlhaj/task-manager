ThisBuild / scalaVersion := "3.4.0"
ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.taskmanager"

// modules
lazy val domain = (project in file("domain"))
  .settings(name := "domain")

lazy val application = (project in file("application"))
  .settings(name := "application")
  .dependsOn(domain)

lazy val infrastructure = (project in file("infrastructure"))
  .settings(
    name := "infrastructure",
    libraryDependencies ++= Seq(
      "org.jooq"           % "jooq"                % "3.21.6",
      "org.postgresql"     % "postgresql"           % "42.7.12",
      "org.flywaydb"       % "flyway-core"          % "12.10.0",
      "org.flywaydb"       % "flyway-database-postgresql" % "12.10.0"
    )
  )
  .dependsOn(domain)

lazy val api = (project in file("api"))
  .settings(name := "api")
  .dependsOn(application)

lazy val ui = (project in file("ui"))
  .settings(name := "ui")
  .dependsOn(application)

lazy val root = (project in file("."))
  .aggregate(domain, application, infrastructure, api, ui)
  .settings(name := "task-manager")