package Task1;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class CancellationForm {

    JFrame frame;

    JTable table;
    DefaultTableModel model;

    int userId;

    public CancellationForm(int userId) {

        this.userId = userId;

        frame = new JFrame("Cancel Ticket");
        frame.setSize(1000,550);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("ACTIVE BOOKINGS",SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,24));
        title.setOpaque(true);
        title.setBackground(new Color(255,69,0));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(1000,50));

        frame.add(title,BorderLayout.NORTH);

        String columns[]={
                "PNR",
                "Passenger",
                "Train No",
                "Train Name",
                "Journey Date",
                "Class",
                "Fare",
                "Cancel"
        };

        model=new DefaultTableModel(columns,0){

            @Override
            public boolean isCellEditable(int row,int column){

                return column==7;
            }

        };

        table=new JTable(model);

        table.setRowHeight(35);

        table.setFont(new Font("Segoe UI",Font.PLAIN,14));

        JTableHeader header=table.getTableHeader();

        header.setFont(new Font("Segoe UI",Font.BOLD,15));
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.black);
        header.setPreferredSize(new Dimension(100,38));

        DefaultTableCellRenderer center=
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(SwingConstants.CENTER);

        for(int i=0;i<7;i++)
            table.getColumnModel().getColumn(i).setCellRenderer(center);

        table.getColumn("Cancel")
                .setCellRenderer(new ButtonRenderer());

        table.getColumn("Cancel")
                .setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane sp=new JScrollPane(table);

        frame.add(sp,BorderLayout.CENTER);

        loadBookings();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadBookings(){

        model.setRowCount(0);

        try{

            Connection con=DBConnection.getConnection();

            PreparedStatement ps=
                    con.prepareStatement(

                            "SELECT R.PNR," +
                            "R.PASSENGER_NAME," +
                            "R.TRAIN_NO," +
                            "T.TRAIN_NAME," +
                            "R.JOURNEY_DATE," +
                            "R.CLASS_TYPE," +
                            "R.FARE " +
                            "FROM RESERVATIONS R " +
                            "JOIN TRAINS T " +
                            "ON R.TRAIN_NO=T.TRAIN_NO " +
                            "WHERE R.USER_ID=? " +
                            "ORDER BY R.JOURNEY_DATE"

                    );

            ps.setInt(1,userId);

            ResultSet rs=ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{

                        rs.getLong("PNR"),
                        rs.getString("PASSENGER_NAME"),
                        rs.getInt("TRAIN_NO"),
                        rs.getString("TRAIN_NAME"),
                        rs.getDate("JOURNEY_DATE"),
                        rs.getString("CLASS_TYPE"),
                        rs.getDouble("FARE"),
                        "Cancel"

                });

            }

            con.close();

        }

        catch(Exception ex){

            ex.printStackTrace();

        }

    }

    class ButtonRenderer extends JButton
            implements TableCellRenderer{

        public ButtonRenderer(){

            setText("Cancel");

            setBackground(new Color(220,53,69));

            setForeground(Color.WHITE);

        }

        @Override
        public Component getTableCellRendererComponent(

                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column){

            return this;

        }

    }

    class ButtonEditor extends DefaultCellEditor{

        JButton button;

        int row;

        public ButtonEditor(JCheckBox box){

            super(box);

            button=new JButton("Cancel");

            button.setBackground(new Color(220,53,69));

            button.setForeground(Color.WHITE);

            button.addActionListener(e->fireEditingStopped());

        }

        @Override
        public Component getTableCellEditorComponent(

                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column){

            this.row=row;

            return button;

        }

        @Override
        public Object getCellEditorValue(){

            cancelTicket(row);

            return "Cancel";

        }

    }

    private void cancelTicket(int row){

        int option=JOptionPane.showConfirmDialog(

                frame,

                "Are you sure you want to cancel this ticket?",

                "Confirm Cancellation",

                JOptionPane.YES_NO_OPTION

        );

        if(option!=JOptionPane.YES_OPTION)
            return;

        try{

            long pnr=(Long)model.getValueAt(row,0);

            int trainNo=(Integer)model.getValueAt(row,2);

            Connection con=DBConnection.getConnection();

            PreparedStatement ps=

                    con.prepareStatement(

                            "DELETE FROM RESERVATIONS " +
                            "WHERE PNR=?"

                    );

            ps.setLong(1,pnr);

            ps.executeUpdate();

            PreparedStatement seat=

                    con.prepareStatement(

                            "UPDATE TRAINS " +
                            "SET SEATS=SEATS+1 " +
                            "WHERE TRAIN_NO=?"

                    );

            seat.setInt(1,trainNo);

            seat.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(

                    frame,

                    "Ticket Cancelled Successfully"

            );

            loadBookings();

        }

        catch(Exception ex){

            ex.printStackTrace();

        }

    }

}