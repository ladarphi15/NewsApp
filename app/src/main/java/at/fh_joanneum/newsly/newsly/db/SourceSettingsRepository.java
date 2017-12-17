package at.fh_joanneum.newsly.newsly.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.fh_joanneum.newsly.newsly.db.entity.RessortSetting;
import at.fh_joanneum.newsly.newsly.db.entity.SourceSetting;
import at.fh_joanneum.newsly.newsly.ressorts.RessortCategory;

/**
 * Created by edi on 07/05/2017.
 */

public class SourceSettingsRepository
        implements SettingsRepository<SourceSetting> {
    private SettingsDBHelper settingsHelper;

    public SourceSettingsRepository(Context context) {
        settingsHelper = new SettingsDBHelper(context);
    }

    @Override
    public void updateState(long id, boolean active) {
        final SQLiteDatabase database = settingsHelper.getWritableDatabase();

        try {
            final ContentValues values = new ContentValues();
            values.put(SettingsDBHelper.COLUMN_SOURCE_SETTING_ACTIVE, active ? 1 : 0);
            database.beginTransaction();
            database.update(SettingsDBHelper.TABLE_SOURCE_SETTINGS, values,
                    SettingsDBHelper.COLUMN_SOURCE_SETTING_ID + " = ?",
                    new String[]{String.valueOf(id)});

            database.setTransactionSuccessful();
            database.endTransaction();
        } finally {
            database.close();
        }
    }

    @Override
    public Collection<SourceSetting> findAllSettings() {
        final List<SourceSetting> sourceSettings = new ArrayList<>();
        final SQLiteDatabase database = settingsHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.query(SettingsDBHelper.TABLE_SOURCE_SETTINGS,
                    new String[]{SettingsDBHelper.COLUMN_SOURCE_SETTING_ID,
                            SettingsDBHelper.COLUMN_SOURCE_SETTING_NAME,
                            SettingsDBHelper.COLUMN_SOURCE_SETTING_ACTIVE},
                    null, null, null, null,
                    SettingsDBHelper.COLUMN_SOURCE_SETTING_ID
            );

            if (cursor.moveToFirst()) {
                do {
                    final SourceSetting sourceSetting = new SourceSetting();

                    sourceSetting.setId(cursor.getLong(0));
                    sourceSetting.setName(cursor.getString(1));
                    sourceSetting.setActive(cursor.getInt(2) == 1);
                    sourceSettings.add(sourceSetting);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
        return sourceSettings;
    }

    @Override
    public Collection<SourceSetting> findAllActiveSettings() {
        final List<SourceSetting> sourceSettings = new ArrayList<SourceSetting>();
        final String query = "SELECT * FROM " + SettingsDBHelper.TABLE_SOURCE_SETTINGS +
                " WHERE " + SettingsDBHelper.COLUMN_SOURCE_SETTING_ACTIVE + " = 1";
        final SQLiteDatabase database = settingsHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    final SourceSetting sourceSetting = new SourceSetting();
                    sourceSetting.setId(cursor.getLong(0));
                    sourceSetting.setName(cursor.getString(1));
                    sourceSetting.setActive(cursor.getInt(2) == 1);
                    sourceSettings.add(sourceSetting);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            if (database != null) {
                database.close();
            }
        }
        return sourceSettings;
    }
}
