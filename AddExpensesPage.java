import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddExpensesPage extends JFrame implements ActionListener {
    private JTextField idField, dateField, catField, disField, amountField ;
    private JLabel messageLabel;


    public AddExpensesPage() {
        // Frame settings
        setTitle("Expenses Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Using null layout
        

        // Labels
        JLabel titleLabel = new JLabel("<html><h2><b>Add Daily Expenses</b></h2></html>");
        titleLabel.setBounds(95, 5, 300, 50);
        titleLabel.setForeground(java.awt.Color.BLACK);
        add(titleLabel);

        JLabel idLbl = new JLabel("Expense-ID ");
        idLbl.setBounds(50, 60, 130, 25);
        add(idLbl);


        JLabel dateLbl = new JLabel("Date (YYYY-MM-DD)");
        dateLbl.setBounds(50, 100, 130, 25);
        add(dateLbl);

        JLabel catLabel = new JLabel("Category");
        catLabel.setBounds(50, 140, 100, 25);
        add(catLabel);

        JLabel disLabel = new JLabel("Discription");
        disLabel.setBounds(50, 180, 100, 25);
        add(disLabel);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(50, 220, 100, 25);
        add(amountLabel);

        // Text fields
        idField = new JTextField();
        idField.setBounds(180, 60, 150, 25);
        add(idField);

        dateField = new JTextField();
        dateField.setBounds(180, 100, 150, 25);
        add(dateField);

        catField = new JTextField();
        catField.setBounds(180, 140, 150, 25);
        add(catField);

      disField = new JTextField();
      disField.setBounds(180, 180, 150, 25);
        add(disField);

      amountField = new JTextField();
      amountField.setBounds(180, 220, 150, 25);
        add(amountField);

         // Message Label
         messageLabel = new JLabel("");
         messageLabel.setBounds(180, 270, 300, 25);
         add(messageLabel);

        // Add button
        JButton addButton = new JButton("Add Expense");
        addButton.setBounds(50, 270, 120, 30);
        addButton.addActionListener(this);
        add(addButton);

        setVisible(true);
    }

    @Override
public void actionPerformed(ActionEvent e) {
    try {
        // Retrieve and trim input values
        String idText = idField.getText().trim();
        String date = dateField.getText().trim();
        String category = catField.getText().trim();
        String description = disField.getText().trim();
        String amountText = amountField.getText().trim();

        // Check if any required field is empty
        if (idText.isEmpty()||date.isEmpty() || category.isEmpty() || description.isEmpty() || amountText.isEmpty()) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Please fill all fields correctly.");
            return;
        }

        // Parse Expense_ID and amount, handling potential NumberFormatExceptions
        int expenseID = 0;
        double amount = 0.0;

        try {
            expenseID = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Invalid Expense ID.");
            return;
        }

        try {
            amount = Double.parseDouble(amountText);
            if (amount <= 0) {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Amount must be greater than zero.");
                return;
            }
        } catch (NumberFormatException ex) {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Invalid amount.");
            return;
        }

        // Proceed to save the data if all validations pass
        saveToDatabase(expenseID, date, category, description, amount);

    } catch (Exception ex) {
        messageLabel.setForeground(Color.RED);
        messageLabel.setText("An unexpected error occurred.");
        ex.printStackTrace();
    }
}

private void saveToDatabase(int expenseID, String date, String category, String description, double amount) {
    String url = "jdbc:mysql://localhost:3306/dailyexpensesmanagerapp";
    String user = "root";
    String password = "Jitesh@123";

    try (Connection con = DriverManager.getConnection(url, user, password)) {
        String query = "INSERT INTO addexpenses(Expense_ID, date, category, description, amount) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, expenseID);
            pst.setString(2, date);
            pst.setString(3, category);
            pst.setString(4, description);
            pst.setDouble(5, amount);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Expense added successfully!");
             //   new CalculateAllExpenses().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add expense.");
            }
        }}catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(AddExpensesPage::new);
    }
}
