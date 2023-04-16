package space.pixelsg.tutuor.main.lessons.create_lesson

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.take
import org.joda.time.format.DateTimeFormat
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.domain.models.StudentEntity
import space.pixelsg.tutuor.main.lessons.usecase.CreateLessonParams
import space.pixelsg.tutuor.main.lessons.usecase.CreateLessonUseCase
import space.pixelsg.tutuor.main.lessons.usecase.GetStudentsUseCase
import javax.inject.Inject


class CreateLessonViewModel @Inject constructor(
    private val createLessonUseCase: CreateLessonUseCase,
    private val getStudentsUseCase: GetStudentsUseCase,
) : CommonViewModel() {

    val students = getStudentsUseCase.execute(Unit)
        .catch { error.emit(it.localizedMessage ?: it.message ?: "Something vent wrong") }
        .shareWhileSubscribed()

    val student = MutableStateFlow<StudentEntity?>(null)
    val title = MutableStateFlow("")
    val description = MutableStateFlow("")

    val date = MutableStateFlow("")
    val time = MutableStateFlow("")

    val datetime = combine(date, time) { date, time ->
        "$date $time"
    }.shareWhileSubscribed()

    private val dateTime = combine(date, time) { date, time ->
        try {
            val formatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm")
            return@combine formatter.parseDateTime("$date $time")
        } catch (t: Throwable) {
            return@combine null
        }
    }.shareWhileSubscribed()

    val canCreate = combine(
        title,
        student
    ) { title, student ->
        title.isNotEmpty() && student != null
    }.shareWhileSubscribed()

    fun create() = combine(
        student.filterNotNull().take(1),
        title.take(1),
        description.take(1),
        dateTime.filterNotNull().take(1)
    ) { student, title, description, dateTime ->
        CreateLessonParams(title, description, dateTime, student.id)
    }.flatMapConcat {
        createLessonUseCase.execute(it)
    }
}