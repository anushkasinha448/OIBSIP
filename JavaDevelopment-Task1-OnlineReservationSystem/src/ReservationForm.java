package Task1;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReservationForm {

    JFrame frame;
    JLabel lblFare;
    JTextField txtPassenger;
    JDateChooser dateChooser;

    JComboBox<String> classBox;

    JLabel lblTrainNo;
    JLabel lblTrainName;
    JLabel lblSource;
    JLabel lblDestination;

    int userId;
    int trainNo;
    String trainName;
    String source;
    String destination;

    public ReservationForm(int userId,
                           int trainNo,
                           String trainName,
                           String source,
                           String destination) {

        this.userId = userId;
        this.trainNo = trainNo;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;

        frame = new JFrame("Book Ticket");
        frame.setSize(650,590);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new Color(250,250,250));

        JTextArea heading = new JTextArea("BOOK TICKET");
        heading.setEditable(false);
        heading.setBackground(new Color(255,87,34));
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Segoe UI",Font.BOLD,18));
        heading.setBounds(0,0,650,40);
        frame.getContentPane().add(heading);

        Font labelFont = new Font("Segoe UI",Font.BOLD,14);

        JLabel p = new JLabel("Passenger Name");
        p.setFont(labelFont);
        p.setBounds(50,60,150,25);
        frame.getContentPane().add(p);

        txtPassenger = new JTextField();
        txtPassenger.setFont(new Font("Arial", Font.PLAIN, 12));
        txtPassenger.setBounds(220,60,300,30);
        frame.getContentPane().add(txtPassenger);

        JLabel tn = new JLabel("Train Number");
        tn.setFont(labelFont);
        tn.setBounds(50,110,150,25);
        frame.getContentPane().add(tn);

        lblTrainNo = new JLabel(String.valueOf(trainNo));
        lblTrainNo.setBounds(220,110,300,25);
        frame.getContentPane().add(lblTrainNo);

        JLabel train = new JLabel("Train Name");
        train.setFont(labelFont);
        train.setBounds(50,150,150,25);
        frame.getContentPane().add(train);

        lblTrainName = new JLabel(trainName);
        lblTrainName.setBounds(220,150,300,25);
        frame.getContentPane().add(lblTrainName);

        JLabel cls = new JLabel("Class");
        cls.setFont(labelFont);
        cls.setBounds(50,190,150,25);
        frame.getContentPane().add(cls);

        classBox = new JComboBox<>(
                new String[]{
                        "Sleeper",
                        "AC",
                        "First Class"
                });
        classBox.setFont(new Font("Arial", Font.PLAIN, 12));
        classBox.addActionListener(e -> updateFare());

        classBox.setBounds(220,190,300,30);
        frame.getContentPane().add(classBox);

        JLabel date = new JLabel("Journey Date");
        date.setFont(labelFont);
        date.setBounds(50,240,150,25);
        frame.getContentPane().add(date);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(220,240,300,30);
        dateChooser.setDate(new java.util.Date());
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setMinSelectableDate(new java.util.Date());
        frame.getContentPane().add(dateChooser);

        JLabel from = new JLabel("From");
        from.setFont(labelFont);
        from.setBounds(50,290,150,25);
        frame.getContentPane().add(from);

        lblSource = new JLabel(source);
        lblSource.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSource.setBounds(220,290,300,25);
        frame.getContentPane().add(lblSource);

        JLabel to = new JLabel("To");
        to.setFont(labelFont);
        to.setBounds(50,330,150,25);
        frame.getContentPane().add(to);

        lblDestination = new JLabel(destination);
        lblDestination.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDestination.setBounds(220,330,300,25);
        frame.getContentPane().add(lblDestination);
        
        JLabel fare = new JLabel("Fare");
        fare.setFont(labelFont);
        fare.setBounds(50,370,150,25);

        frame.getContentPane().add(fare);

        lblFare = new JLabel("₹ 0");
        lblFare.setFont(new Font("Segoe UI",Font.BOLD,16));
        lblFare.setForeground(new Color(0,128,0));
        lblFare.setBounds(220,370,200,25);

        frame.getContentPane().add(lblFare);
        updateFare();

        JButton reserve = new JButton("Reserve Ticket");
        reserve.setBounds(220,430,180,40);
        reserve.setBackground(new Color(76,175,80));
        reserve.setForeground(new Color(0, 0, 205));
        reserve.setFont(new Font("Segoe UI",Font.BOLD,15));

        reserve.addActionListener(e->reserveTicket());

        frame.getContentPane().add(reserve);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private void updateFare(){

        try{

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(
                    "SELECT FARE FROM TRAIN_FARE " +
                    "WHERE TRAIN_NO=? AND CLASS_TYPE=?");

            ps.setInt(1, trainNo);

            ps.setString(2, classBox.getSelectedItem().toString());

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                lblFare.setText("₹ " + rs.getDouble("FARE"));

            }

            con.close();

        }

        catch(Exception ex){

            ex.printStackTrace();

        }

    }

    private void reserveTicket(){

        if(txtPassenger.getText().trim().isEmpty()){

            JOptionPane.showMessageDialog(frame,"Enter Passenger Name");

            return;
        }

        if(dateChooser.getDate()==null){

            JOptionPane.showMessageDialog(frame,"Enter Journey Date");

            return;
        }

        try{

            Connection con=DBConnection.getConnection();

            PreparedStatement trainPs=
                    con.prepareStatement("SELECT SEATS FROM TRAINS WHERE TRAIN_NO=?");

            trainPs.setInt(1,trainNo);

            ResultSet rs=trainPs.executeQuery();

            if(!rs.next()){

                JOptionPane.showMessageDialog(frame, "Train not found");

                return;
            }

            if(rs.getInt("SEATS")<=0){

                JOptionPane.showMessageDialog(frame, "No Seats Available");

                return;
            }

            String classType=classBox.getSelectedItem().toString();

            PreparedStatement farePs=
                    con.prepareStatement(
                            "SELECT FARE FROM TRAIN_FARE " +
                            "WHERE TRAIN_NO=? AND CLASS_TYPE=?");

            farePs.setInt(1,trainNo);

            farePs.setString(2,classType);

            ResultSet fareRs=farePs.executeQuery();

            double fare=0;

            if(fareRs.next()){

                fare=fareRs.getDouble("FARE");

            }else{

                JOptionPane.showMessageDialog(frame,
                        "Fare not available");

                return;
            }

            long pnr=System.currentTimeMillis();

            PreparedStatement ps=con.prepareStatement("INSERT INTO RESERVATIONS VALUES(?,?,?,?,?,?,?,?,?)");

            ps.setLong(1,pnr);
            ps.setInt(2,userId);
            ps.setString(3,txtPassenger.getText());
            ps.setInt(4,trainNo);
            ps.setString(5,classType);
            java.util.Date utilDate = dateChooser.getDate();

            java.sql.Date sqlDate =new java.sql.Date(utilDate.getTime());

            ps.setDate(6, sqlDate);
            ps.setString(7,source);
            ps.setString(8,destination);
            ps.setDouble(9,fare);

            ps.executeUpdate();

            PreparedStatement seatPs=
                    con.prepareStatement(
                            "UPDATE TRAINS SET SEATS=SEATS-1 WHERE TRAIN_NO=?");

            seatPs.setInt(1,trainNo);

            seatPs.executeUpdate();

            JOptionPane.showMessageDialog(
                    frame,
                    "Ticket Booked Successfully\n\n"+
                    "PNR : "+pnr+
                    "\nPassenger : "+txtPassenger.getText()+
                    "\nTrain : "+trainName+
                    "\nTrain No : "+trainNo+
                    "\nFrom : "+source+
                    "\nTo : "+destination+
                    "\nClass : "+classType+
                    "\nFare : ₹"+fare
            );

            frame.dispose();

            con.close();

        }

        catch(Exception ex){

            ex.printStackTrace();

            JOptionPane.showMessageDialog(frame,
                    ex.getMessage());

        }

    }

}