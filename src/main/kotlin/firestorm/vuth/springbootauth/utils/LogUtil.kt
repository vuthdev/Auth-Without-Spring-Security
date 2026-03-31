package firestorm.vuth.springbootauth.utils

import org.aspectj.apache.bcel.classfile.JavaClass
import org.slf4j.LoggerFactory
import tools.jackson.databind.ObjectMapper

object LogUtil {
    private val objectMapper = ObjectMapper()
    private val logger = LoggerFactory.getLogger(JavaClass::class.java)

    fun logJson(label: String, data: Any?) {
        try {
            val json: String = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)
            logger.info("$label: $json")
        } catch (e: Exception) {
            logger.error("Failed to serialize log data", e)
        }
    }
}