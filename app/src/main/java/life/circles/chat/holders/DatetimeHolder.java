package life.circles.chat.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import life.circles.chat.R;
import life.circles.chat.models.Chat;

public class DatetimeHolder extends RecyclerView.ViewHolder {
    private TextView dateTextView;

    public DatetimeHolder(View itemView) {
        super(itemView);
        // Initiate view
        dateTextView = itemView.findViewById(R.id.dateTime);
    }

    public void showDetail(Chat chat) {
        dateTextView.setText(chat.getMessageDate());
    }
}
