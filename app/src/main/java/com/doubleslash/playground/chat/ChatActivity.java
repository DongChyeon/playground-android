package com.doubleslash.playground.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.doubleslash.playground.R;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText contentEdit;
    private TextView sendBtn;

    private ArrayList<ChatItem> items = new ArrayList<>();  // adapter에서 별도로 업데이트할 수 있도록 변경해야 됨

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        contentEdit = findViewById(R.id.content_edit);
        sendBtn = findViewById(R.id.send_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        addDummyData();
        adapter = new ChatAdapter(items);
        recyclerView.setAdapter(adapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 전송 누르면 할 일
            }
        });
    }

    private void addDummyData() {
        items.add(new ChatItem(null,"유저 1이 입장하셨습니다.", null, ChatType.ViewType.CENTER_CONTENT));
        items.add(new ChatItem(null,"유저 2가 입장하셨습니다.", null, ChatType.ViewType.CENTER_CONTENT));
        items.add(new ChatItem("유저 1","상대가 보낸 메세지", "00:00", ChatType.ViewType.LEFT_CONTENT));
        items.add(new ChatItem(null,"상대가 보낸 메세지", "00:00", ChatType.ViewType.RIGHT_CONTENT));
    }
}