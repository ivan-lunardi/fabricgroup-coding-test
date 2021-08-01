# Fabric Group Coding Test


### Overview
This project is build with Gradle and Java language using Spring Boot framework. 
This document assuming the source code is cloned and run in Linux or Mac OSX.


### Prerequisite
- Java 1.8
- Gradle 6.0 or above



### 1. Clone the application
Clone the source code to a directory. For example clone into /usr/workspace directory
```
cd /usr/workspace
git clone https://github.com/ivan-lunardi/fabricgroup-coding-test.git
```
### 2. Update input file path in application.properties
Input file path is configurable in application.properties and it need to be updated to the location where the source code is cloned.

For example if the source code is cloned to /usr/workspace directory then the file.path proeperty should be updated to:
```
input.file.path=/usr/workspace/fabricgroup-coding-test/src/main/resources/file/
```

### 3. Run the application
To run the application that is cloned into /usr/workspace directory:

```
cd /usr/workspace/fabricgroup-coding-test
./gradlew bootRun
```

### 4. Copy or drop sample file to input file path
Copy sample input file from /usr/workspace/fabricgroup-coding-test/src/main/resources/file/temp/input1.txt to /usr/workspace/fabricgroup-coding-test/src/main/resources/file/ :

```
cp /usr/workspace/fabricgroup-coding-test/src/main/resources/file/temp/input1.txt /usr/workspace/fabricgroup-coding-test/src/main/resources/file/.
```

### 5. How to run the unit test
To run the application unit test that is cloned into /usr/workspace directory:
```
cd /usr/workspace/fabricgroup-coding-test
./gradlew cleanTest test
```

### 6. Location of the JUnit test class
```
/usr/workspace/fabricgroup-coding-test/src/test/java/com/fabricgroup/test/FabricgroupCodingTestApplicationTests.java
```
