package com.example.todolist;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText addTaskInput;
    private Button addTaskButton;
    private LinearLayout tasksLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addTaskInput = findViewById(R.id.addTaskInput);
        addTaskButton = findViewById(R.id.addTaskButton);
        tasksLayout = findViewById(R.id.tasksLayout);

        addTaskButton.setOnClickListener(view -> {
            String inputText = addTaskInput.getText().toString();

            if (!inputText.trim().isEmpty()) { // solo añade tarea si hay texto
                TextView newTask = new TextView(this); // crea nuevo textview en la app
                newTask.setTextColor(Color.BLACK);
                newTask.setText("· "+inputText.strip()); // establece como texto el que habia en el input
                newTask.setTextSize(22); // tamaño de texto
                newTask.setPadding(60, 60, 60, 60); // padding del textview
                newTask.setBackgroundColor(Color.TRANSPARENT);

                // cuando se clica la tarea cambia de color para mostrar que esta hecha
                // en caso de ya estar marcada como hecha la desmarca
                newTask.setOnClickListener(clicTask -> {
                    if (((ColorDrawable) newTask.getBackground()).getColor() == Color.TRANSPARENT) {
                        newTask.setBackgroundColor(Color.GREEN);
                    } else {
                        newTask.setBackgroundColor(Color.TRANSPARENT);
                    }
                });

                // cuando se deja presionada se borra
                newTask.setOnLongClickListener(holdTask -> {
                    tasksLayout.removeView(newTask); // borra tarea

                    // muestra pequeño mensaje flotante
                    Toast.makeText(MainActivity.this, "Tarea borrada.", Toast.LENGTH_SHORT).show();
                    return true;
                });

                // añade al layout de tareas la nueva tarea generada
                tasksLayout.addView(newTask);

                // vaciar input
                addTaskInput.setText("");
                // deja de enfocarse en el input
                addTaskInput.clearFocus();
                hideKeyboard(view);
            } else {
                // muestra pequeño mensaje flotante
                Toast.makeText(MainActivity.this, "No hay texto que añadir.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Investigado, oculta el teclado
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}