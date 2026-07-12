
package Task3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TransferForm extends JFrame {

    public TransferForm(User user) {

        setTitle("Transfer Money");
        setSize(600, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(245, 248, 252));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 45, 95));
        header.setPreferredSize(new Dimension(550, 70));

        JLabel title = new JLabel("🔄  TRANSFER MONEY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 20, 0, 0));

        header.add(title, BorderLayout.WEST);
        background.add(header, BorderLayout.NORTH);


        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                new EmptyBorder(25,30,25,30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12,12,12,12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel icon = new JLabel("💳", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(icon, gbc);

        JLabel heading = new JLabel("Transfer Funds", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(15,45,95));

        gbc.gridy++;
        card.add(heading, gbc);

        gbc.gridwidth = 1;


        JLabel lblAcc = new JLabel("Receiver Account");
        lblAcc.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(lblAcc, gbc);

        JTextField txtAcc = new JTextField();
        txtAcc.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        gbc.gridx = 1;
        card.add(txtAcc, gbc);

        // Amount

        JLabel lblAmt = new JLabel("Amount (₹)");
        lblAmt.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(lblAmt, gbc);

        JTextField txtAmt = new JTextField();
        txtAmt.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        gbc.gridx = 1;
        card.add(txtAmt, gbc);

        // Buttons

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,0));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnTransfer = new JButton("Transfer");
        JButton btnCancel = new JButton("Cancel");

        styleButton(btnTransfer, new Color(111,66,193));   // Purple
        styleButton(btnCancel, new Color(220,53,69));      // Red

        buttonPanel.add(btnTransfer);
        buttonPanel.add(btnCancel);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25,0,0,0);

        card.add(buttonPanel, gbc);


        btnTransfer.addActionListener(e -> {

            try {

                String receiver = txtAcc.getText().trim();

                if(receiver.isEmpty()){

                    JOptionPane.showMessageDialog(this,
                            "Please enter receiver account number.");

                    return;

                }

                double amount = Double.parseDouble(txtAmt.getText().trim());

                if(amount <= 0){

                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid amount.");

                    return;

                }

                if(receiver.equals(user.getAccountNo())){

                    JOptionPane.showMessageDialog(this,
                            "You cannot transfer money to your own account.");

                    return;

                }

                UserDAO dao = new UserDAO();

                boolean success = dao.transfer(
                        user.getAccountNo(),
                        receiver,
                        amount);

                if(success){

                    double balance = dao.getBalance(user.getAccountNo());

                    TransactionDAO td = new TransactionDAO();

                    td.saveTransaction(
                            user.getAccountNo(),
                            "Transfer",
                            amount,
                            balance);

                    JOptionPane.showMessageDialog(this,
                            "₹ " + String.format("%.2f", amount)
                                    + " transferred successfully.\n\nRemaining Balance : ₹ "
                                    + String.format("%.2f", balance));

                    dispose();

                }
                else{

                    JOptionPane.showMessageDialog(this,
                            "Transfer failed.\n\nCheck receiver account or balance.");

                }

            }

            catch(NumberFormatException ex){

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

    private void styleButton(JButton button, Color color){

        button.setBackground(color);
        button.setForeground(Color.black);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(140,42));

        Color normal = color;

        button.addMouseListener(new java.awt.event.MouseAdapter(){

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e){

                button.setBackground(normal.darker());

            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e){

                button.setBackground(normal);

            }

        });

    }

}
