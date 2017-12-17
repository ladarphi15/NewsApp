package at.fh_joanneum.newsly.newsly.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import at.fh_joanneum.newsly.newsly.db.entity.Source;
import at.fh_joanneum.newsly.newsly.ressorts.RessortCategory;

/**
 * Created by edi on 06/05/2017.
 */

public class SettingsDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SETTINGS.db";
    private static final int DATABASE_VERSION = 8;

    public static final String TABLE_SOURCE = "Source";
    public static final String COLUMN_SOURCE_ID = "_id";
    public static final String COLUMN_SOURCE_NAME = "name";
    public static final String COLUMN_SOURCE_SPORT = "sport_link";
    public static final String COLUMN_SOURCE_POLITICS = "politics_link";
    public static final String COLUMN_SOURCE_ECONOMY = "economy_link";
    public static final String COLUMN_SOURCE_LIFE = "life_link";
    public static final String COLUMN_SOURCE_EDUCATION = "education_link";
    public static final String COLUMN_SOURCE_CULTURE = "culture_link";

    public static final String TABLE_RESSORT_SETTINGS = "RessortSettings";
    public static final String COLUMN_RESSORT_ID = "_id";
    public static final String COLUMN_RESSORT_CATEGORY = "name";
    public static final String COLUMN_RESSORT_ACTIVE = "active";

    public static final String TABLE_SOURCE_SETTINGS = "SourceSettings";
    public static final String COLUMN_SOURCE_SETTING_ID = "_id";
    public static final String COLUMN_SOURCE_SETTING_NAME = "name";
    public static final String COLUMN_SOURCE_SETTING_ACTIVE = "active";

    private static final String TABLE_RESSORT_CREATE = "create table "
            + TABLE_RESSORT_SETTINGS + "("
            + COLUMN_RESSORT_ID + " integer primary key autoincrement, "
            + COLUMN_RESSORT_CATEGORY + " text not null, "
            + COLUMN_SOURCE_SETTING_ACTIVE + " integer);";

    private static final String TABLE_SOURCE_SETTINGS_CREATE = "create table "
            + TABLE_SOURCE_SETTINGS + "("
            + COLUMN_SOURCE_SETTING_ID + " integer primary key autoincrement, "
            + COLUMN_SOURCE_SETTING_NAME + " text not null, "
            + COLUMN_SOURCE_SETTING_ACTIVE + " integer);";

    private static final String TABLE_SOURCE_CREATE = "create table "
            + TABLE_SOURCE + "("
            + COLUMN_SOURCE_ID + " integer primary key autoincrement, "
            + COLUMN_SOURCE_NAME + " text not null, "
            + COLUMN_SOURCE_SPORT + " text, "
            + COLUMN_SOURCE_POLITICS + " text, "
            + COLUMN_SOURCE_ECONOMY + " text, "
            + COLUMN_SOURCE_LIFE + " text, "
            + COLUMN_SOURCE_EDUCATION + " text, "
            + COLUMN_SOURCE_CULTURE + " text); ";

    public SettingsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_RESSORT_CREATE);
        db.execSQL(TABLE_SOURCE_SETTINGS_CREATE);
        db.execSQL(TABLE_SOURCE_CREATE);

        initData(db);
    }

    private void initData(SQLiteDatabase db) {

        for (RessortCategory ressortCategory : RessortCategory.values()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_RESSORT_CATEGORY, ressortCategory.getValue());
            values.put(COLUMN_RESSORT_ACTIVE, 1);
            db.insert(TABLE_RESSORT_SETTINGS, null, values);
        }

        Source derStandard = new Source("derStandard.at",
                "http://derStandard.at/?page=rss&ressort=Sport",
                "http://derstandard.at/?page=rss&ressort=Seite1&searchText=Politik",
                "http://derStandard.at/?page=rss&ressort=Wirtschaft",
                "http://derStandard.at/?page=rss&ressort=Lifestyle",
                "http://derStandard.at/?page=rss&ressort=Bildung",
                "http://derStandard.at/?page=rss&ressort=Kultur");

        Source kleineZeitung = new Source("kleinezeitung.at",
                "http://www.kleinezeitung.at/rss/sport",
                "http://www.kleinezeitung.at/rss/politik",
                "http://www.kleinezeitung.at/rss/wirtschaft",
                "http://www.kleinezeitung.at/rss/leben",
                null,
                null);

        Source apa = new Source("ots.at (apa)",
                null,
                "https://www.ots.at/rss/politik",
                "https://www.ots.at/rss/wirtschaft",
                null,
                null,
                "https://www.ots.at/rss/kultur");

        Source kurier = new Source("kurier.at",
                null,
                "https://kurier.at/politik/xml/rssd",
                "https://kurier.at/wirtschaft/xml/rssd",
                "https://kurier.at/stars/xml/rssd",
                null,
                "https://kurier.at/kultur/xml/rssd");

        Source diePresse = new Source("diePresse.at",
                null,
                "http://diepresse.com/feeds/rsssearch.do?searchText=Politik",
                "http://diepresse.com/feeds/rsssearch.do?searchText=Wirtschaft",
                null,
                "http://diepresse.com/feeds/rsssearch.do?searchText=Bildung",
                "http://diepresse.com/feeds/rsssearch.do?searchText=Kultur");

        Source salzburgerNachrichten = new Source(
                "salzburg.com (Salzburger Nachrichten)",
                "http://www.salzburg.com/nachrichten/kategorie/6/rss.xml",
                "http://www.salzburg.com/nachrichten/kategorie/7/rss.xml",
                "http://www.salzburg.com/nachrichten/kategorie/5/rss.xml",
                "http://www.salzburg.com/nachrichten/kategorie/57/rss.xml",
                null,
                "http://www.salzburg.com/nachrichten/kategorie/8/rss.xml"
        );

        insertSource(db, derStandard);
        insertSource(db, apa);
        insertSource(db, kurier);
        insertSource(db, diePresse);
        insertSource(db, salzburgerNachrichten);
    }

    private void insertSource(SQLiteDatabase db, Source source) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_SOURCE_NAME, source.getName());
        values.put(COLUMN_SOURCE_SPORT, source.getSportLink());
        values.put(COLUMN_SOURCE_CULTURE, source.getCultureLink());
        values.put(COLUMN_SOURCE_ECONOMY, source.getEconomyLink());
        values.put(COLUMN_SOURCE_EDUCATION, source.getEducationLink());
        values.put(COLUMN_SOURCE_POLITICS, source.getPoliticsLink());
        values.put(COLUMN_SOURCE_LIFE, source.getLifeLink());
        long result = db.insert(TABLE_SOURCE, null, values);

        values = new ContentValues();
        values.put(COLUMN_SOURCE_SETTING_NAME, source.getName());
        values.put(COLUMN_SOURCE_SETTING_ACTIVE, 1);
        db.insert(TABLE_SOURCE_SETTINGS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESSORT_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOURCE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOURCE);
        onCreate(db);
    }
}
