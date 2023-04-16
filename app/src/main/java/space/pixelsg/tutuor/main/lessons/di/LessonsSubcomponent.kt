package space.pixelsg.tutuor.main.lessons.di

import dagger.Subcomponent
import space.pixelsg.tutuor.main.lessons.calendar.CalendarSubcomponent
import space.pixelsg.tutuor.main.lessons.create_lesson.CreateLessonSubcomponent
import space.pixelsg.tutuor.main.lessons.list.LessonsListSubcomponent
import space.pixelsg.tutuor.main.lessons.usecase.CreateLessonUseCase
import space.pixelsg.tutuor.main.lessons.usecase.GetLessonsUseCase
import space.pixelsg.tutuor.main.lessons.usecase.GetStudentUseCase
import space.pixelsg.tutuor.main.lessons.usecase.GetStudentsUseCase

@Subcomponent
interface LessonsSubcomponent {
    val getLessonsUseCase: GetLessonsUseCase
    val createLessonsUseCase: CreateLessonUseCase
    val getStudentUseCase: GetStudentUseCase
    val getStudentsUseCase: GetStudentsUseCase

    val calendarSubcomponent: CalendarSubcomponent
    val lessonsListSubcomponent: LessonsListSubcomponent
    val createLessonsSubcomponent: CreateLessonSubcomponent
}