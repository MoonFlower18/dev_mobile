package com.example.zhuravleva.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
    }

    public void onClickNewActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);

        EditText editText = findViewById(R.id.edit_text);
        Button sendButton = findViewById(R.id.send_button);
        String textToSend = editText.getText().toString();

        intent.putExtra("key", textToSend);

//        intent.putExtra("key", "MIREA - ЖУРАВЛЕВА ЮЛИЯ СЕРГЕЕВНА");
        startActivity(intent);
    }

}