package space.pixelsg.tutuor.main.lessons.edit_lesson

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import space.pixelsg.tutuor.common.CommonViewModel
import space.pixelsg.tutuor.domain.models.LessonEntity
import space.pixelsg.tutuor.main.lessons.usecase.EditLessonParams
import space.pixelsg.tutuor.main.lessons.usecase.EditLessonUseCase
import space.pixelsg.tutuor.main.lessons.usecase.GetLessonUseCase
import javax.inject.Inject

@OptIn(FlowPreview::class)
class EditLessonViewModel @Inject constructor(
    private val getLessonUseCase: GetLessonUseCase,
    private val editLessonUseCase: EditLessonUseCase,
) : CommonViewModel() {

    val canEdit = MutableStateFlow(false)
    val description = MutableStateFlow("")
    val grade = MutableStateFlow(-1)

    private val nullableLesson = MutableStateFlow<LessonEntity?>(null)
    val lesson = nullableLesson.filterNotNull().onEach {
        description.emit(it.description)
        grade.emit(it.grade)
    }.shareWhileSubscribed()

    fun loadLesson(id: Long) {
        getLessonUseCase.execute(id).take(1).collectViewModel(nullableLesson)
    }

    val isChanged = combine(
        lesson,
        description,
        grade
    ) { lesson, description, grade ->
        lesson.description != description || lesson.grade != grade
    }.shareWhileSubscribed()

    fun save() = combine(
        lesson.take(1),
        description.take(1),
        grade.take(1)
    ) { lesson, description, grade ->
        EditLessonParams(lesson.id, grade, description)
    }.flatMapConcat {
        editLessonUseCase.execute(it)
    }.onEach {
        canEdit.emit(false)
    }

    fun edit() {
        viewModelScope.launch {
            canEdit.emit(true)
        }
    }

    fun setGrade(g: Int) {
        viewModelScope.launch {
            grade.emit(g)
        }
    }
}