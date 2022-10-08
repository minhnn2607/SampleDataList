package vn.nms.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import vn.nms.sample.data.entity.MovieEntity

@Dao
interface MovieDao : BaseDao<MovieEntity> {

    @Query("DELETE FROM movie_table")
    fun deleteAll()

}