package joansanchez.jediapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;
import android.os.Binder;

public class MusicPl extends Service implements MediaPlayer.OnCompletionListener{
    private static final String TAG = "Service";
    MediaPlayer mediaplayer;
    private final IBinder MiBinder = new MyLocalBinder();

    public MusicPl() {
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        Log.v(TAG, "servicio encendido");
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.v(TAG, "servicio creado");
        mediaplayer = MediaPlayer.create(this, R.raw.sleepaway);
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "servicio destruido");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return MiBinder;
    }

    public void play() {
        Log.v (TAG, "estic dins");
        try {
            mediaplayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        try {
            mediaplayer.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            mediaplayer.stop();
            mediaplayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        try {
            mediaPlayer.stop();
            mediaPlayer.prepare();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyLocalBinder extends Binder {
        public MusicPl getService(){
            return MusicPl.this;
        }
    }
}
