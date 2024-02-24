package usecases

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.coroutines.BaseUseCase
import kotlinx.coroutines.Dispatchers
import java.util.Locale

class DateFormaterUseCase: BaseCoroutinesUseCase<String?, String>(
    Dispatchers.Default
) {
    override suspend fun buildUseCase(params: String?): String? {
        val timestamp = params
        val timestampFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val outputFormat = "MMM dd, yyyy HH:mm:ss"

        val dateFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT")

        val parser = SimpleDateFormat(timestampFormat, Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("GMT")

        try {
            val date = parser.parse(timestamp)
            if (date != null) {
                dateFormatter.timeZone = TimeZone.getDefault()
                return dateFormatter.format(date)
            }
        } catch (e: Exception) {
            // Handle parsing error
            e.printStackTrace()
        }

        // If parsing fails, return the original timestamp
        return timestamp
    }
}