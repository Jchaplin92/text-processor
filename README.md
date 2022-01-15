# text-processor app
- author: Joe Chaplin
- languages/technologies: Java8/Maven
- created: 9th Jan 2022

---

## Installation

### Packaging and running as a JAR file
- you must have the Java Runtime Environment installed - check that you are at least version 8 with:
`java -version` from command line.
- `java -jar jarfilename.jar` to run
- the app is running on localhost:8080 by default.

### Running as an executable
- this uses

---

## Usage
Given more time this app would benefit from an input form. In this version however, you can use the terminal command 

`curl -v -F file=@bible_daily.txt localhost:8080`

from the location of bible_daily.txt in a command line environment (altering file path if performing the instruction from elsewhere). 
This example also assumes you have begun running text-processor on your home machine

---

## Other limitations
- Current file limit is 5MB as can be seen from `application.properties`
- Further discussion is possible on what constitutes a word. 
  - Is for instance an ellipsis (`...`) a word? 
  - Should all words consisting entirely of punctuation be removed or does, for example, `:^)` or the `*` in ` 2 * 2 = 4` constitute a word? 