package ru.cherepanovk.core.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
//    object BadApiVersion: Failure()
    object BadRequest: Failure(){
        var error: String? = null
    }
//    object Unauthorized: Failure()
//    object Unregistered: Failure()
    object TimeOut: Failure()
    object DataBaseError: Failure()
    object CreateNotificationError: Failure()
    object NoGoogleAccount: Failure()
    object UrlError: Failure()
    object NoEmailApplication: Failure()
    object UnexpectedError: Failure()
    object WhatsAppNotInstalled: Failure()

    companion object {
        const val RESULT_CODE_UNREGISERED_USER = 403
        const val RESULT_CODE_UNAUTHOTIZED = 401
        const val RESULT_CODE_BAD_REQUEST = 400
        const val RESULT_CODE_BAD_API_VERSION = 406
        const val RESULT_CODE_SERVICE_UNAVAILABLE = 503
        const val RESULT_CODE_ROUTE_NOT_FOUND = 404
    }
}
