package com.example.project4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    private EditText editTextReply;
    private RecyclerView recyclerViewChat;
    private ChatMessageAdapter adapter;
    private ArrayList<ChatMessage> chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_second);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextReply = findViewById(R.id.editTextReply);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_CHAT_HISTORY)) {
            @SuppressWarnings("unchecked")
            ArrayList<ChatMessage> originalList = (ArrayList<ChatMessage>) intent.getSerializableExtra(MainActivity.EXTRA_CHAT_HISTORY);
            chatList = new ArrayList<>();
            if (originalList != null) {
                for (ChatMessage msg : originalList) {
                    chatList.add(new ChatMessage(msg.getText(), !msg.isSentByMe()));
                }
            }
        } else {
            chatList = new ArrayList<>();
        }

        adapter = new ChatMessageAdapter(chatList);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(adapter);
        if (!chatList.isEmpty()) {
            recyclerViewChat.scrollToPosition(chatList.size() - 1);
        }
    }

    public void returnReply(View view) {
        String reply = editTextReply.getText().toString().trim();
        if (reply.isEmpty()) {
            return;
        }

        Intent replyIntent = new Intent();
        replyIntent.putExtra(MainActivity.EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);

        finish();
    }
}