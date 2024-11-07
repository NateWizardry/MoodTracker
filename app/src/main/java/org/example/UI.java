package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class UI {

    Color bg_color = Color.decode("#e3edf8"); // Original background color
    Color lightGreenColor = new Color(200, 255, 200); // Very light green color
    Color yellowColor = new Color(255, 255, 100); // Yellow color for selected radio button

    public static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // do nothing, fall back to default UI
        }
        SwingUtilities.invokeLater(UI::createAndShowGUI);
    }

    
    private static void createAndShowGUI() {
        //def ui
        // Create the main frame
        UI ui = new UI();
        JFrame frame = new JFrame("Mood Tracker");
        frame.getContentPane().setBackground(ui.bg_color); // Set initial background color
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 365);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setLayout(new BorderLayout(10, 10));

        
        // Greeting panel at the top
        JPanel greetingPanel = new JPanel();
        greetingPanel.setBackground(ui.bg_color);
        greetingPanel.setBorder(new EmptyBorder(10, 10, 5, 10));
        greetingPanel.setLayout(new BoxLayout(greetingPanel, BoxLayout.Y_AXIS));
        //poly
        JLabel greetingLabel = new JLabel("Hello Nate,");
        greetingLabel.setBorder(new EmptyBorder(10, 10, 5, 10));
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel questionLabel = new JLabel("How are you doing today?");
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        greetingPanel.add(greetingLabel);
        greetingPanel.add(Box.createVerticalStrut(5));
        greetingPanel.add(questionLabel);
        frame.add(greetingPanel, BorderLayout.NORTH);

        // Mood selection panel with padding
        JPanel moodPanel = new JPanel();
        moodPanel.setBackground(ui.bg_color);
        moodPanel.setLayout(new BoxLayout(moodPanel, BoxLayout.Y_AXIS));
        moodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JRadioButton mainCharacterButton = createMoodRadioButton("Main Character Energy 😎");
        JRadioButton petDogButton = createMoodRadioButton("Might pet a random dog. 😁");
        JRadioButton mehButton = createMoodRadioButton("Meh 😐");
        JRadioButton potatoButton = createMoodRadioButton("Current mood: potato 😕");
        JRadioButton helpButton = createMoodRadioButton("Send help and snacks 😭");

        // Group radio buttons
        ButtonGroup moodGroup = new ButtonGroup();
        moodGroup.add(mainCharacterButton);
        moodGroup.add(petDogButton);
        moodGroup.add(mehButton);
        moodGroup.add(potatoButton);
        moodGroup.add(helpButton);

        // Add mood buttons to panel with padding
        moodPanel.add(mainCharacterButton);
        moodPanel.add(Box.createVerticalStrut(10));
        moodPanel.add(petDogButton);
        moodPanel.add(Box.createVerticalStrut(10));
        moodPanel.add(mehButton);
        moodPanel.add(Box.createVerticalStrut(10));
        moodPanel.add(potatoButton);
        moodPanel.add(Box.createVerticalStrut(10));
        moodPanel.add(helpButton);

        //evdr
        // Adding ActionListeners to each radio button to change the greeting panel, mood panel, and frame color
        ActionListener moodChangeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set the background color of the greeting panel, mood panel, and frame to light green
                greetingPanel.setBackground(ui.lightGreenColor);
                moodPanel.setBackground(ui.lightGreenColor);
                frame.getContentPane().setBackground(ui.lightGreenColor);

                // Reset the background color of all radio buttons to light green
                mainCharacterButton.setBackground(ui.lightGreenColor);
                petDogButton.setBackground(ui.lightGreenColor);
                mehButton.setBackground(ui.lightGreenColor);
                potatoButton.setBackground(ui.lightGreenColor);
                helpButton.setBackground(ui.lightGreenColor);

                // Change the selected radio button to yellow
                JRadioButton selectedButton = (JRadioButton) e.getSource();
                selectedButton.setBackground(ui.yellowColor);
            }
        };

        mainCharacterButton.addActionListener(moodChangeListener);
        petDogButton.addActionListener(moodChangeListener);
        mehButton.addActionListener(moodChangeListener);
        potatoButton.addActionListener(moodChangeListener);
        helpButton.addActionListener(moodChangeListener);

        frame.add(moodPanel, BorderLayout.CENTER);

        // Button panel at the bottom with improved layout
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.PLAIN, 12));
        submitButton.setPreferredSize(new Dimension(120, 30));

        JButton historyButton = new JButton("Show Mood History");
        historyButton.setFont(new Font("Arial", Font.PLAIN, 12));
        historyButton.setPreferredSize(new Dimension(160, 30));

        buttonPanel.add(submitButton);
        buttonPanel.add(historyButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        
        submitButton.addActionListener((ActionEvent e) -> {
            String selectedMood = "";
            int selectedMoodValue = 0;
            if (mainCharacterButton.isSelected()) {
                selectedMood = "Main Character Energy";
                selectedMoodValue = 5;
            } else if (petDogButton.isSelected()) {
                selectedMood = "Might pet a random dog.";
                selectedMoodValue = 4;
            } else if (mehButton.isSelected()) {
                selectedMood = "Meh";
                selectedMoodValue = 3;
            } else if (potatoButton.isSelected()) {
                selectedMood = "Current mood: potato";
                selectedMoodValue = 2;
            } else if (helpButton.isSelected()) {
                selectedMood = "Send help and snacks";
                selectedMoodValue = 1;
            }
            LocalDate today = LocalDate.now();
            App.saveMood(today, selectedMoodValue);

            if (!selectedMood.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "You selected: " + selectedMood);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a mood.");
            }
        });

        historyButton.addActionListener(e -> {
            Map<LocalDate, Integer> moodData = App.readMoodData();
            App.showMoodGraph(moodData);
        });

        // Display the frame
        frame.setVisible(true);
    }

    private static JRadioButton createMoodRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setBackground(Color.decode("#e3edf8")); // Set the background color to match the main UI
        radioButton.setForeground(Color.BLACK); // Set text color
        radioButton.setBorder(null); // Remove focus border
        radioButton.setFocusPainted(false); // Disable focus paint (removes dotted line)
        return radioButton;
    }
}
