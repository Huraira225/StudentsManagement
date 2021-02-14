package com.example.studentmanagement.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.studentmanagement.classes.ScreenItems;
import com.example.studentmanagement.R;

import java.util.List;

public class OnboardingSliderAdapter extends PagerAdapter {
LayoutInflater layoutInflater;
    Context context ;
    List<ScreenItems> mListScreen;

    public OnboardingSliderAdapter(Context mContext, List<ScreenItems> mListScreen) {
        this.context = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_oboarding_slider_layout, container, false);

        ImageView slide_imageView = view.findViewById(R.id.Slide_image);
        TextView Slide_headings = view.findViewById(R.id.slide_headings);
        TextView Slide_doc = view.findViewById(R.id.slide_doc);

        Slide_headings.setText(mListScreen.get(position).getTitle());
        Slide_doc.setText(mListScreen.get(position).getDescription());
        slide_imageView.setImageResource(mListScreen.get(position).getScreenImg());

        container.addView(view);
       return view;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View)object);

    }
}
