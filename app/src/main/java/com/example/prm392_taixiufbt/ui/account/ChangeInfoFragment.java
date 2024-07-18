

package com.example.prm392_taixiufbt.ui.account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.databinding.FragmentChangeinfoBinding;
import com.example.prm392_taixiufbt.models.User;

import android.view.MenuItem;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
public class ChangeInfoFragment extends Fragment {

    private FragmentChangeinfoBinding binding;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChangeinfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String username = bundle.getString("Username", ""); // Default to empty if not found
            fetchUserInfoAndUpdateUI(username);
        }

        binding.submitChangesButton.setOnClickListener(v -> {
            // Retrieve updated information
            String currentId = binding.idTextView.getText().toString();
            String newName = binding.nameEditText.getText().toString();
            String newUsername = binding.usernameEditText.getText().toString();
            String newEmail = binding.emailEditText.getText().toString();
            String newPassword = binding.passwordEditText.getText().toString();
            String newPhone = binding.phoneEditText.getText().toString();
            String newMoney = binding.moneyTextView.getText().toString();
            String newRole = binding.roleTextView.getText().toString();
            // Validate input (simple validation for demonstration)
            if (newName.isEmpty() || newUsername.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                User user = new User();
                // Ensure all necessary fields, including the primary key, are set
                user.setId(Integer.parseInt(currentId));
                user.setName(newName);
                user.setUsername(newUsername);
                user.setEmail(newEmail);
                user.setPassword(newPassword);
                user.setPhone(newPhone);
                user.setMoney(Integer.parseInt(newMoney));
                user.setTypeId(Integer.parseInt(newRole));
                try {
                    // Call the update method on your DAO
                    FBTTaiXiuDatabase.getDatabase(getContext()).userDao().update(user);
                    Log.d("UpdateOperation", "User information updated successfully");
                    getActivity().runOnUiThread(() -> {
                        // Show a success message
                        Toast.makeText(getContext(), "User information updated successfully", Toast.LENGTH_SHORT).show();
                    });
                    // Redirect to AccountFragment
                    NavController navController = NavHostFragment.findNavController(ChangeInfoFragment.this);
                    navController.navigate(R.id.navigation_account);
                } catch (Exception e) {
                    Log.e("UpdateOperation", "Error updating user information", e);
                    getActivity().runOnUiThread(() -> {
                        // Show an error message
                        Toast.makeText(getContext(), "Error updating user information", Toast.LENGTH_SHORT).show();
                    });
                }
            }).start();
        });

        return root;
    }

    private void fetchUserInfoAndUpdateUI(String username) {
        new Thread(() -> {
            // Assuming FBTTaiXiuDatabase is your Room database instance and it's already initialized
            User user = FBTTaiXiuDatabase.getDatabase(getContext()).userDao().findUserByUsername(username);
            if (user != null) {
                getActivity().runOnUiThread(() -> {
                    // Update UI with user information
                    binding.nameEditText.setText(user.getName());
                    binding.usernameEditText.setText(user.getUsername());
                    binding.emailEditText.setText(user.getEmail());
                    // Assuming there's a getPassword() method. If not, consider security implications.
                    binding.passwordEditText.setText(user.getPassword());
                    binding.idTextView.setText(getString(R.string.currentId, user.getId()));
                    binding.phoneEditText.setText(user.getPhone());
                    binding.moneyTextView.setText(getString(R.string.currentMoney, user.getMoney()));
                    binding.roleTextView.setText(getString(R.string.curentRole, user.getTypeId()));

                });
            }
        }).start();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Check if the context is an instance of AppCompatActivity
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            // Get the ActionBar from the AppCompatActivity
            ActionBar actionBar = activity.getSupportActionBar();
            // Check if the ActionBar is not null
            if (actionBar != null) {
                // Hide the up button
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
        // Handle button click to navigate to AccountFragment
        view.findViewById(R.id.gotoAccountFragmentButton).setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.navigation_account);
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}