package com.example.electrictime;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.electrictime.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private VehicleItemAdapter vehicleItemAdapter;

    private final ArrayList<VehicleData> vehicleDataArrayList = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        //populateVehicleData();
    }

    private void populateVehicleData() {

        vehicleDataArrayList.add(new VehicleData("Walking", R.mipmap.walking_man, 30, 3.1));
        vehicleDataArrayList.add(new VehicleData("Boosted Mini S Board", R.mipmap.boosted_board, 7, 18));
        vehicleDataArrayList.add(new VehicleData("Evolve Bamboo GTR 2in1", R.mipmap.evolve_board, 31, 24));
        vehicleDataArrayList.add(new VehicleData("OneWheel XR", R.mipmap.onewheel_board, 18, 19));
        vehicleDataArrayList.add(new VehicleData("MotoTec Skateboard", R.mipmap.mototec_board, 10, 22));
        vehicleDataArrayList.add(new VehicleData("Segway Ninebot S", R.mipmap.segway_ninebot, 13, 10));
        vehicleDataArrayList.add(new VehicleData("Segway Ninebot S-PLUS", R.mipmap.segway_i2_se, 22, 12));
        vehicleDataArrayList.add(new VehicleData("Razor Scooter ", R.mipmap.razor_scooter, 15, 18));
        vehicleDataArrayList.add(new VehicleData("GeoBlade 500", R.mipmap.geoblade_500, 8, 15));
        vehicleDataArrayList.add(new VehicleData("Hovertrax Hoverboard", R.mipmap.hovertrax_board, 6, 9));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateVehicleData();
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        vehicleItemAdapter = new VehicleItemAdapter(vehicleDataArrayList);
        vehicleItemAdapter.setVehicleItemClickListener(new VehicleItemAdapter.OnVehicleItemClickListener() {
            @Override
            public void OnVehicleItemClick(View view, VehicleData vehicleData, String finalTimeTaken) {
                binding.image.setImageResource(vehicleData.getImgId());
                binding.name.setText(String.format("Selected Transport :  %s", vehicleData.getTitle()));
                binding.time.setText(finalTimeTaken);
                if (binding.vehicleCard.getVisibility() == View.GONE) {
                    binding.vehicleCard.setVisibility(View.VISIBLE);
                }
            }
        });
        binding.vehiclesInfo.setAdapter(vehicleItemAdapter);
        binding.vehiclesInfo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        setSupportActionBar((Toolbar) binding.toolbar);
        configureToolbarLayout();
        binding.inputDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    if(s.toString().isEmpty()){
                        vehicleItemAdapter.setDistance(0);
                        binding.time.setText("");
                    }
                    vehicleItemAdapter.setDistance(Double.parseDouble(s.toString()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Log.d("Electric Time", "Invalid Input");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });
    }

    private void configureToolbarLayout() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Electric Time");
        }
    }
}

