Feature: product

  Scenario Outline: Successful Login to the page, product creation, record validation, deletion and logout after
    Given I open web browser
    When I navigate to admin-login.html page
    And I provide username as "<username>" and password as "<password>"
    And I click on login button
    Then name should be "<name>"
    And I navigate to products
    And click on new product creating button
    And I provide product name as "<productName>", cost as "<cost>" and category as "<category>"
    And click submit button
    And I fill product filters, name as "<productName>", mincost and maxcost as "<cost>", and category as "<category>"
    And apply filter
    Then found product name should be "<productName>", cost "<cost>", and category "<category>"
    And I click on edit button
    And I edit product name as "<productName2>", cost as "<cost2>" and category as "<category2>"
    And click submit button
    And I fill product filters, name as "<productName2>", mincost and maxcost as "<cost2>", and category as "<category2>"
    And apply filter
    Then found product name should be "<productName2>", cost "<cost2>", and category "<category2>"
    And I click on delete button
    And I click on success deleting button
    And I fill product filters, name as "<productName2>", mincost and maxcost as "<cost2>", and category as "<category2>"
    And apply filter
    Then product list should be empty
    And click logout button
    Then user logged out from admin service

    Examples:
      | username | password | name | productName         | cost   | category | productName2         | cost2 | category2 |
      | root     |          | root | TheFirstProductName | 664    | Fio      | TheSecondProductName | 123.1 | Sello     |