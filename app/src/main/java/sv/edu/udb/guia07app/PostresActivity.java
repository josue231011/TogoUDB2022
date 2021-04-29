package sv.edu.udb.guia07app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class PostresActivity extends AppCompatActivity {

    private TextView mTextViewData;
    private DatabaseReference mDatabase;


    //Declarando botones de navegacion
    Button btn_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postres);



    }




}