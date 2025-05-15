import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Displayexp extends JFrame {

    private JTable expensesTable;

    public Displayexp() {
        setTitle("All Expenses (MySQL)");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Column names
        String[] columnNames = {"expense_id", "Date", "Category", "Description", "Amount"};

        // Table model
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        expensesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(expensesTable);

        // Load data from MySQL
        loadExpensesFromDatabase(tableModel);

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("  All Recorded Expenses:");
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(titleLabel);

        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(scrollPane);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(closeButton);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void loadExpensesFromDatabase(DefaultTableModel tableModel) {
        // Replace these with your actual database credentials
        String url = "jdbc:mysql://localhost:3306/dailyexpensesmanagerapp";
        String user = "root"; // Your MySQL username
        String pass = "Jitesh@123"; // Your MySQL password

        String query = "SELECT expense_id, date, category, description, amount FROM addexpenses";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("expense_id"),
                    rs.getDate("date").toString(),
                    rs.getString("category"),
                    rs.getString("description"),
                    rs.getDouble("amount")
                };
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Displayexp::new);
    }
}
