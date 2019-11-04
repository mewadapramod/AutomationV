Feature: Tickets Resale

  Background: User landed on homepage
    Given User is on / Page
    Given User credentials passed from Jenkins

  Scenario: Verify Resale flow and positive validations
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    And User selects Valid Event with resale tickets for member %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User clicks on Sell tickets
    And User selects two seats for resale and hit Continue button
    Then Set Price Page appears with all valid fields
    And User set the price as 7 per ticket and hit Continue button
    Then Review Page appears with all valid fields and correct payout amount as per pricing policy API
    And Confirmation page appears with all valid fields when User clicks Submit listing button
    And User is navigated to events page by clicking Done Button

  Scenario: Verify error message when user enter minimum price and maximum price with all cancel functionality
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    And User selects Valid Event with resale tickets for member %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User clicks on Sell tickets
    And User selects two seats for resale and hit Continue button
    Then Set Price Page appears with all valid fields
    Then User verify miniumum price 2.5 error message
    Then User verify maximum price 9999 error message
    And User clicks on cancel button and verify page should redirect to event detail page
    And User clicks on Sell tickets
    And User selects two seats for resale and hit Continue button
    Then Set Price Page appears with all valid fields
    And User set the price as 1000 per ticket and hit Continue button
    And User clicks on cancel button and verify page should redirect to event detail page
    And User clicks on Sell tickets
    And User selects two seats for resale and hit Continue button
    Then Set Price Page appears with all valid fields
    And User set the price as 1000 per ticket and hit Continue button
    And User Select payout menthod "Seller Credit" and clicks on Submit Listing
    Then User Click on Edit/Manage and hit on cancel posting 

  Scenario: Verify user able to Edit Posting after successfull resale
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    And User selects Valid Event with resale tickets for member %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User clicks on Sell tickets
    And User selects two seats for resale and hit Continue button
    Then Set Price Page appears with all valid fields
    And User set the price as 100 per ticket and hit Continue button
    And User Select payout menthod "Seller Credit" and clicks on Submit Listing
    Then User Clcik on Edit/Manage and hit on edit posting
    And User set the price as 200 per ticket and hit Continue button
    And User Select payout menthod "Seller Credit" and clicks on Submit Listing
    Then User Verify edit posting is completed successfully
