package com.example.android.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 *  This app is s simple quiz having 5 questions.   The quesitons are displayed
 *  on a single scrollable screen with user input via radioButton, EditText, or
 *  checkBox views.   The app will tell the user how they scored using a Toast.
 */
public class MainActivity extends AppCompatActivity {

    String [] answers = new String[5]; // answers to questions
//    boolean[] answeredCorrect = {false, false, false, false, false}; // tracks if each answer is correct

    int [] question4CheckBoxIds = new int[4]; // ids for question 4 checkBoxes
    boolean [] answers4 ; // correct status for question 4 checkBoxes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize answers[] from resource strings
        answers[0] = getString(R.string.answer0);
        answers[1] = getString(R.string.answer1);
        answers[2] = getString(R.string.answer2);
        answers[3] = getString(R.string.answer3);
        answers[4] = getString(R.string.answer4);

        // question 4 has four checkboxes and the array answers4[] contains
        // the correct status of each checkbox
        answers4 = stringToBooleanArray(answers[4]);

        // the ids of the question 4 checkboxes are placed in array
        // question4CheckBoxIds[] so the answer can be checked using
        // a for loop
        question4CheckBoxIds[0] = R.id.question4_cbox1;
        question4CheckBoxIds[1] = R.id.question4_cbox2;
        question4CheckBoxIds[2] = R.id.question4_cbox3;
        question4CheckBoxIds[3] = R.id.question4_cbox4;
    }

    /**
     * check answers, calculate the total correct answers and score
     * post a message to the display with the results
     * note: only questions 2 and 4 are checked here.  The other questions use
     * radio buttons and are checked when a radio button is selected.
     *
     * @param view  required argument
     */
    public void submitAnswers(View view) {
        boolean[] answeredCorrect = {false, false, false, false, false};
        int selectedButtonId;
        // check the answer to question 0
        // note added else statement to meet project requirements but is redundant since
        // answeredCorrect is initialized to false
        selectedButtonId = ((RadioGroup) findViewById(R.id.question0_radioGroup)).getCheckedRadioButtonId();
        if(selectedButtonId != -1) {
            answeredCorrect[0] = ((RadioButton) findViewById(selectedButtonId)).getText().equals(answers[0]);
        } else {
            answeredCorrect[0] = false;
        }
        // check the answer to question 1
        selectedButtonId = ((RadioGroup) findViewById(R.id.question1_radioGroup)).getCheckedRadioButtonId();
        if(selectedButtonId != -1){
            answeredCorrect[1] = ((RadioButton) findViewById(selectedButtonId)).getText().equals(answers[1]);
        }
        // check the answer to question 2
        String question2Response = ((EditText) findViewById(R.id.question2_edittext)).getText().toString().trim().toLowerCase();
        answeredCorrect[2] = question2Response.equals(answers[2].toLowerCase());
        // check the answer to question 3
        selectedButtonId = ((RadioGroup) findViewById(R.id.question3_radioGroup)).getCheckedRadioButtonId();
        if(selectedButtonId != -1){
            answeredCorrect[3] = ((RadioButton) findViewById(selectedButtonId)).getText().equals(answers[3]);
        }
        // check the answer to question 4
        answeredCorrect[4] = checkQuestion4();
        // calculate the total number of correct answers and a corresponding score out of 100
        int totalCorrectAnswers = 0;
        for (int i = 0; i < answers.length; i++) {
            if (answeredCorrect[i]) {
                totalCorrectAnswers++;
            }
        }
        double score = 100 * totalCorrectAnswers / answers.length;
        // create a string messaage with the results and display using Toast
        DecimalFormat df = new DecimalFormat("####");
        String message = getString(R.string.result_message,totalCorrectAnswers,answers.length,df.format(score));
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Checks if question 4 has been answered correctly and
     * returns a boolean: True if correct; False if incorrect.
     * Question 4 has 4 checkboxes and this method checks if the
     * correct ones have been selected.  Two public arrays are used:
     * question4CheckBoxIds[] holds the ids of the checkboxes
     * answers4[] holds the isChecked{} status for a correct answer.
     *
     * @return a boolean that is true if the quesion is correcly answered
     */
    private boolean checkQuestion4(){

        for (int i = 0; i < answers4.length ; i++){

            if(((CheckBox) findViewById(question4CheckBoxIds[i])).isChecked() != answers4[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a boolean array that reflects the contents of the input string.
     * This array will thereafter be used as the answer key for a question
     * that has multiple checkboxes.
     *
     * @param   str a string containing the words "true" and "false" one or more times separated by spaces
     * @return      a boolean array derived from the input string.
     */
    private boolean[] stringToBooleanArray(String str){
        String[] parts = str.split(" ");
        boolean[] array = new boolean[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Boolean.parseBoolean(parts[i]);
        }
        return array;
    }

}
