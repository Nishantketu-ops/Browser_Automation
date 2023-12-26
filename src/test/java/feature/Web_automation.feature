@web_automation
Feature: User opens Browser and visits Google search Page types Cricket and vists First and second Weblink

  Scenario: Search and Capture Screenshots
    Given the user opens a Chrome browser
    When the user visits the Google search page
    And the user searches for the keyword "cricket"
    And the user retrieves the links from the search results
    And the user visits the first link from the search results
    Then the user takes a screenshot of the first webpage
    And the user goes back to the search results page
    And the user visits the second link from the search results
    Then the user takes a screenshot of the second webpage
    And the user closes the browser
