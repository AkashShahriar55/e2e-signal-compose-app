package usecases

import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.R
import com.nsa.domain.model.UiText
import com.nsa.domain.model.ValidationResult
import common.isEmailValid
import common.isPasswordValid
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ValidatePasswordUseCase: BaseCoroutinesUseCase<ValidationResult, String>(
    executionDispatcher = Dispatchers.Default
) {


    override suspend fun buildUseCase(params: String?): ValidationResult {

        if (params.isNullOrBlank() || params.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = R.string.strThePasswordNeedsToConsistOfAtLeastEightCharacters
            )
        }

        if (!isPasswordValid(params)) {
            return ValidationResult(
                successful = false,
                errorMessage =  R.string.strThePasswordNeedsToContainAtLeastOneLetterAndDigit
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}