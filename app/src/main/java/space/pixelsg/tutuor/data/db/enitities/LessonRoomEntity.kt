package space.pixelsg.tutuor.data.db.enitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.joda.time.DateTime
import space.pixelsg.tutuor.data.db.converters.DateTimeConverter
import space.pixelsg.tutuor.data.mapper.LessonMapper
import space.pixelsg.tutuor.mapper.Mappable
import space.pixelsg.tutuor.mapper.annotations.MapperClass

@Entity(tableName = "lessons")
@TypeConverters(value = [DateTimeConverter::class])
@MapperClass(mapper = LessonMapper::class)
data class LessonRoomEntity(
    @PrimaryKey
    override val id: Long? = null,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val grade: Int = -1,
    @ColumnInfo
    val date: DateTime,
    @ColumnInfo
    val teacherID: Long,
    @ColumnInfo
    val studentID: Long
) : RoomEntity<Long>, Mappable
