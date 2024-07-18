package com.example.prm392_taixiufbt.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.LoginActivity;
import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.databinding.FragmentAccountBinding;
import com.example.prm392_taixiufbt.models.User;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        loadUserInfo();

        binding.changeInfoButton.setOnClickListener(v -> {
            // Fetch current user login from SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("Username", "N/A");
            // Assuming you also store other login credentials that need to be passed

            // Create a Bundle to pass this information
            Bundle bundle = new Bundle();
            bundle.putString("Username", username);

            // Add other login credentials to the bundle as needed

            // Create an instance of ChangeInfoFragment and set arguments
            ChangeInfoFragment changeInfoFragment = new ChangeInfoFragment();
            changeInfoFragment.setArguments(bundle);

            // Navigate to ChangeInfoFragment with the user's login information
            NavHostFragment.findNavController(AccountFragment.this)
                    .navigate(R.id.change_info, bundle); // Ensure you have a navigation action `change_info` defined in your nav_graph.xml
        });
        binding.supportButton.setOnClickListener(v ->
                NavHostFragment.findNavController(AccountFragment.this)
                        .navigate(R.id.support_page));

        binding.logoutButton.setOnClickListener(v -> {
            // Clear SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            // Navigate to LoginActivity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

            // Finish the current activity
            getActivity().finish();
        });

        return root;
    }

    private void loadUserInfo() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "N/A");

        // Fetch user money from the database
        new Thread(() -> {
            User user = FBTTaiXiuDatabase.getDatabase(getContext()).userDao().findUserByUsername(username);
            if (user != null) {
                getActivity().runOnUiThread(() -> {
                    binding.userName.setText(getString(R.string.usernameField, user.getUsername()));
                    binding.totalMoney.setText(getString(R.string.moneyField, user.getMoney()));
                    binding.fullName.setText(getString(R.string.fullnameField, user.getName()));
                });
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}