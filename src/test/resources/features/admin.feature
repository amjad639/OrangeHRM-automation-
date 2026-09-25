Feature: Admin - User Management

  Background:
    Given the user is logged in with username "Admin" and password "admin123"

  Scenario: Verify Add User page fields
    When the user navigates to Admin and clicks Add
    Then the User Role, Employee Name, Username and Password fields should be displayed

  Scenario: Add a new user with ESS role
    When the user navigates to Admin and clicks Add
    And the user fills the new user form:
      | userRole     | ESS                     |
      | employeeName | Paul akhil Dev-Ops      |
      | status       | Enabled                 |
      | password     | Test@1234               |
    Then a success toast should be displayed
    And the new user should appear when searched by username

  Scenario: Search for an existing user by username
    When the user searches for username "Admin" in Admin page
    Then the search result should show username "Admin"

  Scenario: Add user with duplicate username shows validation error
    When the user navigates to Admin and clicks Add
    And the user fills the new user form with duplicate username:
      | userRole     | ESS                 |
      | employeeName | Paul akhil Dev-Ops  |
      | username     | Admin               |
      | password     | Test@1234           |
    Then an "Already exists" error should be shown on Admin page