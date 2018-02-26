package ggdesign.limatex;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity {

    //declare layouts
    RelativeLayout all;
    RelativeLayout home;
    RelativeLayout splash;
    ImageView logo;
    //  FloatingActionButton shopButton;
    boolean homeOpen = false;

    //recicler for categories
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //add support for layouts
        all = findViewById(R.id.all);
        home = findViewById(R.id.Home);
        splash = findViewById(R.id.Splash);

        //add support for buttons
        //shopButton = findViewById(R.id.shopButton);

        //add support for Recicler ListView
        mRecyclerView = findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //add support for toolbar Menu
        //Toolbar myMenu = findViewById(R.id.menu);
        //setSupportActionBar(myMenu);

        //set default layout
        home.setVisibility(View.VISIBLE);
        splash.setVisibility(View.VISIBLE);

        //Load LOGO image to splash screen immediately
        logo = findViewById(R.id.Logo);
        int idLogo = this.getResources().getIdentifier("logo1", "drawable", this.getPackageName());
        Context context = this;
        Picasso.with(context).load(idLogo)
                .noPlaceholder()
                .into(logo);

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

    public void showC() {
        //   Intent intent = new Intent(getApplicationContext(), categ.class);
        //   startActivity(intent);
    }

    public void createListView() {

        //  Resources res = getResources();

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

        //CategoriesListAdaptor adapter = new CategoriesListAdaptor(this,R.layout.listclayout,categoriesList);

        //  adapter = new CategoriesListAdaptor(this,R.layout.listclayout,categoriesList);

        adapter = new MyRecyclerViewAdapter(home.this, categoriesList);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Categories item) {


               // Toast.makeText(home.this, item.getTitle(), Toast.LENGTH_LONG).show();

                //get position of the clicked item from screen
                int x = 0;
                int y = 0;

                int startRadius = (int) Math.hypot(home.getWidth(), home.getHeight());
                int endRadius = 0;


                Animator anim = ViewAnimationUtils.createCircularReveal(home, x, y, startRadius, endRadius);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        home.setVisibility(View.GONE);
                    }
                });
                anim.setDuration(1500);
                anim.start();


            }
        });


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);


    }


    public void showHome() {
        if (homeOpen == false) {

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

            homeOpen = true;
        }
    }


}
