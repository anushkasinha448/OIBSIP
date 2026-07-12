
package Task3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class WithdrawForm extends JFrame {
	 private ATMMenu menu;
    public WithdrawForm(User user,ATMMenu menu) {
    	 this.menu = menu;

        setTitle("Withdraw Money");
        setSize(520, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(245,248,252));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15,45,95));
        header.setPreferredSize(new Dimension(520,70));

        JLabel title = new JLabel("💸  WITHDRAW MONEY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0,20,0,0));

        header.add(title, BorderLayout.WEST);
        background.add(header, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                new EmptyBorder(25,30,25,30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12,10,12,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel icon = new JLabel("🏧", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(icon, gbc);

        JLabel heading = new JLabel("Withdraw Cash", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(15,45,95));

        gbc.gridy++;
        card.add(heading, gbc);

        gbc.gridwidth = 1;

        JLabel lblAmount = new JLabel("Amount (₹)");
        lblAmount.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(lblAmount, gbc);

        JTextField txtAmount = new JTextField();
        txtAmount.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        gbc.gridx = 1;
        card.add(txtAmount, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,0));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnCancel = new JButton("Cancel");

        styleButton(btnWithdraw, new Color(255,140,0));
        styleButton(btnCancel, new Color(220,53,69));

        buttonPanel.add(btnWithdraw);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25,0,0,0);

        card.add(buttonPanel, gbc);

        btnWithdraw.addActionListener(e -> {

            try {

                double amount = Double.parseDouble(txtAmount.getText().trim());

                if (amount <= 0) {

                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid amount.");

                    return;
                }

                UserDAO dao = new UserDAO();

                if (dao.withdraw(user.getAccountNo(), amount)) {

                    double balance = dao.getBalance(user.getAccountNo());
                    user.setBalance(balance);
                    TransactionDAO td = new TransactionDAO();

                    td.saveTransaction(
                            user.getAccountNo(),
                            "Withdraw",
                            amount,
                            balance);
                    
                    menu.refreshBalance();

                    JOptionPane.showMessageDialog(this,
                            "Please collect your cash.\n\nRemaining Balance : ₹ "
                                    + String.format("%.2f", balance));

                    dispose();

                } else {

                    JOptionPane.showMessageDialog(this,
                            "Insufficient Balance.");

                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this,
                        "Please enter a numeric amount.");

            }

        });

        btnCancel.addActionListener(e -> dispose());

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(245,248,252));
        center.add(card);

        background.add(center, BorderLayout.CENTER);

        add(background);

        setVisible(true);

    }


    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.black);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140,42));

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
