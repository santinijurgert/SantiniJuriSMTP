
package santinijurismtp;

import javax.swing.JOptionPane;

public class FrameSMTP extends javax.swing.JFrame {
    

    private String HOST = "localhost";
    private int PORT = 25; 
    
    public FrameSMTP() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtFrom = new javax.swing.JTextField();
        txtTo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSubject = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        atxMess = new javax.swing.JTextArea();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 3, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SUPER TESTO INVIATORE");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(40, 10, 320, 40);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Mittente");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 60, 90, 20);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Destinatario");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 90, 90, 20);
        getContentPane().add(txtFrom);
        txtFrom.setBounds(110, 60, 240, 22);
        getContentPane().add(txtTo);
        txtTo.setBounds(110, 90, 240, 22);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel4.setText("Messaggio");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 160, 80, 20);
        getContentPane().add(txtSubject);
        txtSubject.setBounds(110, 130, 240, 22);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel5.setText("Oggetto");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 130, 80, 20);

        atxMess.setColumns(20);
        atxMess.setLineWrap(true);
        atxMess.setRows(5);
        jScrollPane1.setViewportView(atxMess);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(110, 160, 240, 90);

        btnSend.setFont(new java.awt.Font("Rockwell Nova", 0, 14)); // NOI18N
        btnSend.setText("Invia");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });
        getContentPane().add(btnSend);
        btnSend.setBounds(30, 270, 320, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        if (!isInputValid()) {
            return; 
        }
        sendEmail(txtFrom.getText(), txtTo.getText(), txtSubject.getText(), atxMess.getText());
    }//GEN-LAST:event_btnSendActionPerformed

    
    private boolean isInputValid() {
        if (txtFrom.getText().isEmpty() || txtTo.getText().isEmpty() || 
            txtSubject.getText().isEmpty() || atxMess.getText().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Per favore, compila tutti i campi (Mittente, Destinatario, Oggetto, Messaggio).", 
                "Errore di Validazione", JOptionPane.WARNING_MESSAGE);
            return false; 
        }
        return true;
    }
    
    private String buildMessage(String from, String to, String subject, String body) {
        String message = "";
        message += "From: " + from + "\r\n";
        message += "To: " + to + "\r\n";
        
        message += "Subject: " + subject + "\r\n"; 
        
        message += "\r\n"; 
        message += body;
        
        return message;
    }
    
    private void sendEmail(String from, String to, String subject, String body) {
        System.out.println("--- Starting SMTP Transaction ---");
        try {
            SMTPClient client = new SMTPClient(HOST, PORT);
            SMTPResponse greeting = client.getLastResponse();
            System.out.println("1. GREETING (Initial): " + (greeting != null ? greeting.toDetailedString() : " (none)"));

            SMTPResponse helo = client.helo("localhost");
            System.out.println("2. HELO/EHLO: " + (helo != null ? helo.toDetailedString() : " (none)"));

            SMTPResponse mfrom = client.from(from);
            System.out.println("3. MAIL FROM: " + (mfrom != null ? mfrom.toDetailedString() : " (none)"));

            SMTPResponse rcpt = client.to(to);
            System.out.println("4. RCPT TO: " + (rcpt != null ? rcpt.toDetailedString() : " (none)"));
            
            
            String message = buildMessage(from, to, subject, body);
            
            SMTPResponse dataResp = client.data(subject, message); 
            System.out.println("5. DATA (Final Response): " + (dataResp != null ? dataResp.toDetailedString() : " (none)"));
            
            SMTPResponse quit = client.quit();
            System.out.println("6. QUIT: " + (quit != null ? quit.toDetailedString() : " (no response expected or received)"));

            if (dataResp != null && dataResp.getCode() == 250) {
                JOptionPane.showMessageDialog(this, "Messaggio inviato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "Errore durante l'invio del messaggio. Controlla l'output della console per i dettagli SMTP.", "Errore SMTP", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.err.println("Error during SMTP transaction: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Errore di connessione o invio: " + e.getMessage(), "Errore Critico", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("--- SMTP Transaction Ended ---");
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea atxMess;
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtFrom;
    private javax.swing.JTextField txtSubject;
    private javax.swing.JTextField txtTo;
    // End of variables declaration//GEN-END:variables
}
