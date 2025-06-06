package com.mirea.zhuravleva.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.mirea.zhuravleva.securesharedpreferences.databinding.ActivityMainBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> {
            try {
                savePoetName();
            } catch (GeneralSecurityException | IOException e) {
                throw new RuntimeException(e);
            }
            loadAndDisplayPoet();
        });

        loadAndDisplayPoet();
    }

    private void savePoetName() throws GeneralSecurityException, IOException {
        String poetName = binding.editTextText.getText().toString();

        SharedPreferences sharedPrefs = EncryptedSharedPreferences.create(
                "crypto_prefs",
                MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                getApplicationContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );

        sharedPrefs.edit().putString("poetName", poetName).apply();

    }

    private void loadAndDisplayPoet() {
        try {
            SharedPreferences sharedPrefs = EncryptedSharedPreferences.create(
                    "crypto_prefs",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            String poetName = sharedPrefs.getString("poetName", "");

            if (!poetName.isEmpty()) {
                binding.layout.setVisibility(View.VISIBLE);
                binding.imageView.setImageResource(R.drawable.turgenev);
            } else {
                binding.layout.setVisibility(View.GONE);
            }
        } catch (GeneralSecurityException | IOException e) {
        }
    }
}