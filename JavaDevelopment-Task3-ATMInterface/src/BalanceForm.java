/*package Task3;

import Task3.UserDAO;
import Task3.User;

import javax.swing.*;
import java.awt.*;

public class BalanceForm extends JFrame {

    public BalanceForm(User user) {

        UserDAO dao = new UserDAO();

        double balance = dao.getBalance(user.getAccountNo());

        setTitle("Balance Enquiry");
        setSize(400,300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel name = new JLabel("Name : "+user.getName());
        name.setFont(new Font("SansSerif", Font.BOLD, 14));
        name.setBounds(50,80,300,25);
        getContentPane().add(name);

        JLabel acc = new JLabel("Account No : "+user.getAccountNo());
        acc.setFont(new Font("SansSerif", Font.BOLD, 14));
        acc.setBounds(50,120,300,25);
        getContentPane().add(acc);

        JLabel bal = new JLabel("Current Balance : ₹ "+balance);
        bal.setFont(new Font("Arial Black", Font.BOLD, 18));
        bal.setBounds(50,160,300,25);
        getContentPane().add(bal);

        JButton close = new JButton("Close");
        close.setFont(new Font("Century", Font.BOLD, 13));
        close.setBounds(140,210,100,35);
        close.addActionListener(e->dispose());
        getContentPane().add(close);

        getContentPane().setBackground(new Color(230,240,255));
        
        JTextArea txtrBalanceEnquiry = new JTextArea();
        txtrBalanceEnquiry.setBackground(new Color(75, 0, 130));
        txtrBalanceEnquiry.setForeground(new Color(255, 255, 0));
        txtrBalanceEnquiry.setFont(new Font("Algerian", Font.BOLD, 18));
        txtrBalanceEnquiry.setText("BALANCE ENQUIRY");
        txtrBalanceEnquiry.setBounds(0, 0, 386, 35);
        getContentPane().add(txtrBalanceEnquiry);

        setVisible(true);
    }
}
*/

package Task3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BalanceForm extends JFrame {

    public BalanceForm(User user) {

        UserDAO dao = new UserDAO();
        double balance = dao.getBalance(user.getAccountNo());

        setTitle("Balance Enquiry");
        setSize(800, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(245, 248, 252));

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(520, 70));
        header.setBackground(new Color(15, 45, 95));

        JLabel title = new JLabel("💰  BALANCE ENQUIRY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 20, 0, 0));

        header.add(title, BorderLayout.WEST);

        background.add(header, BorderLayout.NORTH);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(25, 25, 25, 25)));

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel icon = new JLabel("🏦");
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 46));

        JLabel welcome = new JLabel("Account Details");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcome.setForeground(new Color(15, 45, 95));

        card.add(icon);
        card.add(Box.createVerticalStrut(10));
        card.add(welcome);
        card.add(Box.createVerticalStrut(25));

        card.add(createInfoPanel("👤  Name", user.getName()));
        card.add(Box.createVerticalStrut(15));

        card.add(createInfoPanel("💳  Account No.", user.getAccountNo()));
        card.add(Box.createVerticalStrut(15));

        JPanel balancePanel = new JPanel(new BorderLayout());
        balancePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        balancePanel.setBackground(new Color(230, 245, 255));
        balancePanel.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));

        JLabel balTitle = new JLabel("Current Balance");
        balTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        balTitle.setBorder(new EmptyBorder(10, 15, 0, 0));

        JLabel balAmount = new JLabel("₹ " + String.format("%.2f", balance));
        balAmount.setForeground(new Color(0, 130, 60));
        balAmount.setFont(new Font("Segoe UI", Font.BOLD, 26));
        balAmount.setBorder(new EmptyBorder(0, 15, 10, 0));

        balancePanel.add(balTitle, BorderLayout.NORTH);
        balancePanel.add(balAmount, BorderLayout.CENTER);

        card.add(balancePanel);

        card.add(Box.createVerticalStrut(30));

        JButton close = new JButton("Close");
        close.setAlignmentX(Component.CENTER_ALIGNMENT);
        styleButton(close, new Color(220, 53, 69));
        close.addActionListener(e -> dispose());

        card.add(close);

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(245, 248, 252));
        center.add(card);

        background.add(center, BorderLayout.CENTER);

        add(background);

        setVisible(true);
    }

    private JPanel createInfoPanel(String label, String value) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setBorder(new EmptyBorder(5, 12, 0, 0));

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        val.setBorder(new EmptyBorder(0, 12, 8, 0));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(val, BorderLayout.CENTER);

        return panel;
    }

    // ================= BUTTON STYLE =================

    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 42));

        Color normal = color;

        button.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(normal.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(normal);
            }
        });
    }
}