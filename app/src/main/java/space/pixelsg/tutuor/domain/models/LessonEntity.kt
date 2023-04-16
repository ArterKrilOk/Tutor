package space.pixelsg.tutuor.domain.models

import org.joda.time.DateTime
import space.pixelsg.tutuor.mapper.Mappable

data class LessonEntity(
    override val id: Long,
    val title: String,
    val description: String,
    val grade: Int,
    val date: DateTime,
    val teacherID: Long,
    val studentID: Long
) : Entity<Long>, Mappable
