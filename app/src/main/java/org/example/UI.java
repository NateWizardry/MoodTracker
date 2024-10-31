package org.example;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            // since this function throws errors, it must be caught
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
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        // Greeting panel at the top
        JPanel greetingPanel = new JPanel();
        greetingPanel.setLayout(new BoxLayout(greetingPanel, BoxLayout.Y_AXIS));
        JLabel greetingLabel = new JLabel("Hello Nate,");
        JLabel questionLabel = new JLabel("How are you doing today?");
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetingPanel.add(greetingLabel);
        greetingPanel.add(questionLabel);
        frame.add(greetingPanel, BorderLayout.NORTH);

        // Mood selection panel
        JPanel moodPanel = new JPanel();
        moodPanel.setLayout(new BoxLayout(moodPanel, BoxLayout.Y_AXIS));

        JRadioButton mainCharacterButton = new JRadioButton("😎 Main Character Energy");
        JRadioButton petDogButton = new JRadioButton("😁 Might pet a random dog.");
        JRadioButton mehButton = new JRadioButton("😐 Meh");
        JRadioButton potatoButton = new JRadioButton("🥔 Current mood: potato");
        JRadioButton helpButton = new JRadioButton("📦 Send help and snacks");

        ButtonGroup moodGroup = new ButtonGroup();
        moodGroup.add(mainCharacterButton);
        moodGroup.add(petDogButton);
        moodGroup.add(mehButton);
        moodGroup.add(potatoButton);
        moodGroup.add(helpButton);

        moodPanel.add(mainCharacterButton);
        moodPanel.add(petDogButton);
        moodPanel.add(mehButton);
        moodPanel.add(potatoButton);
        moodPanel.add(helpButton);

        frame.add(moodPanel, BorderLayout.CENTER);

        // Button panel at the bottom
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        JButton historyButton = new JButton("Show mood history");
        buttonPanel.add(submitButton);
        buttonPanel.add(historyButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for buttons
        submitButton.addActionListener((ActionEvent e) -> {
            String selectedMood = "";
            int selectedMoodValue;
            if (mainCharacterButton.isSelected()) {
                selectedMood = "Main Character Energy";
                selectedMoodValue = 5;
                }
            else if (petDogButton.isSelected()){ 
                selectedMood = "Might pet a random dog.";
                selectedMoodValue = 4;
            }
            else if (mehButton.isSelected()) {
                selectedMood = "Meh";
                selectedMoodValue = 3;
                }
            else if (potatoButton.isSelected()) {
                selectedMood = "Current mood: potato";
                selectedMoodValue = 2;
            }
            else if (helpButton.isSelected()) {
                selectedMood = "Send help and snacks";
                selectedMoodValue = 1;
            }

            
            if (!selectedMood.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "You selected: " + selectedMood);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a mood.");
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Mood history feature coming soon!");
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
