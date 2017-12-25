package at.fh_joanneum.newsly.news4u;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import at.fh_joanneum.newsly.news4u.db.RessortSettingsRepository;
import at.fh_joanneum.newsly.news4u.db.entity.RessortSetting;

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
