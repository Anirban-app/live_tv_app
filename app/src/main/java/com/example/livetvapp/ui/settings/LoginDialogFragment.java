package com.example.livetvapp.ui.settings;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.livetvapp.R;

public class LoginDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_login, null);
        EditText usernameInput = view.findViewById(R.id.usernameInput);
        EditText passwordInput = view.findViewById(R.id.passwordInput);
        Button loginSubmitButton = view.findViewById(R.id.loginSubmitButton);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(view)
                .setCancelable(true)
                .create();

        loginSubmitButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(requireContext(), "Enter username and password", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(requireContext(), "Login is mocked for demo only", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        return dialog;
    }
}
