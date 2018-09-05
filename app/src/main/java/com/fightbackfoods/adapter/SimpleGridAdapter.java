package com.fightbackfoods.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fightbackfoods.R;
import com.fightbackfoods.activity.ImageProgressCallback;
import com.fightbackfoods.interfaces.SerializableListener;
import com.fightbackfoods.model.Item;
import com.fightbackfoods.utils.Validate;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleGridAdapter extends RecyclerView.Adapter implements Filterable {
    private int itemsCount = 0;
    private int lastAnimatedPosition = -1;
    private int avatarSize;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;
    List<Item> mValues;
    List<Item> filteredList;
    Context mContext;
    protected SerializableListener mListener;
    private ItemFilter mFilter = new ItemFilter();


    public SimpleGridAdapter(Context context, List<Item> values, SerializableListener  itemListener) {

        mValues = values;
        filteredList= values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_image)
        public ImageView imageView;
        @BindView(R.id.tv_title)
        public TextView textView;
        @BindView(R.id.pb)
        public ProgressBar progressBar;

        Item item;

        public ViewHolder(View v) {

            super(v);
            ButterKnife.bind(this, v);
        }

    }

    @Override
    public SimpleGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple_grid, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final Item item = filteredList.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        String imagePath= Validate.path(item.getDescription());
        /*
        if(!Validate.isNullString(item.getDescription())) {
            imagePath = Validate.path(imagePath);
        }*/
        Log.d(SimpleGridAdapter.class.getSimpleName(),"imagePath "+ imagePath);
        if(!Validate.isNullString(imagePath))
            Picasso.with(mContext)
                    .load(imagePath)
                    .into(holder.imageView,  new ImageProgressCallback(holder.imageView, holder.progressBar));
        holder.textView.setText(item.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener==null) return;
                mListener.onClick(item,v);
            }
        });
        runEnterAnimation(viewHolder.itemView, position);

    }

    /*@Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mValues.get(position));

    }*/

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(50).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }



    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public Item getItem(int position) {
        return filteredList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Item> list = mValues;

            int count = list.size();
            final List<Item> nlist = new ArrayList<Item>(count);

            String filterableString ;
            //String filterableString2 ;

            for (int i = 0; i < count; i++) {
                Item item = list.get(i);
                filterableString = item.getName();
                //filterableString2 = list.get(i).getDescription();

                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(item);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Item>) results.values;
            notifyDataSetChanged();
        }

    }
}
