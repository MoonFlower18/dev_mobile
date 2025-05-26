package com.mirea.zhuravleva.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.mirea.zhuravleva.mireaproject.databinding.FragmentCompasBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Compas#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Compas extends Fragment implements SensorEventListener {

    private FragmentCompasBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    private float[] gravity;
    private float[] geomagnetic;
    private float azimuth = 0f;
    private float currentAzimuth = 0f; // для анимации


    public Compas() {
        // Required empty public constructor
    }

    public static Compas newInstance(String param1, String param2) {
        Compas fragment = new Compas();
        Bundle args = new Bundle();
        args.putString("ARG_PARAM1", param1);
        args.putString("ARG_PARAM2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCompasBinding.inflate(inflater, container, false);

        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            gravity = event.values.clone();
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            geomagnetic = event.values.clone();

        if (gravity != null && geomagnetic != null) {
            float[] R = new float[9];
            float[] I = new float[9];
            if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float) Math.toDegrees(orientation[0]);
                if (azimuth < 0)
                    azimuth += 360;
            }

            String direction = getDirectionFromAzimuth(azimuth);
            binding.textView.setText(direction + " (" + Math.round(azimuth) + "°)");

            rotateCompass(azimuth);
        }
    }

    private void rotateCompass(float newAzimuth) {
        if (binding == null) return;

        RotateAnimation rotate = new RotateAnimation(
                -currentAzimuth,
                -newAzimuth,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        rotate.setDuration(500);
        rotate.setFillAfter(true);

        binding.compassImage.startAnimation(rotate);
        currentAzimuth = newAzimuth;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private String getDirectionFromAzimuth(float azimuth) {
        if (azimuth >= 337.5 || azimuth < 22.5)
            return "Север";
        else if (azimuth >= 22.5 && azimuth < 67.5)
            return "Северо-восток";
        else if (azimuth >= 67.5 && azimuth < 112.5)
            return "Восток";
        else if (azimuth >= 112.5 && azimuth < 157.5)
            return "Юго-восток";
        else if (azimuth >= 157.5 && azimuth < 202.5)
            return "Юг";
        else if (azimuth >= 202.5 && azimuth < 247.5)
            return "Юго-запад";
        else if (azimuth >= 247.5 && azimuth < 292.5)
            return "Запад";
        else
            return "Северо-запад";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
