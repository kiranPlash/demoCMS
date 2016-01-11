package in.plash.trunext.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.plash.trunext.Interfaces.OnLoadMoreListener;
import in.plash.trunext.R;
import in.plash.trunext.network.VolleySingleton;
import in.plash.trunext.responceObjects.ResponseArticles;
import in.plash.trunext.util.AnimationUtils;
import in.plash.trunext.util.RoundedTransformation;


/**
 * Created by Kiran on 10/21/2015.
 */
public class forbesArticleAdapter extends RecyclerView.Adapter {

    Context context;
    List<ResponseArticles.ListEntity> responseArticles = new ArrayList<ResponseArticles.ListEntity>();
    ImageLoader mImageLoader;
    private final int THEME1 = 1, THEME2 = 2, THEME3 = 3, THEME4 = 4, THEME5 = 5, THEME6 = 6, THEME7 = 7, THEME8 = 8, THEME9 = 9, THEME10 = 10, THEME99 = 99, VIEW_PROG = 0;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private static ClickArticleListener clickListenerArticle;
    private OnLoadMoreListener onLoadMoreListener;
    private int mPreviousPosition = 0;


    public forbesArticleAdapter(Activity activity, List<ResponseArticles.ListEntity> articles, RecyclerView recyclerView) {
        this.context = activity;
        responseArticles = articles;
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                int priorityID = 0;
                                try {
                                    priorityID = responseArticles.get(responseArticles.size() - 1).getPriority();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore(priorityID);
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (responseArticles.get(position) != null) {
            int tempID = responseArticles.get(position).getTemplateid();

            switch (tempID) {
                case 1:
                    return THEME1;
                case 2:
                    return THEME2;
                case 3:
                    return THEME3;
                case 4:
                    return THEME4;
                case 5:
                    return THEME5;
                case 6:
                    return THEME6;
                case 7:
                    return THEME7;
                case 8:
                    return THEME8;
                case 9:
                    return THEME9;
                case 10:
                    return THEME10;
                case 99:
                    return THEME99;
                case 0:
                    return VIEW_PROG;
                default:
                    return THEME7;
            }
        }
        return VIEW_PROG;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case THEME1:
                //  View v1 = inflater.inflate(R.layout.forbes_temp1, parent, false);
                View v1 = inflater.inflate(R.layout.forbes_new_temp1, parent, false);
                viewHolder = new ForbesViewHolder1(v1);
                break;
            case THEME2:
                View v2 = inflater.inflate(R.layout.forbes_new_temp2, parent, false);
                viewHolder = new ForbesViewHolder2(v2);
                break;
            case THEME3:
                View v3 = inflater.inflate(R.layout.forbes_new_temp3, parent, false);
                viewHolder = new ForbesViewHolder3(v3);
                break;
            case THEME4:
                View v4 = inflater.inflate(R.layout.forbes_new_temp4, parent, false);
                viewHolder = new ForbesViewHolder4(v4);
                break;
            case THEME5:
                View v5 = inflater.inflate(R.layout.forbes_new_temp5, parent, false);
                viewHolder = new ForbesViewHolder5(v5);
                break;
            case THEME6:
                View v6 = inflater.inflate(R.layout.forbes_new_temp6, parent, false);
                viewHolder = new ForbesViewHolder6(v6);
                break;
            case THEME7:
                View v7 = inflater.inflate(R.layout.forbes_new_temp7, parent, false);
                viewHolder = new ForbesViewHolder7(v7);
                break;
            case THEME8:
                View v8 = inflater.inflate(R.layout.forbes_new_temp8, parent, false);
                viewHolder = new ForbesViewHolder8(v8);
                break;
            case THEME9:
                View v9 = inflater.inflate(R.layout.forbes_new_temp9, parent, false);
                viewHolder = new ForbesViewHolder9(v9);
                break;
            case THEME10:
                View v10 = inflater.inflate(R.layout.forbes_new_temp10, parent, false);
                viewHolder = new ForbesViewHolder10(v10);
                break;
            case THEME99:
                View v99 = inflater.inflate(R.layout.adds_layout, parent, false);
                viewHolder = new ForbesViewHolder99(v99);
                break;
            case VIEW_PROG:
                View vProg = inflater.inflate(R.layout.progress_item, parent, false);
                viewHolder = new ProgressViewHolder(vProg);
            default:
              /*  View vDflt = inflater.inflate(R.layout.forbes_temp_default, parent, false);
                viewHolder = new ForbesViewHolderDflt(vDflt);*/
        }
        return viewHolder;

    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        ResponseArticles.ListEntity mItems = responseArticles.get(position);

        switch (viewHolder.getItemViewType()) {

            case VIEW_PROG:
                ProgressViewHolder vPro = (ProgressViewHolder) viewHolder;
                configureViewHolderDflt(vPro, mItems);
                break;
            case THEME1:
                ForbesViewHolder1 vh1 = (ForbesViewHolder1) viewHolder;
                configureViewHolder1(vh1, position, mItems);
                break;
            case THEME2:
                ForbesViewHolder2 vh2 = (ForbesViewHolder2) viewHolder;
                configureViewHolder2(vh2, position, mItems);
                break;
            case THEME3:
                ForbesViewHolder3 vh3 = (ForbesViewHolder3) viewHolder;
                configureViewHolder3(vh3, position, mItems);
                break;
            case THEME4:
                ForbesViewHolder4 vh4 = (ForbesViewHolder4) viewHolder;
                configureViewHolder4(vh4, position, mItems);
                break;
            case THEME5:
                ForbesViewHolder5 vh5 = (ForbesViewHolder5) viewHolder;
                configureViewHolder5(vh5, position, mItems);
                break;
            case THEME6:
                ForbesViewHolder6 vh6 = (ForbesViewHolder6) viewHolder;
                configureViewHolder6(vh6, position, mItems);
                break;
            case THEME7:
                ForbesViewHolder7 vh7 = (ForbesViewHolder7) viewHolder;
                configureViewHolder7(vh7, position, mItems);
                break;
            case THEME8:
                ForbesViewHolder8 vh8 = (ForbesViewHolder8) viewHolder;
                configureViewHolder8(vh8, position, mItems);
                break;
            case THEME9:
                ForbesViewHolder9 vh9 = (ForbesViewHolder9) viewHolder;
                configureViewHolder9(vh9, position, mItems);
                break;
            case THEME10:
                ForbesViewHolder10 vh10 = (ForbesViewHolder10) viewHolder;
                configureViewHolder10(vh10, position, mItems);
                break;
            case THEME99:
                ForbesViewHolder99 vh99 = (ForbesViewHolder99) viewHolder;
                configureViewHolder99(vh99, mItems);
                break;

            default:
                /*ForbesViewHolderDflt vhDflt = (ForbesViewHolderDflt) viewHolder;
                configureViewHolderDflt(vhDflt, mItems);*/

        }
    }

    private void configureViewHolderDflt(ProgressViewHolder holder, ResponseArticles.ListEntity mItems) {
        holder.progressBar.setIndeterminate(true);
    }

    private void configureViewHolder99(ForbesViewHolder99 holder, ResponseArticles.ListEntity mItems) {
        String videoUrl = mItems.getImageurl();
// !imageUrl.equals("")
        if (videoUrl != null) {

            holder.videoView.setVideoURI(Uri.parse(videoUrl));
            holder.videoView.start();
            holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }
        holder.addsHeadline.setText(mItems.getArticleHeadline());
        holder.addsSummary.setText(mItems.getArticleSummary());
    }

    private void configureViewHolder10(ForbesViewHolder10 holder, int position, ResponseArticles.ListEntity mItems) {

        holder.txtHeadingThm10.setText(mItems.getCategoryName());


        animateItems(holder, position);
    }

    private void configureViewHolder9(ForbesViewHolder9 holder, int position, ResponseArticles.ListEntity mItems) {

        holder.txtHeadingThm9.setText(mItems.getCategoryName());
        holder.txtCategoryThm9.setText(mItems.getTagname());
      /*  holder.txtSummaryThm9.setText(mItems.getArticleSummary());
        if (mItems.getTagname() != null) {
            holder.txtTagThm9.setText(mItems.getTagname());
        }*/

        animateItems(holder, position);
    }

    private void configureViewHolder8(ForbesViewHolder8 holder, int position, ResponseArticles.ListEntity mItems) {
        String imageUrl8 = mItems.getImageurl();
// !imageUrl.equals("")
        if (imageUrl8 != null) {
            //   mImageLoader.get(imageUrl8, ImageLoader.getImageListener(holder.imgTheme8, 0, 0));

            Picasso.with(context)
                    .load(imageUrl8)
                    .error(R.drawable.placeholder1)
                    .into(holder.imgTheme8);

        }
        holder.txtHeadingThm8.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm8.setText(mItems.getCategoryName());
        holder.txtSummaryThm8.setText(mItems.getArticleSummary());
        if (mItems.getTagname() != null) {
            holder.txtTagThm8.setText(mItems.getTagname());
        }

        animateItems(holder, position);
    }

    private void configureViewHolder7(ForbesViewHolder7 holder, int position, ResponseArticles.ListEntity mItems) {
        String imageUrl7 = mItems.getImageurl();
// !imageUrl.equals("")
        if (imageUrl7 != null) {
            //   mImageLoader.get(imageUrl7, ImageLoader.getImageListener(holder.imgTheme7, 0, 0));

            Picasso.with(context)
                    .load(imageUrl7)
                    .error(R.drawable.placeholder1)
                    .into(holder.imgTheme7);

        }
        holder.txtHeadingThm7.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm7.setText(mItems.getCategoryName());
        holder.txtSummaryThm7.setText(mItems.getArticleSummary());
       /* if (mItems.getTagname() != null) {
            holder.txtTagThm7.setText(mItems.getTagname());
        }*/


        animateItems(holder, position);
    }

    private void configureViewHolder6(ForbesViewHolder6 holder, int position, ResponseArticles.ListEntity mItems) {
        String imageUrl6 = mItems.getImageurl();
// !imageUrl.equals("")
        if (imageUrl6 != null && !imageUrl6.isEmpty()) {
            //   mImageLoader.get(imageUrl6, ImageLoader.getImageListener(holder.imgTheme6, 0, 0));

            Picasso.with(context)
                    .load(imageUrl6)
                    .error(R.drawable.placeholder1)
                    .into(holder.imgTheme6);

        }
        holder.txtHeadingThm6.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm6.setText(mItems.getCategoryName());
        holder.txtSummaryThm6.setText(mItems.getArticleSummary());
        if (mItems.getTagname() != null) {
            holder.txtTagThm6.setText(mItems.getTagname());
        }

        animateItems(holder, position);
    }

    private void configureViewHolder5(ForbesViewHolder5 holder, int position, ResponseArticles.ListEntity mItems) {
        String imageUrl5 = mItems.getImageurl();
// !imageUrl.equals("")
        if (imageUrl5 != null) {
            //  mImageLoader.get(imageUrl5, ImageLoader.getImageListener(holder.imgTheme5, 0, 0));

            Picasso.with(context)
                    .load(imageUrl5)
                    .error(R.drawable.placeholder1)
                    .into(holder.imgTheme5);

        }
        holder.txtHeadingThm5.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm5.setText(mItems.getCategoryName());
        holder.txtSummaryThm5.setText(mItems.getArticleSummary());
       /* if (mItems.getTagname() != null) {
            holder.txtTagThm5.setText(mItems.getTagname());
        }*/

        animateItems(holder, position);
    }

    private void configureViewHolder4(ForbesViewHolder4 holder, int position, ResponseArticles.ListEntity mItems) {
        String imageUrl4 = mItems.getImageurl();
// !imageUrl.equals("")
        if (imageUrl4 != null) {
          /*  mImageLoader.get(imageUrl4, ImageLoader.getImageListener(holder.imgTheme4, 0, 0));*/

            Picasso.with(context)
                    .load(imageUrl4)
                    .transform(new RoundedTransformation(100, 4))
                    .resizeDimen(R.dimen.nav_drw_190px, R.dimen.nav_drw_190px)
                    .centerCrop()
                    .into(holder.imgTheme4);

        }
        holder.txtHeadingThm4.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm4.setText(mItems.getCategoryName());
        holder.txtSummaryThm4.setText(mItems.getArticleSummary());
        if (mItems.getTagname() != null) {
            holder.txtTagThm4.setText(mItems.getTagname());
        }

        animateItems(holder, position);
    }

    private void configureViewHolder3(ForbesViewHolder3 holder, int position, ResponseArticles.ListEntity mItems) {

        holder.txtHeadingThm3.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm3.setText(mItems.getCategoryName());
        holder.txtSummaryThm3.setText(mItems.getArticleSummary());

        animateItems(holder, position);
    }

    private void configureViewHolder2(ForbesViewHolder2 holder, int position, ResponseArticles.ListEntity mItems) {

        String imageUrl2 = mItems.getImageurl();
// !imageUrl.equals("")
        if (imageUrl2 != null) {
         /*   mImageLoader.get(imageUrl2, ImageLoader.getImageListener(holder.imgTheme2, 0, 0));*/

            Picasso.with(context)
                    .load(imageUrl2)
                    .error(R.drawable.placeholder1)
                    .into(holder.imgTheme2);

        }
        holder.txtHeadingThm2.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm2.setText(mItems.getCategoryName());
        holder.txtSummaryThm2.setText(mItems.getArticleSummary());
        /*if (mItems.getTagname() != null) {
            holder.txtTagThm2.setText(mItems.getTagname());
        }*/
        animateItems(holder, position);
    }

    private void configureViewHolder1(ForbesViewHolder1 holder, int position, ResponseArticles.ListEntity mItems) {

        String imageUrl1 = mItems.getImageurl();
// !imageUrl.equals("")
        if (imageUrl1 != null) {
         //   mImageLoader.get(imageUrl1, ImageLoader.getImageListener(holder.imgTheme1, 0, 0));

            Picasso.with(context)
                    .load(imageUrl1)
                    .error(R.drawable.placeholder1)
                    .into(holder.imgTheme1);

        }
        holder.txtHeadingThm1.setText(mItems.getArticleHeadline());
        holder.txtCategoryThm1.setText(mItems.getCategoryName());
        holder.txtSummaryThm1.setText(mItems.getArticleSummary());
        if (mItems.getTagname() != null) {
            holder.txtTagThm1.setText(mItems.getTagname());
        }

        animateItems(holder, position);

    }

    private void animateItems(RecyclerView.ViewHolder holder, int position) {
        if (position > mPreviousPosition) {
         //   AnimationUtils.animateSunblind(holder, true);
            AnimationUtils.animate(holder, true);
        } else {
         //   AnimationUtils.animateSunblind(holder, false);
            AnimationUtils.animate(holder, false);

        }
        mPreviousPosition = position;
    }


    @Override
    public int getItemCount() {
        return responseArticles.size();
    }


    public class ForbesViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme1;
        TextView txtHeadingThm1, txtCategoryThm1, txtSummaryThm1, txtTagThm1;


        public ForbesViewHolder1(View itemView) {
            super(itemView);

            imgTheme1 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm1 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm1 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm1 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm1 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }

    public class ForbesViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme2;
        TextView txtHeadingThm2, txtCategoryThm2, txtSummaryThm2, txtTagThm2;


        public ForbesViewHolder2(View itemView) {
            super(itemView);

            imgTheme2 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm2 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm2 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm2 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm2 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }


    public class ForbesViewHolder3 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme3;
        TextView txtHeadingThm3, txtCategoryThm3, txtSummaryThm3, txtTagThm3;


        public ForbesViewHolder3(View itemView) {
            super(itemView);


            txtHeadingThm3 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm3 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm3 = (TextView) itemView.findViewById(R.id.article_summary);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }

    public class ForbesViewHolder4 extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imgTheme4;
        TextView txtHeadingThm4, txtCategoryThm4, txtSummaryThm4, txtTagThm4;


        public ForbesViewHolder4(View itemView) {
            super(itemView);

            imgTheme4 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm4 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm4 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm4 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm4 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }


    public class ForbesViewHolder5 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme5;
        TextView txtHeadingThm5, txtCategoryThm5, txtSummaryThm5, txtTagThm5;


        public ForbesViewHolder5(View itemView) {
            super(itemView);

            imgTheme5 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm5 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm5 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm5 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm5 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }


    public class ForbesViewHolder6 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme6;
        TextView txtHeadingThm6, txtCategoryThm6, txtSummaryThm6, txtTagThm6;


        public ForbesViewHolder6(View itemView) {
            super(itemView);

            imgTheme6 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm6 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm6 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm6 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm6 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }

    public class ForbesViewHolder7 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme7;
        TextView txtHeadingThm7, txtCategoryThm7, txtSummaryThm7, txtTagThm7;


        public ForbesViewHolder7(View itemView) {
            super(itemView);

            imgTheme7 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm7 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm7 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm7 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm7 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }


    public class ForbesViewHolder8 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme8;
        TextView txtHeadingThm8, txtCategoryThm8, txtSummaryThm8, txtTagThm8;


        public ForbesViewHolder8(View itemView) {
            super(itemView);

            imgTheme8 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm8 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm8 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm8 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm8 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }


    public class ForbesViewHolder9 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme9;
        TextView txtHeadingThm9, txtCategoryThm9, txtSummaryThm9, txtTagThm9;


        public ForbesViewHolder9(View itemView) {
            super(itemView);

            imgTheme9 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm9 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm9 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm9 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm9 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }


    public class ForbesViewHolder10 extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgTheme10;
        TextView txtHeadingThm10, txtCategoryThm10, txtSummaryThm10, txtTagThm10;


        public ForbesViewHolder10(View itemView) {
            super(itemView);

            imgTheme10 = (ImageView) itemView.findViewById(R.id.article_imageview);
            txtHeadingThm10 = (TextView) itemView.findViewById(R.id.article_headline);
            txtCategoryThm10 = (TextView) itemView.findViewById(R.id.article_catigory);
            txtSummaryThm10 = (TextView) itemView.findViewById(R.id.article_summary);
            txtTagThm10 = (TextView) itemView.findViewById(R.id.artilce_tagname);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }

    public class ForbesViewHolder99 extends RecyclerView.ViewHolder implements View.OnClickListener {

        VideoView videoView;
        TextView addsHeadline, addsSummary;


        public ForbesViewHolder99(View itemView) {
            super(itemView);

            videoView = (VideoView) itemView.findViewById(R.id.adds_video_view);
            addsHeadline = (TextView) itemView.findViewById(R.id.adds_headline);
            addsSummary = (TextView) itemView.findViewById(R.id.adds_Summary);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            String addLinkUrl = "";

            addLinkUrl = responseArticles.get(getAdapterPosition()).getLink();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(addLinkUrl));
            context.startActivity(browserIntent);


            //   clickListenerArticle.onItemClick(getAdapterPosition(), v, responseArticles.get(getAdapterPosition()).getId());
        }
    }


    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


    public void setOnItemClickListener(ClickArticleListener clickListener) {
        forbesArticleAdapter.clickListenerArticle = clickListener;
    }

    public interface ClickArticleListener {
        void onItemClick(int position, View v, int id);
        //  void onItemLongClick(int position, View v);
    }

}
