package com.example.Examen_PMDM_3T_Raul_Villodres;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Mascota.class}, version = 1)
public abstract class MascotaDatabase extends RoomDatabase {

    private static MascotaDatabase instance;

    public abstract MascotaDao noteDao();

    public static synchronized MascotaDatabase getInstance(Context context){
        if(instance == null){
            //Creamos una nueva entrada, que recibe el contexto de nuestra aplicacion, esta misma clase y el nombre de la tabla, cuidando errores de versión
           instance = Room.databaseBuilder(context.getApplicationContext(), MascotaDatabase.class, "mascota_database").
                   fallbackToDestructiveMigration().
                   addCallback(roomCallback).
                   build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private MascotaDao mascotaDao;

        private PopulateDbAsyncTask(MascotaDatabase db) {
            mascotaDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //mascotaDao.insert(new Mascota("Title1" , "Description1", "123123","aaaa","qqq","bbb","ccc"));
            //mascotaDao.insert(new Mascota("Title1" , "Description1", "123123","aaaa","qqq","bbb","ccc"));
            //mascotaDao.insert(new Mascota("Title1" , "Description1", "123123","aaaa","qqq","bbb","ccc"));
            return null;
        }
    }

}
