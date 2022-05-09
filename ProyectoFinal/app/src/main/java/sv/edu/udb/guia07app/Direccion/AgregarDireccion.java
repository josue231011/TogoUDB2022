package sv.edu.udb.guia07app.Direccion;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import sv.edu.udb.guia07app.Modelo.Direccion;
import sv.edu.udb.guia07app.Modelo.Producto;
import sv.edu.udb.guia07app.Productos.ActividadProducto;
import sv.edu.udb.guia07app.Productos.Validaciones;
import sv.edu.udb.guia07app.R;

public class AgregarDireccion extends AppCompatActivity {
    EditText edtId, edtNombre, edtDireccion;
    String key="",id="",nombre_direccion="",direccion="",correo="",accion="";
    Validaciones validar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_direccion);
        validar = new Validaciones();
        inicializar();
    }

    private void inicializar() {
        edtId = findViewById(R.id.edtId);
        edtNombre = findViewById(R.id.edtNombre);
        edtDireccion = findViewById(R.id.edtDireccion);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        key = datos.getString("key");
        nombre_direccion=datos.getString("nombre");
        direccion=datos.getString("direccion");
        correo=datos.getString("correo");
        accion=datos.getString("accion");

        edtNombre.setText(nombre_direccion);
        edtDireccion.setText(direccion);
    }

    public void guardar(View v){
        String nombre = edtNombre.getText().toString();
        String direccion = edtDireccion.getText().toString();

        if (!validar.Vacio(edtNombre) && !validar.Vacio(edtDireccion)){
            if(!validar.soloLetras(nombre)){
                edtNombre.setError("Solo letras");
                edtNombre.requestFocus();
            }else{
                // Se forma objeto producto
                Direccion direcciones = new Direccion(nombre,direccion,correo);

                if (accion.equals("a")) { //Agregar usando push()
                    ActividadDireccion.refDireccion.push().setValue(direcciones);
                }
                else // Editar usando setValue
                {
                    ActividadDireccion.refDireccion.child(key).setValue(direcciones);
                }
                finish();
            }
        }
    }
    public void cancelar(View v){
        finish();
    }


}
