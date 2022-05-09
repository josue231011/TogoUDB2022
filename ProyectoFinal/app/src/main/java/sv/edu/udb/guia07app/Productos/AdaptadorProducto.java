package sv.edu.udb.guia07app.Productos;

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
import sv.edu.udb.guia07app.Modelo.Producto;
import sv.edu.udb.guia07app.R;


public class AdaptadorProducto extends ArrayAdapter<Producto> {

    List<Producto> productos;
    private Activity context;

    public AdaptadorProducto(@NonNull Activity context, @NonNull List<Producto> productos) {
        super(context, R.layout.producto_layout, productos);
        this.context = context;
        this.productos = productos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        // Método invocado tantas veces como elementos tenga la coleccion productos
        // para formar a cada item que se visualizara en la lista personalizada
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View rowview=null;
        if (view == null)
             rowview = layoutInflater.inflate(R.layout.producto_layout,null);
        else rowview = view;

        TextView tvId = rowview.findViewById(R.id.tvId);
        TextView tvNombre = rowview.findViewById(R.id.tvNombre);
        TextView tvCategoria = rowview.findViewById(R.id.tvCartegoria);
        TextView tvDescripcion = rowview.findViewById(R.id.tvDescripcion);
        TextView tvStock = rowview.findViewById(R.id.tvStock);
        TextView tvPrecio = rowview.findViewById(R.id.tvPrecio);
        CircleImageView img = rowview.findViewById(R.id.Img);

        tvId.setText("ID : " + productos.get(position).getIdProducto());
        tvNombre.setText("Nombre : "+productos.get(position).getNombre());
        tvCategoria.setText("Categoria : " + productos.get(position).getCategoria());
        tvDescripcion.setText("Descripcion : " + productos.get(position).getDescripcion());
        tvStock.setText("Stock : " + productos.get(position).getEnStock());
        tvPrecio.setText("Precio : $" + productos.get(position).getPrecio());
        Glide.with(img.getContext()).load(productos.get(position).getpImg()).into(img);

        return rowview;
    }
}
