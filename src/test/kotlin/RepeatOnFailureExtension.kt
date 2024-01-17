import io.kotest.core.annotation.AutoScan
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

@AutoScan
class RepeatOnFailureExtension : TestCaseExtension {
    private val attemptMuxNumber = 3
    private var attemptNumber = 1

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        var testResult: TestResult = execute(testCase)

        while (!testResult.isSuccess && attemptNumber < attemptMuxNumber) {
            println("Rerun attempt #$attemptNumber")
            attemptNumber += 1
            testResult = execute(testCase)
        }
        return testResult
    }
}