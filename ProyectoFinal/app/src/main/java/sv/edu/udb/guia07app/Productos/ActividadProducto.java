package sv.edu.udb.guia07app.Productos;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

import sv.edu.udb.guia07app.Modelo.Producto;
import sv.edu.udb.guia07app.R;


public class ActividadProducto extends AppCompatActivity {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");

    // Ordenamiento
    Query consultaOrdenada = refProductos.orderByChild("nombre");

    List<Producto> productos;
    ListView listaProductos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        inicializar();

    }

    private void inicializar() {
        FloatingActionButton fab_agregar= findViewById(R.id.btn_agregar);
        listaProductos = findViewById(R.id.ListaProducto);

        // Cuando el usuario haga clic en la lista (para editar registro)
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), AgregarProducto.class);

                intent.putExtra("accion","e"); // Editar
                intent.putExtra("key", productos.get(i).getKey());
                intent.putExtra("id",productos.get(i).getIdProducto());
                intent.putExtra("nombre",productos.get(i).getNombre());
                intent.putExtra("categoria",productos.get(i).getCategoria());
                intent.putExtra("descripcion",productos.get(i).getDescripcion());
                intent.putExtra("enStock",productos.get(i).getEnStock());
                intent.putExtra("precio",productos.get(i).getPrecio());
                intent.putExtra("img",productos.get(i).getpImg());
                startActivity(intent);
            }
        });

        // Cuando el usuario hace un LongClic (clic sin soltar elemento por mas de 2 segundos)
        // Es por que el usuario quiere eliminar el registro
        listaProductos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                    // Preparando cuadro de dialogo para preguntar al usuario
                    // Si esta seguro de eliminar o no el registro
                    AlertDialog.Builder ad = new AlertDialog.Builder(sv.edu.udb.guia07app.Productos.ActividadProducto.this);
                    ad.setMessage("Está seguro de eliminar registro?")
                            .setTitle("Confirmación");

                    ad.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sv.edu.udb.guia07app.Productos.ActividadProducto.refProductos
                                    .child(productos.get(position).getKey()).removeValue();

                            Toast.makeText(sv.edu.udb.guia07app.Productos.ActividadProducto.this,
                                    "Registro borrado!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(sv.edu.udb.guia07app.Productos.ActividadProducto.this,
                                    "Operación de borrado cancelada!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    ad.show();
                    return true;
            }
        });

        fab_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando el usuario quiere agregar un nuevo registro
                Intent i = new Intent(getBaseContext(), AgregarProducto.class);
                i.putExtra("accion","a"); // Agregar
                i.putExtra("key","");
                i.putExtra("id","");
                i.putExtra("nombre","");
                i.putExtra("categoria","");
                i.putExtra("descripcion","");
                i.putExtra("enStock","");
                i.putExtra("precio","");
                i.putExtra("img","");
                startActivity(i);
            }
        });

        productos = new ArrayList<>();

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consultaOrdenada.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                productos.removeAll(productos);
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Producto producto = dato.getValue(Producto.class);
                    producto.setKey(dato.getKey());
                    productos.add(producto);
                }

                AdaptadorProducto adapter = new AdaptadorProducto(ActividadProducto.this,
                        productos);
                listaProductos.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

