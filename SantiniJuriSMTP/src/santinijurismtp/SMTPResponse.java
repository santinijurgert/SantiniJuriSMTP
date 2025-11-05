package santinijurismtp;


public class SMTPResponse {
    
    Integer code;
    String message;
    String rawResponse;

    public SMTPResponse(Integer code, String message, String rawResponse) {
        this.code = code;
        this.message = message;
        this.rawResponse = rawResponse;
    }

    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }

    public String getRawResponse() {
        return rawResponse;
    }
        
    public Boolean isSuccess()
    {
        // 2xx
        return SMTPParser.isSuccessCode(code);
    }
    
    public Boolean isError()
    {
        // 4xx o 5xx
        return SMTPParser.isErrorCode(code);
    }
    
    public Boolean isIntermediate()
    {
        int c = code / 100;
        return c == 1 || c == 3;
    }
    
    public String toDetailedString() {
        return "SMTPResponse{" + "code=" + code + ", message=" + message + ", rawResponse=" + rawResponse + '}';
    }
    
    @Override
    public String toString()
    {
        return rawResponse;
    }
}