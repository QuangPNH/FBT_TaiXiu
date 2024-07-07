package com.example.prm392_taixiufbt.ui.account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.models.QAItem;

import java.util.ArrayList;
import java.util.List;

public class SupportFragment extends Fragment {
    private final List<QAItem> qaItems = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_support, container, false);
        LinearLayout qaLinearLayout = root.findViewById(R.id.qaLinearLayout);

        // Example Q&A items
        qaItems.add(new QAItem("Question 1", "Answer 1"));
        qaItems.add(new QAItem("Question 2", "Answer 2"));
        // Add more items as needed

        for (QAItem item : qaItems) {
            View qaView = inflater.inflate(R.layout.qa_item, null); // Ensure you have a layout file `qa_item.xml`
            TextView questionTextView = qaView.findViewById(R.id.questionTextView);
            TextView answerTextView = qaView.findViewById(R.id.answerTextView);

            questionTextView.setText(item.getQuestion());
            answerTextView.setText(item.getAnswer());

            qaLinearLayout.addView(qaView);
        }
        root.findViewById(R.id.callSupportButton).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1234567890")); // Replace with actual support number
            startActivity(intent);
        });

        return root;
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
}