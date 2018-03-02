package ggdesign.limatex;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class home extends AppCompatActivity {

    // TODO LIST:
    /*
     - add animation between categories and items
     - add animation when adding to shopping cart
     - redesign categories
     - functional shopping cart
     - search function for items
     - connect to basedata
     - floating window of shopping list
     - bubble with card count
     - floating shopping button visible in categories when something in cart
    */



    //declare layouts
    RelativeLayout all;
    RelativeLayout home;
    RelativeLayout splash;
    ImageView logo;
    android.support.v7.widget.Toolbar myToolbar;
    FloatingActionButton shopButton;

    String thisScreen = "Splash";


    //recicler for categories
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private MyRecyclerViewAdapterItems adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //add support for layouts
        all = findViewById(R.id.all);
        home = findViewById(R.id.Home);
        splash = findViewById(R.id.Splash);

        //add support for buttons
        shopButton = findViewById(R.id.shopButton);

        //add support for Recicler ListView
        mRecyclerView = findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        //add support for toolbar Menu
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        //Load LOGO image to splash screen immediately
        logo = findViewById(R.id.Logo);
        int idLogo = this.getResources().getIdentifier("logo1", "drawable", this.getPackageName());
        Context context = this;
        Picasso.with(context).load(idLogo)
                .noPlaceholder()
                .into(logo);

        // add dividers to RecyclerView
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        //Create Food Categories
        createListView();

        //RUN MAIN animation with DELAY
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showHome();
            }
        }, 1000);

    }


    public void CreateItemView(String item_name) {
        //save the screen name for back button
        thisScreen = "Items";

        //Get all the items from the selected Category
        int id = this.getResources().getIdentifier(item_name, "array", this.getPackageName());
        List<String> Lines = Arrays.asList(getResources().getStringArray(id));

        //crate a list for RecyclerView adapter
        ArrayList<CategoriesItems> categoriesList = new ArrayList<>();

        for (int i = 0; i < Lines.size(); i++) {
            //extract the strings
            String item_name_ex = Lines.get(i);
            String item_desc_ex = Lines.get(i + 1);
            String item_ph_ex = Lines.get(i + 2);
            String item_price = Lines.get(i + 3);

            // String item_name_ex = "Pizza Casei";
            // String item_desc_ex = "Descriere";
            // String item_ph_ex = "pizza1";
            //  String item_price = "10 Lei";


            //extract the image from resources
            int idd = this.getResources().getIdentifier(item_ph_ex, "drawable", this.getPackageName());

            // add item to category list
            CategoriesItems temp = new CategoriesItems(item_name_ex, item_desc_ex, idd, item_price, 0);
            categoriesList.add(temp);


            i++; //jump over the description
            i++; //jump over the icon
            i++; //jump over the icon
        }

        //Register the adapter + item click ADAPTER 2
        adapter2 = new MyRecyclerViewAdapterItems(home.this, categoriesList);
        mRecyclerView.setAdapter(adapter2);

        /*
        adapter2.setOnItemClickListener(new OnItemClickListenerItems() {
            @Override
            public void onItemClick(CategoriesItems item) {
                //Create the new list
                final String sel_item_name = item.getTitle();


            }
        });

        */

        //show the Search

        //show the shopButton
        shopButton.setVisibility(View.VISIBLE);

        //show the toolbar
        //search function in toolbar coming soon
        myToolbar.setVisibility(View.VISIBLE);
        myToolbar.setTitle(item_name);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_back();
            }
        });


    }

    public void go_back() {

        switch (thisScreen) {
            case "Categ":
                //exit application
                super.onBackPressed();
                break;
            case "Items":
                //Create/going back to Food Categories
                createListView();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //Listen to system back button and redirect it
        go_back();

    }

    public void createListView() {
        //save the screen name for back button
        thisScreen = "Categ";

        //set default layout
        home.setVisibility(View.VISIBLE);

        //hide the toolbar for the category list
        myToolbar.setVisibility(View.GONE);

        //hide the shopButton
        shopButton.setVisibility(View.GONE);


        int id1 = this.getResources().getIdentifier("slide1", "drawable", this.getPackageName());
        int id2 = this.getResources().getIdentifier("slide2", "drawable", this.getPackageName());
        int id3 = this.getResources().getIdentifier("slide3", "drawable", this.getPackageName());
        int id4 = this.getResources().getIdentifier("slide4", "drawable", this.getPackageName());
        int id5 = this.getResources().getIdentifier("slide5", "drawable", this.getPackageName());
        int id6 = this.getResources().getIdentifier("slide6", "drawable", this.getPackageName());

        Categories Pizza = new Categories("Pizza", "Proaspat preparate", id1);
        Categories Bauturi = new Categories("Bauturi", "Racoritoare", id3);
        Categories Meniuri = new Categories("Meniuri", "Perfect combinate", id2);
        Categories Sandwichuri = new Categories("Sandwichuri", "Delicioase", id4);
        Categories Salate = new Categories("Salate", "Orientate", id5);
        Categories Altele = new Categories("Altele", "Bunatati", id6);

        ArrayList<Categories> categoriesList = new ArrayList<>();
        categoriesList.add(Pizza);
        categoriesList.add(Bauturi);
        categoriesList.add(Meniuri);
        categoriesList.add(Sandwichuri);
        categoriesList.add(Salate);
        categoriesList.add(Altele);


        adapter = new MyRecyclerViewAdapter(home.this, categoriesList);
        mRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Categories item) {

                //Create the new list
                final String sel_item_name = item.getTitle();
                CreateItemView(sel_item_name);

            }
        });


    }


    public void showHome() {

        home.setVisibility(View.VISIBLE);
        splash.setVisibility(View.VISIBLE);

            //  int x = shopButton.getLeft() + (shopButton.getWidth() / 2);
            //  int y = shopButton.getTop() + (shopButton.getWidth() / 2);
            int x = splash.getWidth() / 2;
            int y = splash.getHeight();

            int startRadius = (int) Math.hypot(splash.getWidth(), splash.getHeight());
            int endRadius = 1;


            Animator anim = ViewAnimationUtils.createCircularReveal(splash, x, y, startRadius, endRadius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    splash.setVisibility(View.GONE);
                }
            });
            anim.setDuration(1500);
            anim.start();

    }


    //saved code for future

/*
                //RUN Animation with delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Animation
                        int x = splash.getWidth() / 2;
                        int y = splash.getHeight();

                        int startRadius = (int) Math.hypot(home.getWidth(), home.getHeight());
                        int endRadius = 0;


                        Animator anim = ViewAnimationUtils.createCircularReveal(home, x, y, startRadius, endRadius);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                //   home.setVisibility(View.GONE);
                            }

                        });
                        anim.setDuration(1500);
                        anim.start();
                    }
                }, 50);

    public void showC() {
        //   Intent intent = new Intent(getApplicationContext(), categ.class);
        //   startActivity(intent);
    }


    int lastTouchX;
    int lastTouchY;
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Toast.makeText(home.this, "Pressed at x:" + e.getX() + " and y:" + e.getY(), Toast.LENGTH_LONG).show();
                // register last touch if finger is down on the screen
                lastTouchX = (int) e.getX();
                lastTouchY = (int) e.getY();
                break;
        }
        return true;
    }
    */


}
