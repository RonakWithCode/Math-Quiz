package com.example.mathquizgame.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mathquizgame.MainActivity;
import com.example.mathquizgame.R;
import com.example.mathquizgame.databinding.FragmentGameOverScreenBinding;


public class GameOverScreenFragment extends Fragment {

    private static final String WRONG = "WRONG";
    private static final String ROUND = "ROUND";

    FragmentGameOverScreenBinding binding;
    // TODO: Rename and change types of parameters
    private String getWrong;
    private String getRound;

    public GameOverScreenFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getRound = getArguments().getString(ROUND);
            getWrong = getArguments().getString(WRONG);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameOverScreenBinding.inflate(inflater,container,false);
//        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
//        assert navHostFragment != null;
//        NavController navController = navHostFragment.getNavController();
//        navController.navigate(R.id.action_gameScreenFragment_to_gameOverScreenFragment, bundle);

        binding.round.setText(getRound);
        binding.Wrong.setText(getWrong);
        binding.goHome.setOnClickListener(view->{
            requireActivity().finish();
            startActivity(new Intent(requireContext(), MainActivity.class));
        });
        binding.back.setOnClickListener(view->{
            requireActivity().finish();
            startActivity(new Intent(requireContext(), MainActivity.class));
        });
        binding.share.setOnClickListener(share->{
            // Create a share message with the total rounds, total wrong answers, and app download link
            String shareMessage = "Total Rounds: " + getRound + "\n" +
                    "Total Wrong Answers: " + getWrong + "\n" +
                    "Download our app: [https://crazy-studio-website.firebaseapp.com/]";

            // Create a share intent
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);

            // Start the activity chooser
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
        return binding.getRoot();
    }




}