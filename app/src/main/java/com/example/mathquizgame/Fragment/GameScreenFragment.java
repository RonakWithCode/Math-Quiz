package com.example.mathquizgame.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.mathquizgame.classes.MathQuestionGenerator;
import com.example.mathquizgame.databinding.FragmentGameScreenBinding;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

//
//<LinearLayout
//        android:id="@+id/LinearImage"
//                android:layout_below="@id/error"
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content">
//
//
//<RadioGroup
//        android:padding="@dimen/_2sdp"
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content"
//                android:gravity="center_horizontal"
//                android:orientation="horizontal">
//
//<RadioButton
//            android:id="@+id/Layout1"
//                    android:layout_width="0dp"
//                    android:layout_height="wrap_content"
//                    android:layout_marginStart="@dimen/_5sdp"
//                    android:layout_marginEnd="@dimen/_5sdp"
//                    android:padding="@dimen/_8sdp"
//                    android:text="Input"
//                    android:layout_weight="1"
//                    android:background="@null"
//                    android:button="@android:color/transparent"
//                    android:drawableTop="@drawable/add_icon"
//                    android:textAlignment="center"
//                    android:textSize="14sp"/>
//<Space
//            android:layout_width="@dimen/_10sdp"
//                    android:layout_height="match_parent" />
//<RadioButton
//            android:id="@+id/Layout2"
//                    android:layout_width="0dp"
//                    android:layout_height="wrap_content"
//                    android:layout_marginStart="@dimen/_5sdp"
//                    android:layout_marginEnd="@dimen/_5sdp"
//                    android:background="@null"
//                    android:button="@android:color/transparent"
//                    android:layout_weight="1"
//                    android:drawableTop="@drawable/subtract_icon"            android:padding="@dimen/_8sdp"
//                    android:text="MCQ"
//                    android:textAlignment="center"
//                    android:textSize="14sp"/>
//
//</RadioGroup>
//</LinearLayout>
public class GameScreenFragment extends Fragment {
    FragmentGameScreenBinding binding;
    private static final String GAME_TYPE = "GAME_TYPE";
    private static final String GAME_MODE = "GAME_MODE";
    private static final String GAME_WRONG = "GAME_WRONG";
    private static final String GAME_ROUND = "GAME_ROUND";
    private String getGameType;
    private String getGameMode;
    private int getGameWrong;
    private int getGameRound;
    MathQuestionGenerator questionGenerator;
    List<MathQuestionGenerator.MathQuestion> mathQuestions;
    AtomicInteger CorrectRound;
    int WrongAnswer;

    public GameScreenFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            getGameMode = getArguments().getString(GAME_MODE);
            getGameType = getArguments().getString(GAME_TYPE);
            getGameWrong = getArguments().getInt(GAME_WRONG);
            getGameRound = getArguments().getInt(GAME_ROUND);

        }else {
            Toast.makeText(requireContext(), "provide the game settings", Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGameScreenBinding.inflate(inflater,container,false);

        init();


        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void init(){
        binding.numberOfWrong.setText("0/"+getGameWrong);
        binding.rounded.setText("0/"+getGameRound);
        CorrectRound = new AtomicInteger();
//        WrongAnswer = new AtomicInteger(1);

        questionGenerator = new MathQuestionGenerator();
        mathQuestions = questionGenerator.generateMathQuestions(getGameType, getGameMode, getGameRound);
        NextQuestion();
    }

    @SuppressLint("SetTextI18n")
    private void NextQuestion() {
        if (CorrectRound.get()>=getGameRound){
            Toast.makeText(requireContext(), "Done", Toast.LENGTH_SHORT).show();
        }
        else {
            int CurrentRound = CorrectRound.get()+1;
            binding.rounded.setText(CurrentRound+"/"+getGameRound);
            MathQuestionGenerator.MathQuestion mathQuestion = mathQuestions.get(CorrectRound.get());
// Format the math question string
            @SuppressLint("DefaultLocale")
            String questionText = String.format("%d %s %d", mathQuestion.getOperand1(), getOperatorString(), mathQuestion.getOperand2());
            int CorrectAnswer = mathQuestion.getCorrectAnswer();
            binding.Ques.setText(questionText);
            Random random = new Random();
            // Generate a random boolean value
            boolean randomBoolean = random.nextBoolean();
            if (randomBoolean){
                binding.ans1.setText(String.valueOf(mathQuestion.getCorrectAnswer())); // Assuming ans1 is the correct answer
                binding.ans2.setText(String.valueOf(mathQuestion.getFalseAnswer()));   // Assuming ans2 is the false answer
            }else {
                binding.ans2.setText(String.valueOf(mathQuestion.getCorrectAnswer())); // Assuming ans1 is the correct answer
                binding.ans1.setText(String.valueOf(mathQuestion.getFalseAnswer()));   // Assu
            }
            binding.ans1.setOnClickListener(Ans -> {
                int ansBtn = Integer.parseInt(binding.ans1.getText().toString());
                if (ansBtn == CorrectAnswer) {
                    CorrectRound.set(CorrectRound.get() + 1);
                    NextQuestion();
                }else {
                    WrongAnswer++;
                    if (getGameWrong>=WrongAnswer){
                        binding.numberOfWrong.setText(WrongAnswer+"/"+getGameWrong);
                        CorrectRound.set(CorrectRound.get() + 1);
                        NextQuestion();
                    }else {
                        Toast.makeText(requireContext(), "Wrong linet", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            binding.ans2.setOnClickListener(Ans2 -> {
                int ansBtn = Integer.parseInt(binding.ans2.getText().toString());
                if (ansBtn == CorrectAnswer) {
                    CorrectRound.set(CorrectRound.get() + 1);
                    NextQuestion();
                }else {
                    WrongAnswer++;
                    if (getGameWrong>=WrongAnswer){
                        binding.numberOfWrong.setText(WrongAnswer+"/"+getGameWrong);
                        CorrectRound.set(CorrectRound.get() + 1);
                        NextQuestion();
                    }else {
                        Toast.makeText(requireContext(), "Wrong linet", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private String getOperatorString() {
        switch (getGameType) {
            case "add":
                return "+";
            case "subtract":
                return "-";
            case "multiply":
                return "×";
            case "divide":
                return "÷";
            default:
                // Default to addition if game type is not recognized
                return "";
        }
    }
}