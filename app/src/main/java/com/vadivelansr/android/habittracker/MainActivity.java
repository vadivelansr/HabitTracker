package com.vadivelansr.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vadivelansr.android.habittracker.data.HabitColumns;
import com.vadivelansr.android.habittracker.data.HabitProvider;
import com.vadivelansr.android.habittracker.fragment.NewHabitDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, NewHabitDialogFragment.OnAddHabitListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.habit_list)
    ListView habitList;
    @Bind(R.id.text_add_habit)
    AppCompatTextView textAddHabit;
    TextInputLayout layoutHabit;
    private HabitAdapter habitAdapter;
    private NewHabitDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        habitAdapter = new HabitAdapter(this, null);
        habitList.setAdapter(habitAdapter);
        habitList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) habitAdapter.getItem(position);
                String selectionClause = HabitColumns._ID + " = ?";
                String[] selectionArgs = {
                        Integer.toString(cursor.getInt(cursor.getColumnIndex(HabitColumns._ID)))
                };
                ContentValues contentValues = new ContentValues();
                contentValues.put(HabitColumns.COUNT, cursor.getInt(cursor.getColumnIndex(HabitColumns.COUNT)) + 1);
                getContentResolver().update(HabitProvider.Habit.CONTENT_URI, contentValues, selectionClause, selectionArgs);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddHabitDialog();
            }
        });
        getSupportLoaderManager().initLoader(0, null, this);
    }

    private void showAddHabitDialog() {
        dialogFragment = new NewHabitDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), getString(R.string.add_habit));
        getSupportFragmentManager().executePendingTransactions();

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onAddHabit(String habitName) {
        String selectionClause = HabitColumns.NAME + " = ? COLLATE NOCASE";
        String[] selectionArgs = {
                habitName
        };
        Cursor cursor = getContentResolver().query(HabitProvider.Habit.CONTENT_URI, null, selectionClause, selectionArgs, null);

        if (cursor.getCount() > 0) {
            if (dialogFragment != null) {
                layoutHabit = (TextInputLayout) dialogFragment.getView().findViewById(R.id.input_layout_habit);
                layoutHabit.setError(getString(R.string.error_habit_redundancy));
            }
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(HabitColumns.NAME, habitName);
            contentValues.put(HabitColumns.COUNT, 0);
            contentValues.put(HabitColumns.CREATION_DATE, getDateTime());
            getContentResolver().insert(HabitProvider.Habit.CONTENT_URI, contentValues);
            if (dialogFragment != null)
                dialogFragment.dismiss();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, HabitProvider.Habit.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        habitAdapter.swapCursor(cursor);
        if (habitAdapter != null && habitAdapter.getCount() <= 0) {
            textAddHabit.setVisibility(View.VISIBLE);
        } else {
            textAddHabit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        habitAdapter.swapCursor(null);
    }
}
