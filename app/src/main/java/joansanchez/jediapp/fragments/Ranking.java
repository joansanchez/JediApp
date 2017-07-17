package joansanchez.jediapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import joansanchez.jediapp.Contact;
import joansanchez.jediapp.MyCustomAdapter;
import joansanchez.jediapp.R;
import joansanchez.jediapp.database.MyDataBaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ranking extends Fragment {
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;
    MyCustomAdapter contactsAdapter;
    private MyDataBaseHelper myDataBaseHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ranking, container, false);
        //findViewById del layout activity_main
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        //LinearLayoutManager necesita el contexto de la Activity.
        //El LayoutManager se encarga de posicionar los items dentro del recyclerview
        //Y de definir la politica de reciclaje de los items no visibles.
        mLinearLayout = new LinearLayoutManager(getContext());

        //Asignamos el LinearLayoutManager al recycler:
        mRecyclerView.setLayoutManager(mLinearLayout);

        //El adapter se encarga de  adaptar un objeto definido en el c�digo a una vista en xml
        //seg�n la estructura definida.
        //Asignamos nuestro custom Adapter
        contactsAdapter = initAdapterData();

        mRecyclerView.setAdapter(contactsAdapter);

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDataBaseHelper = MyDataBaseHelper.getInstance(getActivity());

    }

    private List<Contact> getContactsData() {
        List<Contact> contactos = new ArrayList<>();
        contactos = myDataBaseHelper.getpuntuaciones(contactos);
        return contactos;
    }




    private MyCustomAdapter initAdapterData () {
        List<Contact> contacts = getContactsData();
        return new MyCustomAdapter(getActivity(),contacts);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menuranking, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            myDataBaseHelper.deletetable();
            Fragment f = new Ranking();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, f);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
