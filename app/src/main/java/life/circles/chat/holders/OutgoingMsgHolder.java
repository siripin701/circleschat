package life.circles.chat.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import life.circles.chat.R;
import life.circles.chat.models.Chat;

public class OutgoingMsgHolder extends RecyclerView.ViewHolder {
    private TextView outMessageTextView, outMessageTimeTextView;

    public OutgoingMsgHolder(View itemView) {
        super(itemView);
        // Initiate view
        outMessageTextView = itemView.findViewById(R.id.outMessageText);
        outMessageTimeTextView = itemView.findViewById(R.id.outMessageTime);
    }

    public void showDetail(Chat chat) {
        outMessageTextView.setText(chat.getMessage());
        outMessageTimeTextView.setText(chat.getMessageTime());
    }
}
