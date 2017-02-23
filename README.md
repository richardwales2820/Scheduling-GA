# Scheduling-GA
Genetic Algorithm solving a scheduling problem with three different genome encodings

Encoding types:
- Binary
- Gray
- Integer/Object

## Sample Results

![Input](sample/Input.png)

Sample input from testcase #2

![Results](sample/Results.png)

Sample results from gray encoding, testcase #2

## Running the GA (This one's for you Dr. Wu!)

- Put the input data file in the same directory as the program
- Either set the input name as testdata1.txt, or set the name in the scheduling.params or hw1.params or hw2.params
    - I created all three of these params files to cover all of my bases with what you could call the code with
- javac *.java
- java Search scheduling.params
- Or run with any of the other params files
- The program will run and print out all of the valid schedules at the end in addition to the normal GA output