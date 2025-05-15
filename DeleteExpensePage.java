import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteExpensePage extends JFrame implements ActionListener {
    private JTextField idField;
    private JLabel messageLabel;
    private JButton deleteButton;

    public DeleteExpensePage() {
        setTitle("Delete Expense");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); // Center on screen

        // Label for Expense ID
        JLabel idLabel = new JLabel("Enter Expense ID to Delete:");
        idLabel.setBounds(50, 40, 200, 25);
        add(idLabel);

        // Text field for ID
        idField = new JTextField();
        idField.setBounds(210, 40, 100, 25);
        add(idField);

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(100, 100, 100, 30);
        deleteButton.setForeground(Color.RED);
        deleteButton.addActionListener(this);
        add(deleteButton);

        // Message Label
        messageLabel = new JLabel("");
        messageLabel.setBounds(50, 140, 300, 25);
        add(messageLabel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please enter an Expense ID.");
            return;
        }

        int expenseID;
        try {
            expenseID = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Invalid Expense ID.");
            return;
        }

        deleteExpense(expenseID);
    }

    private void deleteExpense(int expenseID) {
        String url = "jdbc:mysql://localhost:3306/dailyexpensesmanagerapp";
        String user = "root";
        String password = "Jitesh@123";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            String query = "DELETE FROM addexpenses WHERE Expense_ID = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, expenseID);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    messageLabel.setForeground(Color.black);
                    messageLabel.setText("Expense deleted successfully.");
                } else {
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Expense ID not found.");
                }
            }
        } catch (SQLException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeleteExpensePage::new);
    }
}
