package com.mirea.zhuravleva.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.mirea.zhuravleva.mireaproject.databinding.FragmentHomeWebBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class HomeWebFragment extends Fragment {

    private FragmentHomeWebBinding binding;
    private MireaApiService apiService;

    // Интерфейс для API запросов
    public interface MireaApiService {
        @GET("/") // Главная страница
        Call<String> getMainPage();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeWebBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Инициализация Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.mirea.ru/") // Базовый URL
                .addConverterFactory(ScalarsConverterFactory.create()) // Конвертер для сырого HTML
                .build();

        // 2. Создание сервиса для API запросов
        apiService = retrofit.create(MireaApiService.class);

        // 3. Загрузка данных
        loadData();

        // Кнопка выхода
        binding.signOutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            requireActivity().onBackPressed(); // Возврат на предыдущий фрагмент
        });
    }

    private void loadData() {

        // 4. Выполнение запроса
        apiService.getMainPage().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful() && response.body() != null) {
                    // 5. Обработка успешного ответа
                    String htmlContent = response.body();
                    // Здесь можно парсить HTML и отображать нужные данные
                    binding.webView.loadDataWithBaseURL(
                            "https://www.mirea.ru/",
                            htmlContent,
                            "text/html",
                            "UTF-8",
                            null
                    );
                } else {
                    Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(requireContext(), "Ошибка: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}