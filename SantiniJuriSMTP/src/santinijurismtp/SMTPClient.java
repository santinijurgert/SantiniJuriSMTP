
package santinijurismtp;

import java.io.*;
import java.net.Socket;
import java.util.StringJoiner;


public class SMTPClient {
    
    BufferedReader reader;
    BufferedWriter writer;
    Socket socket;
    private SMTPResponse lastRisposta;

    public SMTPClient(String ip, int port) {
        try
        {
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String greeting = readServer();
            lastRisposta = SMTPParser.parse(greeting);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private String readServer()
    {
        /*
        ogni messaggio inizia con un codice da 3 caratteri
        messaggi multipli hanno un - dopo il codice, l'ultimo messaggio ha uno spazio
        elabora finche il 4 char e' uno spazio
         */
        try {
            String tot = "";
            String line = reader.readLine();
            if (line == null) {
                return "";
            }
            tot += line + "\r\n";
            while (line.length() >= 4 && line.charAt(3) == '-') {
                line = reader.readLine();
                if (line == null) break;
                tot += line + "\r\n";
            }
            return tot.trim();
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    
    private SMTPResponse sendCommand(String command)
    {
        /*
        invia un comando al server
        return risposta parsata
         */
        try {
            if (writer == null) {
                lastRisposta = new SMTPResponse(0, "No writer available", "");
                return lastRisposta;
            }
            writer.write(command + "\r\n");
            writer.flush();
            String raw = readServer();
            SMTPResponse resp = SMTPParser.parse(raw);
            lastRisposta = resp;
            return resp;
        } catch (IOException e) {
            lastRisposta = new SMTPResponse(0, "I/O error: " + e.getMessage(), "");
            return lastRisposta;
        } catch (IllegalArgumentException e) {
            lastRisposta = new SMTPResponse(0, "Parse error: " + e.getMessage(), "");
            return lastRisposta;
        }
    }
    
    public SMTPResponse helo(String server)
    {
        return sendCommand("HELO " + server);
    }

    public SMTPResponse from(String from)
    {
        // MAIL FROM:<indirizzo>
        return sendCommand("MAIL FROM:<" + from + ">");
    }

    public SMTPResponse to(String to)
    {
        // RCPT TO:<indirizzo>
        return sendCommand("RCPT TO:<" + to + ">");
    }

    public SMTPResponse data(String subject, String message)
    {
        // DATA 
        SMTPResponse resp = sendCommand("DATA");
        if (resp == null || resp.getCode() != 354) {
            return resp;
        }
        try {

            String tot = "";
            if (subject != null && !subject.isEmpty()) {
                tot += "Subject: " + subject + "\r\n";
            }
            tot += "\r\n"; 
            if (message != null) {
                String[] lines = message.split("\\r?\\n");
                for (String l : lines) {
                    if (l.startsWith(".")) {
                        tot += "." + l + "\r\n";
                    } else {
                        tot += l + "\r\n";
                    }
                }
            }
            writer.write(tot);
            writer.write(".\r\n");
            writer.flush();
            String raw = readServer();
            SMTPResponse risposta = SMTPParser.parse(raw);
            lastRisposta = risposta;
            return risposta;
        } catch (IOException e) {
            lastRisposta = new SMTPResponse(0, "I/O error: " + e.getMessage(), "");
            return lastRisposta;
        } catch (IllegalArgumentException e) {
            lastRisposta = new SMTPResponse(0, "Parse error: " + e.getMessage(), "");
            return lastRisposta;
        }
    }

    public SMTPResponse quit()
    {
        SMTPResponse resp = sendCommand("QUIT");
        closeConnection();
        return resp;
    }

    private void closeConnection()
    {
        try {
            if (reader != null) reader.close();
        } catch (IOException ignored) {}
        try {
            if (writer != null) writer.close();
        } catch (IOException ignored) {}
        try {
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
    }

    public SMTPResponse getLastResponse()
    {
        return lastRisposta;
    }
    
}

    
    

