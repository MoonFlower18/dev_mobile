package com.mirea.zhuravleva.internalfilestorage;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "history_note.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText dateEditText = findViewById(R.id.dateEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        Button saveToFileButton = findViewById(R.id.saveToFileButton);

        saveToFileButton.setOnClickListener(v -> {
            String date = dateEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            if (!date.isEmpty() && !description.isEmpty()) {
                saveToFile(date, description);
            } else {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void saveToFile(String date, String description) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            String content = "Дата:\n" + date + "\nОписание:\n" + description;
            fos.write(content.getBytes());
            fos.close();

            Toast.makeText(this, "Файл сохранён!", Toast.LENGTH_SHORT).show();
            Log.d("FileSave", "Файл записан: " + getFilesDir() + "/" + FILE_NAME);

        } catch (IOException e) {
            Toast.makeText(this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}