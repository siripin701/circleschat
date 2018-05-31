package life.circles.chat.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import life.circles.chat.R;
import life.circles.chat.models.Chat;

public class IncomingMsgHolder extends RecyclerView.ViewHolder {
    private TextView inMessageTextView, inMessageTimeTextView;

    public IncomingMsgHolder(View itemView) {
        super(itemView);
        // Initiate view
        inMessageTextView = itemView.findViewById(R.id.inMessageText);
        inMessageTimeTextView = itemView.findViewById(R.id.inMessageTime);
    }

    public void showDetail(Chat chat) {
        inMessageTextView.setText(chat.getMessage());
        inMessageTimeTextView.setText(chat.getMessageTime());
    }
}
