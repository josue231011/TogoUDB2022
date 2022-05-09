package sv.edu.udb.guia07app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
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

import sv.edu.udb.guia07app.Modelo.Carrito;
import sv.edu.udb.guia07app.Modelo.Producto;
import sv.edu.udb.guia07app.Productos.ActividadProducto;
import sv.edu.udb.guia07app.Productos.AdaptadorProducto;
import sv.edu.udb.guia07app.Productos.AgregarProducto;
import sv.edu.udb.guia07app.R;

public class HamburguesasActivity extends AppCompatActivity {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refProductos = database.getReference("productos");
    public static DatabaseReference refCarrito = database.getReference("carrito");

    // Ordenamiento
    Query consultaOrdenada = refProductos.orderByChild("categoria").equalTo("Hamburguesas");

    List<Producto> productos;
    ListView listaProductos;
    FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;

    //Declarando botones de navegacion
    Button btn_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburguersas);

        inicializar();

    }

    private void inicializar() {
        listaProductos = findViewById(R.id.ListaHamburger);
        productos = new ArrayList<>();

        googleSignInClient = GoogleSignIn.getClient(HamburguesasActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);
        //Iniciamos la auth de firebase
        mAuth = FirebaseAuth.getInstance();
        //Iniciamos firebase usuario
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        // Cuando el usuario haga clic en la lista (para agregar al carrito)
        listaProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Preparando cuadro de dialogo para preguntar al usuario
                // Si esta seguro de eliminar o no el registro
                AlertDialog.Builder ad = new AlertDialog.Builder(HamburguesasActivity.this);
                ad.setMessage("Desea agregar este producto al carrito")
                        .setTitle("Confirmación");

                ad.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        String nombre = productos.get(i).getNombre();
                        Double precio = productos.get(i).getPrecio();
                        String correo = firebaseUser.getEmail();
                        String img = productos.get(i).getpImg();
                        boolean actividad = true;

                        Carrito carrito = new Carrito(nombre, precio, img, correo, actividad, null, null);
                        HamburguesasActivity.refCarrito.push().setValue(carrito);

                        Toast.makeText(HamburguesasActivity.this,
                                "Agregado al carrito!", Toast.LENGTH_SHORT).show();
                    }
                });
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(HamburguesasActivity.this,
                                "No se agrego al carrito!", Toast.LENGTH_SHORT).show();
                    }
                });

                ad.show();
            }
        });

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

                AdaptadorVista adapter = new AdaptadorVista(HamburguesasActivity.this,
                        productos);
                listaProductos.setAdapter(adapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}