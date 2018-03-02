package ggdesign.limatex;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.travijuu.numberpicker.library.NumberPicker;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by meg3o on 2/26/2018.
 */

public class MyRecyclerViewAdapterItems extends RecyclerView.Adapter<MyRecyclerViewAdapterItems.CustomViewHolder> {
    private List<CategoriesItems> CategoriesList;
    private Context mContext;
  //  private OnItemClickListenerItems onItemClickListenerItems;


    public MyRecyclerViewAdapterItems(Context context, List<CategoriesItems> CategoriesList) {
        this.CategoriesList = CategoriesList;
        this.mContext = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listilayout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final CategoriesItems CategoriesItems = CategoriesList.get(i);

        //Render image using Picasso library
        //  if (!TextUtils.isEmpty(CategoriesItems.getImgURL())) {
        Picasso.with(mContext).load(CategoriesItems.getImgURL())
                .fit()
                .into(customViewHolder.imageView);
        // }

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(CategoriesItems.getTitle()));
        customViewHolder.textView2.setText(Html.fromHtml(CategoriesItems.getSubtitle()));
        customViewHolder.price.setText(Html.fromHtml(CategoriesItems.getPrice()));


        /*
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListenerItems.onItemClick(CategoriesItems);
            }
        };
        customViewHolder.imageView.setOnClickListener(listener);
        customViewHolder.textView.setOnClickListener(listener);
        customViewHolder.textView2.setOnClickListener(listener);
        customViewHolder.price.setOnClickListener(listener);
        customViewHolder.bglayout.setOnClickListener(listener);
*/


    }


    @Override
    public int getItemCount() {
        return (null != CategoriesList ? CategoriesList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView textView2;
        protected TextView price;
        protected com.travijuu.numberpicker.library.NumberPicker nrpick;
        protected RelativeLayout bglayout;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);
            this.textView = view.findViewById(R.id.text);
            this.textView2 = view.findViewById(R.id.text2);
           this.nrpick = view.findViewById(R.id.numberPicker);
            this.price = view.findViewById(R.id.price);
            this.bglayout = view.findViewById(R.id.bglayout);
            nrpick.setValue(0);

        }
    }

/*
    //SUPPORT FOR ON CLICK LISENER
    public OnItemClickListenerItems getOnItemClickListener() {
        return onItemClickListenerItems;
    }

    public void setOnItemClickListener(OnItemClickListenerItems onItemClickListenerItems) {
        this.onItemClickListenerItems = onItemClickListenerItems;
    }
*/



}