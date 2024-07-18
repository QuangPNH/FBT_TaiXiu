package com.example.prm392_taixiufbt.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.databinding.FragmentShopBinding;
import com.example.prm392_taixiufbt.models.GameProfile;

import java.util.List;

public class ShopFragment extends Fragment {

    private FragmentShopBinding binding;
    private GameProfileAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Create an instance of the factory
        ShopViewModelFactory factory = new ShopViewModelFactory(requireActivity().getApplication());
        // Use the factory to create a ViewModel instance
        ShopViewModel shopViewModel = new ViewModelProvider(this, factory).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setupRecyclerView();

        shopViewModel.getGameProfiles().observe(getViewLifecycleOwner(), gameProfiles -> {
            adapter.setGameProfiles(gameProfiles);
        });

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateActiveGameProfileDisplay();
    }
    private void setupRecyclerView() {
        adapter = new GameProfileAdapter(new GameProfileAdapter.GameProfileListener() {
            @Override
            public void onGameProfileSelected(GameProfile gameProfile) {
                // Update the TextView with the selected game profile's name
                binding.selectedGameProfileTextView.setText(getString(R.string.selected_game_profile, gameProfile.getTitle()));
            }
        });
        binding.saveSelectedProfileButton.setOnClickListener(v -> saveActiveGameProfile());
        binding.editSelectedProfileButton.setOnClickListener(v -> {
            new Thread(() -> {
                String selectedTitle = binding.selectedGameProfileTextView.getText().toString();
                // Remove prefix from the selectedTitle if necessary
                String prefix = "Selected Game Profile: ";
                if (selectedTitle.startsWith(prefix)) {
                    selectedTitle = selectedTitle.substring(prefix.length()).trim();
                }

                GameProfile selectedGameProfile = findGameProfileByTitle(selectedTitle);
                if (selectedGameProfile != null) {
                    getActivity().runOnUiThread(() -> {
                        Intent intent = new Intent(getContext(), EditGameProfile.class);
                        intent.putExtra("gameProfile", selectedGameProfile); // Ensure GameProfile is Serializable or Parcelable
                        startActivity(intent);
                    });
                }
            }).start();
        });
        binding.gameProfilesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.gameProfilesRecyclerView.setAdapter(adapter);
    }
    private GameProfile findGameProfileByTitle(String titleText) {
        FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(getContext());
        List<GameProfile> gameProfiles = db.gameProfileDao().getAll();
        for (GameProfile profile : gameProfiles) {
            if (profile.getTitle().equals(titleText)) {
                return profile;
            }
        }
        return null; // Return null if no matching GameProfile is found
    }
    private void saveActiveGameProfile() {
        new Thread(() -> {
            FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(getContext());
            List<GameProfile> gameProfiles = db.gameProfileDao().getAll();
            String selectedTitle = binding.selectedGameProfileTextView.getText().toString();

            // Remove prefix from the selectedTitle if necessary
            String prefix = "Selected Game Profile: ";
            if (selectedTitle.startsWith(prefix)) {
                selectedTitle = selectedTitle.substring(prefix.length()).trim();
            }


            GameProfile activeGameProfile = findGameProfileByTitle(selectedTitle);
            // Set isActive to false for all profiles and find the active profile
            for (GameProfile profile : gameProfiles) {
                if (profile.getTitle().equals(selectedTitle)) {
                    activeGameProfile = profile; // This is the profile to be activated
                }
                profile.setActive(false);
            }

            if (activeGameProfile != null) {
                activeGameProfile.setActive(true); // Activate the selected profile
                db.gameProfileDao().update(activeGameProfile); // Update the active profile immediately
            }

            // Update the rest of the profiles
            for (GameProfile profile : gameProfiles) {
                if (!profile.equals(activeGameProfile)) { // Avoid updating the active profile again
                    db.gameProfileDao().update(profile);
                }
            }

            // Optionally, refresh UI here if necessary
            updateActiveGameProfileDisplay();
        }).start();
    }

    private void updateActiveGameProfileDisplay() {
        new Thread(() -> {
            FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(getContext());
            GameProfile activeGameProfile = db.gameProfileDao().getActiveGameProfile(true); // Assuming there's a method getActiveGameProfile(boolean isActive) in your DAO
            if (activeGameProfile != null) {
                getActivity().runOnUiThread(() -> {
                    binding.activeGameProfileTextView.setText("Selected Game Profile: "+ activeGameProfile.getTitle());
                });
            }
        }).start();
    }
    private void startEditGameProfileActivity(GameProfile gameProfile) {
        Intent intent = new Intent(getContext(), EditGameProfile.class);
        intent.putExtra("gameProfile", gameProfile); // Ensure GameProfile is Serializable or Parcelable
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}