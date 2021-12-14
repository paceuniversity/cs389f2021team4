package com.cs389team4.needtofeed.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.amplifyframework.auth.AuthUserAttribute;
import com.cs389team4.needtofeed.MainActivity;
import com.cs389team4.needtofeed.R;
import com.cs389team4.needtofeed.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Grabs the user name from AWS attributes
        AuthUserAttribute name = MainActivity.userAttrs.get(2);
        String name_string = name.getValue();
        TextView name_tv = binding.userName;
        name_tv.setText(name_string);

        //Grabs the user email from AWS attributes
        AuthUserAttribute email = MainActivity.userAttrs.get(3);
        String email_string = email.getValue();
        TextView email_tv = binding.userEmail;
        email_tv.setText(email_string);

        //Grabs the user phone number from AWS attributes
//        AuthUserAttribute phone = MainActivity.userAttrs.get(5);
//        String phone_string = phone.getValue();
//        TextView phone_tv = binding.userPhoneNumber;
//        phone_tv.setText(phone_string);

        //Navigate to EditNameFragment on Click
        binding.nameLblContainer.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.editNameFragment);
            }
        );

        //Navigate to EditEmailFragment on Click
        binding.emailLblContainer.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(R.id.editEmailFragment);
                }
        );

        //Navigate to EditPasswordFragment on Click
        binding.passwordLblContainer.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(R.id.editPasswordFragment);
                }
        );

        //Navigate to EditAddressFragment on Click
        binding.addressLblContainer.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(R.id.editAddressFragment);
                }
        );

        //Navigate to EditPhoneNumberFragment on Click
        binding.phoneNumberLblContainer.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(R.id.editPhoneNumberFragment);
                }
        );

        //Navigate to Help/Contact Fragment on Click
        binding.helpLblContainer.setOnClickListener(v -> {
                    Navigation.findNavController(view).navigate(R.id.helpContactFragment);
                }
        );

    }
}