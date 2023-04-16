package space.pixelsg.tutuor.data.db.converters

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateTimeConverter {
    @TypeConverter
    fun dateTimeToLong(dateTime: DateTime?): Long {
        if (dateTime == null) return -1L
        return dateTime.millis
    }

    @TypeConverter
    fun longToDateTime(value: Long): DateTime? {
        if (value == -1L) return null
        return DateTime(value)
    }
}