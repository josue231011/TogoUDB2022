package sv.edu.udb.guia07app.Direccion;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sv.edu.udb.guia07app.Modelo.Direccion;
import sv.edu.udb.guia07app.Modelo.Producto;
import sv.edu.udb.guia07app.R;


public class AdaptadorDireccion extends ArrayAdapter<Direccion> {

    List<Direccion> direcciones;
    private Activity context;

    public AdaptadorDireccion(@NonNull Activity context, @NonNull List<Direccion> direcciones) {
        super(context, R.layout.direccion_layout, direcciones);
        this.context = context;
        this.direcciones = direcciones;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        // Método invocado tantas veces como elementos tenga la coleccion productos
        // para formar a cada item que se visualizara en la lista personalizada
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=null;
        if (view == null)
             rowview = layoutInflater.inflate(R.layout.direccion_layout,null);
        else rowview = view;

        TextView tvNombre = rowview.findViewById(R.id.tvNombre);
        TextView tvDireccion = rowview.findViewById(R.id.tvDireccion);

        tvNombre.setText("Nombre : "+direcciones.get(position).getNombre_direccion());
        tvDireccion.setText("Direccion : " + direcciones.get(position).getDireccion());

        return rowview;
    }
}
