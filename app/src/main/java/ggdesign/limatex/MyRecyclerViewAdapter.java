package ggdesign.limatex;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by meg3o on 2/26/2018.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<Categories> CategoriesList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<Categories> CategoriesList) {
        this.CategoriesList = CategoriesList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listclayout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Categories Categories = CategoriesList.get(i);

        //Render image using Picasso library
        //  if (!TextUtils.isEmpty(Categories.getImgURL())) {
        Picasso.with(mContext).load(Categories.getImgURL())
                .fit()
                .into(customViewHolder.imageView);
        // }

        //Setting text view title
        customViewHolder.textView.setText(Html.fromHtml(Categories.getTitle()));
        customViewHolder.textView2.setText(Html.fromHtml(Categories.getSubtitle()));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(Categories);
            }
        };
        customViewHolder.imageView.setOnClickListener(listener);
        customViewHolder.textView.setOnClickListener(listener);
        customViewHolder.textView2.setOnClickListener(listener);

    }



    @Override
    public int getItemCount() {
        return (null != CategoriesList ? CategoriesList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView textView2;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = view.findViewById(R.id.image);
            this.textView = view.findViewById(R.id.text);
            this.textView2 = view.findViewById(R.id.text2);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}