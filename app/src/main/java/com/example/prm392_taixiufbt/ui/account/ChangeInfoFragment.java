

package com.example.prm392_taixiufbt.ui.account;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.databinding.FragmentChangeinfoBinding;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
public class ChangeInfoFragment extends Fragment {

    private FragmentChangeinfoBinding binding;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupPermissionLauncher();
        setupImagePickerLauncher();
    }

    private void setupPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                openImagePicker();
            } else {
                // Handle permission denial
            }
        });
    }

    private void setupImagePickerLauncher() {
        pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                Uri selectedImageUri = result.getData().getData();
                binding.profileImageView.setImageURI(selectedImageUri);
            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentChangeinfoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.profileImageView.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(
                    getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
        binding.submitChangesButton.setOnClickListener(v -> {
            // Handle the submission of changes here
        });
        return root;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
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