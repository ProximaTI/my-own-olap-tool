/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewJFrame.java
 *
 * Created on Apr 3, 2011, 5:45:08 PM
 */
package br.com.proximati.biprime.server;

import br.com.proximati.biprime.server.olapql.language.query.ASTSelect;
import br.com.proximati.biprime.server.olapql.language.query.QueryParser;
import br.com.proximati.biprime.server.olapql.language.query.translator.QuerySqlTranslator;
import java.awt.Cursor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author luiz
 */
public class NewJFrame extends javax.swing.JFrame {

    /** Creates new form NewJFrame */
    public NewJFrame() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Instrução");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jButton1.setText("Executar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE))
                    .addComponent(jButton1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            OlapQlServer server = new OlapQlServer();

            QueryParser parser = new QueryParser(IOUtils.toInputStream(jTextArea1.getText()));
            ASTSelect select = (ASTSelect) parser.select();

            select.dump(" ");

            QueryTableModel model = new QueryTableModel();

            QuerySqlTranslator translator = new QuerySqlTranslator();

            System.out.println(translator.translateOlapQlInstruction(jTextArea1.getText()));

            model.setResultset(server.openResultset(jTextArea1.getText()));
            jTable1.setModel(model);
            System.out.println("quantidade de linhas: " + jTable1.getModel().getRowCount());
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            System.out.println(sw.toString());
            // JOptionPane.showMessageDialog(this, sw.toString());
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } finally {
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    class QueryTableModel extends AbstractTableModel {

        Vector cache; // will hold String[] objects . . .
        int colCount;
        String[] headers;
        Connection db;
        Statement statement;
        String currentURL;

        public QueryTableModel() {
            cache = new Vector();
        }

        public String getColumnName(int i) {
            return headers[i];
        }

        public int getColumnCount() {
            return colCount;
        }

        public int getRowCount() {
            return cache.size();
        }

        public Object getValueAt(int row, int col) {
            return ((String[]) cache.elementAt(row))[col];
        }

        // All the real work happens here; in a real application,
        // we'd probably perform the query in a separate thread.
        public void setResultset(ResultSet rs) {
            cache = new Vector();
            try {
                ResultSetMetaData meta = rs.getMetaData();
                colCount = meta.getColumnCount();

                // Now we must rebuild the headers array with the new column names
                headers = new String[colCount];
                for (int h = 1; h <= colCount; h++) {
                    headers[h - 1] = meta.getColumnLabel(h);
                }

                // and file the cache with the records from our query. This would
                // not be
                // practical if we were expecting a few million records in response
                // to our
                // query, but we aren't, so we can do this.
                while (rs.next()) {
                    String[] record = new String[colCount];
                    for (int i = 0; i < colCount; i++) {
                        record[i] = rs.getString(i + 1);
                    }
                    cache.addElement(record);
                }
                fireTableChanged(null); // notify everyone that we have a new table.
            } catch (Exception e) {
                cache = new Vector(); // blank it out and keep going.
                e.printStackTrace();
            }
        }
    }
}
