package com.example.mathquizgame.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mathquizgame.R;
import com.example.mathquizgame.databinding.FragmentHomeBinding;
import com.example.mathquizgame.databinding.GameModeBoxBinding;
import com.example.mathquizgame.databinding.ProfileViewBoxBinding;

import java.util.Objects;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);

        IsTimeOpen();

        binding.AddBtu.setOnClickListener(view->{
            GameMode("add");
        });
        binding.subtract.setOnClickListener(view->{
            GameMode("subtract");
        });
        binding.MultiplyBtu.setOnClickListener(view->{
            GameMode("multiply");
        });

        binding.divideBtu.setOnClickListener(view->{
            GameMode("divide");
        });

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void IsTimeOpen() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("IsOpenBefore","").equals("yes")) {
//            startActivity(new Intent(this, MainActivity.class));
            ProfileViewBoxBinding profileViewBoxBinding;
            profileViewBoxBinding = ProfileViewBoxBinding.inflate(getLayoutInflater());
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(profileViewBoxBinding.getRoot());
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_box);
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

            profileViewBoxBinding.save.setOnClickListener(view -> {
                String name = Objects.requireNonNull(profileViewBoxBinding.name.getEditText()).getText().toString().trim();
                String ageString = Objects.requireNonNull(profileViewBoxBinding.age.getEditText()).getText().toString().trim();

                if (name.isEmpty() || ageString.isEmpty()) {
                    profileViewBoxBinding.error.setText("Check the input fields");
                    profileViewBoxBinding.error.setVisibility(View.VISIBLE);
                } else {
                    try {
                        int age = Integer.parseInt(ageString);

                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("name", name);
                        editor.putInt("age", age);
                        editor.putString("IsOpenBefore","yes");
                        editor.apply();

                        // Optional: Display a message indicating successful save
                        Toast.makeText(requireContext(), "Profile saved successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } catch (NumberFormatException e) {
                        // Handle the case when ageString is not a valid integer
                        profileViewBoxBinding.error.setText("Age must be a valid number");
                        profileViewBoxBinding.error.setVisibility(View.VISIBLE);
                    }
                }
            });

            dialog.show();

        }
    }

    @SuppressLint("SetTextI18n")
    private void GameMode(String GameType){
        GameModeBoxBinding gameModeBoxBinding;
        gameModeBoxBinding = GameModeBoxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(gameModeBoxBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_box);
        dialog.setCancelable(true);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();

        gameModeBoxBinding.close.setOnClickListener(view-> dialog.dismiss());
        gameModeBoxBinding.save.setOnClickListener(view -> {
            String numberOfRoundsStr = Objects.requireNonNull(gameModeBoxBinding.numberOfRoundsEditText.getText()).toString().trim();
            String numberOfWrongStr = Objects.requireNonNull(gameModeBoxBinding.numberOfWrongEditText.getText()).toString().trim();

            try {
                int numberOfRounds = Integer.parseInt(numberOfRoundsStr);
                int numberOfWrong = Integer.parseInt(numberOfWrongStr);

                // Find the selected radio button within the radio group
                RadioButton selectedRadioButton = dialog.findViewById(gameModeBoxBinding.radioGroup.getCheckedRadioButtonId());
                String selectedMode = selectedRadioButton.getText().toString();

                // Check if the parsed integers are valid
                if (numberOfRounds > 0 && numberOfWrong < numberOfRounds) {
                    // Input is valid, proceed with saving
                    Bundle bundle = new Bundle();
                    bundle.putInt("GAME_ROUND", numberOfRounds);
                    bundle.putInt("GAME_WRONG", numberOfWrong);
                    bundle.putString("GAME_TYPE", GameType);
                    bundle.putString("GAME_MODE", selectedMode);
                    Log.i("bundleInfo", "GameMode: "+bundle);
                    NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
                    assert navHostFragment != null;
                    NavController navController = navHostFragment.getNavController();
                    // Navigate to the destination with the provided bundle
                    navController.navigate(R.id.action_homeFragment_to_gameScreenFragment, bundle);
                    // Do something with the bundle
                    dialog.dismiss();
                } else {
                    // Invalid input, show an error message

                    gameModeBoxBinding.error.setVisibility(View.VISIBLE);
                    if (numberOfRounds <= 0) {
                        gameModeBoxBinding.error.setText("Please enter positive integers");
                    } else {
                        gameModeBoxBinding.error.setText("Number of wrong guesses must be less than number of rounds");
                    }
                }
            } catch (NumberFormatException e) {
                // Input is not a valid integer, show an error message
                gameModeBoxBinding.error.setVisibility(View.VISIBLE);
                gameModeBoxBinding.error.setText("Please enter valid integers");
            }
        });
    }
}