package Task1;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class SearchTrainForm {

    JFrame frame;

    JComboBox<String> cmbSource;
    JComboBox<String> cmbDestination;
    private int userId;
    JTable table;
    DefaultTableModel model;

    Timer timer;

    public SearchTrainForm( int userId) {
    	this.userId = DashboardForm.currentUserId;
        frame = new JFrame("Search Trains");
        frame.getContentPane().setBackground(new Color(244, 164, 96));
        frame.setSize(950, 520);
        frame.getContentPane().setLayout(null);

        JLabel lblSource = new JLabel("From");
        lblSource.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblSource.setBounds(40, 30, 60, 25);

        cmbSource = new JComboBox<>();
        cmbSource.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbDestination = new JComboBox<>();
        cmbDestination.setFont(new Font("Arial", Font.PLAIN, 12));

        cmbSource.setBounds(90,30,180,28);
        cmbDestination.setBounds(340,30,180,28);

        loadStations();

        JLabel lblDestination = new JLabel("To");
        lblDestination.setFont(new Font("Arial Black", Font.PLAIN, 14));
        lblDestination.setBounds(300, 30, 60, 25);

        JButton btnSearch = new JButton("Search");
        btnSearch.setForeground(new Color(25, 25, 112));
        btnSearch.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSearch.setBounds(540, 30, 100, 25);

        String[] columns = {
                "Train No",
                "Train Name",
                "Source",
                "Destination",
                "Seats",
                "Sleeper Fare",
                "AC Fare",
                "First Class Fare",
                "Action"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);
        table.setRowHeight(35);

        table.setFont(new Font("Segoe UI",Font.PLAIN,14));

        JTableHeader header=table.getTableHeader();

        header.setFont(new Font("Segoe UI",Font.BOLD,15));
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.black);
        header.setPreferredSize(new Dimension(100,38));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 80, 900, 380);

        btnSearch.addActionListener(e ->  searchTrains());

        frame.getContentPane().add(lblSource);
        frame.getContentPane().add(cmbSource);
        frame.getContentPane().add(lblDestination);
        frame.getContentPane().add(cmbDestination);
        frame.getContentPane().add(btnSearch);
        frame.getContentPane().add(scrollPane);

        // BUTTON RENDERER + EDITOR
        table.getColumn("Action").setCellRenderer(new ButtonRenderer());
        table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        // LIVE SEAT UPDATE EVERY 5 SEC
        timer = new Timer(5000, e -> refreshSeats());
        timer.start();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // SEARCH TRAINS
    private void searchTrains() {
      
    	String source = cmbSource.getSelectedItem().toString();
    	String destination = cmbDestination.getSelectedItem().toString();

        if (source.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Enter Source and Destination");
            return;
        }

        model.setRowCount(0);

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT t.TRAIN_NO, t.TRAIN_NAME, t.SOURCE, t.DESTINATION, t.SEATS, " +
                    "MAX(CASE WHEN f.CLASS_TYPE='Sleeper' THEN f.FARE END) AS SLEEPER_FARE, " +
                    "MAX(CASE WHEN f.CLASS_TYPE='AC' THEN f.FARE END) AS AC_FARE, " +
                    "MAX(CASE WHEN f.CLASS_TYPE='First Class' THEN f.FARE END) AS FIRST_FARE " +
                    "FROM TRAINS t " +
                    "LEFT JOIN TRAIN_FARE f ON t.TRAIN_NO = f.TRAIN_NO " +
                    "WHERE UPPER(t.SOURCE)=UPPER(?) " +
                    "AND UPPER(t.DESTINATION)=UPPER(?) " +
                    "GROUP BY t.TRAIN_NO, t.TRAIN_NAME, t.SOURCE, t.DESTINATION, t.SEATS";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, source);
            ps.setString(2, destination);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                model.addRow(new Object[] {
                        rs.getInt("TRAIN_NO"),
                        rs.getString("TRAIN_NAME"),
                        rs.getString("SOURCE"),
                        rs.getString("DESTINATION"),
                        rs.getInt("SEATS"),
                        rs.getDouble("SLEEPER_FARE"),
                        rs.getDouble("AC_FARE"),
                        rs.getDouble("FIRST_FARE"),
                        DashboardForm.currentUserId > 0 ? "BOOK" : "LOGIN TO BOOK"
                });
            }

            if (!found) {
                JOptionPane.showMessageDialog(frame, "No trains found");
            }

            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, ex.getMessage());
        }
    }
    private void loadStations() {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT DISTINCT SOURCE FROM TRAINS " +
                    "UNION " +
                    "SELECT DISTINCT DESTINATION FROM TRAINS " +
                    "ORDER BY 1"
            );

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                String station = rs.getString(1);

                cmbSource.addItem(station);
                cmbDestination.addItem(station);

            }

            con.close();

        } catch(Exception e) {

            e.printStackTrace();

        }

    }
 // LIVE SEAT UPDATE
    private void refreshSeats() {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT TRAIN_NO, SEATS FROM TRAINS"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int trainNo = rs.getInt("TRAIN_NO");
                int seats = rs.getInt("SEATS");

                for (int i = 0; i < model.getRowCount(); i++) {

                    if (model.getValueAt(i, 0).equals(trainNo)) {
                        model.setValueAt(seats, i, 4);
                    }
                }
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // OPEN RESERVATION FORM
    public void openReservation(int row) {

        int trainNo = Integer.parseInt(model.getValueAt(row,0).toString());

        String trainName = model.getValueAt(row,1).toString();

        String source = model.getValueAt(row,2).toString();

        String destination = model.getValueAt(row,3).toString();

        new ReservationForm(
        		 DashboardForm.currentUserId,
                trainNo,
                trainName,
                source,
                destination);
    }

    // BUTTON RENDERER
    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setText("BOOK");
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            setText(value.toString());

            return this;
        }
    }

    // BUTTON EDITOR
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private SearchTrainForm parent;
        private int row;

        public ButtonEditor(JCheckBox checkBox, SearchTrainForm parent) {
            super(checkBox);

            this.parent = parent;

            button = new JButton();

            button.addActionListener(e -> fireEditingStopped());
        }

        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {

            this.row = row;

            button.setText(value.toString());

            return button;
        }

        public Object getCellEditorValue() {

        	if (DashboardForm.currentUserId <= 0) {

        	    JOptionPane.showMessageDialog(
        	            parent.frame,
        	            "Please login first.");

        	    new LoginForm(parent.frame, () -> {

        	        parent.userId = DashboardForm.currentUserId;

        	        parent.openReservation(row);

        	    });

        	    return "LOGIN TO BOOK";
        	}

        	parent.userId = DashboardForm.currentUserId;
        	parent.openReservation(row);

        	return "BOOK";
        }
    }

   
}