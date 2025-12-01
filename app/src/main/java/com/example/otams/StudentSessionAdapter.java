package com.example.otams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentSessionAdapter extends RecyclerView.Adapter<StudentSessionAdapter.ViewHolder> {

    private List<SessionEntity> sessions;
    private OnActionClickListener listener;
    private AppDatabase db; // Reference to DB to fetch tutor names

    public interface OnActionClickListener {
        void onCancelClick(SessionEntity session);
        void onRateClick(SessionEntity session);
    }

    public StudentSessionAdapter(List<SessionEntity> sessions, AppDatabase db, OnActionClickListener listener) {
        this.sessions = sessions;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // We reuse the row_session layout or you can create a specific one.
        // For simplicity, we reuse row_session but you might want to hide student info text in the binding.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_session, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SessionEntity session = sessions.get(position);

        holder.courseText.setText(session.courseCode);
        holder.dateText.setText(session.date);
        holder.timeText.setText(session.time);
        holder.statusText.setText("Status: " + session.status);

        // Fetch Tutor Name dynamically
        // Note: Running DB query on main thread is not ideal but allowed for this project scope
        TutorEntity tutor = db.tutorDao().getTutorById(session.tutorId);
        if (tutor != null) {
            holder.infoText.setText("Tutor: " + tutor.firstName + " " + tutor.lastName);
        } else {
            holder.infoText.setText("Tutor ID: " + session.tutorId);
        }

        // Handle Button Visibility
        // We are hijacking the card view. Ideally, you should add specific buttons to row_session.xml
        // or create a row_student_session.xml.
        // For now, assume the user clicks the item to act, or we add logic here.

        holder.itemView.setOnClickListener(v -> {
            if ("COMPLETED".equals(session.status)) {
                listener.onRateClick(session);
            } else {
                listener.onCancelClick(session);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseText, dateText, timeText, infoText, statusText;

        public ViewHolder(View itemView) {
            super(itemView);
            courseText = itemView.findViewById(R.id.courseText);
            dateText = itemView.findViewById(R.id.dateText);
            timeText = itemView.findViewById(R.id.timeText);
            // Reusing the ID 'studentInfoText' but using it to display Tutor info
            infoText = itemView.findViewById(R.id.studentInfoText);
            statusText = itemView.findViewById(R.id.statusText);
        }
    }
}