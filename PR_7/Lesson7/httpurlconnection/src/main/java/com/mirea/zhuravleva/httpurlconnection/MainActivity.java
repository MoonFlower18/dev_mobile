package com.mirea.zhuravleva.httpurlconnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mirea.zhuravleva.httpurlconnection.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            if (isNetworkAvailable()) {
                new DownloadGeoPoint().execute("https://ipinfo.io/json");
            } else {
                Toast.makeText(this, "Нет соединения с интернетом!", Toast.LENGTH_LONG).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager != null ?
                connectivityManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    private class DownloadGeoPoint extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.button.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Ошибка: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);

                binding.ipView.setText(json.optString("ip", "неизвестно"));
                binding.cityView.setText(json.optString("city", "неизвестно"));
                binding.regionView.setText(json.optString("region", "неизвестно"));
                binding.countryView.setText(json.optString("country", "неизвестно"));

                String[] coords = json.optString("loc", "0,0").split(",");
                if (coords.length == 2) {
                    new DownloadWeatherTask().execute(
                            "https://api.open-meteo.com/v1/forecast?" +
                                    "latitude=" + coords[0] +
                                    "&longitude=" + coords[1] +
                                    "&current_weather=true");
                }
            } catch (JSONException e) {
                binding.weatherView.setText("Ошибка загрузки данных о погоде\n\uD83D\uDE1F");
            } finally {
                binding.progressBar.setVisibility(View.GONE);
                binding.button.setEnabled(true);
            }
        }
    }

    private class DownloadWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Ошибка получения данных о погоде: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONObject currentWeather = json.getJSONObject("current_weather");

                double temperature = currentWeather.getDouble("temperature");
                double windspeed = currentWeather.getDouble("windspeed");
                int weathercode = currentWeather.getInt("weathercode");
                double winddirection = currentWeather.getDouble("winddirection");
                String weatherDescription = getWeatherDescription(weathercode);
                String windDirection = getWindDirection(winddirection);

                String weatherText = String.format(Locale.getDefault(),
                        "Температура: %.1f°C\nСкорость ветра: %.1f км/ч (%s)\nПогода: %s",
                        temperature, windspeed, windDirection, weatherDescription);

                binding.weatherView.setText(weatherText);
            } catch (JSONException e) {
                binding.weatherView.setText("Ошибка загрузки данных о погоде\n\uD83D\uDE1F");
            }
        }
    }

    private String getWeatherDescription(int code) {
        switch (code) {
            case 0: return "Ясно ☀️";
            case 1: return "Преимущественно ясно 🌤";
            case 2: return "Переменная облачность ⛅";
            case 3: return "Пасмурно ☁️";
            case 45: return "Туман 🌫";
            case 48: return "Туман с инеем 🌫";
            case 51: return "Лёгкая морось 🌧";
            case 53: return "Умеренная морось 🌧";
            case 55: return "Сильная морось 🌧";
            case 56: return "Лёгкая ледяная морось 🌧❄️";
            case 57: return "Сильная ледяная морось 🌧❄️";
            case 61: return "Лёгкий дождь 🌦";
            case 63: return "Умеренный дождь 🌧";
            case 65: return "Сильный дождь 🌧💦";
            case 66: return "Лёгкий ледяной дождь 🌧❄️";
            case 67: return "Сильный ледяной дождь 🌧❄️";
            case 71: return "Лёгкий снег ❄️";
            case 73: return "Умеренный снег ❄️🌨";
            case 75: return "Сильный снег ❄️🌨";
            case 77: return "Снежные зёрна ❄️";
            case 80: return "Лёгкие ливни 🌦";
            case 81: return "Умеренные ливни 🌧";
            case 82: return "Сильные ливни 🌧💦";
            case 85: return "Лёгкий снегопад ❄️";
            case 86: return "Сильный снегопад ❄️🌨";
            case 95: return "Гроза ⚡";
            case 96: return "Гроза с градом ⚡🌨";
            case 99: return "Сильная гроза с градом ⚡🌨💥";
            default: return "Неизвестно (" + code + ")";
        }
    }
    private String getWindDirection(double degrees) {
        if (degrees >= 337.5 || degrees < 22.5) return "Северный";
        if (degrees >= 22.5 && degrees < 67.5) return "Северо-восточный";
        if (degrees >= 67.5 && degrees < 112.5) return "Восточный";
        if (degrees >= 112.5 && degrees < 157.5) return "Юго-восточный";
        if (degrees >= 157.5 && degrees < 202.5) return "Южный";
        if (degrees >= 202.5 && degrees < 247.5) return "Юго-западный";
        if (degrees >= 247.5 && degrees < 292.5) return "Западный";
        if (degrees >= 292.5 && degrees < 337.5) return "Северо-западный";
        return "Неизвестное направление";
    }

    private String downloadUrl(String urlString) throws IOException {
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            } else {
                throw new IOException("Ошибка HTTP: " + connection.getResponseCode());
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}