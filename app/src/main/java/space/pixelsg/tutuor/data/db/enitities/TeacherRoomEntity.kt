package space.pixelsg.tutuor.data.db.enitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import space.pixelsg.tutuor.data.mapper.TeacherMapper
import space.pixelsg.tutuor.mapper.Mappable
import space.pixelsg.tutuor.mapper.annotations.MapperClass

@Entity(tableName = "teachers")
@MapperClass(mapper = TeacherMapper::class)
data class TeacherRoomEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long? = null,
    @ColumnInfo()
    val telegram: String,
    @ColumnInfo()
    val fullName: String,
    @ColumnInfo()
    val email: String
) : RoomEntity<Long>, Mappable