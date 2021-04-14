package com.example.bdd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public PlaneteDao planeteDao;

    final String PREFS_NAME = "preferences_file";

    private FloatingActionButton fab;
    private FragmentManager fm;
    public List<Planete> planets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "planetesDB").build();

        planeteDao = db.planeteDao();

        loadData(planeteDao);

        fm = getSupportFragmentManager();
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> {
            AddPlanetFragment fragment = AddPlanetFragment.newInstance();
            fragment.show(fm, "fragment_add_planet");
        });
    }

    private void loadData(PlaneteDao planeteDao) {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        new Thread((Runnable) () -> {

            if (settings.getBoolean("is_data_not_loaded", true)) {
                initData(planeteDao);
                settings.edit().putBoolean("is_data_not_loaded", false).commit();
            }

            planets = planeteDao.getAll();

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

        planets.add(new Planete("Mercure",4900));
        planets.add(new Planete("Venus",12000));
        planets.add(new Planete("Terre",12800));
        planets.add(new Planete("Mars",6800));
        planets.add(new Planete("Jupiter",144000));
        planets.add(new Planete("Saturne",120000));
        planets.add(new Planete("Uranus",52000));
        planets.add(new Planete("Neptune",50000));
        planets.add(new Planete("Pluton",2300));

        for (int index = 0; index < planets.size(); index++) {
            Planete planet = planets.get(index);
            planeteDao.insert(planet);
        }
    }

    public MyRecyclerViewAdapter getmAdapter() {
        return mAdapter;
    }
}