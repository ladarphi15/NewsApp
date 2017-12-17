package at.fh_joanneum.newsly.newsly.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import at.fh_joanneum.newsly.newsly.db.entity.RessortSetting;
import at.fh_joanneum.newsly.newsly.ressorts.RessortCategory;

/**
 * Created by edi on 06/05/2017.
 */

public class RessortSettingsRepository
        implements SettingsRepository<RessortSetting> {
    private SettingsDBHelper settingsHelper;

    public RessortSettingsRepository(Context context) {
        settingsHelper = new SettingsDBHelper(context);
    }

    @Override
    public Collection<RessortSetting> findAllActiveSettings() {
        final List<RessortSetting> ressortSettings = new ArrayList<>();
        final String query = "SELECT * FROM " + SettingsDBHelper.TABLE_RESSORT_SETTINGS +
                " WHERE " + SettingsDBHelper.COLUMN_RESSORT_ACTIVE + " = 1";
        final SQLiteDatabase database = settingsHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    final RessortSetting ressortSetting = new RessortSetting();

                    ressortSetting.setId(cursor.getLong(0));
                    ressortSetting.setCategory(RessortCategory.getRessortCategory(cursor.getString(1)));
                    ressortSetting.setActive(cursor.getInt(2) == 1);
                    ressortSettings.add(ressortSetting);
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
        return ressortSettings;
    }

    @Override
    public void updateState(final long id, final boolean active) {
        final SQLiteDatabase database = settingsHelper.getWritableDatabase();
        try {
            final ContentValues values = new ContentValues();
            values.put(SettingsDBHelper.COLUMN_RESSORT_ACTIVE, active ? 1 : 0);
            database.beginTransaction();
            database.update(SettingsDBHelper.TABLE_RESSORT_SETTINGS, values,
                    SettingsDBHelper.COLUMN_RESSORT_ID + " = ?",
                    new String[]{String.valueOf(id)});

            database.setTransactionSuccessful();
            database.endTransaction();
        } finally {
            database.close();
        }

    }

    @Override
    public Collection<RessortSetting> findAllSettings() {
        final List<RessortSetting> ressortSettings = new ArrayList<>();
        final SQLiteDatabase database = settingsHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.query(SettingsDBHelper.TABLE_RESSORT_SETTINGS,
                    new String[]{SettingsDBHelper.COLUMN_RESSORT_ID, SettingsDBHelper.COLUMN_RESSORT_CATEGORY, SettingsDBHelper.COLUMN_RESSORT_ACTIVE},
                    null, null, null, null,
                    SettingsDBHelper.COLUMN_RESSORT_ID
            );

            if (cursor.moveToFirst()) {
                do {
                    final RessortSetting ressortSetting = new RessortSetting();

                    ressortSetting.setId(cursor.getLong(0));
                    ressortSetting.setCategory(RessortCategory.getRessortCategory(cursor.getString(1)));
                    ressortSetting.setActive(cursor.getInt(2) == 1);
                    ressortSettings.add(ressortSetting);
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
        return ressortSettings;
    }
}
