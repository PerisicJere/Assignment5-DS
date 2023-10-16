# Car Sales Analysis with Binary Search Tree

Author: Jere Perisic
Version: October 13, 2023

This Java project allows you to analyze car sales records stored in a CSV file using a Binary Search Tree. You can search for car sales based on car make and date, and it provides both iterative and recursive methods for analysis.

## Prerequisites

- A CSV file containing car sales records.

## How to Compile and Run

1. Compile the Java source files:

   ```bash
   javac SaleRecord.java
   javac myBST.java
   javac program5.java

2. Run
    ```bash
    java -Xss1024m program5 <csv-file>

## Issues 
I had to use -Xss1024m to fix stackoverflow error. This is necessary for the recursive analysis method.


