package Task1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;
import java.util.regex.Pattern;

public class RegisterForm {

    JFrame frame;

    JTextField txtUsername;
    JTextField txtEmail;
    JPasswordField txtPassword;

    private JFrame dashboardFrame;
    private Runnable onSuccess;

    public RegisterForm(JFrame dashboardFrame, Runnable onSuccess) {

        this.dashboardFrame = dashboardFrame;
        this.onSuccess = onSuccess;

        frame = new JFrame("Railway Reservation System - Register");
        frame.setSize(650, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Color primary = new Color(30,41,59);
        Color accent = new Color(59,130,246);
        Color bg = new Color(245,247,250);

        frame.setLayout(new BorderLayout());

        //HEADER

        JPanel header = new JPanel();
        header.setBackground(primary);

        JLabel title = new JLabel("Create Your Account");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,28));

        header.add(title);

        frame.add(header,BorderLayout.NORTH);

        // FORM PANEL 

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(bg);
        panel.setBorder(new EmptyBorder(30,40,30,40));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Segoe UI",Font.BOLD,15);

        // Username
        gbc.gridx=0;
        gbc.gridy=0;

        JLabel lblUser=new JLabel("Username");
        lblUser.setFont(labelFont);
        panel.add(lblUser,gbc);

        gbc.gridx=1;

        txtUsername=new JTextField(20);
        txtUsername.setFont(new Font("Segoe UI",Font.PLAIN,15));
        panel.add(txtUsername,gbc);

        // Password

        gbc.gridx=0;
        gbc.gridy++;

        JLabel lblPass=new JLabel("Password");
        lblPass.setFont(labelFont);
        panel.add(lblPass,gbc);

        gbc.gridx=1;

        txtPassword=new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI",Font.PLAIN,15));
        panel.add(txtPassword,gbc);

        // Email

        gbc.gridx=0;
        gbc.gridy++;

        JLabel lblEmail=new JLabel("Email");
        lblEmail.setFont(labelFont);
        panel.add(lblEmail,gbc);

        gbc.gridx=1;

        txtEmail=new JTextField(20);
        txtEmail.setFont(new Font("Segoe UI",Font.PLAIN,15));
        panel.add(txtEmail,gbc);

        // Register Button

        gbc.gridx=0;
        gbc.gridy++;
        gbc.gridwidth=2;
        gbc.anchor=GridBagConstraints.CENTER;

        JButton registerBtn=new JButton("REGISTER");

        registerBtn.setBackground(accent);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("Segoe UI",Font.BOLD,16));
        registerBtn.setFocusPainted(false);
        registerBtn.setPreferredSize(new Dimension(180,45));

        panel.add(registerBtn,gbc);

     

        gbc.gridy++;

        JButton loginBtn=new JButton("Already have an account? Login");

        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(false);
        loginBtn.setForeground(accent);
        loginBtn.setFont(new Font("Segoe UI",Font.BOLD,14));

        panel.add(loginBtn,gbc);

        frame.add(panel,BorderLayout.CENTER);


        registerBtn.addActionListener(e->registerUser());

        loginBtn.addActionListener(e->{

            frame.dispose();

            new LoginForm(null,null);

        });

        frame.setVisible(true);
    }

    private void registerUser(){

        String username=txtUsername.getText().trim();
        String password=String.valueOf(txtPassword.getPassword()).trim();
        String email=txtEmail.getText().trim();

        if(username.isEmpty()||password.isEmpty()||email.isEmpty()){

            JOptionPane.showMessageDialog(frame,
                    "All fields are required.");

            return;
        }

        if(!Pattern.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
                email)){

            JOptionPane.showMessageDialog(frame,
                    "Enter a valid email.");

            return;
        }

        try{

            Connection con=DBConnection.getConnection();

            PreparedStatement check=
                    con.prepareStatement(
                    "SELECT * FROM USERS WHERE USERNAME=?");

            check.setString(1,username);

            ResultSet rs=check.executeQuery();

            if(rs.next()){

                JOptionPane.showMessageDialog(frame,
                        "Username already exists.");

                return;
            }

            PreparedStatement ps=
                    con.prepareStatement(
                    "INSERT INTO USERS(USERNAME,PASSWORD,EMAIL) VALUES(?,?,?)");

            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,email);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame,
                    "Registration Successful!");

            con.close();

            frame.dispose();

            new LoginForm(null,null);

        }

        catch(Exception ex){

            ex.printStackTrace();

            JOptionPane.showMessageDialog(frame,
                    "Registration Failed.");

        }

    }

}