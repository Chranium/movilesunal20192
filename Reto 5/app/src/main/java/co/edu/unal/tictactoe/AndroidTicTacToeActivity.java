package co.edu.unal.tictactoe;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidTicTacToeActivity extends AppCompatActivity {

    // Represents the internal state of the game
    private TicTacToeGame mGame;
    // Buttons making up the board
    private Button mBoardButtons[];
    // Various text displayed
    private TextView mInfoTextView;

    private TextView mHumanScoreTextView;
    private TextView mComputerScoreTextView;
    private TextView mTieScoreTextView;

    private boolean mGameOver;

    // Whose turn to go first
    private char mTurn = TicTacToeGame.COMPUTER_PLAYER;

    private int mHumanWins = 0;
    private int mComputerWins = 0;
    private int mTies = 0;

    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_tic_tac_toe);

        mBoardButtons = new Button[TicTacToeGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);
        mInfoTextView = (TextView) findViewById(R.id.information);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mHumanScoreTextView = (TextView) findViewById(R.id.player_score);
        mComputerScoreTextView = (TextView) findViewById(R.id.computer_score);
        mTieScoreTextView = (TextView) findViewById(R.id.tie_score);

        mGame = new TicTacToeGame();

        startNewGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                startNewGame();
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }
        return false;
    }

    // Set up the game board.
    private void startNewGame() {
        mGameOver = false;
        mGame.clearBoard();
        // Reset all buttons
        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }// Human goes first

        // Alternate who goes first
        if (mTurn == TicTacToeGame.HUMAN_PLAYER) {
            mTurn = TicTacToeGame.COMPUTER_PLAYER;
            mInfoTextView.setText(R.string.first_computer);
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            mInfoTextView.setText(R.string.turn_human);
        } else {
            mTurn = TicTacToeGame.HUMAN_PLAYER;
            mInfoTextView.setText(R.string.first_human);
        }

    }

    private void setMove(char player, int location) {
        if (mGameOver != true) {
            mGame.setMove(player, location);
            mBoardButtons[location].setEnabled(false);
            mBoardButtons[location].setText(String.valueOf(player));
            if (player == TicTacToeGame.HUMAN_PLAYER)
                mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
            else
                mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }
    }

    private class ButtonClickListener implements View.OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (mBoardButtons[location].isEnabled()) {
                setMove(TicTacToeGame.HUMAN_PLAYER, location);
                // If no winner yet, let the computer make a move
                int winner = mGame.checkForWinner();
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_computer);
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    winner = mGame.checkForWinner();
                }
                if (winner == 0) {
                    mInfoTextView.setText(R.string.turn_human);
                } else if (winner == 1) {
                    mInfoTextView.setText(R.string.result_tie);
                    mGameOver = true;
                    mTies++;
                    mTieScoreTextView.setText(Integer.toString(mTies));
                } else if (winner == 2) {
                    mInfoTextView.setText(R.string.result_human_wins);
                    mGameOver = true;
                    mHumanWins++;
                    mHumanScoreTextView.setText(Integer.toString(mHumanWins));
                } else {
                    mInfoTextView.setText(R.string.result_computer_wins);
                    mGameOver = true;
                    mComputerWins++;
                    mComputerScoreTextView.setText(Integer
                            .toString(mComputerWins));
                }
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {
            case DIALOG_DIFFICULTY_ID:
                builder.setTitle(R.string.difficulty_choose);
                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert) };
                // TODO: Set selected, an integer (0 to n-1), for the Difficulty
                // dialog.
                int selected = 3;
                // selected is the radio button that should be selected.
                builder.setSingleChoiceItems(levels, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss(); // Close dialog
                                // TODO: Set the diff level of mGame based on which
                                // item was selected.
                                DifficultyLevel d;
                                if (item == 0) {
                                    d = mGame.getDifficultyLevel().Easy;
                                    mGame.setDifficultyLevel(d);
                                }
                                if (item == 1) {
                                    d = mGame.getDifficultyLevel().Harder;
                                    mGame.setDifficultyLevel(d);
                                }
                                if (item == 2) {
                                    d = mGame.getDifficultyLevel().Expert;
                                    mGame.setDifficultyLevel(d);
                                }
                                // Display the selected difficulty level
                                Toast.makeText(getApplicationContext(),
                                        levels[item], Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog = builder.create();
                break;
        }

        switch (id) {
            case DIALOG_QUIT_ID:
                // Create the quit confirmation dialog
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {
                                        AndroidTicTacToeActivity.this.finish();
                                    }
                                }).setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;
        }

        return dialog;
    }
}