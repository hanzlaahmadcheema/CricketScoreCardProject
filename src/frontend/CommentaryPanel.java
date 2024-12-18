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

public class CommentaryPanel extends BackgroundPanel {
    private JTextArea commentaryArea;
    private List<String> commentaryList;
    private Random random;

    public CommentaryPanel(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        commentaryList = new ArrayList<>();
        random = new Random();

        JLabel titleLabel = new JLabel("Live Commentary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);

        commentaryArea = new JTextArea();
        commentaryArea.setEditable(false);
        commentaryArea.setLineWrap(true);
        commentaryArea.setWrapStyleWord(true);
        commentaryArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(commentaryArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Commentary Feed", TitledBorder.LEFT, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 18), new Color(0, 102, 204)));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton generateButton = new JButton("Generate Commentary");
        JButton customizeButton = new JButton("Add Custom Commentary");
        JButton clearButton = new JButton("Clear Commentary");
        JButton saveButton = new JButton("Save Commentary");
        JButton loadButton = new JButton("Load Commentary");

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

        bottomPanel.add(generateButton);
        bottomPanel.add(customizeButton);
        bottomPanel.add(clearButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(loadButton);

        add(bottomPanel, BorderLayout.SOUTH);

        initializeDefaultCommentary();
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Monospaced", Font.BOLD, 16));
        button.setBackground(new Color(0, 173, 181));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
    }

    private void initializeDefaultCommentary() {
        commentaryList.add("Player X hits a boundary!");
        commentaryList.add("Player Y takes a spectacular catch!");
        commentaryList.add("Player Z smashes a six!");
        commentaryList.add("What a delivery by the bowler!");
        commentaryList.add("The batsmen are building a strong partnership.");
        commentaryList.add("This is turning into a thrilling game!");
    }

    private void generateRandomCommentary() {
        if (commentaryList.isEmpty()) {
            commentaryArea.append("No commentary messages available.\n");
            return;
        }
        String randomCommentary = commentaryList.get(random.nextInt(commentaryList.size()));
        appendCommentary(randomCommentary);
    }

    private void addCustomCommentary() {
        String customCommentary = JOptionPane.showInputDialog(
            this,
            "Enter your custom commentary:",
            "Add Custom Commentary",
            JOptionPane.PLAIN_MESSAGE
        );
        if (customCommentary != null && !customCommentary.trim().isEmpty()) {
            commentaryList.add(customCommentary);
            appendCommentary(customCommentary);
            JOptionPane.showMessageDialog(
                this,
                "Custom commentary added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void clearCommentary() {
        commentaryArea.setText("");
        JOptionPane.showMessageDialog(this, "Commentary cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveCommentaryToFile() {
        try (FileWriter writer = new FileWriter("commentary.txt")) {
            writer.write(commentaryArea.getText());
            JOptionPane.showMessageDialog(this, "Commentary saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving commentary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCommentaryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("commentary.txt"))) {
            commentaryArea.setText("");
            String line;
            while ((line = reader.readLine()) != null) {
                commentaryArea.append(line + "\n");
            }
            JOptionPane.showMessageDialog(this, "Commentary loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading commentary: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void appendCommentary(String commentary) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        commentaryArea.append(String.format("[%s] %s\n", timestamp, commentary));
        commentaryArea.setCaretPosition(commentaryArea.getDocument().getLength()); // Auto-scroll
    }
}
