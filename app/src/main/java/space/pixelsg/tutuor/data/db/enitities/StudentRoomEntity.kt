package space.pixelsg.tutuor.data.db.enitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import space.pixelsg.tutuor.data.mapper.StudentMapper
import space.pixelsg.tutuor.mapper.Mappable
import space.pixelsg.tutuor.mapper.annotations.MapperClass

@Entity(tableName = "students")
@MapperClass(mapper = StudentMapper::class)
data class StudentRoomEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long? = null,
    @ColumnInfo
    val fullName: String,
    @ColumnInfo
    val telegram: String,
    @ColumnInfo
    val teacherID: Long,
    @ColumnInfo
    val address: String,
    @ColumnInfo()
    val telNumber: String
) : RoomEntity<Long>, Mappable
