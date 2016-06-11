package com.vadivelansr.android.habittracker;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.vadivelansr.android.habittracker.data.HabitColumns;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by vadivelansr on 6/11/2016.
 */
public class HabitAdapter extends CursorAdapter {
    public HabitAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.habit_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.habitName.setText(cursor.getString(cursor.getColumnIndex(HabitColumns.NAME)));
        viewHolder.habitCount.setText("" + cursor.getInt(cursor.getColumnIndex(HabitColumns.COUNT)));
        double frequency = cursor.getInt(cursor.getColumnIndex(HabitColumns.COUNT))
                /getDays(cursor.getString(cursor.getColumnIndex(HabitColumns.CREATION_DATE)));
        viewHolder.habitFrequency.setText("" + frequency);

    }

    private double getDays(String localCreationDate) {
        long days = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date creationDate = dateFormat.parse(localCreationDate);
            Date currentDate = new Date();
            long diff = currentDate.getTime() - creationDate.getTime();
            days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            days = days > 0 ? days : 1;
        } catch (Exception e) {

        }
        return days;
    }

    public static class ViewHolder {
        @Bind(R.id.habit_name)
        AppCompatTextView habitName;
        @Bind(R.id.habit_count)
        AppCompatTextView habitCount;
        @Bind(R.id.habit_frequency)
        AppCompatTextView habitFrequency;
        @Bind(R.id.habit_increment)
        AppCompatImageView habitIncrement;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
