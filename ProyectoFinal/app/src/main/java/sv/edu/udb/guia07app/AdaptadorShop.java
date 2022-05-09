package sv.edu.udb.guia07app;

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
import sv.edu.udb.guia07app.Modelo.Carrito;
import sv.edu.udb.guia07app.Modelo.Producto;

public class AdaptadorShop extends ArrayAdapter<Carrito> {


    List<Carrito> carritos;
    private Activity context;

    public AdaptadorShop(@NonNull Activity context, @NonNull List<Carrito> carritos) {
        super(context, R.layout.adaptador_shop, carritos);
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
            rowview = layoutInflater.inflate(R.layout.adaptador_shop,null);
        else rowview = view;

        TextView tvNombre = rowview.findViewById(R.id.tvNombre);
        TextView tvPrecio = rowview.findViewById(R.id.tvPrecio);
        CircleImageView img = rowview.findViewById(R.id.Img);

        tvNombre.setText("Nombre : "+carritos.get(position).getNombre_producto());
        tvPrecio.setText("Precio : $" + carritos.get(position).getPrecio());
        Glide.with(img.getContext()).load(carritos.get(position).getpImg()).into(img);

        return rowview;
    }


}
