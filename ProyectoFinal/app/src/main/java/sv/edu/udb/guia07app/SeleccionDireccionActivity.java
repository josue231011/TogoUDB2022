package sv.edu.udb.guia07app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sv.edu.udb.guia07app.Direccion.ActividadDireccion;
import sv.edu.udb.guia07app.Direccion.AdaptadorDireccion;
import sv.edu.udb.guia07app.Direccion.AgregarDireccion;
import sv.edu.udb.guia07app.Modelo.Carrito;
import sv.edu.udb.guia07app.Modelo.Direccion;

public class SeleccionDireccionActivity extends AppCompatActivity {

    TextView txDireccion;
    List<Direccion> direcciones;
    List<Carrito> carritos;
    ListView listaDireccion;
    Button btnCancel;
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refDireccion = database.getReference("direccion");
    public static DatabaseReference refCarrito = database.getReference("carrito");
    FirebaseAuth mAuth;

    String key = "", nombre_producto = "", img = "", correo_usuario = "", direccion_envio = "";
    double precio = 0.0;
    boolean bandera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_direccion);
        listaDireccion = findViewById(R.id.ListaDireccion);
        txDireccion = (TextView)findViewById(R.id.tvDirecc);
        btnCancel = (Button)findViewById(R.id.btnCancelar);

        inicializar();

    }

    private void inicializar() {
        //Iniciamos la auth de firebase
        mAuth = FirebaseAuth.getInstance();
        //Iniciamos firebase usuario
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Query consulta = refDireccion.orderByChild("correo").equalTo(firebaseUser.getEmail());
        Query consultaOrdenada = refCarrito.orderByChild("correo_usuario").equalTo(firebaseUser.getEmail());
        direcciones = new ArrayList<>();
        carritos = new ArrayList<>();

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
        consulta.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                direcciones.removeAll(direcciones);
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Direccion direccion = dato.getValue(Direccion.class);
                    direccion.setKey(dato.getKey());
                    direcciones.add(direccion);
                }

                AdaptadorDireccion adapter = new AdaptadorDireccion(SeleccionDireccionActivity.this,
                        direcciones);
                listaDireccion.setAdapter(adapter);

                if(adapter.getCount() == 0){
                    txDireccion.setText("No tiene ninguna direccion registrada");
                }else {
                    txDireccion.setText("Selecciona una direccion para el envio");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // Cuando el usuario haga clic en la lista
        //Cambiara el estado de la bandera a false, lo que quiere decir que
        //Ya no aparecera en el carrito y se mostrara en el historial...
        //Ademas agregamos los campos faltantes que son la direccion de envio y la fecha de compra.
        try {
            listaDireccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getBaseContext(), DashboardActivity.class);
                    //Obtenemos fecha y hora del celular
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                    for(int indice = 0; indice < carritos.size(); indice++){
                        key = carritos.get(indice).getKey();
                        nombre_producto = carritos.get(indice).getNombre_producto();
                        img = carritos.get(indice).getpImg();
                        precio = carritos.get(indice).getPrecio();
                        correo_usuario = carritos.get(indice).getCorreo_usuario();
                        //Si la direccion esta vacia que agregue los campos que faltan
                        if(carritos.get(indice).getDireccion_envio() == null){
                            //Agregamos campos que faltan
                            String fecha = simpleDateFormat.format(new Date());
                            direccion_envio = direcciones.get(i).getDireccion();
                            bandera = false;
                            Carrito carrito = new Carrito(nombre_producto, precio, img, correo_usuario,
                                    bandera, direccion_envio, fecha);
                            SeleccionDireccionActivity.refCarrito.child(key).setValue(carrito);
                        }else{
                            //Si la direccion es diferente del null, que no haga cambios
                        }
                    }

                    Toast.makeText(SeleccionDireccionActivity.this,
                            "Se realizo la compra correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(SeleccionDireccionActivity.this,
                    "No tiene direcciones registradas!", Toast.LENGTH_SHORT).show();
        }

        // Cambiarlo refProductos a consultaOrdenada para ordenar lista
       consultaOrdenada.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                carritos.removeAll(carritos);
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Carrito carrito = dato.getValue(Carrito.class);
                    carrito.setKey(dato.getKey());
                    carritos.add(carrito);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cuando el usuario quiere agregar un nuevo registro
                Intent i = new Intent(getBaseContext(), DashboardActivity.class);
                startActivity(i);
            }
        });

    }

}