
package Task3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DepositForm extends JFrame {

    private ATMMenu menu;
    public DepositForm(User user , ATMMenu menu) {
    	 this.menu = menu;

        setTitle("Deposit Money");
        setSize(600, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(245,248,252));


        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15,45,95));
        header.setPreferredSize(new Dimension(520,70));

        JLabel title = new JLabel("💵  DEPOSIT MONEY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,24));
        title.setBorder(new EmptyBorder(0,20,0,0));

        header.add(title,BorderLayout.WEST);

        background.add(header,BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                new EmptyBorder(25,30,25,30)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12,10,12,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel icon = new JLabel("💰",SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji",Font.PLAIN,42));

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth=2;
        card.add(icon,gbc);

        JLabel heading = new JLabel("Deposit Amount",SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI",Font.BOLD,22));
        heading.setForeground(new Color(15,45,95));

        gbc.gridy++;
        card.add(heading,gbc);

        gbc.gridwidth=1;

        JLabel lblAmount = new JLabel("Amount (₹)");
        lblAmount.setFont(new Font("Segoe UI",Font.BOLD,15));

        gbc.gridx=0;
        gbc.gridy++;
        card.add(lblAmount,gbc);

        JTextField txtAmount = new JTextField();
        txtAmount.setFont(new Font("Segoe UI",Font.PLAIN,16));

        gbc.gridx=1;
        card.add(txtAmount,gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,15,0));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnDeposit = new JButton("Deposit");
        JButton btnCancel = new JButton("Cancel");

        styleButton(btnDeposit,new Color(0,120,215));
        styleButton(btnCancel,new Color(220,53,69));

        buttonPanel.add(btnDeposit);
        buttonPanel.add(btnCancel);

        gbc.gridx=0;
        gbc.gridy++;
        gbc.gridwidth=2;
        gbc.insets=new Insets(25,0,0,0);

        card.add(buttonPanel,gbc);


        btnDeposit.addActionListener(e->{

            try{

                double amount = Double.parseDouble(txtAmount.getText().trim());

                if(amount<=0){

                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid amount.");

                    return;

                }

                UserDAO dao = new UserDAO();

                if (dao.deposit(user.getAccountNo(), amount)) {

                    double balance = dao.getBalance(user.getAccountNo());

                    user.setBalance(balance);

                    TransactionDAO td = new TransactionDAO();

                    td.saveTransaction(
                            user.getAccountNo(),
                            "Deposit",
                            amount,
                            balance);

                    menu.refreshBalance();

                    JOptionPane.showMessageDialog(this,
                            "₹ " + amount + " deposited successfully.");

                    dispose();
                }

            }

            catch(NumberFormatException ex){

                JOptionPane.showMessageDialog(this,
                        "Please enter a numeric amount.");

            }

        });

        btnCancel.addActionListener(e->dispose());

        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(245,248,252));
        center.add(card);

        background.add(center,BorderLayout.CENTER);

        add(background);

        setVisible(true);

    }

    // ================= BUTTON STYLE =================

    private void styleButton(JButton button, Color color){

        button.setBackground(color);
        button.setForeground(Color.black);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI",Font.BOLD,15));
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
