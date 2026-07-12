package Task3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame implements ActionListener {

    private JTextField txtUser;
    private JPasswordField txtPin;
    private JButton btnLogin, btnExit;
    private JCheckBox showPin;

    public LoginForm() {

        setTitle("ATM Interface");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
      
        setLocationRelativeTo(null);

        GradientPanel background = new GradientPanel();
        background.setLayout(new GridBagLayout());

        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(420, 550));
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(30, 35, 30, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        JLabel icon = new JLabel("💳", SwingConstants.CENTER);
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42));

        gbc.gridy = 0;
        card.add(icon, gbc);

        JLabel title = new JLabel("ATM INTERFACE", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(18, 52, 86));

        gbc.gridy++;
        card.add(title, gbc);

        JLabel subtitle = new JLabel("Secure Banking Login", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(Color.GRAY);

        gbc.gridy++;
        gbc.insets = new Insets(0,0,25,0);
        card.add(subtitle, gbc);

        gbc.insets = new Insets(6,0,6,0);

        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridy++;
        card.add(lblUser, gbc);

        txtUser = new JTextField();
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUser.setPreferredSize(new Dimension(0,40));

        gbc.gridy++;
        card.add(txtUser, gbc);

        JLabel lblPin = new JLabel("PIN");
        lblPin.setFont(new Font("Segoe UI", Font.BOLD, 15));

        gbc.gridy++;
        gbc.insets = new Insets(18,0,6,0);
        card.add(lblPin, gbc);

        txtPin = new JPasswordField();
        txtPin.setFont(new Font("Segoe UI", Font.PLAIN,16));
        txtPin.setPreferredSize(new Dimension(0,40));

        gbc.gridy++;
        gbc.insets = new Insets(6,0,6,0);
        card.add(txtPin, gbc);

        showPin = new JCheckBox("Show PIN");
        showPin.setBackground(Color.WHITE);
        showPin.setFont(new Font("Segoe UI", Font.PLAIN,13));

        showPin.addActionListener(e -> {
            if(showPin.isSelected())
                txtPin.setEchoChar((char)0);
            else
                txtPin.setEchoChar('\u2022');
        });

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        card.add(showPin, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,18,0));
        buttonPanel.setBackground(Color.WHITE);

        btnLogin = new JButton("LOGIN");
        btnExit = new JButton("EXIT");
       
      styleButton(btnLogin,new Color(0,0,0));
      styleButton(btnExit,new Color(220,53,69));

        btnLogin.addActionListener(this);
        btnExit.addActionListener(this);

        buttonPanel.add(btnLogin);
        buttonPanel.add(btnExit);

        gbc.gridy++;
        gbc.insets = new Insets(28,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        card.add(buttonPanel, gbc);

        background.add(card);

        add(background);

        setVisible(true);
    }

    private void styleButton(JButton button, Color color){

        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI",Font.BOLD,15));
        button.setPreferredSize(new Dimension(140,42));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color normal=color;

        button.addMouseListener(new MouseAdapter(){

            public void mouseEntered(MouseEvent e){
                button.setBackground(normal.darker());
            }

            public void mouseExited(MouseEvent e){
                button.setBackground(normal);
            }

        });

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==btnLogin){

            String username=txtUser.getText().trim();
            String pin=String.valueOf(txtPin.getPassword());

            if(username.isEmpty() || pin.isEmpty()){

                JOptionPane.showMessageDialog(this,
                        "Please enter Username and PIN.");
                return;
            }

            UserDAO dao=new UserDAO();
            User user=dao.login(username,pin);

            if(user!=null){

                JOptionPane.showMessageDialog(this,
                        "Welcome "+user.getName());

                dispose();
                new ATMMenu(user);

            }else{

                JOptionPane.showMessageDialog(this,
                        "Invalid Username or PIN.");

            }

        }

        if(e.getSource()==btnExit){

            System.exit(0);

        }

    }

    class GradientPanel extends JPanel{

        @Override
        protected void paintComponent(Graphics g){

            super.paintComponent(g);

            Graphics2D g2=(Graphics2D)g;

            GradientPaint gp=new GradientPaint(
                    0,0,new Color(15,45,95),
                    getWidth(),getHeight(),
                    new Color(0,110,220));

            g2.setPaint(gp);
            g2.fillRect(0,0,getWidth(),getHeight());

        }

    }

}