package at.fh_joanneum.newsly.newsly;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by aneuh on 29.04.2017.
 */

public class TabSettings extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Button sourcesButton = (Button) getView().findViewById(R.id.sources);
        sourcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SourcesSettingsActivity.class);
                startActivity(intent);
            }
        });

        final Button ressourcesButtons = (Button) getView().findViewById(R.id.ressorts);
        ressourcesButtons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RessortSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
