package sv.edu.udb.guia07app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sv.edu.udb.guia07app.Modelo.Carrito;

public class AdaptadorHistorial extends ArrayAdapter<Carrito> {


    List<Carrito> carritos;
    private Activity context;

    public AdaptadorHistorial(@NonNull Activity context, @NonNull List<Carrito> carritos) {
        super(context, R.layout.activity_adaptador_historial, carritos);
        this.context = context;
        this.carritos = carritos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        // Método invocado tantas veces como elementos tenga la coleccion productos
        // para formar a cada item que se visualizara en la lista personalizada
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=null;
        if (view == null)
            rowview = layoutInflater.inflate(R.layout.activity_adaptador_historial,null);
        else rowview = view;

        TextView tvNombre = rowview.findViewById(R.id.tvNombre);
        TextView tvDireccion = rowview.findViewById(R.id.tvDireccions);
        TextView tvFecha = rowview.findViewById(R.id.tvFecha);
        TextView tvPrecio = rowview.findViewById(R.id.tvPrecio);
        CircleImageView img = rowview.findViewById(R.id.Img);

        tvNombre.setText("Nombre : "+carritos.get(position).getNombre_producto());
        tvDireccion.setText("Direccion : "+carritos.get(position).getDireccion_envio());
        tvFecha.setText("Fecha y hora de compra: "+carritos.get(position).getFecha());
        tvPrecio.setText("Precio : $" + carritos.get(position).getPrecio());
        Glide.with(img.getContext()).load(carritos.get(position).getpImg()).into(img);

        return rowview;
    }


}