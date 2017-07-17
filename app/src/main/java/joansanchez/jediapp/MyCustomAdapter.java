package joansanchez.jediapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Joan on 17/07/2017.
 */

public class MyCustomAdapter extends RecyclerView.Adapter<MyCustomAdapter.AdapterViewHolder>{
    List<Contact> contactos;
    Context context;
    public MyCustomAdapter(Context c, List<Contact> contacts){
        this.context = c;
        this.contactos = contacts;
    }

    public interface OnIconTouched {
        void meHanPulsado(int position, Contact contactoPulsado);
    }

    @Override
    public MyCustomAdapter.AdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //Instancia un layout XML en la correspondiente vista.
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        //Inflamos en la vista el layout para cada elemento
        View view = inflater.inflate(R.layout.rowlayout, viewGroup, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyCustomAdapter.AdapterViewHolder adapterViewholder, final int position) {
        final Contact contactoAMostrar = contactos.get(position);

        int iconLayout = contactoAMostrar.getIcon();
        //Dependiendo del entero se asignará una imagen u otra
        switch (iconLayout){
            case 0:
                //male
                adapterViewholder.icon.setImageDrawable(adapterViewholder.v.getResources().getDrawable(R.drawable.ic_banana));
                break;
            case 1:
                //female
                adapterViewholder.icon.setImageDrawable(adapterViewholder.v.getResources().getDrawable(R.drawable.ic_burger));
                break;
        }
        adapterViewholder.name.setText(contactoAMostrar.getName());
        adapterViewholder.phone.setText(contactoAMostrar.getPhone());
        adapterViewholder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactos.remove(position);
                notifyItemRemoved(position);
//                notifyDataSetChanged();
            }
        });

        adapterViewholder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"HAN PULSADO EL ICONO", Toast.LENGTH_SHORT).show();
                ((OnIconTouched) context).meHanPulsado(position, contactoAMostrar);
            }
        });

    }

    @Override
    public int getItemCount() {
        //Debemos retornar el tamaño de todos los elementos contenidos en el viewholder
        //Por defecto es return 0 --> No se mostrará nada.
        return contactos.size();
    }

    public void addContacts(List<Contact> contactos) {
        this.contactos.addAll(contactos);
        notifyDataSetChanged();
    }


    //Definimos una clase viewholder que funciona como adapter para
    public class AdapterViewHolder extends RecyclerView.ViewHolder {
        /*
        *  Mantener una referencia a los elementos de nuestro ListView mientras el usuario realiza
        *  scrolling en nuestra aplicación. Así que cada vez que obtenemos la vista de un item,
        *  evitamos las frecuentes llamadas a findViewById, la cuál se realizaría únicamente la primera vez y el resto
        *  llamaríamos a la referencia en el ViewHolder, ahorrándonos procesamiento.
        */

        public ImageView icon;
        public TextView name;
        public TextView phone;
        public View v;
        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.phone = (TextView) itemView.findViewById(R.id.phone);
        }
    }
}
