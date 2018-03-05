package ggdesign.limatex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.angads25.toggle.LabeledSwitch;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by meg3o on 2/26/2018.
 */

public class MyRecyclerViewAdapterSubItems extends RecyclerView.Adapter<MyRecyclerViewAdapterSubItems.CustomViewHolder> {
    private List<SubItems> CategoriesList;
    private Context mContext;
    //  private OnItemClickListenerItems onItemClickListenerItems;


    public MyRecyclerViewAdapterSubItems(Context context, List<SubItems> CategoriesList) {
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
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final SubItems SubItems = CategoriesList.get(i);

        //Render image using Picasso library
        //  if (!TextUtils.isEmpty(SubItems.getImgURL())) {
        Picasso.with(mContext).load(SubItems.getImgURL())
                .fit()
                .centerCrop()
                .into(customViewHolder.imageView);
        // }

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(SubItems.getTitle()));
        customViewHolder.textView2.setText(Html.fromHtml(SubItems.getSubtitle()));
        customViewHolder.price.setText(Html.fromHtml(SubItems.getPrice()));

        customViewHolder.sizeSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                // Implement your switching logic here
                if (isOn) {
                    customViewHolder.price.setText(Html.fromHtml(SubItems.getPriceB()));
                } else {
                    customViewHolder.price.setText(Html.fromHtml(SubItems.getPrice()));
                }
            }
        });

        /*
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListenerItems.onItemClick(SubItems);
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
        protected com.github.angads25.toggle.LabeledSwitch sizeSwitch;
        protected RelativeLayout bglayout;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);
            this.textView = view.findViewById(R.id.text);
            this.textView2 = view.findViewById(R.id.text2);
            this.sizeSwitch = view.findViewById(R.id.switchSize);
            this.price = view.findViewById(R.id.price);
            this.bglayout = view.findViewById(R.id.bglayout);

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