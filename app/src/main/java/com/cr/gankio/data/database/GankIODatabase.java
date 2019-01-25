package com.cr.gankio.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * @author RUI CAI
 * @date 2018/4/6
 */
@Database(entities = {GankNews.class}, version = 1)
public abstract class GankIODatabase extends RoomDatabase {
    public abstract GankNewsDao gankNewsDao();
}
