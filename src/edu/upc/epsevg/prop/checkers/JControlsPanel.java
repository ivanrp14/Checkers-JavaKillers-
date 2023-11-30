package edu.upc.epsevg.prop.checkers;

import edu.upc.epsevg.prop.checkers.PlayerType;
import edu.upc.epsevg.prop.checkers.CellType;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 *
 * @author bernat
 */
public class JControlsPanel extends javax.swing.JPanel implements ActionListener {

    Board board;
    BufferedImage whiteQueen;

    /**
     * Creates new form JControlsPanel
     */

    public JControlsPanel(Board board) {
        initComponents();
        btnStart.addActionListener(this);
        this.board = board;
        /*
        try {
            whiteQueen = ImageIO.read(this.getClass().getResource("/resources/white_queen.png"));
        } catch (IOException ex) {
            Logger.getLogger(JControlsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
         */
    }

    public void setInfo(String info){
        jTextArea1.setText(info);
    }
    
    public void setPlayer1Name(String name) {
        if (name.length() > 10) {
            name = name.substring(0, 10) + 1;
        }
        lblPlayer1.setText("Player 1: " + name);
    }

    public void setPlayer2Name(String name) {
        if (name.length() > 10) {
            name = name.substring(0, 10) + 1;
        }
        lblPlayer2.setText("Player 2: " + name);
    }

    public void setThinking(boolean thinking) {
        //lblThinking..setBusy
        lblThinking.setVisible(thinking);
    }

    public void highlightPlayer(PlayerType player) {
        if (player == PlayerType.PLAYER1) {
            txfPlayer1.setBackground(Color.yellow);
            txfPlayer2.setBackground(Color.white);
        } else {
            txfPlayer2.setBackground(Color.yellow);
            txfPlayer1.setBackground(Color.white);
        }
    }

    public void setPlayer1Message(String msg) {
        txfPlayer1.setText(msg);
    }

    public void setPlayer2Message(String msg) {
        txfPlayer2.setText(msg);
    }

    public void setButtonText(String txt) {
        btnStart.setText(txt);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        board.OnStartClicked();
    }

    void setButtonEnabled(boolean b) {
        btnStart.setEnabled(b);
    }

    public void setScore1(int score){
        this.jLabelScore1.setText(score+"");
    }

    public void setScore2(int score){
        this.jLabelScore2.setText(score+"");
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPlayer1 = new javax.swing.JLabel();
        lblPlayer2 = new javax.swing.JLabel();
        txfPlayer1 = new javax.swing.JTextField();
        txfPlayer2 = new javax.swing.JTextField();
        btnStart = new javax.swing.JButton();
        lblThinking = new JLabel(new ImageIcon(this.getClass().getResource("/resources/Gears.gif")));
        jLabel1 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getResource("/resources/black.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        jLabel3 = new JLabel(new ImageIcon(new ImageIcon(this.getClass().getResource("/resources/white.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabelScore1 = new javax.swing.JLabel();
        jLabelScore2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(270, 348));
        setMinimumSize(new java.awt.Dimension(270, 348));

        lblPlayer1.setText("5463456345634563456345634563456345634563456");

        lblPlayer2.setBackground(new java.awt.Color(255, 255, 255));
        lblPlayer2.setText("jLabel2123123123123123123123123345634563456");

        txfPlayer1.setEditable(false);
        txfPlayer1.setText("jTextField1");

        txfPlayer2.setEditable(false);
        txfPlayer2.setText("jTextField2");

        btnStart.setText("Start");
        btnStart.setToolTipText("");

        lblThinking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThinking.setMaximumSize(new java.awt.Dimension(128, 128));
        lblThinking.setMinimumSize(new java.awt.Dimension(128, 128));
        lblThinking.setName(""); // NOI18N

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setPreferredSize(new java.awt.Dimension(30, 30));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setPreferredSize(new java.awt.Dimension(30, 30));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("Info:");

        jLabelScore1.setBackground(new java.awt.Color(255, 255, 153));
        jLabelScore1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelScore1.setText("12");
        jLabelScore1.setToolTipText("");
        jLabelScore1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelScore1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelScore1.setOpaque(true);
        jLabelScore1.setPreferredSize(new java.awt.Dimension(37, 37));

        jLabelScore2.setBackground(new java.awt.Color(255, 255, 153));
        jLabelScore2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelScore2.setText("54");
        jLabelScore2.setToolTipText("");
        jLabelScore2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelScore2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelScore2.setOpaque(true);
        jLabelScore2.setPreferredSize(new java.awt.Dimension(37, 37));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPlayer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabelScore2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPlayer2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txfPlayer1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                            .addComponent(txfPlayer2)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblThinking, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPlayer1)
                        .addComponent(jLabelScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txfPlayer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblPlayer2)
                        .addComponent(jLabelScore2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txfPlayer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThinking, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelScore2.getAccessibleContext().setAccessibleName("jLabelScore2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelScore1;
    private javax.swing.JLabel jLabelScore2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblPlayer1;
    private javax.swing.JLabel lblPlayer2;
    private javax.swing.JLabel lblThinking;
    private javax.swing.JTextField txfPlayer1;
    private javax.swing.JTextField txfPlayer2;
    // End of variables declaration//GEN-END:variables

}
