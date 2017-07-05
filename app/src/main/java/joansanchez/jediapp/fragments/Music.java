package joansanchez.jediapp.fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import joansanchez.jediapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Music extends Fragment implements View.OnClickListener {
    MediaPlayer mediaplayer;

    Button play, pause,stop;
    public Music() {
        // Required empty public constructor
    }


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
        mediaplayer = MediaPlayer.create(getActivity(), R.raw.sleepaway);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
               mediaplayer.start();
                break;
            case R.id.pause:
                mediaplayer.pause();
                break;
            case R.id.stop:
                mediaplayer.stop();
                
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaplayer.release();
        mediaplayer = null;
    }
}
















