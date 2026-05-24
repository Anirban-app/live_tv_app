package com.example.livetvapp.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.livetvapp.R;
import com.example.livetvapp.util.PreferenceUtils;
import com.example.livetvapp.util.UiModeHelper;

public class SettingsFragment extends Fragment {
    private Switch themeSwitch;
    private Switch bootSwitch;
    private Button loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        themeSwitch = view.findViewById(R.id.themeSwitch);
        bootSwitch = view.findViewById(R.id.bootToPlayerSwitch);
        loginButton = view.findViewById(R.id.loginButton);

        themeSwitch.setChecked(PreferenceUtils.isDarkTheme(requireContext()));
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> PreferenceUtils.setDarkTheme(requireContext(), isChecked));

        if (!UiModeHelper.isTelevision(requireContext())) {
            bootSwitch.setVisibility(View.GONE);
        }
        bootSwitch.setChecked(PreferenceUtils.isBootToPlayerEnabled(requireContext()));
        bootSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> PreferenceUtils.setBootToPlayerEnabled(requireContext(), isChecked));

        loginButton.setOnClickListener(v -> {
            LoginDialogFragment dialog = new LoginDialogFragment();
            dialog.show(getParentFragmentManager(), "login_dialog");
        });
    }
}
