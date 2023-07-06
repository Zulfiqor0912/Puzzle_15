package uz.gitaz.stone15s;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;




public class MainActivity extends AppCompatActivity {
    private AppCompatImageButton btnNewGame;
    private AppCompatImageButton btnContinue;
    private AppCompatImageButton btnAbout;
    private AppCompatImageButton btnExit;
    private AppCompatImageButton btnRecords;
    private Music music;
    private AlertDialog alertDialog;
    private Local_Storage local_storage;
    private CustomRecordsDialog recordDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        local_storage = Local_Storage.getInstance();
        btnNewGame = findViewById(R.id.newGame);
        btnContinue = findViewById(R.id.continue1);
        btnAbout = findViewById(R.id.about);
        btnExit = findViewById(R.id.exit);
        btnRecords = findViewById(R.id.records);
        music = Music.getInstance();

        btnNewGame.setOnClickListener(view -> {
            clickBtnNewGame();
        });

        btnContinue.setOnClickListener(view -> {
            clickContinue();
        });

        btnRecords.setOnClickListener(v -> {
            clickBtnRecords();
        });

        btnAbout.setOnClickListener(view -> {
            clickBtnAbout();
        });

        btnExit.setOnClickListener(view -> {
            finish();
        });
    }

    private void clickBtnNewGame() {
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        local_storage.setNewGamess(true);
        startActivity(intent);
    }

    private void clickContinue() {
        Intent intent = new Intent(MainActivity.this, FirstActivity.class);
        startActivity(intent);
        local_storage.setContinueGame(true);
    }

    private void clickBtnRecords() {
        recordDialog = new CustomRecordsDialog(MainActivity.this);
        recordDialog.setCancelable(false);
        recordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        recordDialog.show();
        recordDialog.findViewById(R.id.btnBackRecord).setOnClickListener(v -> {
            recordDialog.dismiss();
        });

    }

    private void clickBtnAbout() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_about);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        AppCompatImageView btnBack = dialog.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

}


