package ru.cherepanovk.feature_events_impl.events.domain

import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.*
import org.mockito.Mockito.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import ru.cherepanovk.core.exception.ErrorHandler
import ru.cherepanovk.core.exception.Failure
import ru.cherepanovk.core.functional.Either
import ru.cherepanovk.core.interactor.UseCase
import ru.cherepanovk.core.platform.NetworkHandler
import ru.cherepanovk.core_db_api.data.Reminder
import ru.cherepanovk.core_tests.MainCoroutineRule
import ru.cherepanovk.feature_events_impl.events.data.EventsRepository
import java.io.IOException

/**
 * Unit tests for the implementation of [GetRemindersFromDb]
 */
@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class GetRemindersFromDbTest {

    // region constants ------------------------------------------------------------------------------
    private val dbFailure = Failure.DataBaseError
    // endregion constants ---------------------------------------------------------------------------

    // region helper fields --------------------------------------------------------------------------

    private val eventsRepository: EventsRepository = mock()

    private val networkHandler: NetworkHandler = mock()
    private val errorHandler: ErrorHandler = ErrorHandler(networkHandler)

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    // endregion helper fields -----------------------------------------------------------------------

    private lateinit var SUT: GetRemindersFromDb

    @Before
    fun setup() {
        SUT = GetRemindersFromDb(eventsRepository, errorHandler)
    }

    @Test
    fun getReminders_success_returnListReminders() {
        // Arrange
        success()
        // Act
        mainCoroutineRule.runBlockingTest {
            SUT(UseCase.None()) { it.either(::handleFailure, ::handleSuccess) }
        }
    }

    @Test()
    fun getReminders_failure_returnDbFailure() {
        // Arrange
        dbFailure()
        // Act
        mainCoroutineRule.runBlockingTest {
            SUT(UseCase.None()) { it.either(::handleFailure, ::handleSuccess) }
        }
    }


    private fun handleFailure(failure: Failure) {
        assert(failure == dbFailure)
    }

    private fun handleSuccess(reminders: List<Reminder>) {
        assertThat(reminders, `is`(listOf()))
    }

    // region helper methods -------------------------------------------------------------------------
    private fun success() {
        mainCoroutineRule.runBlockingTest {
            `when`(eventsRepository.getRemindersFromDb()).thenReturn(listOf())

        }
    }


    private fun dbFailure() {
        mainCoroutineRule.runBlockingTest {
            `when`(eventsRepository.getRemindersFromDb()).thenAnswer { throw IOException() }
        }

        `when`(networkHandler.isConnected).thenReturn(true)
    }


    // endregion helper methods ----------------------------------------------------------------------

    // region helper classes -------------------------------------------------------------------------

    // endregion helper classes ----------------------------------------------------------------------


}