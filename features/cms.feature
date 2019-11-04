Feature: CMS

  Background: User landed on CMS
    Given User is on / Page
    Given User credentials passed from Jenkins

  Scenario: Add new invoice template and verify on front-end
    And User navigates to /user/login from NAM
    When User login into CMS
    And Click on Invoices, Add New Invoices and Enter Invoice Title
    And Enter details in ALL LABELS Tab
    And Enter details in INVOICE SUMMARY Tab
    And Enter details in PAYMENT Tab
    And Enter details in REVIEW Tab

  Scenario: Edit Existing Invoice Template CMS
    And User navigates to /user/login from NAM
    When User login into CMS
    And Click on Invoices, View Invoices and Gear
    And Enter details in ALL LABELS Tab
    And Enter details in INVOICE SUMMARY Tab
    And Enter details in PAYMENT Tab
    And Enter details in REVIEW Tab
    And User navigates to "/user/logout" from NAM
    Then User navigates to "/invoice" from NAM
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User is on Invoice page and Invoice list is displayed
    When User clicks on Unpaid tab and selects first without plan invoice %{GD_EMAIL_ADDRESS},%{GD_PASSWORD}
    And Verify Invoice Summary Section Labels changed from CMS
    When User clicks on Continue button in Summary Section
    And Check if Upsells are available %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User selects Pay Other from Payment Option dropdown
    Then Amount Due and Pay Today is displayed
    Then Verify Invoice Payment Section Labels changed from CMS
    When User clicks on add or select payment method
    Then Select Payment Method pop up appears with added cards and Add New Payment button
    When User clicks on Add New Card, adds new card and saves to account,CMS Validation %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
    Then CVV field is displayed
    And Single card added is saved and verified in cc query %{GD_EMAIL_ADDRESS}, %{GD_PASSWORD}, %{GD_FIRST_NAME}, %{GD_LAST_NAME}, %{GD_CARD_NUMBER}, %{GD_CARD_EXPIRY}, %{GD_CARD_ZIP}, %{GD_CARD_ADDRESS}
    When User enters cvv for added card, clicks on Continue button
    Then Payment card is select and Amount is autopopulated
    When User enters %{GD_AMOUNT},pay today gets updated, user clicks on Continue button
    When User clicks on Continue button in Payment Section
    Then Review Your Payment section is displayed with Confirm button
    And Verify Invoice Review Section Labels changed from CMS

  Scenario: Sign In Component
    And User navigates to /user/login from NAM
    When User login into CMS
    And Click on Setting Tab and Sign In Component
    And Click on Interstitial Sign In Gear and Enter Details
    And Click on Homepage Sign In Gear and Enter Details
    And Click on Claim Sign In Gear and Enter Details
    And Click on Change Password Gear and Enter Details
    And User navigates to "/user/LOGOUT" from NAM
    And Verify the Home Page Labels - Create, Sign in and Forgot
    Then User navigates to "/invoice" from NAM
    And Verify the Interstitial Labels - Create, Sign in and Forgot
    Then User navigates to "/" from NAM
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Then User navigates to "/user/change-password" from NAM
    And Verify Change Password Labels
    And User navigates to "/user/logout" from NAM
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And User generate TransferId for %{GD_TransferTicketID}
    And User navigates to "/invites/%{GD_TransferID}" from NAM
    When User verify congratulation message
    Then Verify the Claim Labels - Create, Sign in and Forgot

  Scenario: Customise Dashboard Config and verify on front-end
    And User navigates to /user/login from NAM
    When User login into CMS
    And Click on Settings - View Dashboard config
    And Enter Welcome, Account ID, Client Name, Manage Ticket, Ticket Total, Account Balance, Outstanding Invoices Label under Manage Ticket Dashboard Header
    And Enter Ticket Label under Manage Tickets
    And Enter Invoice Label under Manage Invoices
    And Enter Quick Link Label under Manage Quick Links
    And Click on Dashboard Config Save Button
    #Then User logout from cms
    And User navigates to "/user/logout" from NAM
    And User navigates to / from NAM
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User verifies Customise Dashboard Config and verify on front-end
    And User navigates to "/user/logout" from NAM

  Scenario: Verify Add page section under page manager in cms
    And User navigates to /user/login from NAM
    When User login into CMS
    Then user click on Add Page button
    And user select page type under add new page section
    Then user verify Ticket Sales page
    And User navigates to "/user/logout" from NAM

  Scenario: Verify Provide NAM version in CMS
    And User navigates to /user/login from NAM
    When User login into CMS
    Then User verify version from CMS UI and Drupal API

  Scenario: Verify Email Reporting dashboard in CMS
    And User navigates to /user/login from NAM
    When User login into CMS
    And User click on Email Reporting button
    Then User verify Email Reporting Page in dashboard

  Scenario: Verify Add additional transactional pages to menu manager that is buy, upgrade and Quick Donate
    And User navigates to /user/login from NAM
    When User login into CMS
    And User click on Add New Menu
    Then User click on Transactional Pages
    And User Verify elments present in Transactional pages
    Then User add all items to rightDropContainer under menu mangaer section
    And User select "TestAutomation" as active menu from list
    And User navigates to "/user/logout" from NAM
    And User navigates to / from NAM
    Then User verify transactiona pages under public home page
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User verify transactiona pages under logged In home page
    And User navigates to "/user/logout" from NAM
    And User navigates to /user/login from NAM
    When User login into CMS
    And User click on View Menus
    And User select "None" as active menu from list
    And User navigates to "/user/logout" from NAM

  Scenario: Home page creation with blank layout and verify on frontend
    And User navigates to /user/login from NAM
    When User login into CMS
    Then User click on Add Page button under marketing experience
    And Select page type "Home Pages" as per user input
    Then Under tickets sales option user should be able to select "Blank Layout" layout
    And User fill Name Title access level as "Public,Logged In" under setting page and click on save button
