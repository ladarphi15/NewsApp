package at.fh_joanneum.newsly.newsly.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import at.fh_joanneum.newsly.newsly.db.entity.Source;

/**
 * Created by edi on 07/05/2017.
 */

public class SourceRepository {
    private SettingsDBHelper settingsHelper;

    public SourceRepository(Context context) {
        settingsHelper = new SettingsDBHelper(context);
    }

    public List<Source> findAllSourceByNames(List<String> names) {
        if (names == null || names.isEmpty()) {
            return Collections.emptyList();
        }

        String whereClause = "";
        for (String name : names) {
            whereClause += "'" + name + "', ";
        }

        whereClause = whereClause.substring(0, whereClause.length() - 2);

        final List<Source> sources = new ArrayList<>();
        final String query =
                "SELECT  * FROM " + SettingsDBHelper.TABLE_SOURCE +
                        " WHERE " + SettingsDBHelper.COLUMN_SOURCE_NAME +
                        " IN (" + whereClause + ")";
        final SQLiteDatabase database = settingsHelper.getWritableDatabase();

        Cursor cursor = null;
        try {
            cursor = database.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    final Source source = new Source();
                    source.setId(cursor.getLong(0));
                    source.setName(cursor.getString(1));
                    source.setSportLink(cursor.getString(2));
                    source.setPoliticsLink(cursor.getString(3));
                    source.setEconomyLink(cursor.getString(4));
                    source.setLifeLink(cursor.getString(5));
                    source.setEducationLink(cursor.getString(6));
                    source.setCultureLink(cursor.getString(7));


                    sources.add(source);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return sources;
    }
}
