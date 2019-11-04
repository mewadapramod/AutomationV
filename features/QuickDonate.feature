Feature: Quick Donation

  Background: User landed on homepage
    Given User is on / Page
    Given User credentials passed from Jenkins
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Quick Donation is enabled on Site

  Scenario: Verify Quick Donation flow for any amount, anonymous flag and all available funds
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    When User navigates to "/donate/" from NAM
    Then Quick Donation page title is displayed with Donation Text and Steps to Donate
    And User selects fund number, amount and anonymous flag as: 1, 50 & false respectively
    And User enters following payment details & address info %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_ADDRESS1}, %{GD_ADDRESS2}, %{GD_CVV}, %{GD_CITY}, %{GD_COUNTRY}, %{GD_STATE}
    And User clicks Donate Button
    Then Thank you page is displayed with correct Donation details and homepage button
    And User clicks Home Button on Quick Donation Thanks Page
    Then User is redirected to HomePage


  Scenario: Verify Quick Donation Cancel flow and invalid particulars
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    When User navigates to "/donate/" from NAM
    And Quick Donations funds are listed as per AAPI response
    And User selects fund number, amount and anonymous flag as: 1, 0 & false respectively
    Then Miminum Suggested Amount warning Msg is displayed
    And User enters following payment details & address info Test@, QA#, %{GD_CARD_NUMBER}, 1118, %{GD_CARD_ZIP}, %{GD_ADDRESS1}, %{GD_ADDRESS2}, %{GD_CVV}, %{GD_CITY}, %{GD_COUNTRY}, %{GD_STATE}
    Then Invalid FirstName, Invalid LastName & Invalid Card Expiry Messages are displayed
    And Donate Button is disabled
    When User clicks Cancel Button
    #Behaviour Pending
    #Then User is redirected to HomePage


  Scenario: Verify Anonymous Quick Donation flow
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    When User navigates to "/donate/" from NAM
    Then Quick Donation page title is displayed with Donation Text and Steps to Donate
    And User selects fund number, amount and anonymous flag as: 3, 51 & true respectively
    And User enters following payment details & address info %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_ADDRESS1}, %{GD_ADDRESS2}, %{GD_CVV}, %{GD_CITY}, %{GD_COUNTRY}, %{GD_STATE}
    And User clicks Donate Button
    Then Thank you page is displayed with correct Donation details and homepage button


