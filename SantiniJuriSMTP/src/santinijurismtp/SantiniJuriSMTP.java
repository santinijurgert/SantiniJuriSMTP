package santinijurismtp;


public class SantiniJuriSMTP {
    public static void main(String[] args) {
        String HOST = "localhost";
        int PORT = 25; 

        //Halal
        
        String FROM = "netbeans@com.com";
        String TO = "aaa@aaa.aaa";
        String SUBJECT = "الشتوية ";
        String BODY = " مكن ماذا مقاومة الأرضية كل, مع مدن هنا؟ نتيجة العصبة. أضف و تحرير المارق, مع به، وحتّى عملية. قد بعض كرسي لبولندا،.\n" 
                        + "\n" 
                        + "كلا أم وبعض بينما العالمي. مكن و شعار الباهضة, لإعلان والعتاد حول لم. مسارح إنطلاق الطريق ما وتم. بسبب الله تاريخ ثم يبق, ان يتسنّى الربيع، للإتحاد بين, ٣٠ يكن عملية المبرمة الشّعبين.\r\n"
                        + "Halal\r\n";
        try {
            SMTPClient client = new SMTPClient(HOST, PORT);
            SMTPResponse greeting = client.getLastResponse();
            System.out.println("GREETING -> " + (greeting != null ? greeting.toDetailedString() : "(none)"));

            SMTPResponse helo = client.helo("localhost");
            System.out.println("HELO -> " + (helo != null ? helo.toDetailedString() : "(none)"));

            SMTPResponse mfrom = client.from(FROM);
            System.out.println("MAIL FROM -> " + (mfrom != null ? mfrom.toDetailedString() : "(none)"));

            SMTPResponse rcpt = client.to(TO);
            System.out.println("RCPT TO -> " + (rcpt != null ? rcpt.toDetailedString() : "(none)"));

            String message = "";
            message = message + "From: " + FROM + "\r\n";
            message = message + "To: " + TO + "\r\n";
            message = message + "Subject: " + SUBJECT + "\r\n";
            message = message + "\r\n";
            message = message + BODY;

            SMTPResponse dataResp = client.data(SUBJECT, message);
            System.out.println("DATA -> " + (dataResp != null ? dataResp.toDetailedString() : "(none)"));

            SMTPResponse quit = client.quit();
            System.out.println("QUIT: " + (quit != null ? quit.toDetailedString() : "no response"));
        } catch (Exception e) {
            System.err.println("Error during SMTP demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}