package com.example.Examen_PMDM_3T_Raul_Villodres;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.Examen_PMDM_3T_Raul_Villodres.databinding.ActivityAddMascotaBinding;

public class AddEditMascotaActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.Examen_PMDM_3T_Raul_Villodres.EXTRA_ID";
    public static final String EXTRA_NOMBRE = "com.example.Examen_PMDM_3T_Raul_Villodres.EXTRA_NOMBRE";
    public static final String EXTRA_RAZA = "com.example.Examen_PMDM_3T_Raul_Villodres.EXTRA_RAZA";
    public static final String EXTRA_EDAD = "com.example.Examen_PMDM_3T_Raul_Villodres.EXTRA_EDAD";
    public static final String EXTRA_RUTA_PARCIAL = "com.example.Examen_PMDM_3T_Raul_Villodres.EXTRA_RUTA_PARCIAL";
    private ActivityAddMascotaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMascotaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Mascota");
            binding.editTextNombre.setText(intent.getStringExtra(EXTRA_NOMBRE));
            binding.editTextRaza.setText(intent.getStringExtra(EXTRA_RAZA));
            binding.editTextEdad.setText(intent.getStringExtra(EXTRA_EDAD));
            binding.editTextRutaParcial.setText(intent.getStringExtra(EXTRA_RUTA_PARCIAL));

        } else {
            setTitle("Add Mascota");
        }

    }

    private void saveMascota(){
        String nombre = binding.editTextNombre.getText().toString();
        String raza = binding.editTextRaza.getText().toString();
        String edad = binding.editTextEdad.getText().toString();
        String ruta_parcial = binding.editTextRutaParcial.getText().toString();

        if(nombre.trim().isEmpty() || raza.trim().isEmpty() || edad.trim().isEmpty() || ruta_parcial.trim().isEmpty()){
            mostrarMensaje("Rellena todos los campos de la mascota");
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NOMBRE, nombre);
        data.putExtra(EXTRA_RAZA, raza);
        data.putExtra(EXTRA_EDAD, edad);
        data.putExtra(EXTRA_RUTA_PARCIAL, ruta_parcial);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != -1){
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_mascota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_mascota:
                saveMascota();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void mostrarMensaje(String texto){
        Toast.makeText(this, texto ,Toast.LENGTH_SHORT).show();
    }
}