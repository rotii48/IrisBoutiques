import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IrisBoutiques extends JFrame implements ActionListener {

    private JPanel panel1;
    private JLabel logo;
    private JTextField searchbar;
    private JButton shoppingcart;
    private JButton chat;
    private JButton profile;

    public IrisBoutiques() {

        setTitle("iRis Boutiques");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 1. Load the Logo (Ensure logo.png is in your /src folder)
        try {
            ImageIcon boutiqueLogo = new ImageIcon(getClass().getResource("/logo.png"));
            if (logo != null) {
                logo.setIcon(boutiqueLogo);
                logo.setText("");
            }
        } catch (Exception e) {
            System.out.println("Logo image not found in src folder.");
        }

        if (shoppingcart != null) shoppingcart.addActionListener(this);
        if (chat != null) chat.addActionListener(this);
        if (profile != null) profile.addActionListener(this);

        if (panel1 != null) {
            setContentPane(panel1);
        }

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == shoppingcart) {
            JOptionPane.showMessageDialog(this, "Opening your Shopping Cart...");
        } else if (e.getSource() == chat) {
            JOptionPane.showMessageDialog(this, "Connecting to Customer Chat...");
        } else if (e.getSource() == profile) {
            JOptionPane.showMessageDialog(this, "Opening User Profile...");
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new IrisBoutiques().setVisible(true);
        });
    }

    private void createUIComponents() {

    }
}