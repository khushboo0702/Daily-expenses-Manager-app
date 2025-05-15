import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFrame extends JFrame implements ActionListener {

    JRadioButton addExpenseCB, updateExpenseCB, deleteExpenseCB, displayExpensesCB, calculateTotalCB, exitCB;
    JButton submitButton;

    public MenuFrame() {
        setTitle("Daily Expenses Manager - Menu (Checkbox Version)");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center frame

        // Main panel without GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(null); // No layout manager (absolute positioning)

        // Create Checkboxes
        addExpenseCB = new JRadioButton("Add Expense");
        updateExpenseCB = new  JRadioButton("Update Expense");
        deleteExpenseCB = new JRadioButton("Delete Expense");
        displayExpensesCB = new  JRadioButton("Display All Expenses");
        calculateTotalCB = new  JRadioButton("Calculate Total Expenses");
        exitCB = new  JRadioButton("Exit");

        // Position Checkboxes manually
        addExpenseCB.setBounds(50, 30, 300, 30);
        updateExpenseCB.setBounds(50, 70, 300, 30);
        deleteExpenseCB.setBounds(50, 110, 300, 30);
        displayExpensesCB.setBounds(50, 150, 300, 30);
        calculateTotalCB.setBounds(50, 190, 300, 30);
        exitCB.setBounds(50, 230, 300, 30);

        // Submit Button
        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 280, 100, 30);
        submitButton.addActionListener(this);

        // Add components to panel
        panel.add(addExpenseCB);
        panel.add(updateExpenseCB);
        panel.add(deleteExpenseCB);
        panel.add(displayExpensesCB);
        panel.add(calculateTotalCB);
        panel.add(exitCB);
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (addExpenseCB.isSelected()) {
            new AddExpensesPage();
            dispose();
        }
        if (updateExpenseCB.isSelected()) {
            new UpdateExpenseFrame();
            dispose();
        }
        if (deleteExpenseCB.isSelected()) {
            new DeleteExpensePage();
            dispose();
        }
        if (displayExpensesCB.isSelected()) {
           new Displayexp();
           dispose();
        }
        if (calculateTotalCB.isSelected()) {
            new CalculateAllExpenses();
            dispose();
        }
        if (exitCB.isSelected()) {
            JOptionPane.showMessageDialog(this, "Exiting Application...");
            System.exit(0);
        }
    }

    
    public static void main(String[] args) {
        new MenuFrame();
    }
}