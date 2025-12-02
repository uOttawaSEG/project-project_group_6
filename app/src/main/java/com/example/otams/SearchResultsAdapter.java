package com.example.otams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {

    private List<AvailabilitySlotEntity> slots;
    private AppDatabase db;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(AvailabilitySlotEntity slot);
    }

    public SearchResultsAdapter(List<AvailabilitySlotEntity> slots, AppDatabase db, OnBookClickListener listener) {
        this.slots = slots;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AvailabilitySlotEntity slot = slots.get(position);

        TutorEntity tutor = db.tutorDao().getTutorById(slot.tutorId);
        if (tutor != null) {
            holder.tutorName.setText("Tutor: " + tutor.firstName + " " + tutor.lastName);

            Float avg = db.sessionDao().getAverageRating(slot.tutorId);
            if (avg != null && avg > 0) {
                holder.ratingText.setText(String.format("Rating: %.1f / 5.0", avg));
            } else {
                holder.ratingText.setText("Rating: N/A");
            }
        } else {
            holder.tutorName.setText("Tutor ID: " + slot.tutorId);
            holder.ratingText.setText("Rating: N/A");
        }

        holder.dateTimeText.setText(slot.date + " @ " + slot.startTime);

        holder.bookBtn.setOnClickListener(v -> listener.onBookClick(slot));
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tutorName, dateTimeText, ratingText;
        Button bookBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            tutorName = itemView.findViewById(R.id.tutorName);
            dateTimeText = itemView.findViewById(R.id.dateTimeText);
            ratingText = itemView.findViewById(R.id.ratingText);
            bookBtn = itemView.findViewById(R.id.bookBtn);
        }
    }
}