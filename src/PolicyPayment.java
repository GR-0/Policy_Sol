
import java.sql.ResultSet;
import java.util.*;
import javax.swing.JOptionPane;
public class PolicyPayment extends javax.swing.JFrame {


    public PolicyPayment() {
        initComponents();
    }
     private void tableData(){
        javax.swing.table.DefaultTableModel dtm=(javax.swing.table.DefaultTableModel)jTable2.getModel();
        int rc=dtm.getRowCount();
        while(rc--!=0){
            dtm.removeRow(0);
        }
        
        int cid=Integer.parseInt(cn.getText());
        try{
            ResultSet rs=db.DbConnect.st.executeQuery("select * from policy_assign where cid = "+cid+"");
            while(rs.next()){
                Vector r=new Vector();
                r.add(rs.getInt("p_no"));
                r.add(rs.getString("p_name"));
                r.add(rs.getDate("start_date"));
                java.util.Date d=rs.getDate("start_date");
                r.add(rs.getInt("terms"));
                r.add(rs.getInt("p_amt"));
                r.add(rs.getDate("due_date"));
                r.add(rs.getString("status"));
                dtm.addRow(r);
            }
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
     }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cn = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Policy Payment");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Customer ID :");

        cn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cnKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cnKeyTyped(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Policy No.", "Policy Name", "Start Date", "Terms", "Premium Amt.", "Due Date", "status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable2);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Payment Done");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(cn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {cn, jLabel1});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    //policy payment
        int ri=jTable2.getSelectedRow();
        if(ri!=-1){
            try{
                int p_no=(int)jTable2.getValueAt(ri,0);
                int terms=(int)jTable2.getValueAt(ri,3);
                int p_amt=(int)jTable2.getValueAt(ri,4);
                ResultSet rs=db.DbConnect.st.executeQuery("select * from policy_payments where p_no = "+p_no+"");
                int count=0;
                while(rs.next()){
                    count++;
                }
                if(count<terms){
                    db.DbConnect.st.executeUpdate("insert into policy_payments (p_no,payment_date,p_amt) values("+p_no+",CURRENT_DATE,"+p_amt+")");
                    db.DbConnect.st.executeUpdate("update policy_assign set due_date=DATE_ADD(due_date, INTERVAL 1 YEAR)  where p_no="+p_no);
                    JOptionPane.showMessageDialog(null,"Success");
                    if(count==(terms-1)){
                        db.DbConnect.st.executeUpdate("update policy_assign set status='Matured'  where p_no="+p_no);
                    }
                    
                     tableData();
                }else{
                    JOptionPane.showMessageDialog(null,"Can not pay any more. Your Policy has been matured!");
                }
              
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
            }
             
        }else{
            JOptionPane.showMessageDialog(null,"Plz Select any Entry.");
        } 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cnKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnKeyReleased
tableData();
    }//GEN-LAST:event_cnKeyReleased

    private void cnKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cnKeyTyped
        CommanCode.onlyDigitAllowed(evt);
    }//GEN-LAST:event_cnKeyTyped
//public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new PolicyPayment().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
