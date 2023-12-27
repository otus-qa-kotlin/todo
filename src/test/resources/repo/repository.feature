Feature: Repo
  Scenario: Add a task to the repo
    Given Init a repository
    When Add a task to the repository
    Then Verify the task is present in the repository
  Scenario: Remove a task from the repo
    Given Init a repository
    When Add a task to the repository
    When Remove the task from the repo
    Then Verify the task is not present in the repository