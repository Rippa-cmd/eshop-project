Feature: Login

  Scenario Outline: Successful Login to the page and logout after
    Given I open web browser
    When I navigate to admin-login.html page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    Then name should be "<username>"

    And I navigate to users
    And click on new user creating button
    And I provide user name as "<newUsername>", age as "<age>", password and repeated pass as "<newPassword>" and role as "<role>"
    And click submit button

    And click logout button
    And I provide username as "<newUsername>" and password as "<newPassword>"
    And I click on login button
    Then name should be "<newUsername>"

    And I fill user filters, name as "<newUsername>", minage and maxage as "<age>"
    And apply filter
    Then found user name should be "<newUsername>", age "<age>", and role "<role>"
    And I click on edit button
    And I edit user name as "<newUsername2>", age as "<age2>", password and repeated pass as "<newPassword2>" and role as "<role2>"
    And click submit button
    And I fill user filters, name as "<newUsername2>", minage and maxage as "<age2>"
    And apply filter
    Then found user name should be "<newUsername2>", age "<age2>", and role "<role2>"

    And click logout button
    And I provide username as "<newUsername2>" and password as "<newPassword2>"
    And I click on login button
    Then name should be "<newUsername2>"
    And access should be denied

    And click logout button
    And I provide username as "<username>" and password as "<password>"
    And I click on login button

    And I fill user filters, name as "<newUsername2>", minage and maxage as "<age2>"
    And apply filter
    Then found user name should be "<newUsername2>", age "<age2>", and role "<role2>"
    And I click on delete button
    And I click on success deleting button
    And I fill user filters, name as "<newUsername2>", minage and maxage as "<age2>"
    And apply filter
    Then user list should be empty

    And click logout button
    Then user logged out from admin service

    Examples:
      | username | password | newUsername   | age | newPassword         | role             | newUsername2   | age2 | newPassword2        | role2      |
      | root     |          | FirstUsername | 56  | erydh635!@hxc{sdFsd | ROLE_SUPER_ADMIN | SecondUsername | 3336 | sjdh2y1s2S*DAUCiomp | ROLE_GUEST |