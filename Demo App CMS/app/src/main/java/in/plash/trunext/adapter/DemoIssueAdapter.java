package in.plash.trunext.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import in.plash.trunext.R;
import in.plash.trunext.activity.MainActivity;
import in.plash.trunext.fragments.ArticleFeedFragment;
import in.plash.trunext.network.VolleySingleton;
import in.plash.trunext.responceObjects.ResponsePublication;
import in.plash.trunext.util.Utilities;


public class DemoIssueAdapter extends RecyclerView.Adapter {

    private final int THEME1 = 1, THEME2 = 2, THEME3 = 3, THEME4 = 4, THEME5 = 5, THEME6 = 6, THEME7 = 7, THEME8 = 8, THEME9 = 9, THEME10 = 10, THEME99 = 99, VIEW_PROG = 0;
    List<ResponsePublication.ListEntity> publishersList;
    private LayoutInflater inflater;
    Context context;
    ImageLoader mImageLoader;


    public DemoIssueAdapter(List<ResponsePublication.ListEntity> publishersList, Context context) {
        this.publishersList = publishersList;
        this.context = context;
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
    }

    @Override
    public int getItemViewType(int position) {


        if (publishersList.get(position) != null) {
            int tempID = publishersList.get(position).getLayoutID();

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

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case THEME1:
                View v1 = inflater.inflate(R.layout.bussiness_india_issue, parent, false);
                viewHolder = new AgraBusinessIndiaVH(v1);
                break;
            case THEME2:
                View v2 = inflater.inflate(R.layout.caravan_issue_screen, parent, false);
                viewHolder = new BangaloreCaravanVH(v2);
                break;
            case THEME3:
                View v3 = inflater.inflate(R.layout.inside_outside_issue, parent, false);
                viewHolder = new ChennaiInsideOutsideVH(v3);
                break;
            case THEME4:
                View v4 = inflater.inflate(R.layout.forbes_issue_screen, parent, false);
                viewHolder = new DelhiForbesVH(v4);
                break;
/*
             case THEME5:
                View v5 = inflater.inflate(R.layout.forbes_new_temp5, parent, false);
                viewHolder = new IssueViewHolder5(v5);
                break;

            case THEME99:
                View v99 = inflater.inflate(R.layout.adds_layout, parent, false);
                viewHolder = new ForbesViewHolder99(v99);
                break;
*/

            case VIEW_PROG:
                View vProg = inflater.inflate(R.layout.progress_item, parent, false);
                viewHolder = new ProgressViewHolder(vProg);
                break;
            default:

        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ResponsePublication.ListEntity mItems = publishersList.get(position);

        switch (viewHolder.getItemViewType()) {

            case VIEW_PROG:
                ProgressViewHolder vPro = (ProgressViewHolder) viewHolder;
                configureViewHolderDflt(vPro, mItems);
                break;
            case THEME1:
                AgraBusinessIndiaVH vh1 = (AgraBusinessIndiaVH) viewHolder;
                configureViewHolder1(vh1, position, mItems);
                break;
            case THEME2:
                BangaloreCaravanVH vh2 = (BangaloreCaravanVH) viewHolder;
                configureViewHolder2(vh2, position, mItems);
                break;
            case THEME3:
                ChennaiInsideOutsideVH vh3 = (ChennaiInsideOutsideVH) viewHolder;
                configureViewHolder3(vh3, position, mItems);
                break;
            case THEME4:
                DelhiForbesVH vh4 = (DelhiForbesVH) viewHolder;
                configureViewHolder4(vh4, position, mItems);
                break;
          /*  case THEME5:
                ForbesViewHolder5 vh5 = (ForbesViewHolder5) viewHolder;
                configureViewHolder5(vh5, position, mItems);
                break;
            case THEME99:
                ForbesViewHolder99 vh99 = (ForbesViewHolder99) viewHolder;
                configureViewHolder99(vh99, mItems);
                break;*/

            default:


        }
    }

    private void configureViewHolder4(DelhiForbesVH holder, int position, ResponsePublication.ListEntity mItems) {
        String imageUrl = mItems.getMagazineCoverImage();
        if (imageUrl != null) {
            try {
                mImageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imagePublication,
                        0, 0));
                //   Picasso.with(context).load(imageUrl).into(holder.imagePublication);

              /*  Glide.with(context)
                        .load(imageUrl)
                        .centerCrop()
                        .crossFade()
                        .into(holder.imagePublication);*/

                holder.heading.setText(mItems.getIssueName());
                holder.summary.setText(mItems.getSummary());
                String date = Utilities.getMonthDate(mItems.getIssuepubdate());
                holder.month.setText(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void configureViewHolder3(ChennaiInsideOutsideVH holder, int position, ResponsePublication.ListEntity mItems) {
        String imageUrl = mItems.getMagazineCoverImage();
        if (imageUrl != null) {
            try {
                mImageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imagePublication,
                        0, 0));
                //   Picasso.with(context).load(imageUrl).into(holder.imagePublication);

              /*  Glide.with(context)
                        .load(imageUrl)
                        .centerCrop()
                        .crossFade()
                        .into(holder.imagePublication);*/


                //  holder.heading.setText(mItems.getIssueName());
                holder.summary.setText(mItems.getSummary());
                String date = Utilities.getMonthDate(mItems.getIssuepubdate());
                holder.month.setText(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (mItems.getIssuecolour() != null && !mItems.getIssuecolour().isEmpty()) {
            try {
                holder.month.setTextColor(Color.parseColor(mItems.getIssuecolour()));
                // Setting drawable background
                ((GradientDrawable) holder.readnow.getBackground()).setColor(Color.parseColor(mItems.getIssuecolour()));
                //    ((GradientDrawable) holder.buy.getBackground()).setColor(Color.parseColor(mItems.getIssuecolour()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void configureViewHolder2(BangaloreCaravanVH holder, int position, ResponsePublication.ListEntity mItems) {
        String imageUrl = mItems.getMagazineCoverImage();
        if (imageUrl != null) {
            try {
                mImageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imagePublication,
                        0, 0));
                //   Picasso.with(context).load(imageUrl).into(holder.imagePublication);

              /*  Glide.with(context)
                        .load(imageUrl)
                        .centerCrop()
                        .crossFade()
                        .into(holder.imagePublication);*/


                holder.heading.setText(mItems.getIssueName());
                //  holder.summary.setText(mItems.getSummary());
                String date = Utilities.getMonthDate(mItems.getIssuepubdate());
                holder.month.setText(date);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void configureViewHolder1(AgraBusinessIndiaVH holder, int position, ResponsePublication.ListEntity mItems) {

        String imageUrl = mItems.getMagazineCoverImage();
        if (imageUrl != null) {
            try {
                mImageLoader.get(imageUrl, ImageLoader.getImageListener(holder.imagePublication,
                        0, 0));
                //   Picasso.with(context).load(imageUrl).into(holder.imagePublication);

              /*  Glide.with(context)
                        .load(imageUrl)
                        .centerCrop()
                        .crossFade()
                        .into(holder.imagePublication);*/


                holder.heading.setText(mItems.getIssueName());
                holder.summary.setText(mItems.getSummary());
                String date = Utilities.getMonthDate(mItems.getIssuepubdate());
                holder.month.setText(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void configureViewHolderDflt(ProgressViewHolder holder, ResponsePublication.ListEntity mItems) {
        holder.progressBar.setIndeterminate(true);
    }

    @Override
    public int getItemCount() {
        return publishersList.size();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    public class AgraBusinessIndiaVH extends RecyclerView.ViewHolder {
        ImageView imagePublication, downButton;
        TextView summary, month, heading, readnow;

        public AgraBusinessIndiaVH(View itemView) {
            super(itemView);
            imagePublication = (ImageView) itemView.findViewById(R.id.issue_image);
            month = (TextView) itemView.findViewById(R.id.txt_issue_month);
            heading = (TextView) itemView.findViewById(R.id.issue_headline);
            summary = (TextView) itemView.findViewById(R.id.issue_summary);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // create bundle with the appropriate selected issue position
                    Bundle bundle = new Bundle();
                    //   bundle.putSerializable("ResponsePublication", responsePublicationObj.getList().get(getAdapterPosition()));

                    bundle.putSerializable("ResponsePublication", publishersList.get(getAdapterPosition()));
                    Fragment fragment = new ArticleFeedFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if (fragment.isAdded()) {
                        transaction.show(fragment);
                    } else {
                        //    transaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                    }
                    transaction.commit();

                    MainActivity.setToolBarTitle(publishersList.get(getAdapterPosition()).getIssueName());

                }
            });
        }
    }

    public class BangaloreCaravanVH extends RecyclerView.ViewHolder {
        ImageView imagePublication, downButton;
        TextView summary, month, heading, readnow;

        public BangaloreCaravanVH(View itemView) {
            super(itemView);
            imagePublication = (ImageView) itemView.findViewById(R.id.issue_image);
            month = (TextView) itemView.findViewById(R.id.txt_issue_month);
            heading = (TextView) itemView.findViewById(R.id.issue_headline);
            summary = (TextView) itemView.findViewById(R.id.issue_summary);

            readnow = (TextView) itemView.findViewById(R.id.issue_read);


            readnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // create bundle with the appropriate selected issue position
                    Bundle bundle = new Bundle();
                    //   bundle.putSerializable("ResponsePublication", responsePublicationObj.getList().get(getAdapterPosition()));

                    bundle.putSerializable("ResponsePublication", publishersList.get(getAdapterPosition()));
                    Fragment fragment = new ArticleFeedFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if (fragment.isAdded()) {
                        transaction.show(fragment);
                    } else {
                        //    transaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                    }
                    transaction.commit();

                    MainActivity.setToolBarTitle(publishersList.get(getAdapterPosition()).getIssueName());

                }
            });
        }
    }

    public class ChennaiInsideOutsideVH extends RecyclerView.ViewHolder {
        ImageView imagePublication, downButton;
        TextView summary, month, heading, readnow;

        public ChennaiInsideOutsideVH(View itemView) {
            super(itemView);
            imagePublication = (ImageView) itemView.findViewById(R.id.issue_image);
            month = (TextView) itemView.findViewById(R.id.txt_issue_month);
            // heading = (TextView) itemView.findViewById(R.id.issue_headline);
            summary = (TextView) itemView.findViewById(R.id.issue_summary);

            readnow = (TextView) itemView.findViewById(R.id.issue_read);


            readnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // create bundle with the appropriate selected issue position
                    Bundle bundle = new Bundle();
                    //   bundle.putSerializable("ResponsePublication", responsePublicationObj.getList().get(getAdapterPosition()));

                    bundle.putSerializable("ResponsePublication", publishersList.get(getAdapterPosition()));
                    Fragment fragment = new ArticleFeedFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if (fragment.isAdded()) {
                        transaction.show(fragment);
                    } else {
                        //    transaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                    }
                    transaction.commit();

                    MainActivity.setToolBarTitle(publishersList.get(getAdapterPosition()).getIssueName());

                }
            });
        }
    }

    public class DelhiForbesVH extends RecyclerView.ViewHolder {
        ImageView imagePublication, downButton;
        TextView summary, month, heading, readnow;

        public DelhiForbesVH(View itemView) {
            super(itemView);
            imagePublication = (ImageView) itemView.findViewById(R.id.issue_image);
            month = (TextView) itemView.findViewById(R.id.txt_issue_month);
            heading = (TextView) itemView.findViewById(R.id.issue_headline);
            summary = (TextView) itemView.findViewById(R.id.issue_summary);

            readnow = (TextView) itemView.findViewById(R.id.issue_read);


            readnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // create bundle with the appropriate selected issue position
                    Bundle bundle = new Bundle();
                    //   bundle.putSerializable("ResponsePublication", responsePublicationObj.getList().get(getAdapterPosition()));

                    bundle.putSerializable("ResponsePublication", publishersList.get(getAdapterPosition()));
                    Fragment fragment = new ArticleFeedFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if (fragment.isAdded()) {
                        transaction.show(fragment);
                    } else {
                        //    transaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
                        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

                        transaction.replace(R.id.content_frame, fragment);
                        transaction.addToBackStack(null);
                    }
                    transaction.commit();

                    MainActivity.setToolBarTitle(publishersList.get(getAdapterPosition()).getIssueName());

                }
            });
        }
    }
}
