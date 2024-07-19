import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class calculator implements ActionListener {

    JLabel displayLabel;
    JButton[] digitButtons;
    JButton[] operatorButtons;
    JButton clearButton;
    JButton equalButton;
    JButton dotButton;

    String oldValue;
    String newValue;
    String operator;

    public calculator() {

        JFrame frame = new JFrame("Calculator");
        frame.setLayout(new BorderLayout());

        // Display
        displayLabel = new JLabel("", SwingConstants.RIGHT);
        displayLabel.setBackground(Color.lightGray);
        displayLabel.setOpaque(true);
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setPreferredSize(new Dimension(400, 40));
        frame.add(displayLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] digitLabels = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "0"};
        digitButtons = new JButton[digitLabels.length];
        for (int i = 0; i < digitLabels.length; i++) {
            digitButtons[i] = new JButton(digitLabels[i]);
            digitButtons[i].addActionListener(this);
            gbc.gridx = i % 3;
            gbc.gridy = i / 3;
            buttonPanel.add(digitButtons[i], gbc);
        }

        operatorButtons = new JButton[4];
        operatorButtons[0] = new JButton("+");
        operatorButtons[1] = new JButton("-");
        operatorButtons[2] = new JButton("X");
        operatorButtons[3] = new JButton("/");
        for (int i = 0; i < 4; i++) {
            operatorButtons[i].addActionListener(this);
            gbc.gridx = 3;
            gbc.gridy = i;
            buttonPanel.add(operatorButtons[i], gbc);
        }

        dotButton = new JButton(".");
        dotButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 3;
        buttonPanel.add(dotButton, gbc);

        equalButton = new JButton("=");
        equalButton.addActionListener(this);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridheight = 2;
        buttonPanel.add(equalButton, gbc);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        buttonPanel.add(clearButton, gbc);

        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String buttonText = clickedButton.getText();

        switch (buttonText) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case ".":
                if (operator != null) {
                    displayLabel.setText("");
                    operator = null;
                }
                displayLabel.setText(displayLabel.getText() + buttonText);
                break;
            case "+":
            case "-":
            case "X":
            case "/":
                if (!displayLabel.getText().isEmpty()) {
                    oldValue = displayLabel.getText();
                    operator = buttonText;
                    displayLabel.setText(""); // Clear display after operator press
                }
                break;
            case "=":
                if (oldValue != null && operator != null && !displayLabel.getText().isEmpty()) {
                    newValue = displayLabel.getText();
                    float result = calculate();
                    displayLabel.setText(String.valueOf(result));
                }
                break;
            case "Clear":
                displayLabel.setText("");
                oldValue = null;
                newValue = null;
                operator = null;
                break;
        }
    }

    private float calculate() {
        float result = 0;
        float oldVal = Float.parseFloat(oldValue);
        float newVal = Float.parseFloat(displayLabel.getText());

        switch (operator) {
            case "+":
                result = oldVal + newVal;
                break;
            case "-":
                result = oldVal - newVal;
                break;
            case "X":
                result = oldVal * newVal;
                break;
            case "/":
                if (newVal != 0) {
                    result = oldVal / newVal;
                } else {
                    displayLabel.setText("Error: Division by zero");
                    return 0; // Return 0 as result when division by zero occurs
                }
                break;
        }
        return result;
    }
}

