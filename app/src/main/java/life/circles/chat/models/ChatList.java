package life.circles.chat.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import life.circles.chat.utils.Utils;

public class ChatList {
    @SerializedName("chat")
    public List<Chat> chatList;
    public String lastDate;

    public List<Chat> getChatList() {
        return chatList;
    }

    public void setChatList(List<Chat> chatList) {
        this.chatList = chatList;
    }

    public void getRefineDataList() {
        List<Chat> refineList = new ArrayList<>();
        if (chatList == null)
            return;

        for (int k = 0; k < chatList.size(); k++) {
            Chat chat = chatList.get(k);
            chat.refineMessageDate();

            //Add date row
            if (Utils.isEmpty(lastDate) || !lastDate.equals(chat.getMessageDate())) {
                lastDate = chat.getMessageDate();
                refineList.add(new Chat(lastDate));
            }

            //Add message row
            refineList.add(chat);
        }
        this.chatList = refineList;
    }

    public void addChat(Chat chat) {
        chatList.add(chat);
    }
}
