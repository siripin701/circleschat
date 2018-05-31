package life.circles.chat.models;

import com.google.gson.annotations.SerializedName;
import java.text.ParseException;
import java.util.Date;
import life.circles.chat.utils.Utils;

public class Chat {
    public final static String TYPE_OUTGOING = "OUTGOING", TYPE_INCOMING = "INCOMING", TYPE_DATE = "DATE";
    @SerializedName("timestamp")
    private String rawTimestamp;

    @SerializedName("direction")
    private String messageType;

    private String message;
    private String messageDate;
    private String messageTime;

    public Chat(String messageDate) {
        this.messageDate = messageDate;
        this.messageType = TYPE_DATE;
    }

    public Chat(String message, String messageType) {
        this.message = message;
        this.messageType = messageType;
        Date currentDateTime = new Date();
        setNiceDateAndTime(Utils.enUsFormat.format(currentDateTime));
    }

    public void refineMessageDate() {
        if (Utils.isEmpty(messageDate) && !Utils.isEmpty(rawTimestamp)) {
            setNiceDateAndTime(rawTimestamp);
        }
    }

    private void setNiceDateAndTime(String inputDate) {
        try {
            Date date = Utils.enUsFormat.parse(inputDate);
            messageDate = Utils.dateFormat.format(date);
            messageTime = Utils.timeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getMessageDate() {
        return messageDate;
    }

    public String getMessageTime() {
        return messageTime;
    }

    @Override
    public String toString() {
        return getMessageDate() + ": " + getMessageTime() + "[" + getMessageType() + "] " + message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
