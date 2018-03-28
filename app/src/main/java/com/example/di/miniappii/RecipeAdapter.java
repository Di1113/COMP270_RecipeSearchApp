package com.example.di.miniappii;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by di on 3/25/18.
 */

public class RecipeAdapter extends BaseAdapter {

    //adapter takes the data and load the view
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflator;

    //constructors
    public RecipeAdapter(Context context, ArrayList<Recipe> recipeArrayList) {
        //initialize instance variables'
        this.mContext = context;
        this.mRecipeList = recipeArrayList;
        mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    //return the item at specific position in the data source
    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    //return the row id associated with the specific position in the list
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        //check if the view already exist
        //if yes, no need to inflate and findViewById again
        if (convertView == null){
            //inflate
            convertView = mInflator.inflate(R.layout.list_item, parent, false);
            //add the views to the holder
            viewHolder = new ViewHolder();
            viewHolder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumbnail);
            viewHolder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            viewHolder.prepTimeTextView = convertView.findViewById(R.id.recipe_list_preptime);
            viewHolder.servingsTextView = convertView.findViewById(R.id.recipe_list_servings);
            viewHolder.dietLabelTextView = convertView.findViewById(R.id.recipe_list_diet_label);
            viewHolder.wantToCookButton = convertView.findViewById(R.id.want_cook_button);
            //add the holder to the view
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        //get relevant subview of the row view
        TextView titleTextView = viewHolder.titleTextView;
        TextView prepTimeTextView = viewHolder.prepTimeTextView;
        TextView servingsTextView = viewHolder.servingsTextView;
        TextView dietLabelTextView = viewHolder.dietLabelTextView;
        ImageView thumbnailImageView = viewHolder.thumbnailImageView;
        Button wantCookButton = viewHolder.wantToCookButton;

        //get corresponding recipe for each row
        final Recipe recipe = (Recipe)getItem(position);

        //update the row view's textviews and imageview to display the information
        //titleTextView
        titleTextView.setText(recipe.title);

        prepTimeTextView.setText(recipe.prepTime);

        //serving text view
        servingsTextView.setText("Servings for "+ recipe.servingNumber + " people");

        dietLabelTextView.setText(recipe.dietLabel);

        //imageView
        //use Picasso library to load image from the image url
        Picasso.with(mContext).load(recipe.imageURL).into(thumbnailImageView);

        final Intent resultIntent = new Intent(Intent.ACTION_VIEW);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        resultIntent.setData(Uri.parse(recipe.url));

        final PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0, resultIntent, 0);

        wantCookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "channel_ID")
                        .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                        .setContentTitle("Cooking Instructions")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);


                NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                bigText.bigText("The instructions for " + recipe.title + " can be found here.");
                bigText.setBigContentTitle("Cooking Instructions");
                mBuilder.setStyle(bigText);
                mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
                mBuilder.setContentIntent(pendingIntent);

                NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(mContext);
                mNotificationManager.notify((int)System.currentTimeMillis(), mBuilder.build());
            }
        });


        return convertView;
    }

    //viewHolder
    //is used to customize what you want to put into the view
    //it depends on the layout design of your row
    //this will be a private static class you have to define
    private static class ViewHolder {
        public TextView titleTextView;
        public TextView prepTimeTextView;
        public TextView servingsTextView;
        public TextView dietLabelTextView;
        public ImageView thumbnailImageView;
        public Button wantToCookButton;
    }
}