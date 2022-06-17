package pkg;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class Users {
    private String name;
    private int ChatID;
    private String Date;
    private String password;
    private Long timestamp;


    public Users(int chatID, String name) {
        ChatID = chatID;
        this.timestamp = getTimeInLong();
        this.name = name;
        this.password = generateSimplePassword();
        Date = getCurrentLocalDateTimeStamp() ;
    }

    public void setChatID(int chatID) {
        ChatID = chatID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getChatID() {
        return ChatID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return Date;
    }

    @Override
    public String toString() {
        return "Users{" +
                "ChatID=" + ChatID +
                ", timestamp=" + timestamp +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }



    private String getCurrentLocalDateTimeStamp() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    private String generateSimplePassword(){
        int len = 4;
        String chars = "0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }

    private Long getTimeInLong(){
       return new Date().getTime()/1000;
    }
}
