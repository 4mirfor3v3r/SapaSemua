package amirlabs.sapasemua.data.local.entity

import amirlabs.sapasemua.base.DbModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int?,

    @ColumnInfo(name = "title")
    var reportTitle: String,

    @ColumnInfo(name = "address")
    var reportAddress: String,

    @ColumnInfo(name = "date")
    var reportDate: String,

    @ColumnInfo(name = "status")
    var reportStatus: Int
): DbModel() {
}
