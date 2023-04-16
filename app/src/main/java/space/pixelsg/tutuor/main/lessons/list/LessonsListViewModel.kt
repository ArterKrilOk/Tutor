package space.pixelsg.tutuor.main.lessons.list

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.main.lessons.list.adapter.LessonItem
import space.pixelsg.tutuor.main.lessons.usecase.GetLessonsUseCase
import space.pixelsg.tutuor.main.lessons.usecase.GetStudentUseCase
import javax.inject.Inject

class LessonsListViewModel @Inject constructor(
    getLessonsUseCase: GetLessonsUseCase,
    private val getStudentUseCase: GetStudentUseCase
) : CommonViewModel() {
    val lessons = getLessonsUseCase.execute(Unit)
        .map { lessons ->
            lessons.map {
                val student = getStudentUseCase.execute(it.studentID).first()
                LessonItem(
                    it.id,
                    it.title,
                    it.description,
                    it.grade,
                    it.date,
                    student.fullName
                )
            }
        }
        .catch { error.emit(it.localizedMessage ?: it.message ?: "Something vent wrong") }
        .shareWhileSubscribed()
}