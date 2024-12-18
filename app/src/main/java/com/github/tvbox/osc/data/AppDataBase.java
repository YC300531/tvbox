package com.github.tvbox.osc.data;

import androidx.base.c3.a;
import androidx.base.c3.c;
import androidx.base.c3.e;
import androidx.base.c3.g;
import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {a.class, g.class, e.class, c.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
}
