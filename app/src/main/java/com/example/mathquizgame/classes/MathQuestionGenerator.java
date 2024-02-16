package com.example.mathquizgame.classes;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MathQuestionGenerator {

    // Define math operations
    private enum MathOperation {
        ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION
    }

    // Method to generate math questions
    public List<MathQuestion> generateMathQuestions(String gameType, String gameMode, int numberOfQuestions) {
        List<MathQuestion> mathQuestions = new ArrayList<>();

        for (int i = 0; i < numberOfQuestions; i++) {
            // Determine math operation based on game type
            MathOperation mathOperation = getMathOperation(gameType);

            // Generate random operands based on game mode
            int operand1 = generateOperand(gameMode);
            int operand2 = generateOperand(gameMode);

            // Calculate correct answer
            int correctAnswer = calculateAnswer(mathOperation, operand1, operand2);

            // Generate false answer (different from correct answer)
            int falseAnswer = generateFalseAnswer(correctAnswer);

            // Create MathQuestion object and add to list
            MathQuestion mathQuestion = new MathQuestion(operand1, operand2, mathOperation, correctAnswer, falseAnswer);
            mathQuestions.add(mathQuestion);
        }

        return mathQuestions;
    }

    // Method to determine math operation based on game type
    private MathOperation getMathOperation(String gameType) {
        switch (gameType) {
            case "add":
                return MathOperation.ADDITION;
            case "subtract":
                return MathOperation.SUBTRACTION;
            case "multiply":
                return MathOperation.MULTIPLICATION;
            case "divide":
                return MathOperation.DIVISION;
            default:
                // Default to addition if game type is not recognized
                return MathOperation.ADDITION;
        }
    }

    // Method to generate random operand based on game mode
    private int generateOperand(String gameMode) {
        Random random = new Random();

        // Adjust operand range based on game mode
        switch (gameMode) {
            case "Easy Mode":
//                Log.i("MathQuestion ","Easy Mode");
                return random.nextInt(20) + 1; // Generate numbers from 1 to 10 for easy mode
            case "Hard Mode":
//                Log.i("MathQuestion ","Hard Mode");

                return random.nextInt(100) + 1; // Generate numbers from 1 to 100 for hard mode
            default:
//                Log.i("MathQuestion ","normal Mode");
                return random.nextInt(30) + 1; // Generate numbers from 1 to 100 for hard mode
        }
    }

    // Method to calculate correct answer based on math operation and operands
    private int calculateAnswer(MathOperation operation, int operand1, int operand2) {
        switch (operation) {
            case ADDITION:
                return operand1 + operand2;
            case SUBTRACTION:
                return operand1 - operand2;
            case MULTIPLICATION:
                return operand1 * operand2;
            case DIVISION:
                return operand1 / operand2; // Assumes integer division for simplicity
            default:
                return 0; // Default to 0 if operation is not recognized
        }
    }

    // Method to generate false answer (different from correct answer)
    private int generateFalseAnswer(int correctAnswer) {
        Random random = new Random();
        int falseAnswer = correctAnswer;
        while (falseAnswer == correctAnswer) {
            falseAnswer = correctAnswer + random.nextInt(5) - 2; // Generate a number close to the correct answer
        }
        return falseAnswer;
    }

    // Inner class to represent a math question
    public static class MathQuestion {
        private final int operand1;
        private final int operand2;
        private final MathOperation operation;
        private final int correctAnswer;
        private final int falseAnswer;

        public MathQuestion(int operand1, int operand2, MathOperation operation, int correctAnswer, int falseAnswer) {
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.operation = operation;
            this.correctAnswer = correctAnswer;
            this.falseAnswer = falseAnswer;
        }

        public int getOperand1() {
            return operand1;
        }

        public int getOperand2() {
            return operand2;
        }

        public MathOperation getOperation() {
            return operation;
        }

        public int getCorrectAnswer() {
            return correctAnswer;
        }

        public int getFalseAnswer() {
            return falseAnswer;
        }
// Getter methods for the question attributes
        // Add getter methods as needed
    }
}
