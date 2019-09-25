package com.example.tic_tac_toe;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import java.util.Random;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Button buttons[][] = new Button[4][4];
    int gameTable[][] = new int[4][4];
    Boolean flagEndGame = false;
    Random r = new Random();

    private void checkMove() {
        // check rows
        for(int i = 1; i <= 3; i++){
            int countX = 0;
            int countO = 0;

            for(int j = 1; j <= 3; j++) {
                if(gameTable[i][j] == 1)
                    countX++;
                else if(gameTable[i][j] == 2)
                    countO++;
            }

            if(countX == 3 || countO == 3) {
                flagEndGame = true;
                break;
            }
        }

        // check cols
        if(!flagEndGame) {
            for (int i = 1; i <= 3; i++) {
                int countX = 0;
                int countO = 0;

                for (int j = 1; j <= 3; j++) {
                    if (gameTable[j][i] == 1)
                        countX++;
                    else if (gameTable[j][i] == 2)
                        countO++;
                }

                if (countX == 3 || countO == 3) {
                    flagEndGame = true;
                    break;
                }
            }
        }

        // check diagonal 1
        if(!flagEndGame) {
            int countX = 0;
            int countO = 0;

            for (int i = 1; i <= 3; i++) {


                if (gameTable[i][i] == 1)
                    countX++;
                else if (gameTable[i][i] == 2)
                    countO++;
            }

            if (countX == 3 || countO == 3)
                flagEndGame = true;
        }

        // check diagonal 2
        if(!flagEndGame) {
            int countX = 0;
            int countO = 0;

            int j = 3;
            for(int i = 1; i <= 3; i++) {
                if(gameTable[i][j] == 1)
                    countX++;
                else if(gameTable[i][j] == 2)
                    countO++;
                j--;
            }

            if(countX == 3 || countO == 3)
                flagEndGame = true;
        }

        if(!flagEndGame) {
            int count = 0;

            for (int i = 1; i <= 3; i++) {
                for (int j = 1; j <= 3; j++) {
                    if (gameTable[i][j] != 0)
                        count++;
                }
            }

            if (count == 9)
                flagEndGame = true;
        }
    }

    private void Tic_Tac_Toe(final Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button.getText().equals("") && !flagEndGame) {
                    button.setTextColor(Color.parseColor("#4CAF50"));
                    button.setText("O");

                    String buttonID = getResources().getResourceName(button.getId());
                    int rowPlayer = Integer.parseInt(buttonID.substring(buttonID.length() - 2, buttonID.length() - 1));
                    int colPlayer = Integer.parseInt(buttonID.substring(buttonID.length() - 1));
                    gameTable[rowPlayer][colPlayer] = 2;
                    checkMove();

                    // Machine
                    int rowMachine, colMachine;

                    if(!flagEndGame) {
                        while(true) {
                            rowMachine = r.nextInt(3) + 1;
                            colMachine = r.nextInt(3) + 1;

                            if (gameTable[rowMachine][colMachine] == 0) {
                                gameTable[rowMachine][colMachine] = 1;
                                buttons[rowMachine][colMachine].setTextColor(Color.parseColor("#F44336"));
                                buttons[rowMachine][colMachine].setText("X");
                                checkMove();
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        for(int i = 1; i <= 3; i++) {
            for(int j = 1; j <= 3; j++) {
                String buttonID = "box" + i + j;
                int ID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(ID);
                Tic_Tac_Toe(buttons[i][j]);
            }
        }

        Button btnClear = findViewById(R.id.clear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 1; i <= 3; i++) {
                    for(int j = 1; j <= 3; j++) {
                        buttons[i][j].setText("");
                        gameTable[i][j] = 0;
                        flagEndGame = false;
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
