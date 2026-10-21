package com.example.project4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder> {

    private List<ChatMessage> chatList;

    public ChatMessageAdapter(List<ChatMessage> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = chatList.get(position);
        holder.textMessage.setText(message.getText());

        if (message.isSentByMe()) {
            holder.textMessage.setBackgroundResource(R.drawable.bg_facebook_button);
            holder.textMessage.setTextColor(holder.itemView.getResources().getColor(android.R.color.white));
            ((ViewGroup.MarginLayoutParams) holder.textMessage.getLayoutParams()).leftMargin = 100;
            ((ViewGroup.MarginLayoutParams) holder.textMessage.getLayoutParams()).rightMargin = 0;
        } else {
            holder.textMessage.setBackgroundResource(R.drawable.bg_bubble_received);
            holder.textMessage.setTextColor(holder.itemView.getResources().getColor(R.color.facebook_text));
            ((ViewGroup.MarginLayoutParams) holder.textMessage.getLayoutParams()).leftMargin = 0;
            ((ViewGroup.MarginLayoutParams) holder.textMessage.getLayoutParams()).rightMargin = 100;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
        }
    }
}
