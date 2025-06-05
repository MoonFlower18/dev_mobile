package com.mirea.zhuravleva.mireaproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mirea.zhuravleva.mireaproject.databinding.FragmentFileBinding;
import com.mirea.zhuravleva.mireaproject.databinding.FragmentFirebaseBinding;

import java.util.Objects;


public class FirebaseFragment extends Fragment {
    private static final String TAG = "MyTest";
    private FragmentFirebaseBinding binding;
    private FirebaseAuth fb_auth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirebaseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fb_auth = FirebaseAuth.getInstance();
        binding.signInZone.setOnClickListener(v -> signIn(
                binding.emailZone.getText().toString(),
                binding.passwordZone.getText().toString()));

        binding.createAccZone.setOnClickListener(v -> createAccount(
                binding.emailZone.getText().toString(),
                binding.passwordZone.getText().toString()));

        binding.signOutZone.setOnClickListener(v -> signOut());
        binding.verifMailZone.setOnClickListener(v -> sendEmailVerification());

        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onStart() {
        super.onStart();
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
            navigateToHomeWebFragment();
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

    private void navigateToHomeWebFragment() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_firebase_to_homeweb);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        fb_auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = fb_auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        fb_auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = fb_auth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                    if (!task.isSuccessful()) {
                        binding.signZone.setText(R.string.auth_failed);
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
        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(requireActivity(), task -> {
                    binding.verifMailZone.setEnabled(true);
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(),
                                "Verification email sent to " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.getException());
                        Toast.makeText(requireContext(),
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}