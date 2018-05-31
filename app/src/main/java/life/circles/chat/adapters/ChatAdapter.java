package life.circles.chat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import life.circles.chat.R;
import life.circles.chat.holders.DatetimeHolder;
import life.circles.chat.holders.IncomingMsgHolder;
import life.circles.chat.holders.OutgoingMsgHolder;
import life.circles.chat.models.Chat;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_OUTGOING = 1, TYPE_INCOMING = 2, TYPE_DATE = 3;
    private List<Chat> chatFeed = new ArrayList<>();
    private Context context;

    public ChatAdapter(Context context, List<Chat> chatFeed) {
        this.context = context;
        this.chatFeed = chatFeed;
    }

    public void setChatFeed(List<Chat> chatFeed) {
        this.chatFeed = chatFeed;
    }

    @Override
    public int getItemViewType(int position) {
        String messageType = chatFeed.get(position).getMessageType();

        if (Chat.TYPE_OUTGOING.equalsIgnoreCase(messageType)) {
            return TYPE_OUTGOING;
        } else if (Chat.TYPE_INCOMING.equalsIgnoreCase(messageType)) {
            return TYPE_INCOMING;
        } else if (Chat.TYPE_DATE.equalsIgnoreCase(messageType)) {
            return TYPE_DATE;
        }

        return -1;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int viewType = holder.getItemViewType();
        Chat chat = chatFeed.get(position);

        switch (viewType) {
            case TYPE_OUTGOING:
                ((OutgoingMsgHolder) holder).showDetail(chat);
                break;
            case TYPE_INCOMING:
                ((IncomingMsgHolder) holder).showDetail(chat);
                break;

            case TYPE_DATE:
                ((DatetimeHolder) holder).showDetail(chat);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatFeed.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case TYPE_OUTGOING:
                View outgoingView = LayoutInflater
                        .from(context)
                        .inflate(R.layout.out_message_layout, parent, false);
                viewHolder = new OutgoingMsgHolder(outgoingView);
                break;
            case TYPE_INCOMING:
                View incomingView = LayoutInflater
                        .from(context)
                        .inflate(R.layout.in_message_layout, parent, false);
                viewHolder = new IncomingMsgHolder(incomingView);
                break;
            default:
                //TYPE_DATE
                View dateView = LayoutInflater
                        .from(context)
                        .inflate(R.layout.date_layout, parent, false);
                viewHolder = new DatetimeHolder(dateView);
                break;
        }
        return viewHolder;
    }
}
