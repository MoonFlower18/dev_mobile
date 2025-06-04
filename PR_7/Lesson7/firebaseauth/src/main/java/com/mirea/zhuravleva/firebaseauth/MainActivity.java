package com.mirea.zhuravleva.firebaseauth;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mirea.zhuravleva.firebaseauth.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MyTest";
    private ActivityMainBinding binding;
    private FirebaseAuth fb_auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // активация фб
        fb_auth = FirebaseAuth.getInstance();

        // кнопка для входа в акк
        binding.signInZone.setOnClickListener(v -> signIn(
                binding.emailZone.getText().toString(),
                binding.passwordZone.getText().toString()));

        // кнопка для создания акка
        binding.createAccZone.setOnClickListener(v -> createAccount(
                binding.emailZone.getText().toString(),
                binding.passwordZone.getText().toString()));

        // выйти из акка
        binding.signOutZone.setOnClickListener(v -> signOut());

        // верифицировать акк
        binding.verifMailZone.setOnClickListener(v -> sendEmailVerification());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // проверка на авторизацию
        FirebaseUser currentUser = fb_auth.getCurrentUser();
        updateUI(currentUser);
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = binding.emailZone.getText().toString();
        if (email.isEmpty()) {
            binding.emailZone.setError("Required.");
            valid = false;
        } else {
            binding.emailZone.setError(null);
        }

        String password = binding.passwordZone.getText().toString();
        if (password.isEmpty()) {
            binding.passwordZone.setError("Required.");
            valid = false;
        } else {
            binding.passwordZone.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            binding.signZone.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            binding.infoZone.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            binding.verifMailZone.setVisibility(View.VISIBLE);
            binding.signOutZone.setVisibility(View.VISIBLE);
            binding.emailZone.setVisibility(View.GONE);
            binding.passwordZone.setVisibility(View.GONE);
            binding.signInZone.setVisibility(View.GONE);
            binding.createAccZone.setVisibility(View.GONE);
            binding.verifMailZone.setEnabled(!user.isEmailVerified());
        } else {
            binding.signZone.setText(R.string.signed_out);
            binding.infoZone.setText(null);
            binding.signOutZone.setVisibility(View.GONE);
            binding.verifMailZone.setVisibility(View.GONE);
            binding.emailZone.setVisibility(View.VISIBLE);
            binding.passwordZone.setVisibility(View.VISIBLE);
            binding.signInZone.setVisibility(View.VISIBLE);
            binding.createAccZone.setVisibility(View.VISIBLE);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        fb_auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = fb_auth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        fb_auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = fb_auth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
                if (!task.isSuccessful()) {
                    binding.signZone.setText(R.string.auth_failed);
                }
            }
        });
    }

    private void signOut() {
        fb_auth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        binding.verifMailZone.setEnabled(false);

        final FirebaseUser user = fb_auth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                binding.verifMailZone.setEnabled(true);
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(MainActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}