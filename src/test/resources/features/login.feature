Feature: Login functionality

  Scenario: Login with valid credentials
    Given the user is on the login page
    When the user logs in with username "Admin" and password "admin123"
    Then the dashboard should be displayed

  Scenario: Login with invalid credentials
    Given the user is on the login page
    When the user logs in with username "WrongData" and password "WrongPassword"
    Then an error message "Invalid credentials" should be shown

  Scenario: Login with empty credentials
    Given the user is on the login page
    When the user clicks login without entering credentials
    Then required field validation messages should appear

  Scenario: Logout after valid login
    Given the user is logged in with username "Admin" and password "admin123"
    When the user logs out
    Then the login form should be displayed again