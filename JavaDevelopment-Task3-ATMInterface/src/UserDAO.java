package Task3;
import Task3.User;

import java.sql.*;


public class UserDAO {

    Connection con;

    public UserDAO() {
        con = DBConnection.getConnection();
    }

    public User login(String username, String pin) {

        User user = null;

        try {

            String sql = "SELECT * FROM users3 WHERE username=? AND pin=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, pin);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPin(rs.getString("pin"));
                user.setAccountNo(rs.getString("account_no"));
                user.setName(rs.getString("name"));
                user.setBalance(rs.getDouble("balance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
    public double getBalance(String accountNo) {

        double balance = 0;

        try {

            String sql = "SELECT balance FROM users3 WHERE account_no=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, accountNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                balance = rs.getDouble("balance");
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return balance;
    }

    // ================= DEPOSIT =================
    public boolean deposit(String accountNo, double amount) {

        try {

            String sql = "UPDATE users3 SET balance=balance+? WHERE account_no=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setString(2, accountNo);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean withdraw(String accountNo, double amount) {

        try {

            double balance = getBalance(accountNo);

            if (balance < amount)
                return false;

            String sql = "UPDATE users3 SET balance=balance-? WHERE account_no=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, amount);
            ps.setString(2, accountNo);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public boolean transfer(String sender, String receiver, double amount) {

        try {

            con.setAutoCommit(false);

            double balance = getBalance(sender);

            if (balance < amount) {

                con.rollback();
                return false;
            }

            PreparedStatement ps1 = con.prepareStatement(
                    "UPDATE users3 SET balance=balance-? WHERE account_no=?");

            ps1.setDouble(1, amount);
            ps1.setString(2, sender);

            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(
                    "UPDATE users3 SET balance=balance+? WHERE account_no=?");

            ps2.setDouble(1, amount);
            ps2.setString(2, receiver);

            int row = ps2.executeUpdate();

            if (row == 0) {

                con.rollback();
                return false;
            }

            con.commit();
            con.setAutoCommit(true);

            return true;

        } catch (Exception e) {

            try {
                con.rollback();
            } catch (Exception ex) {
            }

            e.printStackTrace();
        }

        return false;
    }
    public User getUserById(int userId) {

        User user = null;

        try {

            String sql = "SELECT * FROM users3 WHERE user_id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPin(rs.getString("pin"));
                user.setAccountNo(rs.getString("account_no"));
                user.setName(rs.getString("name"));
                user.setBalance(rs.getDouble("balance"));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean changePin(String accountNo,
                             String oldPin,
                             String newPin) {

        try {

            String sql = "UPDATE users3 SET pin=? WHERE account_no=? AND pin=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newPin);
            ps.setString(2, accountNo);
            ps.setString(3, oldPin);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

}
