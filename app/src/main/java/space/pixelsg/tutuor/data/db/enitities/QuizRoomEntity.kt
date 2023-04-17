package space.pixelsg.tutuor.data.db.enitities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import org.joda.time.DateTime
import space.pixelsg.tutuor.data.db.converters.DateTimeConverter
import space.pixelsg.tutuor.data.mapper.QuizzesMapper
import space.pixelsg.tutuor.mapper.Mappable
import space.pixelsg.tutuor.mapper.annotations.MapperClass

@Entity(tableName = "quizzes")
@MapperClass(mapper = QuizzesMapper::class)
@TypeConverters(value = [DateTimeConverter::class])
data class QuizRoomEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long? = null,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val url: String,
    @ColumnInfo
    val dateTime: DateTime,
) : RoomEntity<Long>, Mappable
