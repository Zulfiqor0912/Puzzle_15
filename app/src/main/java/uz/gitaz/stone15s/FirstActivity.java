package uz.gitaz.stone15s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    private Music music;
    private AppCompatButton[][] buttons;
    private TextView stepCounter;
    private TextView record1;
    private Coordinate emptyCoordinate;
    private List<Integer> numbers;
    private Local_Storage local_storage;
    private MediaPlayer buttonSound;
    private int count;
    private RelativeLayout containerButtons;
    private int record;
    private TextView timerText;
    private CustomDialog dialog;
    private ConstraintLayout btnPause;
    private AppCompatButton winNewGameBtn;
    private AppCompatButton winQuitBtn;
    private Chronometer time;
    private AppCompatImageView btnBefore;
    private boolean firstClock;
    private boolean booleanTime;
    private boolean shuffleClock;

    private boolean winDialog;
    private boolean pauseDialog;

    private boolean booleanSound;
    private long timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d("TTT", "onCreate");
        initViews();
        if (savedInstanceState != null) count = savedInstanceState.getInt("COUNT");
        initNumbers();
        setNumbersToButton();
        findViewById(R.id.shuffle).setOnClickListener(view -> onShuffleClick());
        btnPause.setOnClickListener(view -> {
            local_storage.setPauseDialog(pauseDialog = true);
            showPauseDialog();
        });
        btnBefore.setOnClickListener(v -> onBefore());
    }


    public void onBefore() {
        pauseChronometer();
        finish();
    }

    private void showPauseDialog() {
        pauseChronometer();
        dialog = new CustomDialog(FirstActivity.this);
        dialog.setCancelable(false);
        dialog.newGame = view1 -> {
            local_storage.setTimeCount(0);
            local_storage.setPauseDialog(pauseDialog = false);
            startChronometer();
            pauseChronometer();
            count = 0;
            setNumbersToButton();
            dialog.dismiss();
        };
        dialog.resume = view12 -> {
            local_storage.setPauseDialog(pauseDialog = false);
            dialog.dismiss();
        };
        dialog.exit = view -> {
            local_storage.setPauseDialog(pauseDialog = false);
            pauseChronometer();
            finish();
        };
        dialog.show();
    }

    private void onShuffleClick() {
        count = 0;
        setNumbersToButton();
        pauseChronometer();
        restartChronometer();
        pauseChronometer();


    }


    private void initViews() {
        local_storage = Local_Storage.getInstance();
        time = findViewById(R.id.chronometer);
        timeCount = local_storage.getTimeCount();
        count = local_storage.getCount();
        record = local_storage.getRecord();
        record1 = findViewById(R.id.record);
        buttonSound = MediaPlayer.create(this, R.raw.gb7);
        booleanSound = local_storage.getSound();
        music = Music.getInstance();
        containerButtons = findViewById(R.id.container);
        stepCounter = findViewById(R.id.textStepCounter);
        btnPause = findViewById(R.id.btnPause);
        btnBefore = findViewById(R.id.before);
        final int count = containerButtons.getChildCount();
        int size = ((int) Math.sqrt(count));
        buttons = new AppCompatButton[size][size];
        for (int i = 0; i < count; i++) {
            int x = i / size;
            int y = i % size;
            AppCompatButton button = ((AppCompatButton) containerButtons.getChildAt(i));
            button.setTag(new Coordinate(x, y));
            button.setOnClickListener(this);
            buttons[x][y] = button;
        }
        emptyCoordinate = new Coordinate(size - 1, size - 1);
    }

    private void initNumbers() {
        containerButtons = findViewById(R.id.container);
        int count = containerButtons.getChildCount();
        numbers = new ArrayList<>(count - 1);
        for (int i = 1; i < count; i++) {
            numbers.add(i);
        }
    }

    private void setNumbersToButton() {
        shuffleNumbers();
        buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.VISIBLE);
        emptyCoordinate.setX(3);
        emptyCoordinate.setY(3);
        buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.INVISIBLE);
        buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setText("16");
        stepCounter.setText(String.valueOf(count));
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                int index = i * buttons.length + j;
                if (index < 15) buttons[i][j].setText(String.valueOf(numbers.get(index)));
            }
        }

    }

    private void youWin() {
        boolean booleans = false;
        for (int i = 0; i < containerButtons.getChildCount() - 1; i++) {
            if (buttons[i / 4][i % 4].getText().equals(String.valueOf(i + 1))) booleans = true;
            else {
                booleans = false;
                break;
            }
        }

        //add record
        if (booleans) {
            record1.setText(String.valueOf(local_storage.getRecord()));
            local_storage.setRecord(count);
            record1.setText(String.valueOf(local_storage.getRecord()));
            local_storage.setWinDialog(winDialog = true);
            local_storage.setAboutDialog(pauseDialog = true);
            showDialog();
            count = 0;
            Toast.makeText(this, "YOU WIN", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        containerButtons = findViewById(R.id.container);
        outState.putInt("COUNT", count);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < containerButtons.getChildCount(); i++) {
            list.add(((Button) containerButtons.getChildAt(i)).getText().toString());
        }
        outState.putStringArrayList("BUTTONS", list);
        outState.putBoolean("WINDIALOG", winDialog);
        outState.putBoolean("PAUSEDIALOG", pauseDialog);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = containerButtons.getChildCount();
        ArrayList<String> list = savedInstanceState.getStringArrayList("BUTTONS");
        for (int i = 0; i < list.size(); i++) {
            Log.d("LIST", list.get(i));
        }
        loadSetNumbers(list);
        if (savedInstanceState.getBoolean("WINDIALOG", false)) {
            showDialog();
        }

        if (savedInstanceState.getBoolean("PAUSEDIALOG", false)) {
            showPauseDialog();
        }
    }

    public void loadSetNumbers(ArrayList<String> list) {

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).equals("16")) {
                emptyCoordinate = new Coordinate(i / 4, i % 4);
                buttons[i / 4][i % 4].setVisibility(View.INVISIBLE);
                buttons[i / 4][i % 4].setText("16");
                continue;
            }
            buttons[3][3].setVisibility(View.VISIBLE);
            buttons[i / 4][i % 4].setText(list.get(i));
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TTT", "onStop");
        local_storage.setButtons(getStringButtons());
    }

    @Override
    protected void onResume() {
        super.onResume();
        timeCount = local_storage.getTimeCount();
        Log.d("TTT", "onResume");
        record = local_storage.getRecord();
        record1.setText(String.valueOf(record));
        loadSetNumbers(getButtons(stringTo(local_storage.getButtons())));

        if (local_storage.getNewGamess()) {
            count = 0;
            setNumbersToButton();
            local_storage.setTimeCount(0);
            local_storage.setNewGamess(false);
        }

        if (local_storage.getMusic()) {
            music.resumeMusic();
        }

        startChronometer();
        pauseChronometer();
        if (local_storage.getWinDialog()) {
            showDialog();
        }

        if (local_storage.getPauseDialog()) {
           showPauseDialog();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TTT", "onPause");
        local_storage.setSaveCount(count);
        music.pauseMusic();
        local_storage.setButtons(getStringButtons());
        pauseChronometer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TTT", "onDestroy");
    }


    public void shuffleNumbers() {
        Collections.shuffle(numbers);
        if (!isSolvable(numbers))
            shuffleNumbers();

    }


    public void onButtonSound() {
        booleanSound = local_storage.getSound();
        if (booleanSound) {
            if (buttonSound == null) buttonSound = MediaPlayer.create(this, R.raw.gb7);
            buttonSound.start();
        }
    }

    private ArrayList<String> stringToButtons() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < containerButtons.getChildCount(); i++)
            list.add(((Button) containerButtons.getChildAt(i)).getText().toString());
        return list;
    }

    private ArrayList<String> getButtons(String[] strings) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(strings));
        return list;
    }

    private void showDialog() {
        pauseChronometer();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this)
                .setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_win, null);
        dialogBuilder.setView(view).create();
        AlertDialog dialog = dialogBuilder.show();
        dialog.getWindow().setBackgroundDrawable(null);
        winNewGameBtn = view.findViewById(R.id.winNewGameBtn);
        winNewGameBtn.setOnClickListener(view1 -> {
            local_storage.setWinDialog(winDialog = false);
            local_storage.setTimeCount(0);
            setNumbersToButton();
            restartChronometer();
            dialog.dismiss();
        });

        winQuitBtn = view.findViewById(R.id.winQuitBtn);
        winQuitBtn.setOnClickListener(view1 -> {
            local_storage.setWinDialog(winDialog = false);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void onClick(View view) {
        AppCompatButton button = ((AppCompatButton) view);
        Coordinate clickedCoordinate = ((Coordinate) button.getTag());
        int dX = Math.abs(emptyCoordinate.getX() - clickedCoordinate.getX());
        int dY = Math.abs(emptyCoordinate.getY() - clickedCoordinate.getY());
        if (dX + dY == 1) {
            onButtonSound();
            buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setText(button.getText());
            buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.VISIBLE);
            emptyCoordinate.setX(clickedCoordinate.getX());
            emptyCoordinate.setY(clickedCoordinate.getY());
            stepCounter.setText(String.valueOf(++count));
            buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setVisibility(View.INVISIBLE);
            buttons[emptyCoordinate.getX()][emptyCoordinate.getY()].setText("16");
            startChronometer();
            youWin();
        }
    }

    private boolean isSolvable(List<Integer> numbers) {
        RelativeLayout containerButtons = findViewById(R.id.container);
        int count = containerButtons.getChildCount();

        int countInversions = 0;
        for (int i = 0; i < count - 1; i++) {
            for (int j = i; j < count - 1; j++) {
                if (numbers.get(i) > numbers.get(j))
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }

    private void startChronometer() {
        if (!local_storage.getBooleanTime()) {
            time.setBase(SystemClock.elapsedRealtime() - local_storage.getTimeCount());
            local_storage.setBooleanTime(true);
            time.start();
        }
    }

    private void pauseChronometer() {
        if (local_storage.getBooleanTime()) {
            time.stop();
            timeCount = SystemClock.elapsedRealtime() - time.getBase();
            local_storage.setTimeCount(timeCount);
            local_storage.setBooleanTime(false);
        }
//        Log.d("TIME", String.valueOf(timeCount));
//        Log.d("TIME", String.valueOf(SystemClock.elapsedRealtime()));
//        Log.d("TIME", String.valueOf(time.getBase()));
    }

    private void restartChronometer() {
        if (!local_storage.getBooleanTime()) {
            time.setBase(SystemClock.elapsedRealtime());
            local_storage.setTimeCount(0);
            local_storage.setBooleanTime(true);
        }
    }

    private String getStringButtons() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < containerButtons.getChildCount(); i++) {
            stringBuilder.append(((Button) containerButtons.getChildAt(i)).getText().toString()).append("#");
            Log.d("BBB", ((Button) containerButtons.getChildAt(i)).getText().toString());
        }
        return stringBuilder.toString();
    }

    private String[] stringTo(String str) {
        return str.split("#");
    }

}