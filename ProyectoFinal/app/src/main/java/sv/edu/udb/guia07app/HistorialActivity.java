package sv.edu.udb.guia07app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

import sv.edu.udb.guia07app.Modelo.Carrito;

public class HistorialActivity extends AppCompatActivity {

    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference refCarrito = database.getReference("carrito");
    FirebaseAuth mAuth;
    GoogleSignInClient googleSignInClient;
    List<Carrito> historial;
    ListView listaHistorial;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        btn_back = (Button) findViewById(R.id.btn_back);
        inicializar();
    }

    private void inicializar() {
        listaHistorial = findViewById(R.id.ListaHistorial);
        historial = new ArrayList<>();
        googleSignInClient = GoogleSignIn.getClient(HistorialActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);
        //Iniciamos la auth de firebase
        mAuth = FirebaseAuth.getInstance();
        //Iniciamos firebase usuario
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        Query consultaOrdenada = refCarrito.orderByChild("correo_usuario").equalTo(firebaseUser.getEmail());

        // Lista para el historial
        consultaOrdenada.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Procedimiento que se ejecuta cuando hubo algun cambio
                // en la base de datos
                // Se actualiza la coleccion de personas
                historial.removeAll(historial);
                for (DataSnapshot dato : dataSnapshot.getChildren()) {
                    Carrito carrito = dato.getValue(Carrito.class);
                    carrito.setKey(dato.getKey());

                    if (carrito.isActividad()) {
                        Toast.makeText(HistorialActivity.this,
                                "No hay ninguna compra realizada", Toast.LENGTH_SHORT).show();
                    } else {
                        historial.add(carrito);
                        AdaptadorHistorial adapter = new AdaptadorHistorial(HistorialActivity.this,
                                historial);
                        listaHistorial.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), DashboardActivity.class);
                startActivity(i);
            }
        });
    }
}