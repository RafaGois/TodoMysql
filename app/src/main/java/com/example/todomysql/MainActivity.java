package com.example.todomysql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    DataBaseHelser dataBaseHelser;
    MainAdapter mainAdapter;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        floatingActionButton = findViewById(R.id.floatingButton);


        dataBaseHelser = new DataBaseHelser(getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainAdapter = new MainAdapter(this,dataBaseHelser.getArray());

        recyclerView.setAdapter(mainAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                        Color.TRANSPARENT
                ));

                dialog.setContentView(R.layout.dialog_main);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                Button button = dialog.findViewById(R.id.botao);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sText = editText.getText().toString().trim();

                        String sDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(new Date());

                        dataBaseHelser.insert(sText,sDate);
                        mainAdapter.updateArray(dataBaseHelser.getArray());

                        dialog.dismiss();
                    }
                });
            }
        });
        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirmar");
                builder.setMessage("Tem certeza?");

                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataBaseHelser.truncate();
                        mainAdapter.updateArray(dataBaseHelser.getArray());

                        recyclerView.setAdapter(mainAdapter);
                    }
                });

                builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();

                return true;
            }
        });
    }
}