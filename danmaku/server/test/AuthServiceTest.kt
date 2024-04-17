package me.him188.ani.danmaku.server

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.runTest
import me.him188.ani.danmaku.server.service.AuthService
import me.him188.ani.danmaku.server.service.JwtTokenManager
import me.him188.ani.danmaku.server.util.exception.UnauthorizedException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.assertThrows
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import kotlin.test.Test
import kotlin.test.assertTrue

class AuthServiceTest {
    private val koin = object : KoinComponent {}

    private fun runTestWithKoin(block: suspend () -> Unit) = runTest {
        val coroutineScope = CoroutineScope(SupervisorJob())
        startKoin {
            modules(
                getServerKoinModule(
                    EnvironmentVariables(
                        testing = true,
                        jwtIssuer = "test_issuer",
                        jwtAudience = "test_audience",
                    ), coroutineScope
                )
            )
        }
        block()
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test login bangumi succeed`() = runTestWithKoin {
        val authService = koin.get<AuthService>()
        val tokenManager = koin.get<JwtTokenManager>()

        val token = authService.loginBangumi("test_token_1")
        val userId = tokenManager.getTokenVerifier().verify(token).claims["userId"]?.asString()
        assertTrue(userId != null)
    }

    @Test
    fun `test login bangumi failed`() = runTestWithKoin {
        val authService = koin.get<AuthService>()

        assertThrows<UnauthorizedException> { authService.loginBangumi("invalid_token") }
    }
}