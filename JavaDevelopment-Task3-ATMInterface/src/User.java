package Task3;

public class User {

    private int userId;
    private String username;
    private String pin;
    private String accountNo;
    private String name;
    private double balance;

    public User() {
    }

    public User(int userId, String username, String pin,
                String accountNo, String name, double balance) {

        this.userId = userId;
        this.username = username;
        this.pin = pin;
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
