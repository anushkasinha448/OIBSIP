
package Task3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChangePinForm extends JFrame {

    public ChangePinForm(User user) {

        setTitle("Change PIN");
        setSize(800, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(245, 248, 252));


        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(520, 70));
        header.setBackground(new Color(15, 45, 95));

        JLabel title = new JLabel("🔒  CHANGE PIN");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 20, 0, 0));

        header.add(title, BorderLayout.WEST);

        background.add(header, BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                new EmptyBorder(25, 30, 25, 30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel icon = new JLabel("🔐", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(icon, gbc);

        JLabel heading = new JLabel("Update Your ATM PIN", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(new Color(15, 45, 95));

        gbc.gridy++;
        card.add(heading, gbc);

        gbc.gridwidth = 1;

        // Old PIN
        JLabel lblOld = new JLabel("Old PIN");
        lblOld.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(lblOld, gbc);

        JPasswordField oldPin = new JPasswordField(15);
        oldPin.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        gbc.gridx = 1;
        card.add(oldPin, gbc);

        // New PIN
        JLabel lblNew = new JLabel("New PIN");
        lblNew.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(lblNew, gbc);

        JPasswordField newPin = new JPasswordField(15);
        newPin.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        gbc.gridx = 1;
        card.add(newPin, gbc);

        // Confirm PIN
        JLabel lblConfirm = new JLabel("Confirm PIN");
        lblConfirm.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridx = 0;
        gbc.gridy++;
        card.add(lblConfirm, gbc);

        JPasswordField confirmPin = new JPasswordField(15);
        confirmPin.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        gbc.gridx = 1;
        card.add(confirmPin, gbc);

        // Show PIN Checkbox
        JCheckBox showPin = new JCheckBox("Show PIN");
        showPin.setBackground(Color.WHITE);
        showPin.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        showPin.addActionListener(e -> {

            if (showPin.isSelected()) {

                oldPin.setEchoChar((char) 0);
                newPin.setEchoChar((char) 0);
                confirmPin.setEchoChar((char) 0);

            } else {

                oldPin.setEchoChar('\u2022');
                newPin.setEchoChar('\u2022');
                confirmPin.setEchoChar('\u2022');

            }

        });

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(showPin, gbc);

        // Buttons

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnChange = new JButton("Update PIN");
        JButton btnCancel = new JButton("Cancel");

        styleButton(btnChange, new Color(0, 120, 215));
        styleButton(btnCancel, new Color(220, 53, 69));

        buttonPanel.add(btnChange);
        buttonPanel.add(btnCancel);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(buttonPanel, gbc);


        btnChange.addActionListener(e -> {

            String oldp = String.valueOf(oldPin.getPassword()).trim();
            String newp = String.valueOf(newPin.getPassword()).trim();
            String conf = String.valueOf(confirmPin.getPassword()).trim();

            if (oldp.isEmpty() || newp.isEmpty() || conf.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Please fill all the fields.");

                return;
            }

            if (!newp.matches("\\d{4}")) {

                JOptionPane.showMessageDialog(this,
                        "New PIN must be exactly 4 digits.");

                return;
            }

            if (!newp.equals(conf)) {

                JOptionPane.showMessageDialog(this,
                        "New PIN and Confirm PIN do not match.");

                return;
            }

            UserDAO dao = new UserDAO();

            if (dao.changePin(user.getAccountNo(), oldp, newp)) {

                JOptionPane.showMessageDialog(this,
                        "✅ PIN changed successfully.");

                dispose();

            } else {

                JOptionPane.showMessageDialog(this,
                        "❌ Incorrect Old PIN.");

            }

        });

        btnCancel.addActionListener(e -> dispose());

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(245, 248, 252));
        center.add(card);

        background.add(center, BorderLayout.CENTER);

        add(background);

        setVisible(true);

    }


    private void styleButton(JButton button, Color color) {

        button.setBackground(color);
        button.setForeground(Color.black);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(140, 42));

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
