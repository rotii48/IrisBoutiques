import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ChatWindow - Simple chat interface
 * Can create .form later if needed
 */
public class ChatWindow extends JFrame implements ActionListener {
    private JTextArea txtChat;
    private JTextField txtMessage;
    private JButton btnSend;

    public ChatWindow() {
        super.setTitle("Customer Chat - iRis Boutiques");
        super.setSize(400, 400);
        super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Create components
        txtChat = new JTextArea();
        txtChat.setEditable(false);
        txtChat.setText("Welcome to iRis Boutiques Customer Support!\nHow can we help you today?\n\n");

        txtMessage = new JTextField();
        btnSend = new JButton("Send");

        // Layout
        JScrollPane scrollPane = new JScrollPane(txtChat);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(txtMessage, BorderLayout.CENTER);
        bottomPanel.add(btnSend, BorderLayout.EAST);

        Container cp = super.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(scrollPane, BorderLayout.CENTER);
        cp.add(bottomPanel, BorderLayout.SOUTH);

        // Add listeners
        btnSend.addActionListener(this);
        txtMessage.addActionListener(this);  // Enter key also sends

        super.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = txtMessage.getText().trim();

        if (!message.isEmpty()) {
            txtChat.append("You: " + message + "\n");
            txtMessage.setText("");

            // Simulate response
            txtChat.append("Support: Thank you for your message. We'll get back to you soon!\n\n");
            txtChat.setCaretPosition(txtChat.getDocument().getLength());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatWindow();
        });
    }
}