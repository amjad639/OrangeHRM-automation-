Feature: Dashboard functionality

  Background:
    Given the user is logged in with username "Admin" and password "admin123"

  Scenario: Verify core dashboard widgets are displayed
    Then the following dashboard widgets should be displayed:
      | Time at Work            |
      | My Actions               |
      | Quick Launch             |
      | Employees on Leave Today |

  Scenario: Navigate to Leave module via Quick Launch
    When the user clicks the Quick Launch item "Assign Leave"
    Then the URL should contain "/leave/"