package mobile.auth.exception

class AuthFailedException(message: String?) : RuntimeException(message)
class UserNotExistException(message: String?): RuntimeException(message)