package com.mirea.zhuravleva.mireaproject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Background_Worker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Background_Worker extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Background_Worker() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Background_Worker newInstance(String param1, String param2) {
        Background_Worker fragment = new Background_Worker();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.background_worker, container, false);

        CheckBox checkBox  = view.findViewById(R.id.checkBox);
        CheckBox checkBox2  = view.findViewById(R.id.checkBox2);
        CheckBox checkBox3  = view.findViewById(R.id.checkBox3);
        CheckBox checkBox4  = view.findViewById(R.id.checkBox4);

        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(v -> {
            if (isInternetAvailable()) {
                checkBox.setChecked(true);
                startMyWorker();
            } else {
                checkBox.setChecked(false);
            }

            if (isDeviceCharging()) {
                checkBox2.setChecked(true);
                startMyWorker();
            } else {
                checkBox2.setChecked(false);
            }

            if (isBatteryNotLow()) {
                checkBox3.setChecked(true);
                startMyWorker();
            } else {
                checkBox3.setChecked(false);
            }

            if (isStorageNotLow()) {
                checkBox4.setChecked(true);
                startMyWorker();
            } else {
                checkBox4.setChecked(false);
            }
        });
        return view;
    }

    private boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private boolean isDeviceCharging() {
        BatteryManager batteryManager = (BatteryManager) getActivity().getSystemService(Context.BATTERY_SERVICE);
        return batteryManager.isCharging();
    }

    private boolean isBatteryNotLow() {
        BatteryManager batteryManager = (BatteryManager) getActivity().getSystemService(Context.BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) > 20;
    }

    private boolean isStorageNotLow() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long availableBytes = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        long minRequiredSpace = 100 * 1024 * 1024; // 100 МБ
        return availableBytes > minRequiredSpace;
    }

    private void startMyWorker() {
        WorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest);
    }
}