package com.example.prm392_taixiufbt.ui.sport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_taixiufbt.databinding.FragmentSportBinding;

public class SportFragment extends Fragment {

    private FragmentSportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SportViewModel dashboardViewModel =
                new ViewModelProvider(this).get(SportViewModel.class);

        binding = FragmentSportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSport;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}