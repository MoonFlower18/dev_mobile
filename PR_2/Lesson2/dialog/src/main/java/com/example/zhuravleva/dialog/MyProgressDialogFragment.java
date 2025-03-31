package com.example.zhuravleva.dialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class MyProgressDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        String progress_text = "Идёт загрузка диалогового окна...";
        showSnackBar(getView(), "Идёт загрузка диалогового окна...");
        Toast.makeText(requireContext(), progress_text, Toast.LENGTH_LONG).show();

        progressDialog.show();
        return progressDialog;
    }

    private void showSnackBar(View anchorView, String message) {
        View rootView = requireActivity().findViewById(android.R.id.content);
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }
}
