package joansanchez.jediapp.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.material.joanbarroso.flipper.CoolImageFlipper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import joansanchez.jediapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Juego extends Fragment implements View.OnClickListener {
    private static final String TAG = "JUEGO";

    CoolImageFlipper flipper;
    ImageView im11, im21, im31, im41, im12, im22, im32, im42, im13, im23, im33, im43, im14, im24, im34, im44;
    private  Integer[] rutaimagen = {R.drawable.ic_banana, R.drawable.ic_banana, R.drawable.ic_burger, R.drawable.ic_burger, R.drawable.ic_chocolate, R.drawable.ic_chocolate, R.drawable.ic_french_fries, R.drawable.ic_french_fries, R.drawable.ic_gingerbread_man, R.drawable.ic_gingerbread_man, R.drawable.ic_pie, R.drawable.ic_pie, R.drawable.ic_rice, R.drawable.ic_rice, R.drawable.ic_taco, R.drawable.ic_taco};
    private List<Integer> listarutaimagen = Arrays.asList(rutaimagen);
    boolean isBurger = true;

    public Juego() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flipper = new CoolImageFlipper(getActivity());

        View rootView = inflater.inflate(R.layout.fragment_calculadora, container, false);
        im11 = (ImageView) rootView.findViewById(R.id.im11);
        im12 = (ImageView) rootView.findViewById(R.id.im12);
        im13 = (ImageView) rootView.findViewById(R.id.im13);
        im14 = (ImageView) rootView.findViewById(R.id.im14);
        im21 = (ImageView) rootView.findViewById(R.id.im21);
        im22 = (ImageView) rootView.findViewById(R.id.im22);
        im23 = (ImageView) rootView.findViewById(R.id.im23);
        im24 = (ImageView) rootView.findViewById(R.id.im24);
        im31 = (ImageView) rootView.findViewById(R.id.im31);
        im32 = (ImageView) rootView.findViewById(R.id.im32);
        im33 = (ImageView) rootView.findViewById(R.id.im33);
        im34 = (ImageView) rootView.findViewById(R.id.im34);
        im41 = (ImageView) rootView.findViewById(R.id.im41);
        im42 = (ImageView) rootView.findViewById(R.id.im42);
        im43 = (ImageView) rootView.findViewById(R.id.im43);
        im44 = (ImageView) rootView.findViewById(R.id.im44);


        im11.setOnClickListener(this);

        Collections.shuffle(listarutaimagen);
        rutaimagen = listarutaimagen.toArray(new Integer[listarutaimagen.size()]);

        return rootView;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.im11:
                Drawable car1 = getResources().getDrawable(rutaimagen[1]);
                Drawable car2 = getResources().getDrawable(rutaimagen[2]);
                if (isBurger)
                    flipper.flipImage(car1, ((ImageView) view));
                else
                    flipper.flipImage(car2, ((ImageView) view));

                isBurger = !isBurger;
                break;
        }
    }
}

