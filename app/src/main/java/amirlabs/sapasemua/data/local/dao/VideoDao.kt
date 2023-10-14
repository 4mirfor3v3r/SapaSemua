package amirlabs.sapasemua.data.local.dao

import amirlabs.sapasemua.base.DevDao
import amirlabs.sapasemua.data.local.entity.VideoEntity
import androidx.room.Dao
import androidx.room.Query

@Dao
interface VideoDao : DevDao<VideoEntity> {

    @Query("SELECT * FROM video ORDER BY id DESC")
    fun getAllNotification(): List<VideoEntity>
}