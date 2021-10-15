package vn.nms.sample.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import vn.nms.sample.data.entity.MovieEntity
import vn.nms.sample.data.entity.MovieMergeData
import vn.nms.sample.data.entity.MovieRelationEntity

@Dao
interface MovieRelationDao : BaseDao<MovieRelationEntity> {

    @Query("DELETE FROM movie_relation_table")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM movie_relation_table")
    fun getMovieRelationList(): PagingSource<Int, MovieMergeData>

    @Query("SELECT * FROM movie_relation_table WHERE objectId = :objectId")
    fun getRelationItem(objectId: String): MovieMergeData

}