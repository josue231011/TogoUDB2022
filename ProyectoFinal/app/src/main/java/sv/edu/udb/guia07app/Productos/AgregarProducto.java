package sv.edu.udb.guia07app.Productos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sv.edu.udb.guia07app.Modelo.Producto;
import sv.edu.udb.guia07app.R;

public class AgregarProducto extends AppCompatActivity {
    EditText edtId, edtNombre, edtCategoria, edtDescripcion, edtStock, edtPrecio, edtImg;
    String key = "", id = "", nombre = "", categoria = "", descripcion = "", stock = "", img = "", accion = "";
    double precio = 0.0;
    Validaciones validar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producto);
        validar = new Validaciones();
        inicializar();
    }

    private void inicializar() {
        edtId = findViewById(R.id.edtId);
        edtNombre = findViewById(R.id.edtNombre);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtDescripcion = findViewById(R.id.edtDescripcion);
        edtStock = findViewById(R.id.edtEnStock);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtImg = findViewById(R.id.edtImg);

        // Obtención de datos que envia actividad anterior
        Bundle datos = getIntent().getExtras();
        key = datos.getString("key");
        id = datos.getString("id");
        nombre = datos.getString("nombre");
        categoria = datos.getString("categoria");
        descripcion = datos.getString("descripcion");
        stock = datos.getString("enStock");
        precio = datos.getDouble("precio");
        img = datos.getString("img");
        accion = datos.getString("accion");

        edtId.setText(id);
        edtNombre.setText(nombre);
        edtCategoria.setText(categoria);
        edtDescripcion.setText(descripcion);
        edtStock.setText(stock);
        edtPrecio.setText(Double.toString(precio));
        edtImg.setText(img);
    }

    public void guardar(View v) {
        try {
            String id = edtId.getText().toString();
            String nombre = edtNombre.getText().toString();
            String categoria = edtCategoria.getText().toString();
            String descripcion = edtDescripcion.getText().toString();
            String stock = edtStock.getText().toString();
            double precio = Double.parseDouble(edtPrecio.getText().toString());
            String img = edtImg.getText().toString();
            // Se forma objeto producto

            if (!validar.Vacio(edtId) && !validar.Vacio(edtNombre) && !validar.Vacio(edtCategoria) && !validar.Vacio(edtDescripcion) &&
                    !validar.Vacio(edtPrecio) && !validar.Vacio(edtStock)) {
                if(!validar.soloLetras(nombre)){
                    edtNombre.setError("Solo letras");
                    edtNombre.requestFocus();
                }else if(!validar.soloLetras(categoria)){
                    edtCategoria.setError("Solo letras");
                    edtCategoria.requestFocus();
                } else if(precio < 0.01){
                    edtPrecio.setError("Ingrese un precio mayor a 0");
                    edtPrecio.requestFocus();
                }else if(categoria.equals("Pizzas") || categoria.equals("Hamburguesas")  || categoria.equals("Postres")){
                    Producto producto = new Producto(id, nombre, categoria, descripcion, stock, precio, img);
                    if (accion.equals("a")) { //Agregar usando push()
                        ActividadProducto.refProductos.push().setValue(producto);
                    } else // Editar usando setValue
                    {
                        ActividadProducto.refProductos.child(key).setValue(producto);
                    }
                    finish();
                }else {
                    edtCategoria.setError("Categorias validas: Pizzas, Hamburguesas, Postres, Otros");
                    edtCategoria.requestFocus();
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelar(View v) {
        finish();
    }


}
