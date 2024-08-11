import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessNumberGame extends JFrame {

    private int randomNum;
    private int attempts = 0;
    private int maxAttempts = 10;
    private JTextField guessField;
    private JLabel messageLabel;
    private Image backgroundImage;
    private JPanel attemptsPanel;

    public GuessNumberGame() {
        backgroundImage = new ImageIcon("../Guess_Number/bg.png").getImage();

        setTitle("Guess the Number");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(500, 400);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(null);

        randomNum = new Random().nextInt(10) + 1;

        JLabel promptLabel = new JLabel("Guess A Number");
        promptLabel.setForeground(Color.ORANGE);
        promptLabel.setFont(new Font("Arial", Font.BOLD, 35));
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        promptLabel.setBounds(16, 70, 450, 30);
        backgroundPanel.add(promptLabel);

        JLabel higher = new JLabel("1 <");
        higher.setForeground(Color.blue);
        higher.setBounds(130, 120, 80, 50);
        higher.setFont(new Font("Arial", Font.BOLD, 40));
        backgroundPanel.add(higher);

        guessField = new JTextField(10);
        guessField.setFont(new Font("Arial", Font.BOLD, 24));
        guessField.setHorizontalAlignment(SwingConstants.CENTER);
        guessField.setBounds(200, 120, 50, 50);
        guessField.setBorder(new EmptyBorder(0,0,0,0));
        backgroundPanel.add(guessField);
        
        JLabel lower = new JLabel("< 10");
        lower.setForeground(Color.blue);
        lower.setBounds(260, 120, 80, 50);
        lower.setFont(new Font("Arial", Font.BOLD, 40));
        backgroundPanel.add(lower);

        JButton guessButton = new JButton("Guess ?");
        guessButton.setBackground(Color.GREEN);
        guessButton.setForeground(Color.WHITE);
        guessButton.setFont(new Font("Arial", Font.BOLD, 20));
        guessButton.addActionListener(new GuessButtonListener());
        guessButton.setBounds(175, 190, 100, 40);
        guessButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        guessButton.setBorder(new EmptyBorder(0,0,0,0));
        
        backgroundPanel.add(guessButton);

        messageLabel = new JLabel(maxAttempts + " attempts left.");
        messageLabel.setForeground(Color.pink);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 28));
        messageLabel.setBounds(140, 230, 500, 50);
        backgroundPanel.add(messageLabel);

       
        attemptsPanel = new JPanel();
        attemptsPanel.setLayout(new GridLayout(1, maxAttempts));
        attemptsPanel.setBounds(95, 280, 300, 35);
        for (int i = 0; i < maxAttempts; i++) {
            JPanel box = new JPanel();
            box.setBackground(Color.CYAN);
            box.setPreferredSize(new Dimension(30, 30));
            box.setBorder(BorderFactory.createLineBorder(Color.white));
            box.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 2));
            attemptsPanel.add(box);
        }

        backgroundPanel.add(attemptsPanel);

        setContentPane(backgroundPanel);
        setVisible(true);
    }

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class GuessButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;
                if (attempts <= maxAttempts) {
                    JPanel box = (JPanel) attemptsPanel.getComponent(attempts - 1);
                    box.setBackground(Color.RED);
                }
                if (guess == randomNum) {
                    messageLabel.setText("Congratulations! " + guess);
                    guessField.setEnabled(false);
                } else if (attempts >= maxAttempts) {
                    messageLabel.setText("Sorry, you ran out of attempts.");
                    messageLabel.setText("The number was"+ randomNum + ".");
                    guessField.setEnabled(false);
                } else {
                    String hint = (guess < randomNum) ? "  Too low!" : "  Too high!";
                    messageLabel.setText(hint);
                }

                guessField.setText("");
                guessField.requestFocus();

            } catch (NumberFormatException ex) {
                messageLabel.setText("   Invalid Input");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GuessNumberGame());
    }
}