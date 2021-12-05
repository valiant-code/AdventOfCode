import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

object InputUtil {
    // sample filepath: day1/input.txt
    fun readFileAsString(filepath: String): String {
        val file = getFileFromResources(filepath)
        return readFile(file.path)
    }

    fun readFileAsStringList(filepath: String, delimiter: String = "\n"): List<String> {
        val file = getFileFromResources(filepath)
        return readFile(file.path).split(delimiter);
    }

    fun readFileAsIntList(filepath: String): List<Int> {
        return readFileAsStringList(filepath).map { s: String -> Integer.valueOf(s) }
    }

    private fun readFile(path: String): String {
        val encoded = Files.readAllBytes(Paths.get(path))
        return String(encoded, StandardCharsets.UTF_8)
    }

    private fun getFileFromResources(fileName: String): File {
        val resource = this::class.java.getResource(fileName)
        return if (resource == null) {
            throw IllegalArgumentException("file is not found!")
        } else {
            File(resource.file)
        }
    }
}