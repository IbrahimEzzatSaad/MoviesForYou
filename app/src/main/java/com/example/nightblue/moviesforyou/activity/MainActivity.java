package com.example.nightblue.moviesforyou.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.example.nightblue.moviesforyou.R;
import com.example.nightblue.moviesforyou.adapter.GalleryAdapter;
import com.example.nightblue.moviesforyou.app.AppController;
import com.example.nightblue.moviesforyou.model.Image;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.example.nightblue.moviesforyou.interfaces.onSearchListener;
import com.example.nightblue.moviesforyou.interfaces.onSimpleSearchActionsListener;
import com.example.nightblue.moviesforyou.widgets.MaterialSearchView;


public class MainActivity extends AppCompatActivity implements onSimpleSearchActionsListener, onSearchListener {

    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "https://api.jsonbin.io/b/5cbf6edf1f6d9a5478d012db";







    private ArrayList<Image> ImageDrama;
    private ArrayList<Image> ImagesHorror;
    private ArrayList<Image> ImageAction;
    private ArrayList<Image> ImagesAnime;






    private boolean mSearchViewAdded = false;
    private MaterialSearchView mSearchView;
    private WindowManager mWindowManager;
    private MenuItem searchItem;
    private boolean searchActive = false;



    private ProgressDialog pDialog;










    private GalleryAdapter mAdapterDrama,mAdapterAction,mAdapterHorror,mAdapterAnime;


    private RecyclerView recyclerViewDrama;
    private RecyclerView RecycelViewHorror;

    private RecyclerView recyclerViewAction;
    private RecyclerView RecycelViewAnime;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mSearchView = new MaterialSearchView(this);
        mSearchView.setOnSearchListener(this);
        mSearchView.setSearchResultsListener(this);
        mSearchView.setHintText("Search");

        if (toolbar != null) {
            // Delay adding SearchView until Toolbar has finished loading
            toolbar.post(new Runnable() {
                @Override
                public void run() {
                    if (!mSearchViewAdded && mWindowManager != null) {
                        mWindowManager.addView(mSearchView,
                                MaterialSearchView.getSearchViewLayoutParams(MainActivity.this));
                        mSearchViewAdded = true;
                    }
                }
            });
        }





        recyclerViewDrama = (RecyclerView) findViewById(R.id.RecycelViewDrama);
        RecycelViewHorror = (RecyclerView) findViewById(R.id.RecycelViewHorror);
        recyclerViewAction = (RecyclerView) findViewById(R.id.RecycelViewAction);
        RecycelViewAnime = (RecyclerView) findViewById(R.id.RecycelViewAnime);


        pDialog = new ProgressDialog(this);
        ImageDrama = new ArrayList<>();
        ImagesHorror = new ArrayList<>();
        ImageAction = new ArrayList<>();
        ImagesAnime = new ArrayList<>();

        mAdapterDrama = new GalleryAdapter(getApplicationContext(), ImageDrama);
        mAdapterHorror = new GalleryAdapter(getApplicationContext(), ImagesHorror);
        mAdapterAction = new GalleryAdapter(getApplicationContext(), ImageAction);
        mAdapterAnime = new GalleryAdapter(getApplicationContext(), ImagesAnime);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);




        recyclerViewAction.setLayoutManager(layoutManager);
        recyclerViewAction.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAction.setAdapter(mAdapterAction);


        RecycelViewAnime.setLayoutManager(layoutManager);
        RecycelViewAnime.setItemAnimator(new DefaultItemAnimator());
        RecycelViewAnime.setAdapter(mAdapterAnime);

        recyclerViewDrama.setLayoutManager(layoutManager);
        recyclerViewDrama.setItemAnimator(new DefaultItemAnimator());
        recyclerViewDrama.setAdapter(mAdapterDrama);

        RecycelViewHorror.setLayoutManager(layoutManager);
        RecycelViewHorror.setItemAnimator(new DefaultItemAnimator());
        RecycelViewHorror.setAdapter(mAdapterHorror);







        new DrawerBuilder().withActivity(this).build();
        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_settings);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.drawer_item_settings);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.drawer_item_settings);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.drawer_item_settings);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(6).withName(R.string.drawer_item_settings);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(7).withName(R.string.drawer_item_settings);
        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withIdentifier(8).withName(R.string.drawer_item_settings);




        //create the drawer and remember the `Drawer` result object


        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSliderBackgroundColor(getResources().getColor(R.color.menucolor))

                .addDrawerItems(
                        item1.withName("Home").withIdentifier(1).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        item2.withName("Drama").withIdentifier(2).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        item3.withName("Action").withIdentifier(3).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        item4.withName("Comedy").withIdentifier(4).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        item5.withName("Thriller").withIdentifier(5).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        item6.withName("Animation").withIdentifier(6).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        item7.withName("Sci-Fi").withIdentifier(7).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700)),
                        item8.withName("Horror").withIdentifier(8).withTextColor(getResources().getColor(R.color.md_red_A700)).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D


                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1) {
                                String namegenre = "Home";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.MainActivity.class);
                                startActivity(mainIntent);

                            } else if (drawerItem.getIdentifier() == 2) {
                                String namegenre = "Drama";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.Menu_selection.class);
                                mainIntent.putExtra("genre", namegenre);
                                startActivity(mainIntent);

                            }else if (drawerItem.getIdentifier() == 3){
                                String namegenre = "Action";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.Menu_selection.class);
                                mainIntent.putExtra("genre", namegenre);
                                startActivity(mainIntent);

                            }else if (drawerItem.getIdentifier() == 4){
                                String namegenre = "Comedy";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.Menu_selection.class);
                                mainIntent.putExtra("genre", namegenre);
                                startActivity(mainIntent);

                            }else if (drawerItem.getIdentifier() == 5){
                                String namegenre = "Thriller";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.Menu_selection.class);
                                mainIntent.putExtra("genre", namegenre);
                                startActivity(mainIntent);

                            }else if (drawerItem.getIdentifier() == 6){
                                String namegenre = "Animation";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.Menu_selection.class);
                                mainIntent.putExtra("genre", namegenre);
                                startActivity(mainIntent);

                            }else if (drawerItem.getIdentifier() == 7){
                                String namegenre = "Sci-Fi";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.Menu_selection.class);
                                mainIntent.putExtra("genre", namegenre);
                                startActivity(mainIntent);

                            }if (drawerItem.getIdentifier() == 8) {
                                String namegenre = "Horror";
                                Intent mainIntent = new Intent(MainActivity.this, com.example.nightblue.moviesforyou.activity.Menu_selection.class);
                                mainIntent.putExtra("genre", namegenre);
                                startActivity(mainIntent);

                            }
                        }






                        return true;

                    }

                })

                .build();




        recyclerViewDrama.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerViewDrama, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", ImageDrama);
                bundle.putInt("position", position);
                view.getParent().requestDisallowInterceptTouchEvent(true);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        RecycelViewHorror.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), RecycelViewHorror, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", ImagesHorror);
                bundle.putInt("position", position);
                view.getParent().requestDisallowInterceptTouchEvent(true);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerViewAction.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerViewAction, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", ImageAction);
                bundle.putInt("position", position);
                view.getParent().requestDisallowInterceptTouchEvent(true);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        RecycelViewAnime.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), RecycelViewAnime, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", ImagesAnime);
                bundle.putInt("position", position);
                view.getParent().requestDisallowInterceptTouchEvent(true);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        fetchImages();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        searchItem = menu.findItem(R.id.search);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mSearchView.display();
                openKeyboard();
                return true;
            }
        });
        if(searchActive)
            mSearchView.display();
        return true;

    }

    private void openKeyboard(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                mSearchView.getSearchView().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                mSearchView.getSearchView().dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
            }
        }, 200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearch(String query) {

    }

    @Override
    public void searchViewOpened() {
    }

    @Override
    public void searchViewClosed() {
    }

    @Override
    public void onItemClicked(String item) {
    }

    @Override
    public void onScroll() {

    }

    @Override
    public void error(String localizedMessage) {

    }

    @Override
    public void onCancelSearch() {
        searchActive = false;
        mSearchView.hide();
    }

    private void fetchImages() {

        pDialog.setMessage("Downloading json...");

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();
                        ImagesHorror.clear();
                        ImageDrama.clear();
                        ImageAction.clear();
                        ImagesAnime.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(obj.getString("name"));
                                image.setImage(obj.getString("image"));
                                image.setTimestamp(obj.getString("timestamp"));
                                image.setURL(obj.getString("url"));



                                // Genre is json array
                                JSONArray genreArry = obj.getJSONArray("genre");
                                ArrayList<String> genre = new ArrayList<String>();
                                for (int j = 0; j < genreArry.length(); j++) {
                                    genre.add((String) genreArry.get(j));
                                }
                                image.setGenre(genre);
                                if (genre.contains("Drama")){
                                    ImageDrama.add(image);
                                }
                                if(genre.contains("Horror")){
                                    ImagesHorror.add(image);


                                }
                                if(genre.contains("Action")){
                                    ImageAction.add(image);


                                }
                                if(genre.contains("Animation")){
                                    ImagesAnime.add(image);


                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }
                        mAdapterAction.notifyDataSetChanged();
                        mAdapterHorror.notifyDataSetChanged();
                        mAdapterAnime.notifyDataSetChanged();
                        mAdapterDrama.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }



}