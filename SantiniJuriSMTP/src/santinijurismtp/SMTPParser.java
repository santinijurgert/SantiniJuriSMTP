package santinijurismtp;


public class SMTPParser {

    public SMTPParser() {
    }
    
    public static SMTPResponse parse(String rawResponse)
    {
        // parsa solo la prima riga (l'header si trova li)
        if (rawResponse == null || rawResponse.trim().isEmpty()) {
            throw new IllegalArgumentException("Risposta vuota");
        }
        String firstLine = rawResponse.split("\\r?\\n", 2)[0];
        /*
        ESEMPIO
        rawResponse = "250 OK\r\n250-SIZE 35882577\r\n250-PIPELINING\r\n"
            split(...,2) -> ["250 OK", "250-SIZE 35882577\r\n250-PIPELINING\r\n"]
            firstLine = "250 OK"
        
        */
        String[] parts = firstLine.split(" ", 2);
        Integer code;
        String message = "";
        try {
            code = Integer.valueOf(parts[0]);
            if (parts.length > 1) {
                message = parts[1].trim();
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid SMTP response: " + rawResponse);
        }
        return new SMTPResponse(code, message, rawResponse);
    }
    
    public static Boolean isSuccessCode(int code)
    {
        if (code/100 == 2)
        {
            return true;
        }
        return false;
    }
    
    public static Boolean isErrorCode(int code)
    {
        if (code/100 == 4 || code/100 == 5)
        {
            return true;
        }
        return false;
    }
    
}