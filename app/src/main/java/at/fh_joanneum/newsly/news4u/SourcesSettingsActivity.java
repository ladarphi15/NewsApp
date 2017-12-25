package at.fh_joanneum.newsly.news4u;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import at.fh_joanneum.newsly.news4u.db.SourceSettingsRepository;
import at.fh_joanneum.newsly.news4u.db.entity.SourceSetting;

public class SourcesSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sources_settings);
        ViewHelper.formatAppHeader(this);

        SourceSettingsRepository sourceSettingsRepository = new SourceSettingsRepository(this);

        final SourceSetting[] sourceSettings = sourceSettingsRepository.findAllSettings().toArray(new SourceSetting[0]);

        final ListView sourceListView = (ListView) findViewById(R.id.sources_list_view);

        final SettingsAdapter<SourceSetting> adapter = new SettingsAdapter<>(this, R.layout.setting_row, sourceSettingsRepository);

        adapter.addAll(sourceSettings);
        sourceListView.setAdapter(adapter);
    }
}
