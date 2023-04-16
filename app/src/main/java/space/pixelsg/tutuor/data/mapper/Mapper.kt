package space.pixelsg.tutuor.data.mapper

import space.pixelsg.tutuor.data.db.enitities.LessonRoomEntity
import space.pixelsg.tutuor.data.db.enitities.StudentRoomEntity
import space.pixelsg.tutuor.data.db.enitities.TeacherRoomEntity
import space.pixelsg.tutuor.domain.models.LessonEntity
import space.pixelsg.tutuor.domain.models.StudentEntity
import space.pixelsg.tutuor.domain.models.TeacherEntity
import space.pixelsg.tutuor.mapper.annotations.MapMethod

object TeacherMapper {
    @MapMethod
    fun dbToDomain(teacher: TeacherRoomEntity): TeacherEntity = teacher.run {
        return TeacherEntity(id ?: 0, fullName, telegram, email)
    }

    @MapMethod
    fun domainToDB(teacher: TeacherEntity): TeacherRoomEntity = teacher.run {
        TeacherRoomEntity(if (id == -1L) null else id, telegram, fullName, email)
    }
}

object StudentMapper {
    @MapMethod
    fun dbToDomain(student: StudentRoomEntity): StudentEntity = student.run {
        StudentEntity(
            id ?: 0, fullName, telegram, teacherID, address, telNumber
        )
    }

    @MapMethod
    fun domainToDB(student: StudentEntity): StudentRoomEntity = student.run {
        StudentRoomEntity(
            if (id == -1L) null else id,
            fullName,
            telegram,
            teacherID,
            address,
            telNumber
        )
    }
}

object LessonMapper {
    @MapMethod
    fun dbToDomain(lesson: LessonRoomEntity): LessonEntity = lesson.run {
        LessonEntity(id ?: 0, title, description, grade, date, teacherID, studentID)
    }

    @MapMethod
    fun domainToDb(lesson: LessonEntity): LessonRoomEntity = lesson.run {
        LessonRoomEntity(
            if (id == -1L) null else id,
            title,
            description,
            grade,
            date,
            teacherID,
            studentID
        )
    }
}