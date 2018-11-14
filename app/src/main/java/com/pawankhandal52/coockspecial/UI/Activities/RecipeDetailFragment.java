/*
 * Copyright (C) 2018 The Android Nanodegree Project made under Udacity Nanodegree Course
 * Author Pawan Kumar Sharma
 * All Rights Reserved
 */
package com.pawankhandal52.coockspecial.UI.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pawankhandal52.coockspecial.Models.Step;
import com.pawankhandal52.coockspecial.R;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeIngredientsActivity}
 * in two-pane mode (on tablets) or a {@link RecipeIngredientsSinglePaneActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment implements View.OnClickListener {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String STEP_LIST = "step_list";
    public static final String INGREDIENT_LIST = "ingredient_list";
    
    public static final String ARG_STEP_POSITION = "step_postion";
    private static int sStepPostion;
    private final String TAG = RecipeDetailFragment.class.getSimpleName();
    @BindView(R.id.step_desc_textview)
    TextView mStepDescTextView;
    
    @BindView(R.id.step_thumbnail_image_view)
    ImageView mThumbImageView;
    
    @BindView(R.id.exo_player_view)
    PlayerView mPlayerView;
    
    @BindView(R.id.previous_imageButton)
    ImageButton mPreviousStepImageButton;
    
    @BindView(R.id.next_imageButton)
    ImageButton mNextStepImageButton;
    
    private Step mStep;
    private List<Step> mStepList;
    private SimpleExoPlayer mSimpleExoPlayer;
    private Context mContext;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ButterKnife.bind(this, rootView);
        
        mContext = getActivity();
        
        if (Objects.requireNonNull(getArguments()).containsKey(STEP_LIST)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            
            mStepList = getArguments().getParcelableArrayList(STEP_LIST);
            sStepPostion = getStepPostion();
            mStep = mStepList.get(sStepPostion);
            
            
            
        }
        // Show the dummy content as text in a TextView.
        if (mStep != null) {
            mStepDescTextView.setText(String.format("%s: %s", String.valueOf(sStepPostion), mStep.getShortDescription()));
        }
        
        if (sStepPostion == mStepList.size() - 1) {
            mNextStepImageButton.setVisibility(View.GONE);
        } else {
            mNextStepImageButton.setVisibility(View.VISIBLE);
        }
        if (sStepPostion == 0) {
            mPreviousStepImageButton.setVisibility(View.GONE);
        } else {
            mPreviousStepImageButton.setVisibility(View.VISIBLE);
        }
        
        
        
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        initExoPlayer();
        
        if (savedInstanceState != null && mSimpleExoPlayer != null) {
            mSimpleExoPlayer.seekTo(savedInstanceState.getLong(getString(R.string.player_position_key)));
            mSimpleExoPlayer.setPlayWhenReady(savedInstanceState.getBoolean(getString(R.string.player_state_key)));
        }
        
        mPreviousStepImageButton.setOnClickListener(this);
        mNextStepImageButton.setOnClickListener(this);
        return rootView;
    }
    
    private void initExoPlayer() {
        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
        mPlayerView.requestFocus();
        mPlayerView.setPlayer(mSimpleExoPlayer);
        if (!TextUtils.isEmpty(mStep.getVideoURL())) {
            DefaultDataSourceFactory defaultDataSourceFactory = new DefaultDataSourceFactory
                    (mContext, Util.getUserAgent(mContext, getString(R.string.my_player)));
            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(defaultDataSourceFactory).createMediaSource(Uri.parse(mStep.getVideoURL()));
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.setPlayWhenReady(true);
            
            mThumbImageView.setVisibility(View.GONE);
            
        } else {
            Toast.makeText(mContext, getString(R.string.video_), Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onStop() {
        super.onStop();
        mPlayerView.setPlayer(null);
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(false);
            mSimpleExoPlayer.stop();
            mSimpleExoPlayer.release();
        }
        
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerView.setPlayer(null);
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        
        outState.putLong(getString(R.string.player_position_key), mSimpleExoPlayer.getCurrentPosition());
        outState.putBoolean(getString(R.string.player_state_key), mSimpleExoPlayer.getPlayWhenReady());
        
    }
    
    @Override
    public void onClick(View v) {
        //Log.e(TAG, "onClick: Step Postion"+sStepPostion );
        if (v.getId() == R.id.previous_imageButton) {
            if (sStepPostion != 0) {
                sStepPostion--;
                savePositionOfStep();
                FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                ft.detach(this).attach(this).commit();
            }
        } else if (v.getId() == R.id.next_imageButton) {
            if (sStepPostion < mStepList.size()) {
                sStepPostion++;
                savePositionOfStep();
                FragmentTransaction ft = Objects.requireNonNull(getFragmentManager()).beginTransaction();
                ft.detach(this).attach(this).commit();
            }
            
        }
        
    }
    
    private void savePositionOfStep() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences(getString(R.string.recipe_step), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.step_position), sStepPostion);
        editor.apply();
    }
    
    private int getStepPostion() {
        SharedPreferences shared = Objects.requireNonNull(getActivity()).getApplicationContext().getSharedPreferences("Recipe_Step", Context.MODE_PRIVATE);
        return shared.getInt(getString(R.string.step_position), 0);
    }
    
}
