package at.fh_joanneum.newsly.newsly;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import at.fh_joanneum.newsly.newsly.adapter.RssArrayAdapter;
import at.fh_joanneum.newsly.newsly.db.entity.LinkSourceRessort;
import at.fh_joanneum.newsly.newsly.parser.DownloadRssTask;
import at.fh_joanneum.newsly.newsly.parser.RssEntry;
import at.fh_joanneum.newsly.newsly.sensor.ShakeDetector;

/**
 * Created by aneuh on 29.04.2017.
 */

public class TabNews extends ListFragment implements DownloadRssTask.AsyncResponse {

    private RssArrayAdapter adapter;

    private RessortService ressortService;
    private SwipeRefreshLayout newsLayout;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private ShakeDetector shakeDetector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_news, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new RssArrayAdapter(getActivity(), new ArrayList<RssEntry>());
        final ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                NewsWebViewActivity.CURRENT_RSS_ENTRY = (RssEntry) parent.getItemAtPosition(position);
                                                Intent intent = new Intent(getActivity(), NewsWebViewActivity.class);
                                                startActivity(intent);
                                            }
                                        }
        );


        newsLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.news_layout);
        newsLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
            }
        });

        ressortService = new RessortService(getActivity().getApplicationContext());

        // ShakeDetector initialization
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeDetector = new ShakeDetector();
        shakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
                loadNews();
            }
        });

        loadNews();
    }

    private void loadNews() {
        newsLayout.setRefreshing(true);
        final List<LinkSourceRessort> links = ressortService.getAllFeasibleLinks();

        DownloadRssTask task = new DownloadRssTask(this);
        task.execute(links);
    }

    @Override
    public void processFinish(List<RssEntry> output) {
        adapter.clear();
        adapter.addAll(output);
        setListAdapter(adapter);
        newsLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        sensorManager.unregisterListener(shakeDetector);
        super.onPause();
    }
}


