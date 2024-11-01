package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

public class App {

    private static final String FILE_NAME = "mood_data.csv";

    public static void main(String[] args) {
        UI.start();
    }

    public static void saveMood(LocalDate date, int moodIndex) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(date + "," + moodIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<LocalDate, Integer> readMoodData() {
        Map<LocalDate, Integer> moodData = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDate date = LocalDate.parse(parts[0]);
                int mood = Integer.parseInt(parts[1]);
                moodData.put(date, mood);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moodData;
    }

    public static void showMoodGraph(Map<LocalDate, Integer> moodData) {
        int size = moodData.size();
        double[] xData = new double[size];
        double[] yData = new double[size];
        // String[] xLabels = new String[size]; // Array to hold date labels

        // Populate yData and xLabels using mood values from moodData
        int index = 0;
        for (Map.Entry<LocalDate, Integer> entry : moodData.entrySet()) {
            int mood = entry.getValue();
            String date = entry.getKey().toString(); // Store date as a string in xLabels
            xData[index] = index + 1; // Keep the xData as index for plotting
            yData[index] = mood; // Assign mood value to yData
            // xLabels[index] = date; // Store date as a string in xLabels
            System.out.println("Day " + index + ": " + date + " Happiness level: " + mood);
            index++;
        }

        // Create Chart
        XYChart chart = QuickChart.getChart("Mood Tracker", "Day", "Mood Index", "Mood(days)", xData, yData);

        // Customize the x-axis labels
        // Show it
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                new SwingWrapper(chart).displayChart();
            }

        });
        t.start();

    }

}
