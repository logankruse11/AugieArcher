package edu.augustana.csc490.augiearcher;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class PlayFragment extends Fragment {

    private PlayGameView playGameView; // gameView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
        {
            super.onCreateView(inflater, container, savedInstanceState);
            View view =
                    inflater.inflate(R.layout.fragment_play, container, false);

            playGameView = (PlayGameView) view.findViewById(R.id.mainGameView);
            return view;
        }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    // when paused, MainGameFragment stops the game
    @Override
    public void onPause()
    {
        super.onPause();
        playGameView.stopGame();
    }

    // when MainActivity is over, releases game resources
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        playGameView.releaseResources();
    }
}
