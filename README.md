# Noleme JSON

[![Maven Build](https://github.com/noleme/noleme-json/actions/workflows/maven-build.yml/badge.svg?branch=master)](https://github.com/noleme/noleme-json/actions/workflows/maven-build.yml)
[![Maven Central Version](https://img.shields.io/maven-central/v/com.noleme/noleme-json)](https://central.sonatype.com/artifact/com.noleme/noleme-json)
[![javadoc](https://javadoc.io/badge2/com.noleme/noleme-json/javadoc.svg)](https://javadoc.io/doc/com.noleme/noleme-json)
[![coverage](https://codecov.io/gh/noleme/noleme-json/branch/master/graph/badge.svg?token=Y9FD38RLDE)](https://codecov.io/gh/noleme/noleme-json)
![GitHub](https://img.shields.io/github/license/noleme/noleme-json)

This library is meant as a collection of JSON utility functions and most precisely as a specification for JSON third-party dependencies usage throughout all Noleme libraries.
This is basically the place where the version requirements for Jackson (or other) are decided, and developments relying on JSON as a vehicle for data should use whichever
implementation is specified by this project.

## I. Installation

Add the following in your `pom.xml`:

```xml
<dependency>
    <groupId>com.noleme</groupId>
    <artifactId>noleme-json</artifactId>
    <version>0.12</version>
</dependency>
```

## II. Notes on Structure and Design

This library is a collection of JSON (and YAML) utility functions.
It was initially inspired by the PlayFramework namesake utility from quite some time ago, but was expected to cover YAML as well, and be free of PlayFramework dependencies.

Most of the library revolves around the `Json` and `Yaml` classes, which provide static methods for common operations:

* Parsing strings, input streams, or byte arrays into `JsonNode`
* Generating JSON/YAML strings from user-defined POJOs or `JsonNode`
* Converting between user-defined POJOs and `JsonNode`
* Navigating and extracting values from `JsonNode`

It uses Jackson as the underlying implementation (and historically served as a specification for Jackson version requirements across all Noleme projects).

## III. Usage

### A. JSON Operations

The `Json.parse` takes a JSON formatted string and outputs a `JsonNode` object.

```java
import com.noleme.json.Json;
import com.fasterxml.jackson.databind.JsonNode;

String jsonString = "{\"name\": \"John\", \"age\": 30}";
JsonNode node = Json.parse(jsonString);
```

The `Json.stringify` method converts a `JsonNode` object into a JSON formatted string, a `Json.prettyPrint` method does the same, but with (you guessed it) pretty printing.

```java
import com.noleme.json.Json;
import com.fasterxml.jackson.databind.node.ObjectNode;

ObjectNode node = Json.newObject();
node.put("name", "John");
node.put("age", 30);

String json = Json.stringify(node);
String prettyJson = Json.prettyPrint(node);
```

The `fromJson` and `toJson` methods can perform transformation between POJOs and `JsonNode`.

```java
import com.noleme.json.Json;

MyClass myObj = Json.fromJson(jsonNode, MyClass.class);
JsonNode node = Json.toJson(myObj);
```

### B. YAML Operations

YAML operations follow the same pattern as JSON operations, using the `Yaml` class.

```java
import com.noleme.json.Yaml;
import com.fasterxml.jackson.databind.JsonNode;

String yamlString = "name: John\nage: 30";
JsonNode node = Yaml.parse(yamlString);

String yaml = Yaml.stringify(node);
```

### C. Custom Serializers and Deserializers

You can register custom serializers and deserializers using the `Serializers` and `Deserializers` utility classes.

```java
import com.noleme.json.jackson.Serializers;
import com.noleme.json.jackson.Deserializers;

Serializers.register(MyClass.class, new MyCustomSerializer());
Deserializers.register(MyClass.class, new MyCustomDeserializer());
```

## IV. Dev

This project will require you to have the following:

* Git (versioning)
* Maven (dependency resolving, publishing and packaging)
