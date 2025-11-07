package santinijurismtp;


public class SantiniJuriSMTP {
    public static void main(String[] args) {
        
        FrameSMTP f = new FrameSMTP();
        f.setSize(400, 400);
        f.setLocationRelativeTo(null);
        f.setTitle("Invia un messaggio");
        f.setVisible(true);

    }
}