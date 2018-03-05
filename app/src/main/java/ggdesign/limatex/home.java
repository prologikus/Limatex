package ggdesign.limatex;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewAnimationUtils;

import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class home extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    // TODO LIST:
    /*
     - add animation between Categories and Items
     - add *item flying to shopping cart* animation when adding items to shopping cart
     - redesign categories
     - bubble with items count next to the shopping cart floating button
     - functional shopping cart window (showing: items, delivery options, delivery status, restaurant close/open)
     - search function for items
     - connect to Database
     - floating shopping button visible in categories when something in cart
    */

    public ArrayList cart;
    public boolean phone_good;
    // Create SharedPreferences and Editor
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    //declare layouts
    RelativeLayout all;
    RelativeLayout home;
    RelativeLayout splash;
    RelativeLayout shop;
    RelativeLayout shopFloat;
    TextView shopCount;
    ImageView logo;
    android.support.v7.widget.Toolbar myToolbar;
    android.support.design.widget.FloatingActionButton shopButton;
    android.support.v7.widget.SearchView menu;
    EditText phone_text;
    EditText name_text;
    EditText home_text;
    android.support.design.widget.FloatingActionButton back_button;
    String thisScreen = "Splash";
    String lastScreen; // USED TO get user back from shopping cart to prev screen
    //RecyclerView for categories/Items
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private MyRecyclerViewAdapterSubItems adapter2;
    //RecyclerView for cart
    private RecyclerView mRecyclerView2;
    private MyRecyclerViewAdapterCartItems adapter3;
    private List<SubItems> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //add support for Shared Pref
        //Used for shipping address
        sharedPref = getSharedPreferences("shipAddress", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        //add support for layouts
        all = findViewById(R.id.all);
        home = findViewById(R.id.Home);
        splash = findViewById(R.id.Splash);
        splash.setVisibility(View.VISIBLE);
        shop = findViewById(R.id.Shop);
        shop.setVisibility(View.GONE);

        //add support for SHOP BUTTON
        shopFloat = findViewById(R.id.shopFloat);
        shopButton = findViewById(R.id.shopButton);
        shopCount = findViewById(R.id.shopCount);

        //add support for Recicler ListView
        mRecyclerView = findViewById(R.id.RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        //add support for toolbar Menu
        myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //add support for search
        menu = findViewById(R.id.action_search);

        //add support for shopping cart menu
        phone_text = findViewById(R.id.phone_text);
        name_text = findViewById(R.id.name_text);
        home_text = findViewById(R.id.home_text);
        back_button = findViewById(R.id.back_button);
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

        //register shop button
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastScreen = thisScreen;
                anim(home, shop, "Shop", 1000);
            }
        });

        //register BACK BUTTON FROM SHOPPING CART
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim(shop, home, lastScreen, 1000);
            }
        });


        //RecylerView for shopping cart
        mRecyclerView2 = findViewById(R.id.mRecyclerView2);
        cartList = new ArrayList<>();
        adapter3 = new MyRecyclerViewAdapterCartItems(this, cartList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView2.setLayoutManager(mLayoutManager);
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView2.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView2.setAdapter(adapter3);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView2);


        //extract the image from resources
        int idd = this.getResources().getIdentifier("pizza1", "drawable", this.getPackageName());

        // add example item
        SubItems temp = new SubItems("Pizza Elena", "Salata beuf, Iaur pere, Ceai, Sarmale", idd, "1 Lei", "");
        cartList.add(temp);
        adapter3.notifyDataSetChanged();


        //Create Food Categories
        createListView();


        //RUN MAIN animation with DELAY
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                anim(splash, home, "Categ", 1800);
            }
        }, 1600);


        //Read settings
        phone_text.setText(sharedPref.getString("user_phone", ""));
        name_text.setText(sharedPref.getString("user_name", ""));
        home_text.setText(sharedPref.getString("user_addr", ""));


        //Shipping address listener
        phone_text.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                phone_good = false;
                if (editable.toString().length() == 10) {
                    phone_good = editable.toString().startsWith("07");
                } else phone_good = false;

                // BAD text
                if (phone_good) {
                    // GOOD text
                    phone_text.setTextColor(getResources().getColor(R.color.colorSecondaryText));
                } else {
                    // BAD text
                    phone_text.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }
        });
        phone_text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!phone_good) {
                    Toast.makeText(home.this, "Numar de telefon Invalid!", Toast.LENGTH_LONG).show();
                    phone_text.setText("");
                }
            }
        });

    }

    //Create the search function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }


    public void CreateItemView(String item_name) {
        //save the screen name for back button
        thisScreen = "Items";

        //Get all the items from the selected Category
        int id = this.getResources().getIdentifier(item_name, "array", this.getPackageName());
        List<String> Lines = Arrays.asList(getResources().getStringArray(id));

        //crate a list for RecyclerView adapter
        ArrayList<SubItems> categoriesList = new ArrayList<>();

        for (int i = 0; i < Lines.size(); i++) {
            //extract the strings
            String item_name_ex = Lines.get(i);
            String item_desc_ex = Lines.get(i + 1);
            String item_ph_ex = Lines.get(i + 2);
            String item_price = Lines.get(i + 3);
            String item_priceB = Lines.get(i + 4);


            //extract the image from resources
            int idd = this.getResources().getIdentifier(item_ph_ex, "drawable", this.getPackageName());

            // add item to category list
            SubItems temp = new SubItems(item_name_ex, item_desc_ex, idd, item_price, item_priceB);
            categoriesList.add(temp);

            i = i + 4;
        }

        //Register the adapter + item click ADAPTER 2
        adapter2 = new MyRecyclerViewAdapterSubItems(home.this, categoriesList);
        mRecyclerView.setAdapter(adapter2);

        /*
        adapter2.setOnItemClickListener(new OnItemClickListenerItems() {
            @Override
            public void onItemClick(SubItems item) {
                //Create the new list
                final String sel_item_name = item.getTitle();


            }
        });

        */

        //show the Search

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
            case "Shop":
                //Create/going back to Food Categories

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

        //show the shopButton
        shopButton.setVisibility(View.VISIBLE);


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
            public void onItemClick(final Categories item) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Create the new list
                        final String sel_item_name = item.getTitle();
                        CreateItemView(sel_item_name);
                    }
                }, 150);

            }
        });


    }


    public void anim(final View oldScreen, final View newScreen, final String newScreenName, final int dur) {

        newScreen.setElevation(4);
        oldScreen.setElevation(7);

        newScreen.setVisibility(View.VISIBLE);
        oldScreen.setVisibility(View.VISIBLE);

        int x = shopFloat.getLeft() + (shopFloat.getWidth() / 2);
        int y = shopFloat.getTop() + (shopFloat.getWidth() / 2);

        // int x = splash.getWidth() / 2;
        //  int y = splash.getHeight();

        int startRadius = (int) Math.hypot(oldScreen.getWidth(), oldScreen.getHeight());
        int endRadius = 60;


        final Animator anim = ViewAnimationUtils.createCircularReveal(oldScreen, x, y, startRadius, endRadius);

        //disable touch interface
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //enable touch interface
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                oldScreen.setVisibility(View.GONE);
                thisScreen = newScreenName;
            }
        });
        anim.setDuration(dur);
        anim.start();

    }


    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof MyRecyclerViewAdapterCartItems.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final SubItems deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter3.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(shop, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter3.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
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
