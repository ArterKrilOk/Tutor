package space.pixelsg.tutuor.mapper

open class SmartMapperError(message: String = "") : Exception("Smart Mapper Exception $message")

class SmartMapperMethodNotFound : SmartMapperError("Can not find map method")

class SmartMapperMapperClassNotFound : SmartMapperError("Can not find mapper class")