Feature: Invoice New

  Background: User landed on homepage
    Given User is on /invoice Page
    Given User credentials passed from Jenkins

  Scenario: Make Payment using existing card for Plan Invoice
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User selects payment plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    Then User fetches the existing card payment method
    When User clicks on Continue button in Summary Section
    Then Payment card is select and Amount is autopopulated
    And MOP has same card as displayed in UI
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    And User enters CVV number of Card
    When User clicks on Continue button in Payment Section
    Then Verify Payment is successfull
   # Then Review Your Payment section is displayed with Confirm button
  #  When User clicks on Confirm button
  #  Then Confirm Payment pop-up is displayed
  #  When User enters cvv,clicks on Continue button in Confirm Payment
  #  Then Payment of amount %{GD_AMOUNT} should be successfull and amount should get updated

  Scenario: Make Payment using single existing card for invoice with Pay Other without Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    #When User clicks on add or select payment method
    #Then Select Payment Method pop up appears with added cards and Add New Payment button
    #When User selects first added card
    Then CVV field is displayed
    And User enters CVV number of Card
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
    #Then Review Your Payment section is displayed with Confirm button
    #When User clicks on Confirm button
    #Then Payment should be successfull and amount should get updated

  Scenario: Make Payment using multi existing card for invoice with Pay Other without Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    When User clicks on add or select payment method
 #   Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User selects two added card
  #  Then CVV field is displayed
    When User enters CVV number of Card
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
   # Then Review Your Payment section is displayed with Confirm button
   # When User clicks on Confirm button
   # Then Payment should be successfull and amount should get updated

  Scenario: Make Payment using single new card for invoice with Pay Other without saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User clicks on Add New Card, adds new card without saving to account %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
  #  Then CVV field is displayed
  #  And Single card added is not saved and verified in cc query %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}, %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
    When User enters CVV number of Card
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
 #   Then Review Your Payment section is displayed with Confirm button
 #   When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated

  Scenario: Make Payment using multi new card for invoice with Pay Other without saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User clicks on Add New Card, adds two new card without saving to account %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}, %{GD_FIRST_NAME_SEC}, %{GD_LAST_NAME_SEC}, %{GD_CARD_NUMBER_SEC}, %{GD_CARD_EXPIRY_SEC}, %{GD_CARD_ZIP_SEC}, %{GD_CARD_ADDRESS_SEC}
    Then CVV field is displayed
    And Two cards added are not saved and verified in cc query %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}, %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}, %{GD_FIRST_NAME_SEC}, %{GD_LAST_NAME_SEC}, %{GD_CARD_NUMBER_SEC}, %{GD_CARD_EXPIRY_SEC}, %{GD_CARD_ZIP_SEC}, %{GD_CARD_ADDRESS_SEC}
    When User enters cvv for added card, clicks on Continue button
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated

  Scenario: Make Payment using single new card for invoice with Pay Other and saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User clicks on Add New Card, adds new card and saves to account %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
    Then CVV field is displayed
    And Single card added is saved and verified in cc query %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}, %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
    When User enters cvv for added card, clicks on Continue button
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated

  Scenario: Make Payment using multi new card for invoice with Pay Other and saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User clicks on Add New Card, adds two new card and saves to account %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}, %{GD_FIRST_NAME_SEC}, %{GD_LAST_NAME_SEC}, %{GD_CARD_NUMBER_SEC}, %{GD_CARD_EXPIRY_SEC}, %{GD_CARD_ZIP_SEC}, %{GD_CARD_ADDRESS_SEC}
    Then CVV field is displayed
    And Two cards added are saved and verified in cc query %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}, %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}, %{GD_FIRST_NAME_SEC}, %{GD_LAST_NAME_SEC}, %{GD_CARD_NUMBER_SEC}, %{GD_CARD_EXPIRY_SEC}, %{GD_CARD_ZIP_SEC}, %{GD_CARD_ADDRESS_SEC}
    When User enters cvv for added card, clicks on Continue button
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated

  # //pay in full
  Scenario: Make Payment using single existing card for invoice with Pay In Full without Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay In Full from Payment Option dropdown
    Then Amount Due is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User selects first added card
    Then CVV field is displayed
    When User enters first cvv, clicks on Continue button
    Then Payment card is selected, Amount Due is populated and Continue gets enabled
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated

  Scenario: Make Payment using multi existing card for invoice with Pay In Full without Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay In Full from Payment Option dropdown
    Then Amount Due is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User selects two added card
    Then CVV field is displayed
    When User enters first two cvv, clicks on Continue button
    Then Payment card is selected, Amount Due is populated and Continue gets enabled
    And Amount is populated for each card
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated

  Scenario: Make Payment using single new card for invoice with Pay In Full without saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using multi new card for invoice with Pay In Full without saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using single new card for invoice with Pay In Full and saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using multi new card for invoice with Pay In Full and saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  # new Payment Plan
  Scenario: Make Payment using single existing card for new invoice with Payment Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using multi existing card for for new invoice with Payment Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using single new card for new invoice with Payment Plan without saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using multi new card for new invoice with Payment Plan without saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using single new card for new invoice with Payment Plan and saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Make Payment using multi new card for new invoice with Payment Plan and saving card to account
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using existing card for new invoice with optional item with Pay In Full
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User Selects Optional Invoice %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
 

  Scenario: Make Payment using existing card for new invoice with optional item with Payment Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User Selects Optional Invoice %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using existing card for new invoice by removing optional item with Payment Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User Selects Optional Invoice %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Remove button of optional line item
    Then Optional Item is removed and link to Add it is displayed
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using existing card for new invoice by removing optional item with Pay In Full
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User Selects Optional Invoice %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Remove button of optional line item
    Then Optional Item is removed and link to Add it is displayed
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  #Invalid CVV
  Scenario: Make Payment using single existing card for invoice with Pay Other and wrong CVV without Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}


  Scenario: Make Payment using single existing card for invoice with Pay In Full and wrong CVV without Plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
  #  And User selects Pay In Full from Payment Option dropdown


  Scenario: Make Payment using single existing card for new invoice with Payment Plan and wrong CVV
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
 #   And User selects Payment Plan from Payment Option dropdown
 #   Then Amount Due and Pay Today is displayed
 #   When User clicks on add or select payment method


  # Paid invoice
  Scenario: Validate Paid Invoice
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Paid tab and selects first invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Paid invoice Summary is displayed and Continue button is not displayed

  Scenario: Validate error message for wrong card number and wrong expiry
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
  #  And User selects Pay In Full from Payment Option dropdown
  #  Then Amount Due is displayed
  #  When User clicks on add or select payment method
  #  Then Select Payment Method pop up appears with added cards and Add New Payment button
  #  And Click on Add New Card button, enter invalid card details and validate error messages

  Scenario: Verify Terms and Condition
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User selects payment plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
  #  Then Payment card is select and Amount is autopopulated
   # And MOP has same card as displayed in UI
  #  When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
   # When User clicks on Continue button in Payment Section
   # Then Review Your Payment section is displayed with Confirm button
    When User clicks on Terms and Condition link
    Then Terms and Condition are opened in new window

  Scenario: Verify Invoice List Mapping
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    Then Select invoice and validate invoice balance, due date and description matches with TM API response %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}

  Scenario: Verify Invoice Details Mapping
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    Then Select invoice and validate invoice details with TM API response %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}

  Scenario: Verify Invoice List is sorted
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    Then Invoice List is sorted %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}

  Scenario: Invoice Payment through Payment Plan API
    Then Invoice Payment %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}

  Scenario: Invoice Payment Pay Other through API
    Then Invoice Pay Other Payment %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}

  Scenario: Make Payment for invoice with Pay Other with Upsells
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects invoice with Upsells %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Verify Values in Upsell Dropdown from API and select first value
    When User selects two value and clicks on Add button in Add ons Tab, Add on value gets updated, timer is verified and clicks on Continue, verify Amount Displayed on Tab %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User selects first added card
    Then CVV field is displayed
    When User enters first cvv, clicks on Continue button
    Then Amount Due should be updated with Add on amount and service charge
    And Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated for addons
    And Add on event must be displayed in Invoice Summary

  Scenario: Make Payment for invoice with Pay In Full with Upsells
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects invoice with Upsells %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Verify Values in Upsell Dropdown from API and select first value
    When User selects two value and clicks on Add button in Add ons Tab, Add on value gets updated, timer is verified and clicks on Continue, verify Amount Displayed on Tab %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    And User selects Pay In Full from Payment Option dropdown
    Then Amount Due is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User selects first added card
    Then CVV field is displayed
    When User enters first cvv, clicks on Continue button
    Then Amount Due should be updated with Add on amount and service charge
    Then Payment card is selected, Amount Due is populated and Continue gets enabled
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Payment should be successfull and amount should get updated for addons

  Scenario: Make Payment for invoice with Payment Plan with Upsells
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects invoice with Upsells %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    And Verify Values in Upsell Dropdown from API and select first value
    When User selects two value and clicks on Add button in Add ons Tab, Add on value gets updated, timer is verified and clicks on Continue, verify Amount Displayed on Tab %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    And User selects Payment Plan from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User selects first added card


  Scenario: Ability to update credit card on invoice tied to a payment plan
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User selects payment plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    Then User can verify Edit Payment Method present in UI and click on Edit Payment Method
    When User clicks on Add New Card, adds new card and saves to account %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
    Then User enter CVV and verify field is displayed
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
    When User clicks on Confirm button
    Then Confirm Payment pop-up is displayed
    When User enters cvv,clicks on Continue button in Confirm Payment
    Then Payment should be successfull and amount should get updated
    
    # Negative flow Edit Payment Method
    Scenario: Ability to update credit card on invoice tied to a payment plan with negative value
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User selects payment plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When User clicks on Continue button in Summary Section
    Then User can verify Edit Payment Method present in UI and click on Edit Payment Method
    When User clicks on Add New Card, adds new card with invalid data %{GD_FIRST_NAME_NEGATIVE}, %{GD_LAST_NAME_NEGATIVE}, %{GD_CARD_NUMBER_NEGATIVE}, %{GD_CARD_EXPIRY_NEGATIVE}
    Then User Click on Cancel button
    Then User can verify Edit Payment Method present in UI and click on Edit Payment Method 
    When User clicks on Add New Card, adds new card and saves to account %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
    
    
