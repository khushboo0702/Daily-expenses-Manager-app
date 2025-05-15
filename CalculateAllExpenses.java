import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.sql.Date;

public class CalculateAllExpenses extends JFrame implements ActionListener {
    private JTextField fromDateField, toDateField;
    private JLabel resultLabel;
    private Connection conn;

    public CalculateAllExpenses() {
        // JFrame Setup
        setTitle("Calculate Total Expenses");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Database connection setup
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dailyexpensesmanagerapp", "root", "Jitesh@123");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the database.", "Database Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        JLabel fromLabel = new JLabel("From Date (YYYY-MM-DD):");
        fromLabel.setBounds(40, 40, 180, 25);
        add(fromLabel);

        fromDateField = new JTextField();
        fromDateField.setBounds(230, 40, 150, 25);
        add(fromDateField);

        JLabel toLabel = new JLabel("To Date (YYYY-MM-DD):");
        toLabel.setBounds(40, 80, 180, 25);
        add(toLabel);

        toDateField = new JTextField();
        toDateField.setBounds(230, 80, 150, 25);
        add(toDateField);

        JButton calcButton = new JButton("Calculate");
        calcButton.setBounds(150, 130, 120, 30);
        calcButton.addActionListener(this);
        add(calcButton);

        resultLabel = new JLabel("");
        resultLabel.setBounds(40, 180, 350, 25);
        add(resultLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String fromDateStr = fromDateField.getText().trim();
        String toDateStr = toDateField.getText().trim();

        if (fromDateStr.isEmpty() || toDateStr.isEmpty()) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Please enter both dates.");
            return;
        }

        try {
            // Use correct date format: "yyyy-MM-dd"
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fromDate = new Date(sdf.parse(fromDateStr).getTime());
            Date toDate = new Date(sdf.parse(toDateStr).getTime());

            // Ensure the fromDate is before toDate
            if (fromDate.after(toDate)) {
                resultLabel.setForeground(Color.RED);
                resultLabel.setText("From Date should be before To Date.");
                return;
            }

            // Calculate total expenses
            double total = calculateTotalBetweenDates(fromDate, toDate);
            resultLabel.setForeground(new Color(0, 128, 0));
            resultLabel.setText("Total Expenses: ₹" + String.format("%.2f", total));

        } catch (ParseException ex) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Invalid date format.");
        }
    }

    private double calculateTotalBetweenDates(Date fromDate, Date toDate) {
        double total = 0.0;
        String query = "SELECT SUM(amount) FROM addexpenses WHERE date BETWEEN ? AND ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(fromDate.getTime()));
            stmt.setDate(2, new java.sql.Date(toDate.getTime()));

            // Execute the query and get the result
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble(1);  // Use the existing `total` variable, no redeclaration needed.
                System.out.println("Total Expenses: ₹" + total); // Debugging line
            }
        } catch (SQLException e) {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText("Error executing query: " + e.getMessage());
            System.out.println("Error executing query: " + e.getMessage());  // Debugging
        }

        return total;  // Return the correct total
    }

    public static void main(String[] args) {
        new CalculateAllExpenses();
    }
}