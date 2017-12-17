package at.fh_joanneum.newsly.newsly;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import at.fh_joanneum.newsly.newsly.db.RessortSettingsRepository;
import at.fh_joanneum.newsly.newsly.db.entity.RessortSetting;

public class RessortSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ressort_settings);
        ViewHelper.formatAppHeader(this);

        RessortSettingsRepository ressortSettingsRepository = new RessortSettingsRepository(this);

        final RessortSetting[] ressortSettings = ressortSettingsRepository.findAllSettings().toArray(new RessortSetting[0]);

        final ListView ressortListView = (ListView) findViewById(R.id.ressort_list_view);

        final SettingsAdapter<RessortSetting> adapter = new SettingsAdapter<>(this, R.layout.setting_row, ressortSettingsRepository);

        adapter.addAll(ressortSettings);
        ressortListView.setAdapter(adapter);
    }

}
