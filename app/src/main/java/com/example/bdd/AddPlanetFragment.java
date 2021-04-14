package com.example.bdd;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlanetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlanetFragment extends DialogFragment {

    EditText et_name, et_size;
    Button b_add_planet;

    public AddPlanetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddPlanetFragment.
     */
    public static AddPlanetFragment newInstance() {
        AddPlanetFragment fragment = new AddPlanetFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_planet, container, false);
        et_name = view.findViewById(R.id.planet_name);
        et_size = view.findViewById(R.id.planet_size);
        b_add_planet = view.findViewById(R.id.add_planet);
        b_add_planet.setOnClickListener(v -> {
            Boolean flag = true;

            String name = et_name.getText().toString();
            if (!name.matches("[a-z A-Z]+")) {
                Toast.makeText(getContext(), getString(R.string.name_invalid), Toast.LENGTH_SHORT).show();
                flag = false;
            }

            String sizeAsString = et_size.getText().toString();
            if (!sizeAsString.matches("[0-9]+")) {
                Toast.makeText(getContext(), getString(R.string.size_invalid), Toast.LENGTH_SHORT).show();
                flag = false;
            }

            if (flag) {
                Integer size = Integer.parseInt(sizeAsString);
                MainActivity ma = (MainActivity) getActivity();
                new Thread((Runnable) () -> {
                    Planete new_planet = new Planete(name, size);
                    ma.planeteDao.insert(new_planet);
                    ma.planets.add(new_planet);
                    ma.mRecyclerView.post(() -> ma.getmAdapter().notifyDataSetChanged());
                }).start();
                getDialog().dismiss();
            }
        });

        return view;
    }
}