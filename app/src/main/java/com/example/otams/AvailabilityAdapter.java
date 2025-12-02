package com.example.otams;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AvailabilityAdapter extends RecyclerView.Adapter<AvailabilityAdapter.ViewHolder> {

    private List<AvailabilitySlotEntity> slots;
    private OnDeleteClickListener listener;

    public interface OnDeleteClickListener {
        void onDeleteClick(AvailabilitySlotEntity slot);
    }

    public AvailabilityAdapter(List<AvailabilitySlotEntity> slots, OnDeleteClickListener listener) {
        this.slots = slots;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_availability_slot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AvailabilitySlotEntity slot = slots.get(position);

        holder.dateText.setText("Date: " + slot.date);
        holder.timeText.setText("Time: " + slot.startTime + " - " + slot.endTime);
        holder.autoApproveText.setText("Auto-approve: " + (slot.autoApprove ? "Yes" : "No"));

        holder.deleteBtn.setOnClickListener(v -> listener.onDeleteClick(slot));
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText, timeText, autoApproveText;
        Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
            timeText = itemView.findViewById(R.id.timeText);
            autoApproveText = itemView.findViewById(R.id.autoApproveText);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}