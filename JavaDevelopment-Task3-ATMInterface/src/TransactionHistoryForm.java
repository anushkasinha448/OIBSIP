
package Task3;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class TransactionHistoryForm extends JFrame {

    private JTable table;

    public TransactionHistoryForm(User user) {

        setTitle("Transaction History");
        setSize(850, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel background = new JPanel(new BorderLayout());
        background.setBackground(new Color(245, 248, 252));

        JPanel header = new JPanel(new BorderLayout());
        header.setPreferredSize(new Dimension(850, 70));
        header.setBackground(new Color(15, 45, 95));

        JLabel title = new JLabel("📜  TRANSACTION HISTORY");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(new EmptyBorder(0, 20, 0, 0));

        JLabel userLabel = new JLabel("👤 " + user.getName() + "   ");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        header.add(title, BorderLayout.WEST);
        header.add(userLabel, BorderLayout.EAST);

        background.add(header, BorderLayout.NORTH);


        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel(
                "Account No : " + user.getAccountNo(),
                SwingConstants.CENTER);

        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        heading.setForeground(new Color(15, 45, 95));
        heading.setBorder(new EmptyBorder(0, 0, 15, 0));

        card.add(heading, BorderLayout.NORTH);


        String[] columns = {
                "Transaction ID",
                "Type",
                "Amount (₹)",
                "Balance (₹)",
                "Date"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        table = new JTable(model);

        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(32);
        table.setSelectionBackground(new Color(0, 120, 215));
        table.setSelectionForeground(Color.black);
        table.setGridColor(new Color(220, 220, 220));

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 15));
        tableHeader.setBackground(new Color(15, 45, 95));
        tableHeader.setForeground(new Color(101, 67, 33));
        tableHeader.setPreferredSize(new Dimension(100, 38));

        DefaultTableCellRenderer center =
                new DefaultTableCellRenderer();

        center.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {

            table.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(center);

        }

        TransactionDAO dao = new TransactionDAO();

        ArrayList<Transaction> list =
                dao.getHistory(user.getAccountNo());

        if (list.isEmpty()) {

            model.addRow(new Object[]{
                    "-", "No Transactions",
                    "-", "-", "-"
            });

        } else {

            for (Transaction t : list) {

                model.addRow(new Object[]{

                        t.getTransactionId(),

                        t.getTransactionType(),

                        String.format("₹ %.2f",
                                t.getAmount()),

                        String.format("₹ %.2f",
                                t.getBalance()),

                        t.getTransactionDate()

                });

            }

        }

        JScrollPane scroll =
                new JScrollPane(table);

        scroll.setBorder(BorderFactory.createLineBorder(
                new Color(210, 210, 210)));

        card.add(scroll, BorderLayout.CENTER);


        JPanel footer = new JPanel();
        footer.setBackground(Color.WHITE);

        JButton close = new JButton("Close");

        styleButton(close,
                new Color(220, 53, 69));

        close.addActionListener(e -> dispose());

        footer.add(close);

        card.add(footer, BorderLayout.SOUTH);

        background.add(card, BorderLayout.CENTER);

        add(background);

        setVisible(true);

    }


    private void styleButton(JButton button,
                             Color color) {

        button.setBackground(color);
        button.setForeground(Color.black);
        button.setFont(new Font("Segoe UI",
                Font.BOLD, 15));

        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(
                new Dimension(140, 42));

        Color normal = color;

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                normal.darker());

                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(
                                normal);

                    }

                });

    }

}