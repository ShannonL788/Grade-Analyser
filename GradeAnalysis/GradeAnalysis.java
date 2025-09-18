import java.io.*;
import java.util.*;

public class GradeAnalysis {
    public static void main(String [] args) {
    	File inputFile = new File("Student_Final_Scores.csv");
        String outputFile = "AnalysisReport.txt";
        
        ArrayList<Student> students = new ArrayList<>();

        // Read and parse CSV file
        try (Scanner scan = new Scanner(inputFile)) {
            if (scan.hasNextLine()) scan.nextLine(); 
            while (scan.hasNextLine()) {
                String[] parts = scan.nextLine().split(",");

                if (parts.length >= 5) {
                    String id = parts[0];
                    double finalScore = Double.parseDouble(parts[1].trim());
                    double labExam = Double.parseDouble(parts[2].trim());
                    double exam = Double.parseDouble(parts[3].trim());
                    double lab = Double.parseDouble(parts[4].trim());

                    // Add student object to list
                    students.add(new Student(id, finalScore, labExam, exam, lab));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
            return;
        }
        System.out.println("File loaded with " + students.size() + " students");
        System.out.println();
        
        try (PrintWriter writer = new PrintWriter(outputFile)) {
        	analyseScores(students, writer);
            System.out.println("Analysis written to AnalysisReport.txt");
        } catch (IOException e) {
            System.err.println("Error writing analysis file: " + e.getMessage());
        }
    }
    
    public static void analyseScores(ArrayList<Student> students, PrintWriter writer) {
    	// Lists for each category of scores
    	ArrayList<Double> finalList = new ArrayList<>();
        ArrayList<Double> labExamList = new ArrayList<>();
        ArrayList<Double> examList = new ArrayList<>();
        ArrayList<Double> labList = new ArrayList<>();
        
        int finalFailed = 0, labExamFailed = 0, examFailed = 0, labFailed = 0;

        // Fill lists and update fail counts
        for(int i = 0; i < students.size(); i++) {
        	Student student = students.get(i);
        	
            finalList.add(student.finalScore);
            labExamList.add(student.labExam);
            examList.add(student.examPercent);
            labList.add(student.labPercent);
            
            if(student.finalScore < 40) finalFailed++;
            if(student.labExam < 40) labExamFailed++;
            if(student.examPercent < 40) examFailed++;
            if(student.labPercent < 40) labFailed++;
        }
    	
        // Analysis for each list
        printDetails("FINAL RESULTS", finalList, finalFailed, writer);
        printDetails("LAB EXAM", labExamList, labExamFailed, writer);
        printDetails("EXAM RESULTS", examList, examFailed, writer);
        printDetails("LAB RESULTS", labList, labFailed, writer);
    }
    
    // Print statistics and failure count for one list
    public static void printDetails(String header, ArrayList<Double> data, int failedData, PrintWriter writer) {
    	printBoth(header + ":", writer);
        double average = getAverage(data);
        printBoth("Average = " + average + "%", writer);
        printBoth("Median = " + getMedian(data) + "%", writer);
        printBoth("Standard Deviation = " + getStandardDeviation(average, data), writer);
        printBoth("Number of Students Scoring Below 40% = " + failedData, writer);
        printBoth("", writer);
    }
    
    // Calculate average 
    public static double getAverage(ArrayList<Double> data) {
    	double average = 0;
    	
    	for(int i = 0; i < data.size(); i++) {
    		average += data.get(i);
    	}
    	average /= data.size();
    	return Math.round(average * 100.0) / 100.0;
    }
    
    // Calculate median value
    public static double getMedian(ArrayList<Double> data) {
    	ArrayList<Double> sorted = new ArrayList<>(data); 
        sorted.sort(null);
        int size = sorted.size();
        
        if (size % 2 == 1) {
            return sorted.get(size / 2);
        } else {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        }
    }

    // Calculate standard deviation
    public static double getStandardDeviation(double average, ArrayList<Double> data) {
    	double squaredDiffSum = 0.0;
        for (int i = 0; i < data.size(); i++) {
            double diff = data.get(i) - average;
            squaredDiffSum += diff * diff;
        }
        double stdDev = Math.sqrt(squaredDiffSum / data.size());
        return Math.round(stdDev * 100.0) / 100.0;
    }
    
    // Method to allow printing to console and output file
    public static void printBoth(String text, PrintWriter writer) {
        System.out.println(text);
        writer.println(text);
    }
} 
