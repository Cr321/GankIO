package com.cr.gankio.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * @author RUI CAI
 * @date 2018/4/6
 */
@Dao
public interface GankNewsDao {

    @Query("SELECT * FROM gankio")
    List<GankNews> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<GankNews> gankNews);

    @Query("SELECT * FROM gankio WHERE type = :type ORDER BY publishedAt DESC")
    List<GankNews> getByType(String type);
}
