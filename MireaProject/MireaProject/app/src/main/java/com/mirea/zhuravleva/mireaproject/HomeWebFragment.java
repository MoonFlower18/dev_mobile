package com.mirea.zhuravleva.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.mirea.zhuravleva.mireaproject.databinding.FragmentHomeWebBinding;

// пока что без Retrofit, но я над ним ещё работаю...

public class HomeWebFragment extends Fragment {

    private FragmentHomeWebBinding binding;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeWebBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        WebView webView = binding.webView;
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.mirea.ru");

        binding.logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            Navigation.findNavController(view).navigate(R.id.nav_firebase);
        });
    }
}