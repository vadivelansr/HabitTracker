package com.vadivelansr.android.habittracker.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by vadivelansr on 6/11/2016.
 */
@ContentProvider(authority = HabitProvider.AUTHORITY, database = HabitDatabase.class)
public class HabitProvider {
    public static final String AUTHORITY = "com.vadivelansr.android.habittracker.data.HabitProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String HABIT = "habit";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = HabitDatabase.HABIT)
    public static class Habit {
        @ContentUri(path = Path.HABIT,
                type = "vnd.android.cursor.dir/habit",
                defaultSort = HabitColumns._ID + " DESC"
        )
        public static final Uri CONTENT_URI = buildUri(Path.HABIT);

        @InexactContentUri(name = "_ID",
                path = Path.HABIT + "/#",
                type = "vnd.android.cursor.item/coofde",
                whereColumn = HabitColumns._ID,
                pathSegment = 1

        )
        public static Uri withId(long id) {
            return buildUri(Path.HABIT, String.valueOf(id));
        }
    }
}


