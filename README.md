# text-processor app
- author: Joe Chaplin
- languages/technologies: Java17/Maven
- created: 9th Jan 2022

---

## Installation

### Packaging and running as a JAR file
From text-processor app's top level directory in a command line environment:
1. you must have maven and the Java Runtime Environment installed - check that you are at least version 8 with:
`java -version` from command line. Check maven is installed with `maven -version` from command line.
2. `mvn package` to create the jar
3. `java -jar target/text-processor-1.0-SNAPSHOT.jar` to run.
4. the app is running on localhost:8080 by default.

### Running as an executable
This is currently tested on MacOS - in order to use an alternative package from "app-image", "exe", "msi", "rpm", "deb", "pkg", "dmg" types, you must be on the right platform when performing the command e.g. to make a Windows app swap in `-type exe`.

jpackage requires java version 17 on this project where version 8 would have worked otherwise.
The following is all run from text-processor app's top level directory.
1. `mvn clean package spring-boot:repackage` - this performs the mvn package command described above and then adds in the runtime dependencies such as the tomcat library.
2. `jpackage --name text-processor --input target --main-jar text-processor-1.0-SNAPSHOT.jar --type dmg`
3. On MacOS you will be prompted to move the dmg to Applications folder and will be able to run text-processor like any other app.
---

## Usage
Given more time this app would benefit from an input form. In this version however, you can use the terminal command 

`curl -v -F file=@bible_daily.txt localhost:8080`

from the location of bible_daily.txt in a command line environment (altering file path if performing the instruction from elsewhere). 
This example also assumes you have begun running text-processor on your home machine

---

## Shutdown application
Run from command line

`curl -X POST localhost:8080/actuator/shutdown`

---

## Other limitations
- Current file limit is 5MB as can be seen from `application.properties`
- Further discussion is possible on what constitutes a word. 
  - Is for instance an ellipsis (`...`) a word? 
  - Should all words consisting entirely of punctuation be removed or does, for example, `:^)` or the `*` in ` 2 * 2 = 4` constitute a word? 