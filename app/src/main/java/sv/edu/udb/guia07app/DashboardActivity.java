package sv.edu.udb.guia07app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import sv.edu.udb.guia07app.Productos.ActividadProducto;

public class DashboardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Button btn_shop;
    ImageView ima1,ima2,ima3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case  R.id.nav_home:

                        Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_producto:
                        Intent i = new Intent(DashboardActivity.this, ActividadProducto.class);
                        startActivity(i);
                        break;

                    case R.id.nav_perfil:
                        Intent i2 = new Intent(DashboardActivity.this, PerfilActivity.class);
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

                        Intent intent2 = new Intent(DashboardActivity.this, DashboardActivity.class);
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
                Intent intent = new Intent(DashboardActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });


        //Ingreso al menu de hamburgesas
        ima1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, HamburguesasActivity.class);
                startActivity(intent);
            }
        });

        //Ingreso al menu de pizzas
        ima2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PizzaActivity.class);
                startActivity(intent);
            }
        });

        //Ingreso al menu de Postres
        ima3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PostresActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initializeViews() {
        btn_shop = findViewById(R.id.btn_shop);
        ima1 = findViewById(R.id.hamburger_img);
        ima2 = findViewById(R.id.pizzasView1);
        ima3 = findViewById(R.id.postresView);
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