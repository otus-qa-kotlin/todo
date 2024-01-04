package menu

import io.kotest.core.annotation.AutoScan
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult

@AutoScan
class RerunTestExtension : TestCaseExtension {

    private val retryCount = 2
    private var tryNumber = 0

    override suspend fun intercept(testCase: TestCase, execute: suspend (TestCase) -> TestResult): TestResult {
        println("Extension invocation with $retryCount")
        var result : TestResult = execute(testCase)

        while (!result.isSuccess && tryNumber < retryCount) {
            println("Extension rerun test try $tryNumber")
            tryNumber++
            result = execute(testCase)
        }
        return result
    }

}