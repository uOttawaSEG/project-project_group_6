package com.example.otams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PendingRequestsAdapter extends RecyclerView.Adapter<PendingRequestsAdapter.ViewHolder> {

    private List<SessionEntity> sessions;
    private AppDatabase db;
    private OnActionListener listener;

    public interface OnActionListener {
        void onApprove(SessionEntity session);
        void onReject(SessionEntity session);
    }

    public PendingRequestsAdapter(List<SessionEntity> sessions, AppDatabase db, OnActionListener listener) {
        this.sessions = sessions;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_tutor_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SessionEntity session = sessions.get(position);

        // Fetch Student Name
        StudentEntity student = db.studentDao().getStudentById(session.studentId);
        if (student != null) {
            holder.studentName.setText(student.firstName + " " + student.lastName);
        } else {
            holder.studentName.setText("Student ID: " + session.studentId);
        }

        holder.courseText.setText("Course: " + session.courseCode);
        holder.dateTimeText.setText(session.date + " @ " + session.time);

        holder.btnApprove.setOnClickListener(v -> listener.onApprove(session));
        holder.btnReject.setOnClickListener(v -> listener.onReject(session));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, courseText, dateTimeText;
        Button btnApprove, btnReject;

        public ViewHolder(View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            courseText = itemView.findViewById(R.id.courseText);
            dateTimeText = itemView.findViewById(R.id.dateTimeText);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}