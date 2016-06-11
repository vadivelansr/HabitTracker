package com.vadivelansr.android.habittracker.data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by vadivelansr on 6/11/2016.
 */
@Database(version = HabitDatabase.VERSION)
public class HabitDatabase {
    public static final int VERSION = 1;
    @Table(HabitColumns.class)
    public static final String HABIT = "habit";
}
