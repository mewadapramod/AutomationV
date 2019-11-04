Feature: Typeform

  Background: User landed on homepage
    Given User is on / Page
    Given User credentials passed from Jenkins

  Scenario: Typeform feature verification at summary position
    And User navigates to /user/login from NAM
    When User login into CMS
    Then User navigates to superadmin setting of typeform
    When user verifies typeform configuration at admin setup
    Then User select typeform
    When User navigates to /invoice from NAM
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User selects payment plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When Continue button gets display
    Then Verify survey tab gets display
    And Payment card is select and Amount is autopopulated
    And MOP has same card as displayed in UI
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    And User Enters cvv and accept Terms and Conditions
    And User navigates to /user/logout from NAM

  #  When User clicks on Continue button in Payment Section
  #  Then Review Your Payment section is displayed with Confirm button
  #  When User clicks on Confirm button
  #  Then Confirm Payment pop-up is displayed
  #  When User enters cvv,clicks on Continue button in Confirm Payment
  #  Then Payment should be successfull and amount should get updated
  #		New implementation of payment method
  
  Scenario: Typeform feature verification at payment position
    And User navigates to /user/login from NAM
    When User login into CMS
    Then User navigates to superadmin setting of typeform
    When user verifies typeform configuration at admin setup
    Then User select typeform at payment position
    When User navigates to /invoice from NAM
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User selects payment plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    Then Invoice Summary is displayed with Due Amount and Continue button
    When Continue button gets display
    And Payment card is select and Amount is autopopulated
    And MOP has same card as displayed in UI
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    And User Enters cvv and accept Terms and Conditions
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    When User clicks on Confirm button
    Then Confirm Payment pop-up is displayed
    When User enters cvv,clicks on Continue button in Confirm Payment
    Then Typeform submitted and Payment should be successfull and amount should get updated
    Then Verify survey tab gets display
    Then Click on Updated Invoice button that is displayed
    And User navigates to /user/logout from NAM
    #Then Review Your Payment section is displayed with Confirm button
    #When User clicks on Confirm button
    #Then Confirm Payment pop-up is displayed
    #When User enters cvv,clicks on Continue button in Confirm Payment
    #Then Typeform submitted and Payment should be successfull and amount should get updated

 Scenario: AFL ticket payment via credit card
    
		And User navigates to /user/login from NAM
   	When User login into CMS
    Then User navigates to superadmin setting of purchase flow
		Then User Enable AFL Checkout
		Then User navigated to AFL Checkout module
		Then User enabled AFL Contact details
		Then User created AFL link
		Then User navigated to contact us login promotile
    Then User configured promotile name
    Then User configured promotile title
    Then User configured title url
    Then User navigates to /user/logout from NAM 
    When User navigates to /dashboard from NAM
		When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User click at AFL BUY promotile
    Then User input other user details at contact details tab
    Then user enter pwd and click on continue button with %{GD_PASSWORD}
    Then select delivery and shipping 
    Then user select payment method option
    Then user added/selects payment method
    Then user verifies payment has happened 
    Then User navigates to /user/logout from NAM 
    
   
   #Then user added/selects paypal payment method need to be handled 
  Scenario: AFL ticket payment via Paypal method of payment
    
		And User navigates to /user/login from NAM
   	When User login into CMS
    Then User navigates to superadmin setting of purchase flow
		Then User Enable AFL Checkout
		Then User navigated to AFL Checkout module
		Then User enabled AFL Contact details
		Then user enable paypal and payother mop via configure payments
		Then User created AFL link
		Then User navigated to contact us login promotile
    Then User configured promotile name
    Then User configured promotile title
    Then User configured title url
		Then User navigates to superadmin paypal module
		Then User configued paypal endpoints based on dsn 
    Then User navigates to /user/logout from NAM 
    When User navigates to /dashboard from NAM
		When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User click at AFL BUY promotile
    Then User input other user details at contact details tab
    Then user enter pwd and click on continue button with %{GD_PASSWORD}
    Then select delivery and shipping 
    Then user select payment method option
    Then user added/selects paypal payment method
    Then user verifies payment has happened 
    Then User navigates to /user/logout from NAM 
    
    
    
    Scenario: AFL ticket payment via ACH method of payment
    
		And User navigates to /user/login from NAM
   	When User login into CMS
    Then User navigates to superadmin setting of purchase flow
		Then User Enable AFL Checkout
		Then User navigated to AFL Checkout module
		Then User enabled AFL Contact details
		Then user enable ACH and payother mop via configure payments
		Then User created AFL link
		Then User navigated to contact us login promotile
    Then User configured promotile name
    Then User configured promotile title
    Then User configured title url 
    Then User navigates to /user/logout from NAM 
    When User navigates to /dashboard from NAM
		When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User click at AFL BUY promotile
    Then User input other user details at contact details tab
    Then user enter pwd and click on continue button with %{GD_PASSWORD}
    Then select delivery and shipping 
    Then user select payment method option
    Then user added/selects ACH payment method
    Then user verifies payment has happened 
    Then User navigates to /user/logout from NAM 
    
