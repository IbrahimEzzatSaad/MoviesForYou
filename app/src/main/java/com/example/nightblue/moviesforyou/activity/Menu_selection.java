package com.example.nightblue.moviesforyou.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.example.nightblue.moviesforyou.R;
import com.example.nightblue.moviesforyou.adapter.GalleryAdapter;
import com.example.nightblue.moviesforyou.adapter.GalleryAdapterMenu;
import com.example.nightblue.moviesforyou.app.AppController;
import com.example.nightblue.moviesforyou.model.Image;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.nightblue.moviesforyou.app.AppController.TAG;

/**
 * Created by NightBlue on 07/05/2018.
 */

public class Menu_selection extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerViews;
    private GalleryAdapterMenu mAdapter;
    private ArrayList<Image> imagess;
    private static final String endpoint = "https://api.jsonbin.io/b/5cbf6edf1f6d9a5478d012db";
    private String namegenre;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_select);
        Intent intent = getIntent();
        namegenre = intent.getExtras().getString("genre");
        imagess = new ArrayList<>();

        mAdapter = new GalleryAdapterMenu(getApplicationContext(), imagess);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViews = (RecyclerView) findViewById(R.id.RecycelViews);
        recyclerViews.setLayoutManager(mLayoutManager);
        recyclerViews.setItemAnimator(new DefaultItemAnimator());
        recyclerViews.setAdapter(mAdapter);
        fetchImages();

        recyclerViews.addOnItemTouchListener(new GalleryAdapterMenu.RecyclerTouchListener(getApplicationContext(), recyclerViews, new GalleryAdapterMenu.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", imagess);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }



    private void fetchImages() {


        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        imagess.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(obj.getString("name"));
                                image.setImage(obj.getString("image"));
                                image.setTimestamp(obj.getString("timestamp"));


                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                image.setGenre(genre);
                                if (genre.contains(namegenre)){
                                    imagess.add(image);
                                }

                                if (image.getName().toLowerCase().contains(namegenre.toLowerCase())){
                                    imagess.add(image);
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.e(TAG, "Error: " + error.getMessage());


            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }


}
