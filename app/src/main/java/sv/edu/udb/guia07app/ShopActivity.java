package sv.edu.udb.guia07app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShopActivity extends AppCompatActivity {

        private TextView mTextViewData;
        private DatabaseReference mDatabase;


    //Declarando botones de navegacion
    Button btn_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mTextViewData = (TextView) findViewById(R.id.txt_Platillo);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Pedido").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() ){
                    String  platillo  = snapshot.child("platillo").getValue().toString();
                    int  cantidad  = Integer.parseInt(snapshot.child("cantidad").getValue().toString());
                    String  ingrediente  = snapshot.child("ingrediente").getValue().toString();


                    mTextViewData.setText("Platillo :" + platillo +"  Ingredientes : "+ ingrediente + "  Cantidad :" +cantidad );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}