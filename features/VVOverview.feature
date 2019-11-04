Feature: VV API

  Background: User landed on homepage
    Given User is on / Page
    Given User credentials passed from Jenkins

  Scenario: Verify target event info for VV buy flow
    Given User navigates to "/buy" from NAM
    And User select Event
    And User selects Compare button
    Then Verify target event info for Buy Flow

  Scenario: Verify source seat info for VV upgrade flow
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given User navigates to "/upgrade" from NAM
    When User select source seat
    And User selects Next button
    And User select Plan
    And User selects Plan Next button
    And User selects Compare button
    Then Verify source seat info for Upgrade Flow

  Scenario: Verify venue availability on overview page
    Given User navigates to "/buy" from NAM
    And User select Event
    Then Verify availability in list view
