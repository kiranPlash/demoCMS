package in.plash.trunext.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import in.plash.trunext.Constants.Constants;
import in.plash.trunext.R;
import in.plash.trunext.activity.MainActivity;
import in.plash.trunext.fragments.ArticleFeedFragment;
import in.plash.trunext.fragments.SubscriptionFragment;
import in.plash.trunext.network.VolleySingleton;
import in.plash.trunext.responceObjects.ResponsePublication;
import in.plash.trunext.util.Snack;
import in.plash.trunext.util.Utilities;


/**
 * Created by Kiran on 10/21/2015.
 */
public class ForbesIssueAdapter extends RecyclerView.Adapter<ForbesIssueAdapter.ForbesIssueViewHolder> {
    List<ResponsePublication.ListEntity> publishersList;
    private LayoutInflater inflater;

    Context context;
    ImageLoader mImageLoader;

    int width, height;


    public ForbesIssueAdapter(List<ResponsePublication.ListEntity> publishersList, Context context) {
        this.publishersList = publishersList;
        this.context = context;
        inflater = LayoutInflater.from(context);

        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
    }


    @Override
    public ForbesIssueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   View view = inflater.inflate(R.layout.lyt_inside_out, parent, false);
        View view = inflater.inflate(R.layout.forbes_issue_screen, parent, false);
        ForbesIssueViewHolder holder = new ForbesIssueViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ForbesIssueViewHolder holder, int position) {

       /* holder.layout.getLayoutParams().height = height;
        holder.layout.getLayoutParams().width = width;
        holder.layout.requestLayout();
*/

        ResponsePublication.ListEntity mItems = publishersList.get(position);
        String imageUrl = mItems.getMagazineCoverImage();

        // PAYMENT chk shd be written
        if (mItems.isfreetest() || mItems.ispurchased()) {
            holder.buy.setVisibility(View.GONE);
            holder.readnow.setVisibility(View.VISIBLE);
            holder.subcribe.setVisibility(View.GONE);
        } else {
            holder.buy.setVisibility(View.VISIBLE);
            holder.readnow.setVisibility(View.GONE);
            holder.subcribe.setVisibility(View.GONE);
        }


        // Inflating NetworkImageView using Volley
        // holder.imagePublication.setImageUrl(imageUrl, mImageLoader);
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


                String date = Utilities.getMonthDate(mItems.getIssuepubdate());

                holder.month.setText(date);
                holder.heading.setText(mItems.getIssueName());
                holder.summary.setText(mItems.getSummary());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
       /* CharSequence c = mItems.getIssueName();
        float spacing = (float) 0;
        String sum = Utilities.applyKerning(c, spacing).toString();*/



     /*   try {
            holder.month.setTextColor(Color.parseColor(mItems.getIssuecolour()));
            // Setting drawable background
            ((GradientDrawable) holder.readnow.getBackground()).setColor(Color.parseColor(mItems.getIssuecolour()));
            ((GradientDrawable) holder.buy.getBackground()).setColor(Color.parseColor(mItems.getIssuecolour()));
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }


    @Override
    public int getItemCount() {
        return publishersList.size();
    }


    public class ForbesIssueViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePublication, downButton;
        TextView summary, month, heading, readnow, buy, subcribe;
        LinearLayout layout;

        public ForbesIssueViewHolder(View itemView) {
            super(itemView);

            // forbes Inside Outside style
            imagePublication = (ImageView) itemView.findViewById(R.id.issue_image);
            month = (TextView) itemView.findViewById(R.id.txt_lyt_inside_issue_month_year);
            heading = (TextView) itemView.findViewById(R.id.issue_headline);
            summary = (TextView) itemView.findViewById(R.id.issue_summary);

            readnow = (TextView) itemView.findViewById(R.id.issue_read);
            buy = (TextView) itemView.findViewById(R.id.issue_buy);
            subcribe = (TextView) itemView.findViewById(R.id.issue_subscribe);

            // Use ItemView.setOnClick for forbes Triangle Design

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


            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create bundle with the appropriate selected issue position
                    if (Constants.isANNONYMOUS) {

                        Snack.showLong(context, "Please Login With Facebook to Buy");

                    } else {

                        Bundle bundle = new Bundle();

                        bundle.putSerializable("ResponsePublicationBuy", publishersList.get(getAdapterPosition()));
                        Fragment fragment = new SubscriptionFragment();
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
                }
            });

            subcribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create bundle with the appropriate selected issue position
                    Bundle bundle = new Bundle();

                    bundle.putSerializable("ResponsePublicationBuy", publishersList.get(getAdapterPosition()));
                    Fragment fragment = new SubscriptionFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    if (fragment.isAdded()) {
                        transaction.show(fragment);
                    } else {
                 //       transaction.setCustomAnimations(R.anim.fade_in, R.anim.face_out);
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
