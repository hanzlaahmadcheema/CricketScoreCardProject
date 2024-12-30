package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

// CommentaryPanel class to manage and display live commentary
public class CommentaryPanel extends BackgroundPanel {
    private JTextArea commentaryArea; // Area to display commentary feed
    private List<String> commentaryList; // List to store predefined and custom commentary
    private Random random; // Random generator for selecting random commentary

    // Constructor to initialize the CommentaryPanel
    public CommentaryPanel(Image backgroundImage) {
        super(backgroundImage); // Set background image from parent class
        setLayout(new BorderLayout(20, 20)); // Use BorderLayout with spacing
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding around the panel

        // Initialize list and random generator
        commentaryList = new ArrayList<>();
        random = new Random();

        // Title label
        JLabel titleLabel = new JLabel("Live Commentary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH); // Add title at the top

        // Commentary area for displaying live feed
        commentaryArea = new JTextArea();
        commentaryArea.setEditable(false); // Disable editing
        commentaryArea.setLineWrap(true); // Enable line wrapping
        commentaryArea.setWrapStyleWord(true); // Wrap at word boundaries
        commentaryArea.setFont(new Font("Calibri", Font.PLAIN, 18));
        commentaryArea.setOpaque(false); // Transparent background
        commentaryArea.setForeground(foregroundColor);

        JScrollPane scrollPane = new JScrollPane(commentaryArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(transparentColor), 
            "Commentary Feed", 
            TitledBorder.CENTER, 
            TitledBorder.TOP, 
            new Font("Calibri", Font.BOLD, 24), 
            foregroundColor
        ));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        add(scrollPane, BorderLayout.CENTER); // Add scroll pane to the center

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        bottomPanel.setBackground(backgroundColor);

        // Buttons for various actions
        JButton generateButton = new JButton("Generate Commentary");
        JButton customizeButton = new JButton("Add Custom Commentary");
        JButton clearButton = new JButton("Clear Commentary");
        JButton saveButton = new JButton("Save Commentary");
        JButton loadButton = new JButton("Load Commentary");

        // Style buttons and add action listeners
        styleButton(generateButton);
        styleButton(customizeButton);
        styleButton(clearButton);
        styleButton(saveButton);
        styleButton(loadButton);

        generateButton.addActionListener(e -> generateRandomCommentary());
        customizeButton.addActionListener(e -> addCustomCommentary());
        clearButton.addActionListener(e -> clearCommentary());
        saveButton.addActionListener(e -> saveCommentaryToFile());
        loadButton.addActionListener(e -> loadCommentaryFromFile());

        // Add buttons to the bottom panel
        bottomPanel.add(generateButton);
        bottomPanel.add(customizeButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);

        add(bottomPanel, BorderLayout.SOUTH); // Add button panel to the bottom

        initializeDefaultCommentary(); // Load default commentary messages
    }

    // Method to style buttons consistently
    private void styleButton(JButton button) {
        button.setFont(new Font("Calibri", Font.BOLD, 16));
        button.setBackground(secondaryBackgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Method to initialize default commentary messages
    private void initializeDefaultCommentary() {
        commentaryList.add("Player X hits a boundary!");
        commentaryList.add("Player Y takes a spectacular catch!");
        commentaryList.add("Player Z smashes a six!");
        commentaryList.add("What a delivery by the bowler!");
        commentaryList.add("The batsmen are building a strong partnership.");
        commentaryList.add("This is turning into a thrilling game!");
    }

    // Method to generate and display random commentary
    private void generateRandomCommentary() {
        if (commentaryList.isEmpty()) {
            commentaryArea.append("No commentary messages available.\n");
            return;
        }
        String randomCommentary = commentaryList.get(random.nextInt(commentaryList.size()));
        appendCommentary(randomCommentary);
    }

    // Method to add and display custom commentary
    private void addCustomCommentary() {
        String customCommentary = JOptionPane.showInputDialog(
            this,
            "Enter your custom commentary:",
            "Add Custom Commentary",
            JOptionPane.PLAIN_MESSAGE
        );
        if (customCommentary != null && !customCommentary.trim().isEmpty()) {
            commentaryList.add(customCommentary); // Add to list
            appendCommentary(customCommentary); // Display in feed
            JOptionPane.showMessageDialog(
                this,
                "Custom commentary added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    // Method to clear all commentary from the feed
    private void clearCommentary() {
        commentaryArea.setText(""); // Clear text area
        JOptionPane.showMessageDialog(this, "Commentary cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to save commentary to a file
    private void saveCommentaryToFile() {
        try (FileWriter writer = new FileWriter("commentary.txt")) {
            writer.write(commentaryArea.getText()); // Write commentary feed to file
            JOptionPane.showMessageDialog(this, "Commentary saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving commentary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load commentary from a file
    private void loadCommentaryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("commentary.txt"))) {
            commentaryArea.setText(""); // Clear existing feed
            String line;
            while ((line = reader.readLine()) != null) {
                commentaryArea.append(line + "\n"); // Append each line to feed
            }
            JOptionPane.showMessageDialog(this, "Commentary loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading commentary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to append commentary to the feed with a timestamp
    private void appendCommentary(String commentary) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date()); // Get current time
        commentaryArea.append(String.format("[%s] %s\n", timestamp, commentary)); // Format and append commentary
        commentaryArea.setCaretPosition(commentaryArea.getDocument().getLength()); // Auto-scroll to bottom
    }
}
