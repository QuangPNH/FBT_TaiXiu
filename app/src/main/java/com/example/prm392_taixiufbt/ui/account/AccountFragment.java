package com.example.prm392_taixiufbt.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set up listeners for your buttons
        binding.changeInfoButton.setOnClickListener(v ->
                NavHostFragment.findNavController(AccountFragment.this)
                        .navigate(R.id.change_info));

        binding.supportButton.setOnClickListener(v ->
                NavHostFragment.findNavController(AccountFragment.this)
                        .navigate(R.id.support_page));

        binding.logoutButton.setOnClickListener(v -> {
            // Handle logout logic here
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}