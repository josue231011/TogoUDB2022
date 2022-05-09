package sv.edu.udb.guia07app.Direccion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sv.edu.udb.guia07app.Modelo.Direccion;
import sv.edu.udb.guia07app.Modelo.Producto;
import sv.edu.udb.guia07app.Productos.AdaptadorProducto;
import sv.edu.udb.guia07app.Productos.AgregarProducto;
import sv.edu.udb.guia07app.R;


public class ActividadDireccion extends AppCompatActivity {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refDireccion = database.getReference("direccion");

    // Ordenamiento
    TextView txDireccion;
    List<Direccion> direcciones;
    ListView listaDireccion;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direcciones);
        txDireccion = (TextView)findViewById(R.id.txDirecc);
        inicializar();
    }

    private void inicializar() {

        //Iniciamos la auth de firebase
        mAuth = FirebaseAuth.getInstance();
        //Iniciamos firebase usuario
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Query consulta = refDireccion.orderByChild("correo").equalTo(firebaseUser.getEmail());

        FloatingActionButton fab_agregar= findViewById(R.id.btn_agregar);
        listaDireccion = findViewById(R.id.ListaDireccion);

        // Cuando el usuario haga clic en la lista (para editar registro)
        listaDireccion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(), AgregarDireccion.class);

                intent.putExtra("accion","e"); // Editar
                intent.putExtra("key", direcciones.get(i).getKey());
                intent.putExtra("nombre",direcciones.get(i).getNombre_direccion());
                intent.putExtra("direccion",direcciones.get(i).getDireccion());
                intent.putExtra("correo",direcciones.get(i).getCorreo());
                startActivity(intent);
            }
        });

        // Cuando el usuario hace un LongClic (clic sin soltar elemento por mas de 2 segundos)
        // Es por que el usuario quiere eliminar el registro
        listaDireccion.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                    // Preparando cuadro de dialogo para preguntar al usuario
                    // Si esta seguro de eliminar o no el registro
                    AlertDialog.Builder ad = new AlertDialog.Builder(ActividadDireccion.this);
                    ad.setMessage("Está seguro de eliminar la direccion?")
                            .setTitle("Confirmación");

                    ad.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActividadDireccion.refDireccion
                                    .child(direcciones.get(position).getKey()).removeValue();

                            Toast.makeText(ActividadDireccion.this,
                                    "Registro borrado!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(ActividadDireccion.this,
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
                Intent i = new Intent(getBaseContext(), AgregarDireccion.class);
                i.putExtra("accion","a"); // Agregar
                i.putExtra("key","");
                i.putExtra("nombre","");
                i.putExtra("direccion","");
                i.putExtra("correo",firebaseUser.getEmail());
                startActivity(i);
            }
        });

        direcciones = new ArrayList<>();

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

                AdaptadorDireccion adapter = new AdaptadorDireccion(ActividadDireccion.this,
                        direcciones);
                listaDireccion.setAdapter(adapter);

                if(adapter.getCount() == 0){
                    txDireccion.setText("No tiene ninguna direccion registrada");
                }else {
                    txDireccion.setText("Direcciones registradas");
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

