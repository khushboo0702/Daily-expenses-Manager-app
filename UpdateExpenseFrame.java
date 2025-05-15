import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.*;

public class UpdateExpenseFrame extends JFrame implements ActionListener {
    private JTextField idField, dateField, categoryField, descriptionField, amountField;
    private JLabel messageLabel;
    private JButton updateButton;

    public UpdateExpenseFrame() {
        setTitle("Update Expense");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); // Center on screen

        JLabel lheading = new JLabel("<html><h2><b>Update Your Expense</b></h2></html>");
        lheading.setBounds(110, 0, 300, 60);
        lheading.setForeground(Color.BLACK);
        add(lheading);

        // Labels and TextFields
        JLabel idLabel = new JLabel("Expense ID:");
        idLabel.setBounds(50, 60, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(180, 60, 150, 25);
        add(idField);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateLabel.setBounds(50, 95, 130, 25);
        add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(180, 95, 150, 25);
        add(dateField);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 130, 100, 25);
        add(categoryLabel);

        categoryField = new JTextField();
        categoryField.setBounds(180, 130, 150, 25);
        add(categoryField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(50, 165, 100, 25);
        add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(180, 165, 150, 25);
        add(descriptionField);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50, 200, 100, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(180, 200, 150, 25);
        add(amountField);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.setBounds(50, 260, 120, 30);
        updateButton.addActionListener(this);
        updateButton.setForeground(Color.BLUE);
        add(updateButton);

        // Message Label
        messageLabel = new JLabel("");
        messageLabel.setBounds(180, 260, 300, 25);
        add(messageLabel);

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String id = idField.getText().trim();
        String date = dateField.getText().trim();
        String category = categoryField.getText().trim();
        String description = descriptionField.getText().trim();
        String amount = amountField.getText().trim();

        if (id.isEmpty() || date.isEmpty() || category.isEmpty() || description.isEmpty() || amount.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please fill all the fields.");
            return;
        }

        try {
            int expenseID = Integer.parseInt(id);
            double expenseAmount = Double.parseDouble(amount);

            if (expenseAmount <= 0) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Amount must be greater than zero.");
                return;
            }

            String formattedDate = convertDateFormat(date);
            if (formattedDate == null) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Invalid date format. Use YYYY-MM-DD.");
                return;
            }

            boolean updated = updateExpense(expenseID, formattedDate, category, description, expenseAmount);
            if (updated) {
                messageLabel.setForeground(new Color(0, 128, 0));
                messageLabel.setText("Expense updated successfully!");
            } else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("No records updated. Please check the Expense ID.");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please enter valid numbers for ID and Amount.");
        }
    }

    private String convertDateFormat(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            inputFormat.setLenient(false);
            inputFormat.parse(date);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    private boolean updateExpense(int expenseID, String date, String category, String description, double amount) {
        String url = "jdbc:mysql://localhost:3306/dailyexpensesmanagerapp";
        String user = "root";
        String password = "Jitesh@123";

        String sql = "UPDATE addexpenses SET date = ?, category = ?, description = ?, amount = ? WHERE expense_id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, date);
            pst.setString(2, category);
            pst.setString(3, description);
            pst.setDouble(4, amount);
            pst.setInt(5, expenseID);

            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdateExpenseFrame::new);
    }
}