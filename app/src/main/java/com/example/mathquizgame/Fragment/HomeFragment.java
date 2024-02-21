package com.example.mathquizgame.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mathquizgame.MainActivity;
import com.example.mathquizgame.R;
import com.example.mathquizgame.databinding.FragmentHomeBinding;
import com.example.mathquizgame.databinding.GameModeBoxBinding;
import com.example.mathquizgame.databinding.GameSettingBoxBinding;
import com.example.mathquizgame.databinding.ProfileViewBoxBinding;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Objects;



public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences SettingSharedPreferences;
    AdView adView;
    boolean ThisTouchSound;
    private RewardedAd rewardedAd;
    private Bundle bundle;
    private final String TAG = "MainActivity";
    private final String Rewarded = "ca-app-pub-7951692897729874/2369201326";
    private final String Banner = "ca-app-pub-7951692897729874/2950821104";


    NavHostFragment navHostFragment;
//    = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
    //     static Context context;
//    private static final String SERVER_URL = "ws://172.235.0.12:3000";
//    private OkHttpClient client;
//    private WebSocket webSocket;
//    private Map<String, Integer> userPoints = new HashMap<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
    }


    @SuppressLint({"SetTextI18n", "WrongConstant"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
        sharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE);
        SettingSharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        ThisTouchSound = SettingSharedPreferences.getBoolean("TouchSound", true);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment);
        IsTimeOpen();
        View rootView = requireActivity().getWindow().getDecorView().getRootView();
        setButtonSoundEffects(rootView, false);

        bundle = new Bundle();
        adView = new AdView(requireContext());
        adView.findViewById(R.id.adView);
        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId(Banner); // This is a test ad unit ID
        AdRequest adRequest = new AdRequest.Builder().build();

        adView.loadAd(adRequest);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Log.d("adsError", "onAdFailedToLoad: "+adError);
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });
        binding.AddBtu.setOnClickListener(view-> GameMode("add"));
        binding.subtract.setOnClickListener(view-> GameMode("subtract"));
        binding.MultiplyBtu.setOnClickListener(view-> GameMode("multiply"));
        binding.divideBtu.setOnClickListener(view-> GameMode("divide"));

//        binding.online.setOnClickListener(online->{
//            OnlineBoxBinding onlineBoxBinding;
//            onlineBoxBinding = OnlineBoxBinding.inflate(getLayoutInflater());
//            Dialog dialog = new Dialog(getContext());
//            dialog.setContentView(onlineBoxBinding.getRoot());
//            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_box);
//            dialog.setCancelable(true);
//            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//            dialog.show();
//            onlineBoxBinding.close.setOnClickListener(view-> dialog.dismiss());
//
//            onlineBoxBinding.save.setOnClickListener(view->{
//            if (Objects.requireNonNull(onlineBoxBinding.RoomIdInput.getText()).toString().isEmpty() )
//            {
//                onlineBoxBinding.error.setVisibility(View.VISIBLE);
//                onlineBoxBinding.error.setText("Please enter valid Room Id.");
//            }
////            else {
////                sendMessage(onlineBoxBinding.RoomId.toString());
////                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE);
////                String username =  sharedPreferences.getString("name","No name");
//////                if (!sharedPreferences.getString("IsOpenBefore","").equals("yes")) {
////                String userId = onlineBoxBinding.RoomIdInput.getText().toString();
////                connectWebSocket(userId,username);
////            }
//            });
//
//        });
        binding.user.setOnClickListener(view-> ProfileView());
        binding.setting.setOnClickListener(setting -> {
            GameSettingBoxBinding gameSettingBoxBinding = GameSettingBoxBinding.inflate(getLayoutInflater());
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(gameSettingBoxBinding.getRoot());
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_box);
            dialog.setCancelable(true);
            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.show();

            // Retrieve shared preferences

            // Retrieve values from shared preferences
            boolean music = SettingSharedPreferences.getBoolean("music", true);
            boolean touchSound = SettingSharedPreferences.getBoolean("TouchSound", true);
            int screenMode = SettingSharedPreferences.getInt("ScreenMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            Log.d("NightMode", "Retrieved screen mode: " + screenMode);

            // Set initial values in the dialog
            gameSettingBoxBinding.music.setChecked(music);
            gameSettingBoxBinding.TouchSound.setChecked(touchSound);
            if (screenMode == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) {
                gameSettingBoxBinding.ScreenMode.check(R.id.default_Mode);
            } else if (screenMode == AppCompatDelegate.MODE_NIGHT_NO) {
                gameSettingBoxBinding.ScreenMode.check(R.id.light);
            } else {
                gameSettingBoxBinding.ScreenMode.check(R.id.Dark);
            }
            // Close dialog when close button is clicked
            gameSettingBoxBinding.close.setOnClickListener(view -> dialog.dismiss());
            gameSettingBoxBinding.close1.setOnClickListener(view -> dialog.dismiss());

            // Save music setting when changed
            gameSettingBoxBinding.music.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SharedPreferences.Editor editor = SettingSharedPreferences.edit();
                editor.putBoolean("music", isChecked);
                editor.apply();
            });

            // Save touch sound setting when changed
            gameSettingBoxBinding.TouchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SharedPreferences.Editor editor = SettingSharedPreferences.edit();
                editor.putBoolean("TouchSound", isChecked);
                editor.apply();
            });

            // Save screen mode setting when changed
            gameSettingBoxBinding.ScreenMode.setOnCheckedChangeListener((group, checkedId) -> {
                SharedPreferences.Editor editor = SettingSharedPreferences.edit();
                int newScreenMode;
                if (checkedId == gameSettingBoxBinding.light.getId()) {

                    newScreenMode = AppCompatDelegate.MODE_NIGHT_NO;
                } else if (checkedId == gameSettingBoxBinding.Dark.getId()) {
                    newScreenMode = AppCompatDelegate.MODE_NIGHT_YES;
                } else {
                    newScreenMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
                }
                editor.putInt("ScreenMode", newScreenMode);
                editor.apply();
                AppCompatDelegate.setDefaultNightMode(newScreenMode);

            });
        });


        return binding.getRoot();
    }










    private void setButtonSoundEffects(View view, boolean touchSound) {
        if (view instanceof Button) {
            view.setSoundEffectsEnabled(touchSound);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                setButtonSoundEffects(viewGroup.getChildAt(i), touchSound);
            }
        }
    }

//    private void connectWebSocket(String roomId,String userId) {
//        Request request = new Request.Builder().url(SERVER_URL+ "/" + roomId+"/"+userId).build();
//        WebSocketListener socketListener = new WebSocketListener() {
//            @Override
//            public void onOpen(@NotNull WebSocket webSockets, @NonNull okhttp3.Response response) {
//                super.onOpen(webSockets, response);
//                // WebSocket connection is established, you can implement further actions here
//                webSocket = webSockets;
//            }
//
//            @Override
//            public void onMessage(@NotNull WebSocket webSockets, @NotNull String text) {
//                super.onMessage(webSockets, text);
//                // Handle incoming messages from the server
//                processMessage(text);
//            }
//
//            @Override
//            public void onClosed(@NotNull WebSocket webSockets, int code, @NotNull String reason) {
//                super.onClosed(webSockets, code, reason);
//                // WebSocket connection is closed
//                webSocket = null;
//            }
//
//            @Override
//            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, okhttp3.Response response) {
//                super.onFailure(webSocket, t, response);
//                // Handle WebSocket connection failure
//                Toast.makeText(requireContext(), "WebSocket connection failed!", Toast.LENGTH_SHORT).show();
//            }
//        };
//        webSocket = client.newWebSocket(request, socketListener);
//
//    }
//    private void processMessage(String message) {
//        // Process incoming message from the server
//        Log.i("processMessage", "processMessage: "+message);
//    }
//
//    private void sendMessage(String message) {
//        if (webSocket != null) {
//            webSocket.send(message);
//        } else {
//            Toast.makeText(requireContext(), "WebSocket connection is not established!", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void updateUserPoints(String userId, int points) {
//        userPoints.put(userId, points);
//        // Update UI or perform other actions based on updated user points
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (webSocket != null) {
//            webSocket.close(1000, null);
//        }
//    }

    @SuppressLint("SetTextI18n")
    private void IsTimeOpen() {
        if (!sharedPreferences.getString("IsOpenBefore","").equals("yes")) {
            SharedPreferences SettingSharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE);
            SharedPreferences.Editor SettingEditor = SettingSharedPreferences.edit();
            SettingEditor.putInt("ScreenMode",AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM); // is default
            SettingEditor.putBoolean("TouchSound",true); // is TouchSound default
            SettingEditor.putBoolean("music",true); // is music default
            SettingEditor.apply();
            ProfileView();
        }
    }

    @SuppressLint("SetTextI18n")
    private void ProfileView() {
        ProfileViewBoxBinding profileViewBoxBinding;
        profileViewBoxBinding = ProfileViewBoxBinding.inflate(getLayoutInflater());
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(profileViewBoxBinding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.round_dialog_box);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;


        if (sharedPreferences.getString("IsOpenBefore","").equals("yes")) {
            String name = sharedPreferences.getString("name","");
            int age = sharedPreferences.getInt("age",0);
            Objects.requireNonNull(profileViewBoxBinding.name.getEditText()).setText(name);
            Objects.requireNonNull(profileViewBoxBinding.age.getEditText()).setText(String.valueOf(age));
        }
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
                    bundle.putInt("GAME_ROUND", numberOfRounds);
                    bundle.putInt("GAME_WRONG", numberOfWrong);
                    bundle.putString("GAME_TYPE", GameType);
                    bundle.putString("GAME_MODE", selectedMode);
                    Log.i("bundleInfo", "GameMode: "+bundle);
                    AdRequest adRequest = new AdRequest.Builder().build();
                    RewardedAd.load(requireContext(), Rewarded,
                            adRequest, new RewardedAdLoadCallback() {
                                @Override
                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                    // Handle the error.
                                    Log.d(TAG, loadAdError.toString());
                                    Toast.makeText(requireContext(), "Offline Mode", Toast.LENGTH_SHORT).show();
                                    rewardedAd = null;
                                    navigateToGameScreen();
                                }

                                @Override
                                public void onAdLoaded(@NonNull RewardedAd ad) {
                                    rewardedAd = ad;
                                    showRewardedAd();
                                    Log.d(TAG, "Ad was loaded.");
                                }
                            });
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
    private void showRewardedAd() {
        if (rewardedAd != null) {
            rewardedAd.show(requireActivity(), rewardItem -> {
                // User earned reward, navigate to the game screen
                navigateToGameScreen();
            });
        } else {
            // Rewarded ad is not loaded, navigate to the game screen
            navigateToGameScreen();
        }
    }

    private void navigateToGameScreen() {
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        // Navigate to the destination with the provided bundle
        navController.navigate(R.id.action_homeFragment_to_gameScreenFragment, bundle);
    }

}