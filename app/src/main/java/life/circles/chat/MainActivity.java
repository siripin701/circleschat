package life.circles.chat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Timer;
import java.util.TimerTask;

import life.circles.chat.adapters.ChatAdapter;
import life.circles.chat.models.Chat;
import life.circles.chat.models.ChatList;
import life.circles.chat.utils.Utils;

public class MainActivity extends AppCompatActivity {
    private final String SHARE_PREF_KEY = "chat_msg";
    private final String SHARE_PREF_NAME = "chat";
    private final String EMPTY = "";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ChatAdapter chatAdapter;
    private ChatList chatListClass;
    private EditText chatMsg;
    private Timer onLineTimer;
    private Handler onLineHandler = new Handler();
    private long onlineTimerMax = 60000;//60 seconds
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        chatMsg = findViewById(R.id.chatMsg);

        // Set Layout Manager
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadData();
    }

    public void onSendMessage(View view) {
        switch (view.getId()) {
            case R.id.sendButton:
                if (validateMessage()) {
                    sendNewMessage(chatMsg.getText().toString(), Chat.TYPE_OUTGOING);
                    //clear message box
                    chatMsg.setText(EMPTY);
                    //start timer to see if user are still there
                    startOnlineTimer();
                }
                break;
        }
    }

    private void sendNewMessage(String message, String messageType) {
        Chat chat = new Chat(message, messageType);
        int newItem = 1;
        if (addDateRowIfNeeded(chat)) {
            newItem++;
        }
        chatListClass.addChat(chat);

        chatAdapter.setChatFeed(chatListClass.getChatList());
        chatAdapter.notifyItemRangeInserted(chatListClass.getChatList().size() - 1, newItem);
        recyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
    }

    public boolean addDateRowIfNeeded(Chat chat) {
        boolean isDateRowAdded = false;
        if (Utils.isEmpty(chatListClass.lastDate) || !chatListClass.lastDate.equals(chat.getMessageDate())) {
            chatListClass.lastDate = chat.getMessageDate();
            chatListClass.addChat(new Chat(chatListClass.lastDate));
            isDateRowAdded = true;
        }
        return isDateRowAdded;
    }

    public boolean validateMessage() {
        return !Utils.isEmpty(chatMsg.getText().toString());
    }

    private void loadData() {
        if (!retrieveDataInSharedPreferences()) {
            retrieveDataInAsset();
        }

        chatAdapter = new ChatAdapter(this, chatListClass.getChatList());
        recyclerView.setAdapter(chatAdapter);

        if (chatListClass.getChatList().size() > 0) {
            recyclerView.smoothScrollToPosition(chatListClass.getChatList().size() - 1);
        }
        //Observe if user is away
        startOnlineTimer();
    }

    private void retrieveDataInAsset() {
        String strChat = loadJSONFromAsset();
        chatListClass = gson.fromJson(strChat, ChatList.class);
        chatListClass.getRefineDataList();
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("chat_msg.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void startOnlineTimer() {
        Log.d("Chat", "startOnlineTimer");
        //stop the previous timer
        stopOnlineTimer();
        onLineTimer = new Timer();
        TimerTask onlineTimerTask = new TimerTask() {
            public void run() {
                onLineHandler.post(new Runnable() {
                    public void run() {
                        timeout();
                    }
                });
            }
        };

        onLineTimer.schedule(onlineTimerTask, onlineTimerMax);
    }

    private void timeout() {
        sendNewMessage(getString(R.string.you_there), Chat.TYPE_INCOMING);
        //start observing again
        startOnlineTimer();
    }

    private void stopOnlineTimer() {
        if (onLineTimer != null) {
            onLineTimer.cancel();
            onLineTimer.purge();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Chat", "onStop()");
        saveClassInSharedPreferences(chatListClass);
    }

    public void saveClassInSharedPreferences(Object object) {
        String json = gson.toJson(object);
        SharedPreferences userDetails = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        userDetails.edit()
                .putString(SHARE_PREF_KEY, json)
                .apply();
    }

    public boolean retrieveDataInSharedPreferences() {
        SharedPreferences userDetails = getSharedPreferences(SHARE_PREF_NAME, MODE_PRIVATE);
        String json = userDetails.getString(SHARE_PREF_KEY, EMPTY);

        if (Utils.isEmpty(json))
            return false;

        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        chatListClass = gson.fromJson(reader, ChatList.class);
        return true;
    }
}
