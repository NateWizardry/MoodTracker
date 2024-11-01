package org.example;

import java.awt.BorderLayout;
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

public class UI {

    public static void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // do nothing, fall back to default UI
        }
        SwingUtilities.invokeLater(UI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create the main frame
        JFrame frame = new JFrame("Mood Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 450);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setLayout(new BorderLayout(10, 10));

        // Greeting panel at the top
        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new BoxLayout(greetingPanel, BoxLayout.Y_AXIS));

        JLabel greetingLabel = new JLabel("Hello Nate,");
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
        moodPanel.setLayout(new BoxLayout(moodPanel, BoxLayout.Y_AXIS));
        moodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JRadioButton mainCharacterButton = new JRadioButton("😎 Main Character Energy");
        mainCharacterButton.setToolTipText("Feeling confident and in control");

        JRadioButton petDogButton = new JRadioButton("😁 Might pet a random dog.");
        petDogButton.setToolTipText("Feeling joyful and outgoing");

        JRadioButton mehButton = new JRadioButton("😐 Meh");
        mehButton.setToolTipText("Feeling neutral, not great but not bad either");

        JRadioButton potatoButton = new JRadioButton("🥴 Current mood: potato");
        potatoButton.setToolTipText("Feeling lazy or tired");

        JRadioButton helpButton = new JRadioButton("😭 Send help and snacks");
        helpButton.setToolTipText("Not feeling the best, might need a pick-me-up");

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

        // Action listeners for buttons
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

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<LocalDate, Integer> moodData = App.readMoodData();
                App.showMoodGraph(moodData);
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
