package uz.gitaz.stone15s;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;



public class CustomDialog extends AlertDialog {
    private Local_Storage local_storage;
    private AppCompatImageView pauseResume;
    private AppCompatImageView pauseNewGame;
    private AppCompatImageView pauseQuit;
    private Music music;
    private AppCompatImageView onMusic;
    private AppCompatImageView offMusic;
    public AppCompatImageView soundOff;
    public AppCompatImageView soundOn;
    public boolean booleanSounds;
    public boolean booleanMusics;

    public View.OnClickListener exit = view -> {
    };
    public View.OnClickListener resume = view -> {
    };
    public View.OnClickListener newGame = view -> {
    };

    protected CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause);
        Log.d("TTT", "SHOWDIALOG");
        getWindow().setBackgroundDrawable(null);
        initViews();
        assert pauseResume != null;
        pauseResume.setOnClickListener(resume);
        assert pauseNewGame != null;
        pauseNewGame.setOnClickListener(newGame);
        assert pauseQuit != null;
        pauseQuit.setOnClickListener(exit);
        alMusic();
        al();
    }

    private void initViews() {
        local_storage = Local_Storage.getInstance();
        pauseResume = findViewById(R.id.pauseResume);
        pauseNewGame = findViewById(R.id.pauseNewGame);
        pauseQuit = findViewById(R.id.pauseQuit);
        music = Music.getInstance();
        onMusic = findViewById(R.id.onMusic);
        offMusic = findViewById(R.id.musicOff);
        soundOn = findViewById(R.id.soundOn);
        soundOff = findViewById(R.id.soundOff);
        booleanSounds = local_storage.getSound();
        booleanMusics = local_storage.getMusic();
        Log.d("TTT", String.valueOf(booleanSounds));
        Log.d("TTTM", String.valueOf(booleanMusics));
    }

    private void clickedSoundOn() {
        soundOn.setVisibility(View.INVISIBLE);
        soundOff.setVisibility(View.VISIBLE);
        local_storage.setSound(false);
        al();
    }

    private void clickedSoundOff() {
        soundOff.setVisibility(View.INVISIBLE);
        soundOn.setVisibility(View.VISIBLE);
        local_storage.setSound(true);
        al();
    }

    private void al() {
        if (local_storage.getSound()) {
            soundOff.setVisibility(View.INVISIBLE);
            soundOn.setVisibility(View.VISIBLE);
            soundOn.setOnClickListener(v -> {
                clickedSoundOn();
            });
        } else {
            soundOn.setVisibility(View.INVISIBLE);
            soundOff.setVisibility(View.VISIBLE);
            soundOff.setOnClickListener(v -> {
                clickedSoundOff();
            });
        }
    }

    private void clickedMusicOn() {
        onMusic.setVisibility(View.INVISIBLE);
        offMusic.setVisibility(View.VISIBLE);
        Log.d("MUSIC","clickOn->clickOff");
        music.stopMusic();
        alMusic();

    }

    private void clickedMusicOff() {
        onMusic.setVisibility(View.VISIBLE);
        offMusic.setVisibility(View.INVISIBLE);
        Log.d("MUSIC","clickOff->clickOn");
        music.resumeMusic();
        alMusic();
    }


    private void alMusic() {
        if (local_storage.getMusic()) {
            onMusic.setVisibility(View.VISIBLE);
            offMusic.setVisibility(View.INVISIBLE);
            Log.d("MUSIC",String.valueOf(local_storage.getMusic()));
            onMusic.setOnClickListener(v -> {
                clickedMusicOn();
            });
        } else {
            offMusic.setVisibility(View.VISIBLE);
            onMusic.setVisibility(View.INVISIBLE);
            Log.d("MUSIC",String.valueOf(local_storage.getMusic()));
            offMusic.setOnClickListener(v -> {
                clickedMusicOff();
            });
        }
    }
}
