package com.example.buttonclicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

        TextView tvOut = findViewById(R.id.tvOut);
        Button btnWhoAmI = findViewById(R.id.btnWhoAmI);
        Button btnItIsNotMe = findViewById(R.id.btnItIsNotMe);
        CheckBox checkBoxExtra = findViewById(R.id.checkBoxExtra);

        btnWhoAmI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvOut.setText("Мой номер по списку №13");
                checkBoxExtra.setChecked(true);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void onMyButtonClick(View view)
    {
        Toast.makeText(this, "Ещё один способ!", Toast.LENGTH_SHORT).show();

        CheckBox checkBoxExtra = findViewById(R.id.checkBoxExtra);
        TextView tvOut = findViewById(R.id.tvOut);

        checkBoxExtra.setChecked(false);
        tvOut.setText("Вношу корректировки...");
    }
}