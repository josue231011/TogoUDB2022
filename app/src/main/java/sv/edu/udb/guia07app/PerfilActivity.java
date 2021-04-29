package sv.edu.udb.guia07app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import sv.edu.udb.guia07app.Productos.ActividadProducto;

public class PerfilActivity extends AppCompatActivity {

    Button btnLogout;
    ImageView ivImage;
    TextView tvNombre;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth mAuth;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button btn_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ivImage = findViewById(R.id.iv_image);
        tvNombre = findViewById(R.id.tv_nombre);
        btnLogout = findViewById(R.id.btn_logout);
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(PerfilActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_producto:
                        Intent i = new Intent(PerfilActivity.this, ActividadProducto.class);
                        startActivity(i);
                        break;

                    case R.id.nav_perfil:
                        Intent i2 = new Intent(PerfilActivity.this, PerfilActivity.class);
                        startActivity(i2);
                        break;

//Paste your privacy policy link

//                    case  R.id.nav_Policy:{
//
//                        Intent browserIntent  = new Intent(Intent.ACTION_VIEW , Uri.parse(""));
//                        startActivity(browserIntent);
//
//                    }
                    //       break;
                    case  R.id.nav_pedidos:{

                        Intent intent2 = new Intent(PerfilActivity.this, DashboardActivity.class);
                        startActivity(intent2);
                        break;
                    }
                }
                return false;
            }
        });

        initializeViews();

        //Ingreso al carrito de compras
        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });


        googleSignInClient = GoogleSignIn.getClient(PerfilActivity.this
                , GoogleSignInOptions.DEFAULT_SIGN_IN);
        mAuth = FirebaseAuth.getInstance();

        //Iniciamos la auth de firebase
        mAuth = FirebaseAuth.getInstance();
        //Iniciamos firebase usuario
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            //Cuando el usuario de firebase es diferente de null
            //colocamos la img
            Glide.with(PerfilActivity.this)
                    .load(firebaseUser.getPhotoUrl())
                    .into(ivImage);
            //Colocamos el nombre
            if(firebaseUser.getDisplayName() != null){
                tvNombre.setText(firebaseUser.getDisplayName());
            }else{
                tvNombre.setText(firebaseUser.getEmail());
            }
        }else{
            //Cuando el usuario ya haya iniciado sesion
            //Redirigimos hacia el perfil
            startActivity(new Intent(PerfilActivity.this
                    , LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilActivity.this, LoginActivity.class);
                //Sign out de google
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //Si el task es exitoso
                            //sign out de firebase
                            mAuth.signOut();
                            //Mostramos msj en toast
                            Toast.makeText(getApplicationContext()
                                    ,"Logout exitoso",Toast.LENGTH_SHORT).show();
                            //Finalizamos la activity
                            startActivity(i);
                        }
                    }
                });
            }
        });
    }

    private void initializeViews() {
        btn_shop = findViewById(R.id.btn_shop);
    }


    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }
}