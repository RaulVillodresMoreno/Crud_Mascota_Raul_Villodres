package com.example.Examen_PMDM_3T_Raul_Villodres;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MascotaViewModel extends AndroidViewModel {
    private MascotaRepository repository;
    private LiveData<List<Mascota>> allEmpleados;

    public MascotaViewModel(@NonNull Application application) {
        super(application);
        repository = new MascotaRepository(application);
        allEmpleados = repository.getAllMascotas();
    }

    public void insert(Mascota mascota){
        repository.insert(mascota);
    }

    public void update(Mascota mascota){
        repository.update(mascota);
    }

    public void delete(Mascota mascota){
        repository.delete(mascota);
    }

    public void deleteAllMascotas(){
        repository.deleteAllMascotas();
    }

    public LiveData<List<Mascota>> getAllMascotas(){
        return allEmpleados;
    }
}
