package uz.gitaz.stone15s;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;



public class Music {
    private static Music music;
    private MediaPlayer mediaPlayer;
    private static boolean booleanMusic;
    private static Local_Storage local_storage;

    public Music(Context context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.long_music);
        if (booleanMusic) {
            mediaPlayer.start();
        }
    }

    public static Music getInstance() {
        booleanMusic = local_storage.getMusic();
        return music;
    }

    public static void init(Context context) {
        local_storage = Local_Storage.getInstance();
        if (music == null) music = new Music(context);
    }

    public void stopMusic() {
        booleanMusic = false;
        local_storage.setSaveMusic(false);
        Log.d("MUSIC", local_storage.getMusic() + "stopMusic");
        mediaPlayer.pause();
    }

    public void pauseMusic() {
        //booleanMusic = false;
        mediaPlayer.pause();
        Log.d("MUSIC", "pauseMusic");
    }

    public void resumeMusic() {
        booleanMusic = true;
        local_storage.setSaveMusic(true);
        Log.d("MUSIC", local_storage.getMusic() + "resumeMusic");
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

//    public void releaseMusic() {
//        local_storage.setSaveMusic(booleanMusic = false);
//        mediaPlayer.release();
//        music = null;
//    }
//
//    public void stopMusic() {
////        booleanMusic = false;
//        local_storage.setSaveMusic(booleanMusic = false);
//        mediaPlayer.stop();
//    }
//
//    public void pauseMusic() {
//        booleanMusic = false;
//        mediaPlayer.pause();
//    }
//
//    public void resumeMusic() {
//        booleanMusic = true;
//        local_storage.setSaveMusic(true);
//        try {
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();
//    }
//
//    public void releaseMusic() {
//        local_storage.setSaveMusic(booleanMusic = false);
//        mediaPlayer.release();
//        music = null;
//    }
}
