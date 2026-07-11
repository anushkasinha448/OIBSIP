package Task1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardForm {

    public static int currentUserId = -1;
    public static String currentUsername = "Guest";

    private JFrame frame;

    public DashboardForm(int userId, String username) {

        currentUserId = userId;
        currentUsername = username;

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        createUI();
    }

    private void createUI() {

        frame = new JFrame("Railway Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setMinimumSize(new Dimension(1100, 700));

        Color primary = new Color(30, 41, 59);
        Color background = new Color(245, 247, 250);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(background);

        //---------------- HEADER ----------------//

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primary);
        header.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("Railway Reservation System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 34));

        header.add(title, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setOpaque(false);

        JLabel welcome = new JLabel("Welcome, " + currentUsername);

        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        right.add(welcome);

        header.add(right, BorderLayout.EAST);

        frame.getContentPane().add(header, BorderLayout.NORTH);

        //---------------- CENTER ----------------//

        JPanel center = new JPanel();

        center.setBackground(background);

        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        center.setBorder(new EmptyBorder(50, 0, 50, 0));

        JLabel sub = new JLabel(
                "Book your railway tickets quickly and securely");

        sub.setAlignmentX(Component.CENTER_ALIGNMENT);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 22));

        center.add(sub);
        center.add(Box.createVerticalStrut(40));

        JButton searchBtn =
                createButton("Search Trains",
                        new Color(37, 99, 235));

        JButton reserveBtn =
                createButton("Book Ticket",
                        new Color(34, 197, 94));

        JButton bookingBtn =
                createButton("My Bookings",
                        new Color(168, 85, 247));

        JButton cancelBtn =
                createButton("Cancel Ticket",
                        new Color(239, 68, 68));

        center.add(searchBtn);
        center.add(Box.createVerticalStrut(20));

        center.add(reserveBtn);
        center.add(Box.createVerticalStrut(20));

        center.add(bookingBtn);
        center.add(Box.createVerticalStrut(20));

        center.add(cancelBtn);

        frame.getContentPane().add(center, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setBackground(primary);

        JLabel copy = new JLabel(
                "© 2025 Railway Reservation System");

        copy.setForeground(Color.WHITE);

        footer.add(copy);

        frame.getContentPane().add(footer, BorderLayout.SOUTH);
        // Search works without login
        searchBtn.addActionListener(e ->
                new SearchTrainForm(currentUserId));

        reserveBtn.addActionListener(e ->
                requireLogin(() ->
                        new SearchTrainForm(currentUserId)));

        bookingBtn.addActionListener(e ->
                requireLogin(() ->
                        new BookingHistoryForm(currentUserId)));

        cancelBtn.addActionListener(e ->
                requireLogin(() ->
                        new CancellationForm(currentUserId)));


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void requireLogin(Runnable action) {

        if (currentUserId > 0) {

            action.run();
            return;
        }

        JOptionPane.showMessageDialog(frame,
                "Please login first.");

        new LoginForm(frame, () -> {

            frame.dispose();

            new DashboardForm(
                    DashboardForm.currentUserId,
                    DashboardForm.currentUsername);

            action.run();

        });

    }

    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setPreferredSize(new Dimension(320, 55));
        button.setMaximumSize(new Dimension(320, 55));

        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.setFont(new Font("Segoe UI",
                Font.BOLD, 18));

        button.setBackground(color);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR));

        Color hover = color.brighter();

        button.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(hover);

                    }

                    @Override
                    public void mouseExited(
                            java.awt.event.MouseEvent e) {

                        button.setBackground(color);

                    }

                });

        return button;
    }



}