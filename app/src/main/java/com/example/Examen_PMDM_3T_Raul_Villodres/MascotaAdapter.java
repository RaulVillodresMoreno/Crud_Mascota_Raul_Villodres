package com.example.Examen_PMDM_3T_Raul_Villodres;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MascotaAdapter extends ListAdapter<Mascota, MascotaAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public MascotaAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Mascota> DIFF_CALLBACK = new DiffUtil.ItemCallback<Mascota>() {
        @Override
        public boolean areItemsTheSame(@NonNull Mascota oldItem, @NonNull Mascota newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Mascota oldItem, @NonNull Mascota newItem) {
            return oldItem.getNombre().equals(newItem.getNombre()) &&
                    oldItem.getRaza().equals(newItem.getRaza()) &&
                    oldItem.getEdad().equals(newItem.getEdad()) &&
                    oldItem.getRutaParcial().equals(newItem.getRutaParcial());
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mascota_item, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Mascota currentMascota = getItem(position);
        holder.textViewNombre.setText(currentMascota.getNombre());
        holder.textViewRaza.setText(currentMascota.getRaza());
        holder.textViewEdad.setText(currentMascota.getEdad());
        holder.textViewRutaParcial.setText(currentMascota.getRutaParcial());

    }

    public Mascota getNoteAt(int position){
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewRaza;
        private TextView textViewEdad;
        private TextView textViewRutaParcial;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
            textViewRaza = itemView.findViewById(R.id.text_view_raza);
            textViewEdad = itemView.findViewById(R.id.text_view_edad);
            textViewRutaParcial = itemView.findViewById(R.id.text_view_ruta_parcial);

            //Aquí obtenemos la posición del click, tomando en cuenta por ejemplo el pulsar una opción recientemente borrada para evitar errores.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getItem(position));
                    }

                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Mascota mascota);
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        this.listener = listener;
    }
}
