package co.edu.unal.androidtic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AndroidTicTacToe extends AppCompatActivity {
    // Buttons making up the board
    private Toast toast;
    private int selected;
    private boolean endgame=false;
    private TextView mInfoTextView,pcScore,human,empate;
    private TicTacToeGame mGame;
    private boolean GameOver;
    private boolean humanStarts;
    private Integer humanSc=0,androidScore=0,empateScore=0;
    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    private BoardView mBoardView;

    private SharedPreferences mPrefs;

    MediaPlayer mHumanMediaPlayer;
    MediaPlayer mComputerMediaPlayer;

    private void displayScores() {
        human.setText(Integer.toString(humanSc));
        pcScore.setText(Integer.toString(androidScore));
        empate.setText(Integer.toString(empateScore));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        mBoardView.setOnTouchListener(mTouchListener);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Reset:
                resetValues();
                return true;
            case R.id.new_game:
                startNewGame();
                endgame=false;
                return true;
            case R.id.ai_difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            /*case R.id.quit:
                showDialog(DIALOG_QUIT_ID);
                return true;*/
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHumanMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.jump);
        mComputerMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pacman);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mHumanMediaPlayer.release();
        mComputerMediaPlayer.release();
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
                        getResources().getString(R.string.difficulty_expert)};
// TODO: Set selected, an integer (0 to n-1), for the Difficulty dialog.
// selected is the radio button that should be selected.
                builder.setSingleChoiceItems(levels, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss(); // Close dialog
// TODO: Set the diff level of mGame based on which item was selected.
// Display the selected difficulty level
                                switch (item){
                                    case 0:

                                        mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Easy);
                                        break;

                                    case 1:
                                        mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Harder);
                                        break;

                                    case 2:
                                        mGame.setmDifficultyLevel(TicTacToeGame.DifficultyLevel.Expert);
                                        break;
                                }

                                Toast.makeText(getApplicationContext(), levels[item], Toast.LENGTH_SHORT).show();

                            }
                        });
                dialog = builder.create();
                break;

            /*case DIALOG_QUIT_ID:
// Create the quit confirmation dialog
                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AndroidTicTacToe.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();
                break;*/
        }
        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mGame= new TicTacToeGame();
        setContentView(R.layout.activity_main);
        mBoardView = (BoardView) findViewById(R.id.board);
        mBoardView.setGame(mGame);
        mBoardView.setOnTouchListener(mTouchListener);
        mInfoTextView = (TextView) findViewById(R.id.information);
        human = (TextView) findViewById(R.id.humanScore);
        pcScore = (TextView) findViewById(R.id.pcScore);
        empate = (TextView) findViewById(R.id.empate);
        human.setText(humanSc.toString());
        pcScore.setText(androidScore.toString());
        empate.setText(empateScore.toString());
        humanStarts=true;
        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
        selected = 0;
        mPrefs = getSharedPreferences("ttt_prefs", MODE_PRIVATE);

        humanSc = mPrefs.getInt("mHumanWins", 0);
        androidScore = mPrefs.getInt("mComputerWins", 0);
        empateScore = mPrefs.getInt("mTies", 0);
        if (savedInstanceState == null) {
            startNewGame();
        }
        else {
            onRestoreInstanceState(savedInstanceState);
        }
        displayScores();
    }

    @Override
    protected void onStop() {
        super.onStop();
// Save the current scores
        SharedPreferences.Editor ed = mPrefs.edit();
        ed.putInt("mHumanWins", humanSc);
        ed.putInt("mComputerWins", androidScore);
        ed.putInt("mTies", empateScore);
        ed.commit();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mGame.setBoardState(savedInstanceState.getCharArray("board"));
        GameOver = savedInstanceState.getBoolean("mGameOver");
        mInfoTextView.setText(savedInstanceState.getCharSequence("info"));
        humanSc = savedInstanceState.getInt("mHumanWins");
        androidScore = savedInstanceState.getInt("mComputerWins");
        empateScore = savedInstanceState.getInt("mTies");
        humanStarts = savedInstanceState.getBoolean("turn");
    }

    private void startNewGame() {
        mGame.clearBoard();
        mBoardView.invalidate();
        this.GameOver = false;
        if(humanStarts) {
            mInfoTextView.setText("La Persona Empieza.");
            humanStarts=!humanStarts;
        }
        else {
            mInfoTextView.setText("Android Empieza.");
            int move = mGame.getComputerMove();
            setMove(TicTacToeGame.COMPUTER_PLAYER, move);
            humanStarts=!humanStarts;
        }

    }

    private void resetValues(){
        humanSc = 0;
        androidScore = 0;
        empateScore = 0;
        startNewGame();
        displayScores();
    }

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int col=(int) motionEvent.getX()/mBoardView.getBoardCellWidth();
            int row=(int) motionEvent.getY()/mBoardView.getBoardCellHeight();
            int pos=row*3+col;
            if(!GameOver && setMove(TicTacToeGame.HUMAN_PLAYER,pos)){
                int winner = mGame.checkForWinner();
                if (winner == 0 ) {
                    int move = mGame.getComputerMove();
                    setMove(TicTacToeGame.COMPUTER_PLAYER, move);
                    mComputerMediaPlayer.start();
                    mInfoTextView.setText("Turno de Android.");
                    winner = mGame.checkForWinner();
                }
                if (winner == 0) {
                    mInfoTextView.setText("Turno de Persona.");
                    mHumanMediaPlayer.start();
                }
                else if (winner == 1){
                    mInfoTextView.setText("Es un Empate!");
                    GameOver=true;
                    empateScore++;
                    empate.setText(empateScore.toString());
                    endgame=true;
                }else if (winner == 2) {
                    mInfoTextView.setText("La Persona Gana!");
                    GameOver = true;
                    humanSc++;
                    human.setText(humanSc.toString());
                    endgame=true;
                }else {
                    mInfoTextView.setText("Android Gana!");
                    GameOver = true;
                    androidScore++;
                    pcScore.setText(androidScore.toString());
                    endgame=true;
                }
            }
            return false;
        }
    };

    private boolean setMove(char player, int location) {
        if (mGame.setMove(player, location)) {
            mBoardView.invalidate(); // Redraw the board
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharArray("board", mGame.getBoardState());
        outState.putBoolean("mGameOver", GameOver);
        outState.putInt("mHumanWins", Integer.valueOf(humanSc));
        outState.putInt("mComputerWins", Integer.valueOf(androidScore));
        outState.putInt("mTies", Integer.valueOf(empateScore));
        outState.putCharSequence("info", mInfoTextView.getText());
        outState.putBoolean("turn", humanStarts);
    }
}
