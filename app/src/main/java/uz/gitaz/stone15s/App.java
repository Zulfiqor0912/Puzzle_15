package uz.gitaz.stone15s;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Local_Storage.init(this);
        Music.init(this);

    }


}
