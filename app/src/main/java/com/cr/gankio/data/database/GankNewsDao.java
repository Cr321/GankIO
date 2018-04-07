package com.cr.gankio.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.LinkedList;
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
