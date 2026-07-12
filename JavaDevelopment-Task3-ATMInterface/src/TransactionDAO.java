package Task3;

import Task3.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import Task3.Transaction;

public class TransactionDAO {

    Connection con;

    public TransactionDAO() {

        con = DBConnection.getConnection();
    }

    public void saveTransaction(String accountNo,
                                String type,
                                double amount,
                                double balance) {

        try {

            String sql = "INSERT INTO transaction3(account_no,transaction_type,amount,balance) VALUES(?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, accountNo);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.setDouble(4, balance);

            ps.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public ArrayList<Transaction> getHistory(String accountNo) {

        ArrayList<Transaction> list = new ArrayList<>();

        try {

            String sql = "SELECT * FROM transaction3 WHERE account_no=? ORDER BY transaction_date DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, accountNo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Transaction t = new Transaction();

                t.setTransactionId(rs.getInt("transaction_id"));
                t.setAccountNo(rs.getString("account_no"));
                t.setTransactionType(rs.getString("transaction_type"));
                t.setAmount(rs.getDouble("amount"));
                t.setBalance(rs.getDouble("balance"));
                t.setTransactionDate(rs.getTimestamp("transaction_date"));

                list.add(t);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;

    }

}