package com.example.Examen_PMDM_3T_Raul_Villodres;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Examen_PMDM_3T_Raul_Villodres.databinding.ActivityAddMascotaBinding;
import com.example.Examen_PMDM_3T_Raul_Villodres.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private MascotaViewModel mascotaViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Creamos la URL Básica
        String urlBase = "https://www.dam.org.es/";

        //Creamos unas shared preferences
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);

        //Instanciamos el botón y le ponemos una función al ser pulsado
        binding.buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditMascotaActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        //Instanciamos el recycler view
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        MascotaAdapter adapter = new MascotaAdapter();
        binding.recyclerView.setAdapter(adapter);

        mascotaViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MascotaViewModel.class);
        mascotaViewModel.getAllMascotas().observe(this, new Observer<List<Mascota>>() {
            @Override
            public void onChanged(List<Mascota> mascotas) {
                adapter.submitList(mascotas);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mascotaViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                mostrarMensaje("Nota eliminada");
            }
        }).attachToRecyclerView(binding.recyclerView);

        //Manejamos el dar click en un elemento
        adapter.setOnItemClickListener(new MascotaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Mascota mascota) {
                Intent intent = new Intent(MainActivity.this, AddEditMascotaActivity.class);
                intent.putExtra(AddEditMascotaActivity.EXTRA_ID, mascota.getId());
                intent.putExtra(AddEditMascotaActivity.EXTRA_NOMBRE, mascota.getNombre());
                intent.putExtra(AddEditMascotaActivity.EXTRA_RAZA, mascota.getRaza());
                intent.putExtra(AddEditMascotaActivity.EXTRA_EDAD, mascota.getEdad());
                intent.putExtra(AddEditMascotaActivity.EXTRA_RUTA_PARCIAL, mascota.getRutaParcial());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });

        try {
            if (firstStart) {
                OkHttpClient client = new OkHttpClient();

                String url = "https://dam.org.es/files/enlaces.txt";
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    String[] obtenerUrls = myResponse.split("\n");

                                    for (int i = 0; i < obtenerUrls.length; i++) {
                                        OkHttpClient client = new OkHttpClient();

                                        //Creamos dos ArrayList, de mascotas y de urls
                                        ArrayList<Mascota> mascotaArrayList = new ArrayList<>();
                                        ArrayList<String> urlsList = new ArrayList<>();

                                        String url = obtenerUrls[i];
                                        Request request = new Request.Builder()
                                                .url(url)
                                                .build();
                                        client.newCall(request).enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Request request, IOException e) {

                                            }

                                            @Override
                                            public void onResponse(Response response) throws IOException {
                                                if (response.isSuccessful()) {
                                                    final String myresponse2 = response.body().string();
                                                    MainActivity.this.runOnUiThread(new Runnable() {

                                                        @Override
                                                        public void run() {

                                                            String nombre = "";
                                                            String raza = "";
                                                            String edad = "";
                                                            String rutaParcial = "";

                                                            String[] obtenerDatosMascota = myresponse2.split("\n");
                                                            for (int i = 0; i < (obtenerDatosMascota.length); i++) {
                                                                String[] DatosMascota = obtenerDatosMascota[i].split(":");

                                                                if (i == 0) {
                                                                    nombre = DatosMascota[1];
                                                                } else if (i == 1) {
                                                                    raza = DatosMascota[1];
                                                                } else if (i == 2) {
                                                                    edad = DatosMascota[1];
                                                                } else if (i == 3) {
                                                                    rutaParcial = DatosMascota[1];
                                                                    urlsList.add(rutaParcial);
                                                                }
                                                            }
                                                            Mascota emp = new Mascota(nombre, raza, edad, rutaParcial);
                                                            mascotaArrayList.add(emp);

                                                            for (int i = 0; i < mascotaArrayList.size(); i++) {
                                                                mascotaViewModel.insert(mascotaArrayList.get(i));
                                                                //Picasso.with(this).load(urlBase + urlsList[i]).into(imageView);

                                                            }
                                                        }
                                                    });
                                                }

                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("firstStart", false);
                editor.apply();
            }
        } catch (Exception e){
            mostrarMensaje("Las mascotas predeterminadas no se pudieron crear");
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String nombre = data.getStringExtra(AddEditMascotaActivity.EXTRA_NOMBRE);
            String raza = data.getStringExtra(AddEditMascotaActivity.EXTRA_RAZA);
            String edad = data.getStringExtra(AddEditMascotaActivity.EXTRA_EDAD);
            String ruta_parcial = data.getStringExtra(AddEditMascotaActivity.EXTRA_RUTA_PARCIAL);

            Mascota mascota = new Mascota(nombre, raza, edad, ruta_parcial);
            mascotaViewModel.insert(mascota);

            mostrarMensaje("Mascota guardada");
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditMascotaActivity.EXTRA_ID, -1);

            if(id == -1){
                mostrarMensaje("No se pudo actualizar el valor");
                return;
            }

            String nombre = data.getStringExtra(AddEditMascotaActivity.EXTRA_NOMBRE);
            String raza = data.getStringExtra(AddEditMascotaActivity.EXTRA_RAZA);
            String edad = data.getStringExtra(AddEditMascotaActivity.EXTRA_EDAD);
            String ruta_parcial = data.getStringExtra(AddEditMascotaActivity.EXTRA_RUTA_PARCIAL);

            Mascota mascota = new Mascota(nombre, raza, edad, ruta_parcial);
            mascota.setId(id);
            mascotaViewModel.update(mascota);

            mostrarMensaje("Mascota añadida");


        } else {
            mostrarMensaje("No se pudo guardar la mascota");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all_mascotas :
                mascotaViewModel.deleteAllMascotas();
                mostrarMensaje("Se han eliminado todas las notas");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void mostrarMensaje(String texto){
        Toast.makeText(MainActivity.this, texto ,Toast.LENGTH_SHORT).show();
    }
}