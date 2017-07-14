package joansanchez.jediapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicPlayer extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer mediaplayer;

    @Override
    public void onCreate(){
        mediaplayer = MediaPlayer.create(this, R.raw.sleepaway);
        //TODO inicializaciones únicas
    }
    public void play() {
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


    public int onStartCommand(Intent intent){
        //TODO código principal, se ejecutará cada vez que
        //se inicie el servicio desde una actividad
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //No es necesario nada más
        return null;
    }

    @Override
    public void onDestroy(){
        //TODO código para liberar recursos
        mediaplayer.release();
        mediaplayer = null;
        //StopSelf() o StopService()
    }


}
