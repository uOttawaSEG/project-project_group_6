package com.example.otams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StudentSessionAdapter extends RecyclerView.Adapter<StudentSessionAdapter.ViewHolder> {

    private List<SessionEntity> sessions;
    private OnActionClickListener listener;
    private AppDatabase db;
    private boolean isTutorView;

    public interface OnActionClickListener {
        void onCancelClick(SessionEntity session);
        void onRateClick(SessionEntity session);
    }

    public StudentSessionAdapter(List<SessionEntity> sessions, AppDatabase db, boolean isTutorView, OnActionClickListener listener) {
        this.sessions = sessions;
        this.db = db;
        this.isTutorView = isTutorView;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        if (isTutorView) {
            StudentEntity student = db.studentDao().getStudentById(session.studentId);
            if (student != null) {
                holder.infoText.setText("Student: " + student.firstName + " " + student.lastName);
            } else {
                holder.infoText.setText("Student ID: " + session.studentId);
            }
        } else {
            TutorEntity tutor = db.tutorDao().getTutorById(session.tutorId);
            if (tutor != null) {
                holder.infoText.setText("Tutor: " + tutor.firstName + " " + tutor.lastName);
            } else {
                holder.infoText.setText("Tutor ID: " + session.tutorId);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            boolean isPast = false;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);
                Date sessionDate = sdf.parse(session.date + " " + session.time);
                if (sessionDate != null && sessionDate.before(new Date())) {
                    isPast = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if ((isPast || "COMPLETED".equals(session.status)) && !"REJECTED".equals(session.status)) {
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
            infoText = itemView.findViewById(R.id.studentInfoText);
            statusText = itemView.findViewById(R.id.statusText);
        }
    }
}