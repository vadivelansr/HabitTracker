package com.vadivelansr.android.habittracker.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by vadivelansr on 6/11/2016.
 */
public interface HabitColumns {
    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID = "_id";
    @DataType(DataType.Type.TEXT)
    @NotNull
    String NAME = "name";
    @DataType(DataType.Type.INTEGER)
    String COUNT = "count";
    @DataType(DataType.Type.TEXT)
    String CREATION_DATE = "creation_date";
}
