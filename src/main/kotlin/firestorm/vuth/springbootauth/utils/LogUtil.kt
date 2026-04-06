package firestorm.vuth.springbootauth.utils

import org.aspectj.apache.bcel.classfile.JavaClass
import org.slf4j.LoggerFactory

object LogUtil {
    private val log = LoggerFactory.getLogger(JavaClass::class.java)

    fun info(message: String) = log.info(message)

    fun debug(message: String, args: Any) = log.debug(message, args)

    fun debug(message: String, vararg args: Any?) {
        if (log.isDebugEnabled) {
            log.debug(message, args)
        }
    }

    fun warn(message: String, args: Any) = log.warn(message, args)
    fun warn(message: String, vararg args: Any?) {
        if (log.isWarnEnabled) {
            log.warn(message, args)
        }
    }

    fun error(message: String, args: Any) = log.error(message, args)
    fun error(message: String, vararg args: Any?) {
        if (log.isErrorEnabled) {
            log.error(message, *args)
        }
    }
}