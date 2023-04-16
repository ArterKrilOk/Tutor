package space.pixelsg.tutuor.domain.models

import space.pixelsg.tutuor.mapper.Mappable

data class TeacherEntity(
    override val id: Long,
    val fullName: String,
    val telegram: String,
    val email: String,
) : Entity<Long>, Mappable
