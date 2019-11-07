package com.ceng319.testsql;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FlagViewModel mFlagViewModel;
    private String selected ="*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the recyclerview of the main activity.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFlagViewModel = new ViewModelProvider(this).get(FlagViewModel.class);
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

        mFlagViewModel.getmAllSelectedFlags(selected).observe(this, new Observer<List<FlagEntity>>() {
            @Override
            public void onChanged(@Nullable final List<FlagEntity> flags) {
                // Update the cached copy of the flags in the adapter.
                adapter.setFlags(flags);
            }
        });

        addAdapter();
    }

    private void addAdapter() {
        Spinner spinner = findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.continent_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                String selection = adapterView.getItemAtPosition(pos).toString();
                switch (pos){
                    case 0:
                    default:
                        selection ="%";  // If All are selected
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 5:
                        selection = selection + "%";  // If All are selected
                        break;
                    case 4:
                        selection = "North_America" + "%";  // If "North America"
                        break;
                    case 6:
                        selection = "South_America" + "%";  // If "South America"
                        break;
                }
                mFlagViewModel.changeFlagType(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onClick(View view) {

    }
}
