package healthfirstpims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import healthfirstpims.database.DatabaseConnection;

public class ManageMedicines extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(ManageMedicines.class.getName());

    /**
     * Creates new form ManageMedicines
     */
    public ManageMedicines() {
        initComponents();

        // Listeners added here (not in initComponents) so the NetBeans
        // Form Editor cannot wipe them when it regenerates the GUI code.
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        // Click a table row to load it into the form fields.
        tblMedicines.getSelectionModel().addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                fillFieldsFromTable();
            }
        });

        loadMedicines();
    }
    
    // DATABASE METHODS
    private void loadMedicines() {

        DefaultTableModel model = (DefaultTableModel) tblMedicines.getModel();
        model.setRowCount(0);

        String sql = "SELECT * FROM medicines ORDER BY name";
        
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getString("company"),
                    rs.getString("medicine_type"),
                    rs.getDouble("price"),
                    rs.getInt("quantity_in_stock"),
                    rs.getInt("reorder_level"),
                    rs.getDate("expiry_date"),
                    rs.getInt("supplier_id")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading medicines: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        txtMedicineId.setText("");
        txtMedicineName.setText("");
        txtCompany.setText("");
        txtMedicineType.setText("");
        txtPrice.setText("");
        txtQuantity.setText("");
        txtReorderLevel.setText("");
        txtExpiryDate.setText("");
        txtSupplierId.setText("");
        tblMedicines.clearSelection();
    }

    /** Copies the selected table row into the text fields. */
    private void fillFieldsFromTable() {

        int row = tblMedicines.getSelectedRow();
        if (row < 0) {
            return;
        }
        row = tblMedicines.convertRowIndexToModel(row);

        DefaultTableModel model = (DefaultTableModel) tblMedicines.getModel();

        txtMedicineId.setText(text(model.getValueAt(row, 0)));
        txtMedicineName.setText(text(model.getValueAt(row, 1)));
        txtCompany.setText(text(model.getValueAt(row, 2)));
        txtMedicineType.setText(text(model.getValueAt(row, 3)));
        txtPrice.setText(text(model.getValueAt(row, 4)));
        txtQuantity.setText(text(model.getValueAt(row, 5)));
        txtReorderLevel.setText(text(model.getValueAt(row, 6)));
        txtExpiryDate.setText(text(model.getValueAt(row, 7)));
        txtSupplierId.setText(text(model.getValueAt(row, 8)));
    }

    private String text(Object value) {
        return value == null ? "" : value.toString();
    }
    
    /**
     * Checks the form before it is sent to the database.
     * Returns true when every field holds a usable value.
     */
    private boolean validateFields() {

        if (txtMedicineName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Medicine name is required.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            txtMedicineName.requestFocus();
            return false;
        }

        try {
            Double.parseDouble(txtPrice.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Price must be a number, e.g. 24.50",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            txtPrice.requestFocus();
            return false;
        }

        try {
            Integer.parseInt(txtQuantity.getText().trim());
            Integer.parseInt(txtReorderLevel.getText().trim());
            Integer.parseInt(txtSupplierId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Quantity, Reorder Level and Supplier ID must be whole numbers.",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            java.sql.Date.valueOf(txtExpiryDate.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "Expiry date must use the format yyyy-MM-dd, e.g. 2027-05-30",
                    "Validation", JOptionPane.WARNING_MESSAGE);
            txtExpiryDate.requestFocus();
            return false;
        }

        return true;
    }
    
    /** Fills parameters 1-8, shared by the INSERT and UPDATE statements. */
    private void bindFields(PreparedStatement pst) throws SQLException {
        pst.setString(1, txtMedicineName.getText().trim());
        pst.setString(2, txtCompany.getText().trim());
        pst.setString(3, txtMedicineType.getText().trim());
        pst.setDouble(4, Double.parseDouble(txtPrice.getText().trim()));
        pst.setInt(5, Integer.parseInt(txtQuantity.getText().trim()));
        pst.setInt(6, Integer.parseInt(txtReorderLevel.getText().trim()));
        pst.setDate(7, java.sql.Date.valueOf(txtExpiryDate.getText().trim()));
        pst.setInt(8, Integer.parseInt(txtSupplierId.getText().trim()));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblMedicineId = new javax.swing.JLabel();
        lblMedicineName = new javax.swing.JLabel();
        lblCompany = new javax.swing.JLabel();
        lblMedicineType = new javax.swing.JLabel();
        lblPrice = new javax.swing.JLabel();
        lblQuantity = new javax.swing.JLabel();
        lblRecorderLevel = new javax.swing.JLabel();
        txtMedicineId = new javax.swing.JTextField();
        txtMedicineName = new javax.swing.JTextField();
        txtCompany = new javax.swing.JTextField();
        txtMedicineType = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        txtReorderLevel = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lblSupplierId = new javax.swing.JLabel();
        txtExpiryDate = new javax.swing.JTextField();
        txtSupplierId = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblExpiryDate = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMedicines = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblMedicineId.setText("Medicine ID:");

        lblMedicineName.setText("Medicine Name:");

        lblCompany.setText("Company:");

        lblMedicineType.setText("Medicine Type:");

        lblPrice.setText("Price:");

        lblQuantity.setText("Quantity in Stock:");

        lblRecorderLevel.setText("Record Level:");

        txtMedicineId.setEditable(false);

        lblSupplierId.setText("Supplier ID:");

        jLabel10.setText("HEALTHFIRSTPHARMACY");

        jLabel11.setText("MANAGE MEDICINES");

        lblExpiryDate.setText("Expiry Date:");

        btnAdd.setText("ADD");
        btnAdd.addActionListener(this::btnAddActionPerformed);

        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(this::btnUpdateActionPerformed);

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(this::btnDeleteActionPerformed);

        btnClear.setText("CLEAR");
        btnClear.addActionListener(this::btnClearActionPerformed);

        tblMedicines.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Medicine ID", "Name", "Company", "Medicine Type", "Price", "Quantity", "Recorder Level", "Expiry Date", "Supplier ID"
            }
        ));
        jScrollPane1.setViewportView(tblMedicines);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(78, 78, 78)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblQuantity)
                                            .addComponent(lblRecorderLevel)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblExpiryDate)
                                                    .addComponent(lblSupplierId))))
                                        .addGap(99, 99, 99)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtExpiryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSupplierId, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMedicineType, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCompany, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMedicineName, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMedicineId, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(lblMedicineId)
                                                .addComponent(lblMedicineName)
                                                .addComponent(lblCompany)
                                                .addComponent(lblMedicineType)
                                                .addComponent(lblPrice)))
                                        .addGap(51, 51, 51))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(294, 294, 294)
                                .addComponent(jLabel11))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(285, 285, 285)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(342, 342, 342)
                                .addComponent(btnBack))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 11, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedicineId)
                    .addComponent(txtMedicineId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedicineName)
                    .addComponent(txtMedicineName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCompany)
                    .addComponent(txtCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedicineType)
                    .addComponent(txtMedicineType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblQuantity)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRecorderLevel)
                    .addComponent(txtReorderLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblExpiryDate)
                    .addComponent(txtExpiryDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSupplierId)
                    .addComponent(txtSupplierId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnUpdate)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addGap(9, 9, 9)
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(56, 56, 56))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // BUTTON HANDLERS
   
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!validateFields()) {
            return;
    }//GEN-LAST:event_btnAddActionPerformed
        String sql = "INSERT INTO medicines "
                + "(name, company, medicine_type, price, "
                + "quantity_in_stock, reorder_level, expiry_date, supplier_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            bindFields(pst);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Medicine added successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            clearFields();
            loadMedicines();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error adding medicine: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearFields();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (txtMedicineId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Select a medicine from the table first.",
                    "No selection", JOptionPane.WARNING_MESSAGE);
            return;
    }//GEN-LAST:event_btnUpdateActionPerformed
        if (!validateFields()) {
            return;
        }

        String sql = "UPDATE medicines SET name = ?, company = ?, medicine_type = ?, "
                + "price = ?, quantity_in_stock = ?, reorder_level = ?, "
                + "expiry_date = ?, supplier_id = ? WHERE medicine_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            bindFields(pst);
            pst.setInt(9, Integer.parseInt(txtMedicineId.getText().trim()));

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Medicine updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadMedicines();
            } else {
                JOptionPane.showMessageDialog(this, "No medicine was updated.",
                        "Update", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error updating medicine: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete \"" + txtMedicineName.getText() + "\" permanently?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    String sql = "DELETE FROM medicines WHERE medicine_id = ?";

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setInt(
                1,
                Integer.parseInt(txtMedicineId.getText().trim())
        );

        pst.executeUpdate();

        JOptionPane.showMessageDialog(
                this,
                "Medicine deleted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );

        clearFields();
        loadMedicines();

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(
                this,
                "Could not delete this medicine. It may already be linked to a sale.\n\n"
                + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed


     /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ManageMedicines().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCompany;
    private javax.swing.JLabel lblExpiryDate;
    private javax.swing.JLabel lblMedicineId;
    private javax.swing.JLabel lblMedicineName;
    private javax.swing.JLabel lblMedicineType;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblRecorderLevel;
    private javax.swing.JLabel lblSupplierId;
    private javax.swing.JTable tblMedicines;
    private javax.swing.JTextField txtCompany;
    private javax.swing.JTextField txtExpiryDate;
    private javax.swing.JTextField txtMedicineId;
    private javax.swing.JTextField txtMedicineName;
    private javax.swing.JTextField txtMedicineType;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtReorderLevel;
    private javax.swing.JTextField txtSupplierId;
    // End of variables declaration//GEN-END:variables
}


