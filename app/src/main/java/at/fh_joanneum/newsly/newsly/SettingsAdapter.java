package at.fh_joanneum.newsly.newsly;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import at.fh_joanneum.newsly.newsly.db.SettingsRepository;
import at.fh_joanneum.newsly.newsly.db.entity.Setting;

public class SettingsAdapter<T extends Setting> extends ArrayAdapter<T> {
    private final SettingsRepository<T> settingsRepository;

    public SettingsAdapter(Context context, int resource, SettingsRepository<T> settingsRepository) {
        super(context, resource);

        this.settingsRepository = settingsRepository;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            convertView = inflater.inflate(R.layout.setting_row, parent, false);

            final CheckBox cbActive = (CheckBox) convertView.findViewById(R.id.setting_active);

            final T setting = getItem(position);

            cbActive.setText(setting.getName());
            if (setting.isActive()) {
                cbActive.setChecked(true);
            } else {
                cbActive.setChecked(false);
            }

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cbActive.setChecked(!cbActive.isChecked());
                    settingsRepository.updateState(setting.getId(), cbActive.isChecked());
                }
            });
        }
        return convertView;
    }
}
