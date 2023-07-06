package uz.gitaz.stone15s;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Local_Storage {
    private static Local_Storage local_storage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private List<Integer> listRecord = new ArrayList<>(3);

    private Local_Storage(Context context) {
        sharedPreferences = context.getSharedPreferences("Puzzle", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static Local_Storage getInstance() {
        return local_storage;
    }

    public static void init(Context context) {
        if (local_storage == null) local_storage = new Local_Storage(context);
    }


    public void setSaveCount(int count) {
        editor.putInt("COUNT", count);
        editor.apply();
    }

    public int getCount() {
        return sharedPreferences.getInt("COUNT", 0);
    }


    public void setButtons(String string) {
        editor.putString("BUTTON", string);
        editor.apply();
    }

    public String getButtons() {
        return sharedPreferences.getString("BUTTON", "");
    }

//    public void setSaveButtons(ArrayList<String> buttons) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (int i = 0; i < buttons.size(); i++) {
//            stringBuilder.append(buttons.get(i)).append("#");
//        }
//        editor.putString("BUTTON", stringBuilder.substring(0, stringBuilder.length() - 1));
//        editor.apply();
//    }

//    public String[] getSaveButtons() {
//        String[] strings;
//        strings = sharedPreferences.getString("BUTTON", "").split("#");
//        return strings;
//    }


    public void setSound(Boolean btnSound) {
        Log.d("TTT", String.valueOf(btnSound));
        editor.putBoolean("SOUND", btnSound);
        editor.apply();
    }

    public boolean getSound() {
        Log.d("TTT", String.valueOf(sharedPreferences.getBoolean("SOUND", true)));
        return sharedPreferences.getBoolean("SOUND", true);
    }


    public void setSaveMusic(Boolean saveMusic) {
        editor.putBoolean("MUSIC", saveMusic);
        editor.apply();
    }

    public boolean getMusic() {
        return sharedPreferences.getBoolean("MUSIC", true);
    }


    // record
    public void setRecord(int record) {
        if (listRecord.size() < 3) {
            listRecord.add(record);
            Collections.sort(listRecord);
        } else {
            if (listRecord.get(0) > record) listRecord.set(0, record);
            else if (listRecord.get(1) > record) listRecord.set(1, record);
            else if (listRecord.get(2) > record) listRecord.set(1, record);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < listRecord.size(); i++)
            stringBuilder.append(listRecord.get(i)).append("#");
        editor.putString("RECORDS", stringBuilder.toString());
        editor.putInt("RECORD", listRecord.get(0));
        editor.apply();
    }

    public int getRecord() {
        return sharedPreferences.getInt("RECORD", 0);
    }

    public String getRecords() {
        return sharedPreferences.getString("RECORDS", "#");
    }


    //time
    public void setBooleanTime(boolean booleanTime) {
        editor.putBoolean("BOOLEANTIME", booleanTime);
        editor.apply();
    }

    public boolean getBooleanTime() {
        return sharedPreferences.getBoolean("BOOLEANTIME", false);
    }

    public void setTimeCount(long timeCount) {
        editor.putLong("TimeCount", timeCount);
        editor.apply();
    }

    public long getTimeCount() {
        return sharedPreferences.getLong("TimeCount", 0);
    }

    public void setNewGamess(boolean newGame) {
        editor.putBoolean("NEWGAME", newGame);
        editor.apply();
    }

    public boolean getNewGamess() {
        return sharedPreferences.getBoolean("NEWGAME", false);
    }

    public void setContinueGame(boolean continueGame) {
        editor.putBoolean("CONTINUE", continueGame);
        editor.apply();
    }

    public boolean getContinueGame() {
        return sharedPreferences.getBoolean("CONTINUE", false);
    }

    public void setWinDialog(boolean winDialog) {
        editor.putBoolean("win_dialog", winDialog);
        editor.apply();
    }

    public boolean getWinDialog(){
        return sharedPreferences.getBoolean("win_dialog",false);
    }

    public void setAboutDialog(boolean aboutDialog) {
        editor.putBoolean("about_dialog", aboutDialog);
        editor.apply();
    }

    public boolean getAboutDialog(){
        return sharedPreferences.getBoolean("about_dialog",false);
    }

    public void setPauseDialog(boolean pauseDialog) {
        editor.putBoolean("pause_dialog", pauseDialog);
        editor.apply();
    }

    public boolean getPauseDialog(){
        return sharedPreferences.getBoolean("pause_dialog",false);
    }

}
