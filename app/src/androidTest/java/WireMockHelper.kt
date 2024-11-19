import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object WireMockHelper {

    fun fileToString(
        path: String,
        context: Context = InstrumentationRegistry.getInstrumentation().context
    ): String {
        return BufferedReader(
            InputStreamReader(
                context.assets.open(path),
                StandardCharsets.UTF_8
            )
        ).readText()
    }

    fun String.loadJsonFromFileExt(): String {
        val context: Context = InstrumentationRegistry.getInstrumentation().context
        return BufferedReader(
            InputStreamReader(
                context.assets.open(this),
                StandardCharsets.UTF_8
            )
        ).readText()
    }

    fun getUser(num: Int): String {
        val data = "get_person${num}.json".loadJsonFromFileExt()
        return data
    }
}