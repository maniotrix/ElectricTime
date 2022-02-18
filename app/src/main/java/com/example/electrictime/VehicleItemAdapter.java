package com.example.electrictime;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.electrictime.databinding.CardItemsBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class VehicleItemAdapter extends RecyclerView.Adapter<VehicleItemAdapter.VehicleItemHolder> {

    private final ArrayList<VehicleData> vehicleDataArrayList;
    private OnVehicleItemClickListener listener;
    private double distance;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    int selectedPosition=-1;

    public VehicleItemAdapter(ArrayList<VehicleData> vehicles) {
        this.vehicleDataArrayList = vehicles;
        setSelectedPosition(0);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
        notifyDataSetChanged();
    }

    private double calculateVehicleTimeForDistance(VehicleData vehicleData, double distance) {
        if (distance == 0) return 0;
        if(distance > vehicleData.getRange()) return -1;
        double speed = vehicleData.getSpeed();
        double timeTaken = (distance / speed) * 60;
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(timeTaken));
    }

    @NonNull
    @Override
    public VehicleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VehicleItemHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.card_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleItemHolder viewHolder, int position) {
        VehicleData vehicleData = vehicleDataArrayList.get(position);
        if (vehicleData == null) {
            return;
        }

        if (selectedPosition == position) {
            viewHolder.binding.vehicleCard.setCardBackgroundColor(Color.parseColor("#000000"));
            viewHolder.binding.time.setTextColor(Color.parseColor("#ffffff"));
        } else {
            viewHolder.binding.vehicleCard.setCardBackgroundColor(Color.parseColor("#ffffff"));
            viewHolder.binding.time.setTextColor(Color.parseColor("#000000"));
        }

        viewHolder.binding.name.setText(vehicleData.getTitle());

        String timeTaken = "";
        final double calculation  = calculateVehicleTimeForDistance(vehicleData, this.distance);
        if(calculation == 0){
            timeTaken = "";
        }
        else if(calculation == -1){
            timeTaken = "Not available (Distance Out of Range)!";
        }
        else {
            try {
                timeTaken = "Travelling Time :  " + String.valueOf(calculation) + " Mins";
            } catch (Exception exception) {
                exception.printStackTrace();
                Log.d("Electric Time", "Invalid Distance");
            }
        }
        viewHolder.binding.time.setText(timeTaken);
        viewHolder.binding.image.setImageResource(vehicleData.getImgId());

        if (selectedPosition == position) {
            VehicleItemAdapter.this.listener.OnVehicleItemClick(viewHolder.binding.vehicleCard, vehicleData, timeTaken);
        }

        String finalTimeTaken = timeTaken;
        viewHolder.binding.vehicleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VehicleItemAdapter.this.listener != null) {
                    VehicleItemAdapter.this.listener.OnVehicleItemClick(viewHolder.binding.vehicleCard, vehicleData, finalTimeTaken);
                    Log.d("Electric Time", "Onclick");
                    selectedPosition=position;
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return vehicleDataArrayList.size();
    }

    public void setVehicleItemClickListener(OnVehicleItemClickListener listener) {
        this.listener = listener;
    }


    public void insert(VehicleData data, int position) {
        vehicleDataArrayList.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(VehicleData data, int position) {
        vehicleDataArrayList.remove(data);
        notifyItemRemoved(position);
    }

    public interface OnVehicleItemClickListener {
        void OnVehicleItemClick(View view, VehicleData vehicleData, String finalTimeTaken);
    }

    static class VehicleItemHolder extends RecyclerView.ViewHolder {
        private final CardItemsBinding binding;

        private VehicleItemHolder(CardItemsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

}