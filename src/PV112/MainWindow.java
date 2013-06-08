
package PV112;

import java.awt.event.KeyEvent;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;


public class MainWindow extends javax.swing.JFrame {
    private final GLJPanel glPanel;
    private GLProfile profile = GLProfile.get(GLProfile.GL2);
    private OpenGlListener openGlListener = new OpenGlListener();

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        
        // vytvori sa viditelny panel na ktorom sa bude zobrazovat nas graficky vystup
        glPanel = new GLJPanel(new GLCapabilities(profile));
        
        // tento panel sa umiestni na halvne okno aplikacie
        add(glPanel);
        
        // rozmery glPanela sa nastavia tak, aby sa zhodovali s rozmermi hlavneho okna
        glPanel.setSize(getWidth() - getInsets().left - getInsets().right,
                        getHeight() - getInsets().top - getInsets().bottom);
        
        // k glPanelu pripojime OpenGLListener, aby sme mohli reagovat na udalosti
        // generovane tymto panelom
        glPanel.addGLEventListener(openGlListener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        craneRightButton = new javax.swing.JButton();
        craneLeftButton = new javax.swing.JButton();
        craneOnOffButton = new javax.swing.JButton();
        craneBackwardButton = new javax.swing.JButton();
        craneForwardButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cameraRightButton = new javax.swing.JButton();
        cameraLeftButton = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        cameraBackwardButton = new javax.swing.JButton();
        cameraForwardButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cameraClockwiseButton = new javax.swing.JButton();
        cameraAntiClockwiseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crane");
        setState(1);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusable(false);

        craneRightButton.setText("→");
        craneRightButton.setToolTipText("Rotate right");
        craneRightButton.setFocusable(false);
        craneRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                craneRightButtonActionPerformed(evt);
            }
        });

        craneLeftButton.setText("←");
        craneLeftButton.setToolTipText("Rotate left");
        craneLeftButton.setFocusable(false);
        craneLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                craneLeftButtonActionPerformed(evt);
            }
        });

        craneOnOffButton.setText("⌅");
        craneOnOffButton.setToolTipText("Magnet on/off");
        craneOnOffButton.setFocusable(false);
        craneOnOffButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                craneOnOffButtonActionPerformed(evt);
            }
        });

        craneBackwardButton.setText("↓");
        craneBackwardButton.setToolTipText("Move backward");
        craneBackwardButton.setFocusable(false);
        craneBackwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                craneBackwardButtonActionPerformed(evt);
            }
        });

        craneForwardButton.setText("↑");
        craneForwardButton.setToolTipText("Move forward");
        craneForwardButton.setFocusable(false);
        craneForwardButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                craneForwardButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Crane control");
        jLabel1.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(craneForwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(craneBackwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(craneLeftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(craneOnOffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(craneRightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(craneForwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(craneOnOffButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(craneLeftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(craneRightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(craneBackwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setFocusable(false);

        cameraRightButton.setText("→");
        cameraRightButton.setToolTipText("Move right");
        cameraRightButton.setFocusable(false);

        cameraLeftButton.setText("←");
        cameraLeftButton.setToolTipText("Move left");
        cameraLeftButton.setFocusable(false);

        jButton9.setText("►");
        jButton9.setToolTipText("Change camera");
        jButton9.setFocusable(false);

        cameraBackwardButton.setText("↓");
        cameraBackwardButton.setToolTipText("Move backward");
        cameraBackwardButton.setFocusable(false);

        cameraForwardButton.setText("↑");
        cameraForwardButton.setToolTipText("Move forward");
        cameraForwardButton.setFocusable(false);

        jLabel2.setText("Camera control");
        jLabel2.setFocusable(false);

        cameraClockwiseButton.setText("⟳");
        cameraClockwiseButton.setToolTipText("Change camera");
        cameraClockwiseButton.setFocusable(false);
        cameraClockwiseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraClockwiseButtonActionPerformed(evt);
            }
        });

        cameraAntiClockwiseButton.setText("⟲");
        cameraAntiClockwiseButton.setToolTipText("");
        cameraAntiClockwiseButton.setFocusable(false);
        cameraAntiClockwiseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraAntiClockwiseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(40, 40, 40))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cameraLeftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraAntiClockwiseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cameraForwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cameraRightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cameraClockwiseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(cameraBackwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cameraForwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraClockwiseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraAntiClockwiseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraLeftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cameraRightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cameraBackwardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 398, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(432, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // zmena velkosti okna
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        glPanel.setSize(getWidth() - getInsets().left - getInsets().right,
                        getHeight() - getInsets().top - getInsets().bottom);
    }//GEN-LAST:event_formComponentResized

    private void craneRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_craneRightButtonActionPerformed
        openGlListener.rotateCrane(-3);
    }//GEN-LAST:event_craneRightButtonActionPerformed

    private void cameraClockwiseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraClockwiseButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cameraClockwiseButtonActionPerformed

    private void cameraAntiClockwiseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraAntiClockwiseButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cameraAntiClockwiseButtonActionPerformed

    private void craneForwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_craneForwardButtonActionPerformed
        openGlListener.moveHook(-3);
    }//GEN-LAST:event_craneForwardButtonActionPerformed

    private void craneLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_craneLeftButtonActionPerformed
        openGlListener.rotateCrane(3);
    }//GEN-LAST:event_craneLeftButtonActionPerformed

    private void craneBackwardButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_craneBackwardButtonActionPerformed
        openGlListener.moveHook(3);
    }//GEN-LAST:event_craneBackwardButtonActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

        switch(evt.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                openGlListener.rotateCrane(2f);
                break;
            case KeyEvent.VK_RIGHT:
                openGlListener.rotateCrane(-2f);
                break;
            case KeyEvent.VK_UP:
                openGlListener.moveHook(-2f);
                break;
            case KeyEvent.VK_DOWN:
                openGlListener.moveHook(2f);
                break;
            case KeyEvent.VK_U:
                openGlListener.pullHook(4f);
                break;
            case KeyEvent.VK_J:
                openGlListener.pullHook(-4f);
                break;
            case KeyEvent.VK_SPACE:
                openGlListener.magnet();
        }
        glPanel.display();
    }//GEN-LAST:event_formKeyPressed

    private void craneOnOffButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_craneOnOffButtonActionPerformed
        openGlListener.magnet();
    }//GEN-LAST:event_craneOnOffButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cameraAntiClockwiseButton;
    private javax.swing.JButton cameraBackwardButton;
    private javax.swing.JButton cameraClockwiseButton;
    private javax.swing.JButton cameraForwardButton;
    private javax.swing.JButton cameraLeftButton;
    private javax.swing.JButton cameraRightButton;
    private javax.swing.JButton craneBackwardButton;
    private javax.swing.JButton craneForwardButton;
    private javax.swing.JButton craneLeftButton;
    private javax.swing.JButton craneOnOffButton;
    private javax.swing.JButton craneRightButton;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
