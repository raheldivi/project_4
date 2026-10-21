package com.example.project4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.project4.extra.MESSAGE";
    public static final String EXTRA_REPLY = "com.example.project4.extra.REPLY";
    public static final String EXTRA_CHAT_HISTORY = "com.example.project4.extra.CHAT_HISTORY";

    private EditText editTextMessage;
    private RecyclerView recyclerViewChat;
    private ChatMessageAdapter adapter;
    private ArrayList<ChatMessage> chatList;

    private final ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String reply = result.getData().getStringExtra(EXTRA_REPLY);
                    if (reply != null && !reply.trim().isEmpty()) {
                        chatList.add(new ChatMessage(reply, false));
                        adapter.notifyItemInserted(chatList.size() - 1);
                        recyclerViewChat.smoothScrollToPosition(chatList.size() - 1);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextMessage = findViewById(R.id.editTextMessage);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);

        chatList = new ArrayList<>();
        adapter = new ChatMessageAdapter(chatList);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(adapter);
    }

    public void openSecondActivity(View view) {
        String message = editTextMessage.getText().toString().trim();
        if (message.isEmpty()) {
            return;
        }

        chatList.add(new ChatMessage(message, true));
        adapter.notifyItemInserted(chatList.size() - 1);
        recyclerViewChat.smoothScrollToPosition(chatList.size() - 1);

        editTextMessage.setText("");

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_CHAT_HISTORY, chatList);

        mActivityResultLauncher.launch(intent);
    }
}