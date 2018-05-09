# kafka-registry-gradle-plugin

Kafka Registry plugin is a simple gradle plugin which downloads avro schema from schema registry when provided with
schema registry url , output directory and subjects array. This plugin can be used in conjunction with gradle avro plugin 

## QuickStart 

```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
          }
   maven {
         url "http://packages.confluent.io/maven/"
         }
  }
  dependencies {
    classpath "gradle.plugin.com.amit.plugin.download-registry:registry-schema-download-plugin:1.0"
  }
}

apply plugin: "com.amit.plugin.download-registry"

dependencies {
    compile ("io.confluent:kafka-avro-serializer:3.2.1") {
     		exclude group: "org.slf4j", module: "slf4j-log4j12"
       }
    compile("org.apache.avro:avro:1.8.2")
}

schemaRegistry {
   url = "http://xx.xx.xx.xx:8081/"
   outputPath = file("src/main/avro")
   subjects = ['test-value1', 'test-value2']
}
```
