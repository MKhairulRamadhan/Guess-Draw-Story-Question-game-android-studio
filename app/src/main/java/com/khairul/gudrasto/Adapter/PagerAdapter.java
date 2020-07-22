package com.khairul.gudrasto.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.khairul.gudrasto.Activity.GuessSoundActivity;
import com.khairul.gudrasto.Activity.ListPaintActivity;
import com.khairul.gudrasto.Model.PagerModel;
import com.khairul.gudrasto.R;
import com.khairul.gudrasto.Activity.StoryNQuestionActivity;

import java.util.List;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private List<PagerModel> models;
    LayoutInflater layoutInflater;
    Context context;

    public PagerAdapter(List<PagerModel> models, Context context){
        this.models = models;
        this.context = context;
    }


    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_pager, container, false);

        ImageView imageView;
        final TextView title;
        final int id;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        id = models.get(position).getId();

        container.addView(view, 0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == 1){
                    Intent moveToGuess = new Intent(context, GuessSoundActivity.class);
                    context.startActivity(moveToGuess);
                }else if(id == 2){
                    Intent moveToDraw = new Intent(context, ListPaintActivity.class);
                    context.startActivity(moveToDraw);
                }else if(id == 3){
                    Intent moveToStoryGame = new Intent(context, StoryNQuestionActivity.class);
                    context.startActivity(moveToStoryGame);
                }else{
                    Log.d("DATA", "Ada kesalahan...!");
                }
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


}
