package vn.nms.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import vn.nms.sample.data.entity.AdEntity

@Dao
interface AdDao : BaseDao<AdEntity> {

    @Query("DELETE FROM ad_table")
    fun deleteAll()

}