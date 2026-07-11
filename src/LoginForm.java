
package Task1;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginForm {

    JFrame frame;

    JTextField txtUser;
    JPasswordField txtPass;
    private JTextField txtSignIn;
    private JFrame dashboardFrame;
    private Runnable onSuccess;
    
    public LoginForm(JFrame dashboardFrame ,Runnable onSuccess) {
        this.dashboardFrame = dashboardFrame;
        this.onSuccess = onSuccess;     

        frame = new JFrame("Login");
        frame.getContentPane().setBackground(new Color(204, 255, 153));

        frame.setSize(718,534);
        frame.getContentPane().setLayout(null);

        JLabel l1 = new JLabel("Username");
        l1.setForeground(new Color(0, 128, 0));
        l1.setFont(new Font("Century", Font.BOLD, 13));
        l1.setBounds(50,156,115,25);

        JLabel l2 = new JLabel("Password");
        l2.setForeground(new Color(0, 128, 0));
        l2.setFont(new Font("Century", Font.BOLD, 13));
        l2.setBounds(50,217,115,25);

        txtUser = new JTextField();
        txtUser.setBounds(134,156,150,25);

        txtPass = new JPasswordField();
        txtPass.setBounds(134,217,150,25);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(255, 255, 255));
        loginBtn.setForeground(new Color(0, 128, 0));
        loginBtn.setFont(new Font("Century", Font.BOLD, 16));
        loginBtn.setBounds(100,282,100,30);

        loginBtn.addActionListener(e -> login());
        JLabel noAccount = new JLabel("Don't have an account?");
        noAccount.setBounds(36, 340, 143, 20);
        noAccount.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel signup = new JLabel("Register now");
        signup.setBounds(216, 340, 120, 20);
        signup.setForeground(new Color(116, 185, 87));
        signup.setFont(new Font("Segoe UI", Font.BOLD, 12));
        signup.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new RegisterForm(dashboardFrame, onSuccess); // Opens RegisterForm
            }
        });

        frame.getContentPane().add(l1);
        frame.getContentPane().add(l2);
        frame.getContentPane().add(txtUser);
        frame.getContentPane().add(txtPass);
        frame.getContentPane().add(loginBtn);
        frame.getContentPane().add(noAccount);
        frame.getContentPane().add(signup);
        
        JTextArea textArea = new JTextArea();
        textArea.setBackground(new Color(46, 139, 87));
        textArea.setBounds(346, 0, 358, 497);
        frame.getContentPane().add(textArea);
        
        txtSignIn = new JTextField();
        txtSignIn.setBackground(new Color(204, 255, 153));
        txtSignIn.setForeground(new Color(0, 0, 0));
        txtSignIn.setFont(new Font("Times New Roman", Font.BOLD, 27));
        txtSignIn.setText("SIGN IN");
        txtSignIn.setBounds(100, 55, 230, 42);
        txtSignIn.setBorder(null);
        frame.getContentPane().add(txtSignIn);
        txtSignIn.setColumns(10);
        

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void login() {
    	if(txtUser.getText().trim().isEmpty()
    	        || txtPass.getPassword().length==0){

    	    JOptionPane.showMessageDialog(
    	            frame,
    	            "Please enter Username and Password");

    	    return;
    	}

        try {

            Connection con = DBConnection.getConnection();

            String sql ="SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, txtUser.getText());
            ps.setString(2, String.valueOf(txtPass.getPassword()));

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {

            	int id = rs.getInt("USER_ID");
            	String user = rs.getString("USERNAME");

            	DashboardForm.currentUserId = id;
            	DashboardForm.currentUsername = user;

            	JOptionPane.showMessageDialog(
            	        frame,
            	        "Welcome " + user + "!"
            	);

            	frame.dispose();

            	if(onSuccess != null)
            	    onSuccess.run();
               }           
            else {

                JOptionPane.showMessageDialog(frame,
                        "Invalid Credentials");
            }
          
            rs.close();
            ps.close();
            con.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

  
}
