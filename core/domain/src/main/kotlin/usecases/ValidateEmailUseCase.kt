package usecases

import com.nsa.coroutines.BaseCoroutinesUseCase
import com.nsa.domain.R
import com.nsa.domain.model.UiText
import com.nsa.domain.model.ValidationResult
import common.isEmailValid
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ValidateEmailUseCase: BaseCoroutinesUseCase<ValidationResult, String>(
    executionDispatcher = Dispatchers.Default
) {


    override suspend fun buildUseCase(params: String?): ValidationResult {
        if (params.isNullOrBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage =  R.string.strTheEmailCanNotBeBlank
            )
        }
        if (!isEmailValid(params)) {
            return ValidationResult(
                successful = false,
                errorMessage =  R.string.strThatsNotAValidEmail
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}