package com.example.Examen_PMDM_3T_Raul_Villodres;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MascotaRepository {
    private MascotaDao mascotaDao;
    private LiveData<List<Mascota>> allMascotas;

    public MascotaRepository(Application application){
        MascotaDatabase database = MascotaDatabase.getInstance(application);
        mascotaDao = database.noteDao();
        allMascotas = mascotaDao.getAllMascotas();
    }

    //Método para insertar
    public void insert (Mascota mascota){
        new InsertNoteAsyncTask(mascotaDao).execute(mascota);
    }

    //Método para actualizar
    public void update(Mascota mascota){
        new UpdateNoteAsyncTask(mascotaDao).execute(mascota);
    }

    //Método para borrar
    public void delete(Mascota mascota){
        new DeleteNoteAsyncTask(mascotaDao).execute(mascota);
    }

    public void deleteAllMascotas(){
        new DeleteAllMascotasAsyncTask(mascotaDao).execute();
    }

    public LiveData<List<Mascota>> getAllMascotas() {
        return allMascotas;
    }

    //Los asyncask son static para evitar perdida de información
    private static class InsertNoteAsyncTask extends AsyncTask<Mascota, Void, Void> {

        private MascotaDao mascotaDao;

        private InsertNoteAsyncTask(MascotaDao mascotaDao){
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas) {
            mascotaDao.insert(mascotas[0]); //Para colocarlo sobre el index 0
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Mascota, Void, Void> {

        private MascotaDao mascotaDao;

        private UpdateNoteAsyncTask(MascotaDao mascotaDao){
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas) {
            mascotaDao.update(mascotas[0]); //Para colocarlo sobre el index 0
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Mascota, Void, Void> {

        private MascotaDao mascotaDao;

        private DeleteNoteAsyncTask(MascotaDao mascotaDao){
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas) {
            mascotaDao.delete(mascotas[0]); //Para colocarlo sobre el index 0
            return null;
        }
    }

    private static class DeleteAllMascotasAsyncTask extends AsyncTask<Void, Void, Void> {

        private MascotaDao mascotaDao;

        private DeleteAllMascotasAsyncTask(MascotaDao mascotaDao){
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mascotaDao.deleteAllMascotas();
            return null;
        }
    }
}
