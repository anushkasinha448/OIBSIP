package Task1;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.*;

public class BookingHistoryForm {

    JFrame frame;
    JTable table;
    DefaultTableModel model;

    public BookingHistoryForm(int userId) {

        frame = new JFrame("Booking History");

        frame.setSize(900,400);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                setHorizontalAlignment(SwingConstants.CENTER);

                if(!isSelected){
                    if(row % 2 == 0)
                        c.setBackground(Color.WHITE);
                    else
                        c.setBackground(new Color(240,248,255));
                }

                return c;
            }
        };

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
        	    "PNR",
        	    "Passenger",
        	    "Train No",
        	    "Train Name",
        	    "Class",
        	    " Date",
        	    "Source",
        	    "Destination",
        	    "Fare"
        	});
        table = new JTable(model);
        table.setRowHeight(38);
        table.setShowGrid(true);
        table.setGridColor(new Color(130,130,130));
        table.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        for(int i=0;i<table.getColumnCount();i++){
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        JTableHeader header = table.getTableHeader();
 
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0,102,204));   // Blue
        header.setForeground(Color.black);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        JScrollPane sp = new JScrollPane(table);

        frame.add(sp);

        loadBookings(userId);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    

    private void loadBookings(int userId) {

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
            	    "SELECT R.PNR, R.PASSENGER_NAME, R.TRAIN_NO, " +
            	    "T.TRAIN_NAME, R.CLASS_TYPE, R.JOURNEY_DATE, " +
            	    "R.SOURCE_STATION, R.DESTINATION_STATION, R.FARE " +
            	    "FROM RESERVATIONS R " +
            	    "JOIN TRAINS T ON R.TRAIN_NO = T.TRAIN_NO " +
            	    "WHERE R.USER_ID = ?"
            	);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

            	model.addRow(new Object[]{
            		    rs.getLong("PNR"),
            		    rs.getString("PASSENGER_NAME"),
            		    rs.getInt("TRAIN_NO"),
            		    rs.getString("TRAIN_NAME"),
            		    rs.getString("CLASS_TYPE"),
            		    rs.getDate("JOURNEY_DATE"),
            		    rs.getString("SOURCE_STATION"),
            		    rs.getString("DESTINATION_STATION"),
            		    rs.getDouble("FARE")
            		});
            }

            con.close();

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
