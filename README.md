# GradeAnalyser

A Java console app that reads a CSV of student results and produces an analysis report (averages, medians, standard deviations, and fail counts) to both the console and `AnalysisReport.txt`. To run the program with your own CSV file, you just need to change the file path on line 6.

## What it does
- Reads a CSV file with the following columns:  
  Student, FINAL, Lab Exam, Exam%, Lab%
- Creates a list of Student results.
- Calculates and prints, for each of: FINAL, Lab Exam, Exam, Lab:
  - Average 
  - Median
  - Standard Deviation 
  - Count of scores < 40%

The output is printed to the console and to `AnalysisReport.txt`.

## CSV input format
Your CSV must have exactly these headers in this order:

Student, FINAL, Lab Exam, Exam%, Lab%





