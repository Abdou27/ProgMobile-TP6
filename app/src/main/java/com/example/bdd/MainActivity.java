package com.example.bdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    final String PREFS_NAME = "preferences_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "planetesDB").build();

        PlaneteDao planeteDao = db.planeteDao();

        loadData(planeteDao);
    }

    private void loadData(PlaneteDao planeteDao) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        new Thread((Runnable) () -> {

            if (settings.getBoolean("is_data_not_loaded", true)) {
                initData(planeteDao);
                settings.edit().putBoolean("is_data_not_loaded", false).commit();
            }

            List<Planete> planets = planeteDao.getAll();

            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyRecyclerViewAdapter(getApplicationContext(), planets);
            mRecyclerView.setAdapter(mAdapter);
        }).start();

    }

    private void initData(PlaneteDao planeteDao) {

        ArrayList<Planete> planets = new ArrayList<>();

        planets.add(new Planete(1,"Mercure",4900));
        planets.add(new Planete(2,"Venus",12000));
        planets.add(new Planete(3,"Terre",12800));
        planets.add(new Planete(4,"Mars",6800));
        planets.add(new Planete(5,"Jupiter",144000));
        planets.add(new Planete(6,"Saturne",120000));
        planets.add(new Planete(7,"Uranus",52000));
        planets.add(new Planete(8,"Neptune",50000));
        planets.add(new Planete(9,"Pluton",2300));

        for (int index = 0; index < planets.size(); index++) {
            Planete planet = planets.get(index);
            planeteDao.insert(planet);
        }
    }
}