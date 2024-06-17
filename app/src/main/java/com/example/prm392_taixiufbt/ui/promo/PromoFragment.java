package com.example.prm392_taixiufbt.ui.promo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_taixiufbt.databinding.FragmentPromoBinding;

public class PromoFragment extends Fragment {

    private FragmentPromoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PromoViewModel PromoViewModel =
                new ViewModelProvider(this).get(PromoViewModel.class);

        binding = FragmentPromoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPromo;
        PromoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}