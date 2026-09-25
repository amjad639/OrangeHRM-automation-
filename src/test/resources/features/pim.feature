Feature: PIM - Employee Management

  Background:
    Given the user is logged in with username "Admin" and password "admin123"

  Scenario: Search for an existing employee
    When the user searches for employee "mego syria" in PIM
    Then the employee "mego syria" should be displayed in the list

  Scenario: Search for a non-existing employee
    When the user searches for employee "EmployeeDoesNotExist123" in PIM
    Then a "No Records Found" message should be shown

  Scenario: Open Add Employee page
    When the user navigates to PIM and clicks Add
    Then the URL should contain "/pim/addEmployee"
    And the First Name and Last Name fields should be displayed

  Scenario: Add employee with empty first name shows validation error
    When the user navigates to PIM and clicks Add
    And the user enters last name "Tester" only
    And the user clicks Save on employee form
    Then a "Required" validation error should appear under First Name

  Scenario: Add a new employee end to end
    When the user navigates to PIM and clicks Add
    And the user enters first name "John" and last name "Tester2"
    And the user clicks Save on employee form
    Then the URL should contain "/pim/viewPersonalDetails"
    And the Personal Details page should be displayed
    When the user searches for employee "John Tester2" in PIM
    Then the employee "John Tester2" should be displayed in the list

  Scenario: Edit an existing employee
    When the user searches for employee "mego syria" in PIM
    And the user clicks Edit on the first employee row
    And the user updates the last name to "UpdatedLastName"
    And the user clicks Save on employee form
    Then an update success toast should be displayed

  Scenario: Delete an employee
    When the user navigates to PIM and clicks Add
    And the user enters first name "John" and last name "ToDelete"
    And the user clicks Save on employee form
    Then the URL should contain "/pim/viewPersonalDetails"
    When the user searches for employee "John ToDelete" in PIM
    And the user clicks Delete on the first employee row
    And the user confirms the delete
    Then a delete success toast should be displayed
    When the user clicks Search on PIM page
    Then a "No Records Found" message should be shown

  Scenario: Filter employees by employment status
    When the user navigates to PIM Employee List
    And the user selects Employment Status "Full-Time Permanent"
    And the user clicks Search on PIM page
    Then all displayed rows should have status "Full-Time Permanent"