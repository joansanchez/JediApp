package joansanchez.jediapp.fragments;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import joansanchez.jediapp.MusicPl;
import joansanchez.jediapp.R;
import joansanchez.jediapp.MusicPl.MyLocalBinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Music extends Fragment implements View.OnClickListener {

    Button play, pause,stop;
    MusicPl MService;
    boolean isBound = false;
    boolean stopped = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);
        play = (Button) rootView.findViewById(R.id.play);
        pause = (Button) rootView.findViewById(R.id.pause);
        stop = (Button) rootView.findViewById(R.id.stop);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        Intent i = new Intent(getActivity(),MusicPl.class);
        getActivity().bindService(i, Sconexion, Context.BIND_AUTO_CREATE);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                if (stopped){
                    Intent i = new Intent(getActivity(),MusicPl.class);
                    getActivity().bindService(i, Sconexion, Context.BIND_AUTO_CREATE);
                }
                MService.play();
                stopped = false;
                break;
            case R.id.pause:
                MService.pause();
                break;
            case R.id.stop:
                MService.stop();
                getActivity().unbindService(Sconexion);
                isBound = false;
                stopped = true;
                break;
        }
    }

   /* @Override
    public void onStart() {
        super.onStart();
        Intent i = new Intent(getActivity(),MusicPl.class);
        getActivity().bindService(i, Sconexion, Context.BIND_AUTO_CREATE);
    }


     public void onStop() {
        super.onStop();
        if (isBound) {
            getActivity().unbindService(Sconexion);
            isBound = false;
        }
    }*/

   private ServiceConnection Sconexion = new ServiceConnection() {
       @Override
       public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
           MyLocalBinder binder = (MyLocalBinder) iBinder;
           MService = binder.getService();
           isBound = true;

       }

       @Override
       public void onServiceDisconnected(ComponentName componentName) {
           isBound = false;
       }
   };
}
















