package Task3;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ATMMenu extends JFrame implements ActionListener {

    private User user;

    private JButton btnBalance;
    private JButton btnDeposit;
    private JButton btnWithdraw;
    private JButton btnTransfer;
    private JButton btnHistory;
    private JButton btnChangePin;
    private JButton btnLogout;
    private JLabel lblBalance;
    public ATMMenu(User user) {

        this.user = user;

        setTitle("ATM Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245,248,252));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15,45,95));
        header.setBorder(BorderFactory.createEmptyBorder(20,30,20,30));

        JLabel lblTitle = new JLabel("💳 ATM INTERFACE");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI",Font.BOLD,30));

        JLabel lblUser = new JLabel("👤 " + user.getName(),SwingConstants.RIGHT);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("Segoe UI",Font.PLAIN,20));

        header.add(lblTitle,BorderLayout.WEST);
        header.add(lblUser,BorderLayout.EAST);

        mainPanel.add(header,BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setBackground(new Color(245,248,252));
        center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));

        center.add(Box.createVerticalStrut(40));

        JLabel welcome = new JLabel("Welcome Back, " + user.getName() + "!");
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setFont(new Font("Segoe UI",Font.BOLD,36));

        JLabel subtitle = new JLabel("Choose a banking service");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Segoe UI",Font.PLAIN,20));
        subtitle.setForeground(Color.GRAY);

        center.add(welcome);
        center.add(Box.createVerticalStrut(10));
        center.add(subtitle);
        center.add(Box.createVerticalStrut(30));

        JPanel accountCard = new JPanel(new BorderLayout());
        accountCard.setBackground(Color.WHITE);
        accountCard.setMaximumSize(new Dimension(700,110));
        accountCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,225,235),1,true),
                BorderFactory.createEmptyBorder(20,25,20,25)));

        JLabel lblBalanceTitle = new JLabel("Current Balance");
        lblBalanceTitle.setFont(new Font("Segoe UI",Font.PLAIN,18));
        lblBalanceTitle.setForeground(Color.GRAY);

         lblBalance = new JLabel("₹ " + String.format("%,.2f", user.getBalance()));
        lblBalance.setFont(new Font("Segoe UI",Font.BOLD,34));
        lblBalance.setForeground(new Color(15,45,95));

        accountCard.add(lblBalanceTitle,BorderLayout.NORTH);
        accountCard.add(lblBalance,BorderLayout.CENTER);

        center.add(accountCard);

        center.add(Box.createVerticalStrut(35));

        JPanel grid = new JPanel(new GridLayout(2,3,35,35));
        grid.setOpaque(false);
        grid.setMaximumSize(new Dimension(1000,350));

        btnBalance = createButton("View Balance");
        btnDeposit = createButton("Deposit");
        btnWithdraw = createButton("Withdraw");
        btnTransfer = createButton("Transfer");
        btnHistory = createButton("Transactions");
        btnChangePin = createButton("Change PIN");

        grid.add(btnBalance);
        grid.add(btnDeposit);
        grid.add(btnWithdraw);
        grid.add(btnTransfer);
        grid.add(btnHistory);
        grid.add(btnChangePin);

        center.add(grid);

        center.add(Box.createVerticalStrut(45));

        btnLogout = new JButton("🚪 Logout");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setPreferredSize(new Dimension(220,55));
        btnLogout.setMaximumSize(new Dimension(220,55));

        styleLogoutButton(btnLogout);

        center.add(btnLogout);

        center.add(Box.createVerticalGlue());

        mainPanel.add(center,BorderLayout.CENTER);

        add(mainPanel);

        btnBalance.addActionListener(this);
        btnDeposit.addActionListener(this);
        btnWithdraw.addActionListener(this);
        btnTransfer.addActionListener(this);
        btnHistory.addActionListener(this);
        btnChangePin.addActionListener(this);
        btnLogout.addActionListener(this);

        setVisible(true);
       
    }
    public void refreshBalance() {

        UserDAO dao = new UserDAO();

        User latestUser = dao.getUserById(user.getUserId());   // or getId()

        if (latestUser != null) {
            user = latestUser;
            lblBalance.setText("₹ " + String.format("%,.2f", user.getBalance()));
        }
    }
    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(new Font("Segoe UI", Font.BOLD, 20));

        button.setBackground(Color.WHITE);
        button.setForeground(new Color(15,45,95));

        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);

        button.setPreferredSize(new Dimension(260,120));

        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(15,45,95),2,true),
                BorderFactory.createEmptyBorder(15,15,15,15)));

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(15,45,95));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(new Color(15,45,95));
            }

        });

        return button;
    }
    private void styleLogoutButton(JButton button){

        button.setFont(new Font("Segoe UI",Font.BOLD,18));

        button.setBackground(new Color(220,53,69));
        button.setForeground(Color.black);

        button.setFocusPainted(false);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.setBorder(BorderFactory.createEmptyBorder(12,30,12,30));

        button.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseEntered(MouseEvent e){

                button.setBackground(new Color(180,35,50));

            }

            @Override
            public void mouseExited(MouseEvent e){

                button.setBackground(new Color(220,53,69));

            }

        });

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnBalance) {

            new BalanceForm(user);

        }

        else if (e.getSource() == btnDeposit) {

            new DepositForm(user, this);

        }

        else if (e.getSource() == btnWithdraw) {

            new WithdrawForm(user,this);

        }

        else if (e.getSource() == btnTransfer) {

            new TransferForm(user);

        }

        else if (e.getSource() == btnHistory) {

            new TransactionHistoryForm(user);

        }

        else if (e.getSource() == btnChangePin) {

            new ChangePinForm(user);

        }

        else if (e.getSource() == btnLogout) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {

                dispose();
                new LoginForm();

            }

        }

    }
}