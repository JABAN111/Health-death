package mobile.auth

import com.google.protobuf.Empty
import io.grpc.Status
import io.grpc.StatusRuntimeException
import mobile.auth.grpc.*

class AuthorizationService : AuthorizationGrpcKt.AuthorizationCoroutineImplBase() {

    override suspend fun ping(request: Empty): AuthPingResponse {
        return AuthPingResponse.newBuilder()
            .setMessage("Pong")
            .build()
    }

    override suspend fun signUp(request: SignUpRequest): AuthResponse {
        val email = request.email
        val password = request.password

        if (email.isBlank()) {
            throw StatusRuntimeException(
                Status.INVALID_ARGUMENT.withDescription("Email cannot be empty")
            )
        }

        if (password.isBlank()) {
            throw StatusRuntimeException(
                Status.INVALID_ARGUMENT.withDescription("Password cannot be empty")
            )
        }

        println("Received sign up request: $email, $password")
        return AuthResponse.newBuilder()
            .setMessage("token212121")
            .build()
    }

    override suspend fun signIn(request: SignInRequest): AuthResponse {
        val email = request.email
        println("Received sign up request: $email, ${request.password}")
        val password = request.password

        if (email.isBlank()) {
            throw StatusRuntimeException(
                Status.INVALID_ARGUMENT.withDescription("Email cannot be empty")
            )
        }

        if (password.isBlank()) {
            throw StatusRuntimeException(
                Status.INVALID_ARGUMENT.withDescription("Password cannot be empty")
            )
        }

        return AuthResponse.newBuilder()
            .setMessage("token212121")
            .build()
    }
}