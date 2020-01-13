# Agibank - Sales Report
## Introduction
A Spring Boot Application responsible for listening to `$HOME/data/in` folder for incoming files, processing the content for all files found in folder and
creating/updating a report in `$HOME/data/out` with the following data:
- The number of clients
- The number of salesman
- The sale ID of the most expensive sale
- The worst salesman

## Environment Setup
- Install [Gradle 6.0.1](https://gradle.org/install/) and [JDK 1.8+](https://www.oracle.com/technetwork/java/javase/downloads/index.html)

- Create the following folders in your `$HOME` directory:
```
data/in: used for storing incoming .dat files.
data/out: used for storing the report.done.dat file.
```
IMPORTANT: These folders must be created in order to execute the project successfully.

## Build
Run the following command in the project root folder:
```
$ gradle clean build
```

## Run
Run the following command in the project root folder:
```
$ java -jar build/libs/sales-report-1.0-SNAPSHOT.jar
```
