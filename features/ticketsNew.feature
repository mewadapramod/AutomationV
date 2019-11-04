Feature: Tickets New

  Background: User landed on homepage
    Given User is on / Page
    Given User credentials passed from Jenkins

  Scenario: Send ticket NEW after Email ID change in CAM
    Given User creates account using ats with events
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User is on / Page
    When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
    When User clicks your account
    Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
    When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then NAM homepage is displayed
    Given User navigates to /user/logout from NAM
    When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of ticket for %{GD_TransferTicketID}
    When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Save the Status of ticket using %{GD_TransferTicketID}
    Given Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Verify the status of Send ticket %{GD_Status}
    And Verify the state of the ticket %{GD_State}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}

  Scenario: Reclaim ticket NEW after Email ID change in CAM
    Given User creates account using ats with events
    And Get transfer ticket ID for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
    When User clicks your account
    Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
    When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then NAM homepage is displayed
    Given User navigates to /user/logout from NAM
    When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
    Given User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Edit or Cancel ticket, new ticket, for %{GD_TransferTicketID}
    And User clicks on Reclaim
    Then Verify success screen
    When User click on done
    Then Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}

  Scenario: Send ticket with existing user
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of ticket for %{GD_TransferTicketID}
    And Verify and Save the Status and Expiry of ticket using %{GD_TransferTicketID}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify the status and expiry of Send ticket after logout and login %{GD_Status} %{GD_Expiry} %{GD_TransferTicketID}
    And Verify the state of the ticket %{GD_State}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Reclaim ticket with existing user and verify count of Active increased by one and count of Pending decreased by one
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Then Get count of Pending and Active Tickets %{GD_EMAIL_ADDRESS} %{GD_PASSWORD} %{GD_EventId}
    Given User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Edit or Cancel ticket, new ticket, for %{GD_TransferTicketID}
    And User clicks on Reclaim
    Then Verify success screen
    When User click on done
    Then Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Verify count of Pending ticket reduced by one and count of Active tickets increased by one %{GD_ActiveCount} %{GD_PendingCount} %{GD_CompletedCount}

  Scenario: Claim ticket with existing user
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And User generate TransferId for %{GD_TransferTicketID}
    And Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to "/ticket/claim?%{GD_TransferID}" from NAM
    And User click on Claim link
    And Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Send mulitple ticket with existing user
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket IDs
    And Save ticket flags for ticket Ids %{GD_TransferTicketID1} %{GD_TransferTicketID2} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects two seat using %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    And User clicks on continue
    Then Verify Event Detail for both tickets using %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of both ticket for %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    And Verify and Save the Status and Expiry of both the tickets using %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    And Save ticket flags for ticket Ids %{GD_TransferTicketID1} %{GD_TransferTicketID2} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify the status and expiry of both Send tickets after logout and login %{GD_Status} %{GD_Expiry} %{GD_TransferTicketID1} %{GD_Status2} %{GD_Expiry2} %{GD_TransferTicketID2}
    And Verify the state of both the tickets %{GD_State} %{GD_State2}
    And Verify ticket flags of both tickets %{GD_TransferTicketID1} %{GD_TransferTicketID2}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Send ticket with Parking Pass
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID with related event false
    And Save ticket flags for ticket Id %{GD_TransferTicketID1} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID1}
    And User selects Bundle Parking and Parking Slot %{false}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID1},NEW TICKETS
    And Verify parking slot selected in Bundles is displayed %{GD_ParkingSlot1}
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of ticket for %{GD_TransferTicketID1}
    And Verify and Save the Status and Expiry of ticket using %{GD_TransferTicketID1}
    And Save ticket flags for ticket Id %{GD_TransferTicketID1} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify the status and expiry of Send ticket after logout and login %{GD_Status} %{GD_Expiry} %{GD_TransferTicketID1}
    And Verify the state of the ticket %{GD_State}
    And Verify ticket flags for %{GD_TransferTicketID1}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Send multiple tickets with multiple Parking Pass
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket IDs with related events
    And Save ticket flags for ticket Ids %{GD_TransferTicketID1} %{GD_TransferTicketID2} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects two seat using %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    And User selects Bundle Parking and Parking Slot %{GD_Multiple}
    And User clicks on continue
    Then Verify Event Detail for both tickets using %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    And Verify both parking slot selected in Bundles is displayed %{GD_ParkingSlot1} %{GD_ParkingSlot2}
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of both ticket for %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    And Verify and Save the Status and Expiry of both the tickets using %{GD_TransferTicketID1} %{GD_TransferTicketID2}
    And Save ticket flags for ticket Ids %{GD_TransferTicketID1} %{GD_TransferTicketID2} using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify the status and expiry of both Send tickets after logout and login %{GD_Status} %{GD_Expiry} %{GD_TransferTicketID1} %{GD_Status2} %{GD_Expiry2} %{GD_TransferTicketID2}
    And Verify the state of both the tickets %{GD_State} %{GD_State2}
    And Verify ticket flags of both tickets %{GD_TransferTicketID1} %{GD_TransferTicketID2}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Send ticket with multiple Parking Pass
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID with related event true
    And Save ticket flags for ticket Id %{GD_TransferTicketID1} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID1}
    And User selects Bundle Parking and Parking Slot %{GD_Multiple}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID1},NEW TICKETS
    And Verify both parking slot selected in Bundles is displayed %{GD_ParkingSlot1} %{GD_ParkingSlot2}
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of ticket for %{GD_TransferTicketID1}
    And Verify and Save the Status and Expiry of ticket using %{GD_TransferTicketID1}
    And Save ticket flags for ticket Id %{GD_TransferTicketID1} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify the status and expiry of Send ticket after logout and login %{GD_Status} %{GD_Expiry} %{GD_TransferTicketID1}
    And Verify the state of the ticket %{GD_State}
    And Verify ticket flags for %{GD_TransferTicketID1}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Verify False ticket flags for %{GD_TransferTicketID1}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Verify Send Parking Ticket
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID with parking
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of ticket for %{GD_TransferTicketID}
    And Verify and Save the Status and Expiry of ticket using %{GD_TransferTicketID}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify the status and expiry of Send ticket after logout and login %{GD_Status} %{GD_Expiry} %{GD_TransferTicketID}
    And Verify the state of the ticket %{GD_State}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Verify False ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Claim ticket with another user
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And User generate TransferId for %{GD_TransferTicketID}
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
    And User navigates to "/ticket/claim?%{GD_TransferID}" from NAM
    And User click on Claim link for ticketId %{GD_TransferTicketID} and %{GD_TransferID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify User claimed the ticket using %{GD_TransferTicketID} and %{GD_CustomerName}
    And Verify False ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Verify Claim Parking Ticket
    Given Get transfer ticket ID with parking
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And User generate TransferId for %{GD_TransferTicketID}
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
    And User navigates to "/ticket/claim?%{GD_TransferID}" from NAM
    And User click on Claim link for ticketId %{GD_TransferTicketID} and %{GD_TransferID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify User claimed the ticket using %{GD_TransferTicketID} and %{GD_CustomerName}
    And Verify False ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Claim ticket with new user
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And User generate TransferId for %{GD_TransferTicketID}
    And User navigates to "/invites/%{GD_TransferID}" from NAM
    When User verify congratulation message
    Then User creates account from interstitial
    #Then User creates account from interstitial using Email %{GD_NEW_EMAIL_ADDRESS}
    Then Print Seat Detail %{GD_NEW_EMAIL_ADDRESS} %{GD_NEW_PASSWORD} %{GD_EMAIL_ADDRESS} %{GD_PASSWORD} %{GD_TransferTicketID}
    And Accept ticket from popup and verify success message
    #And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    #And User click on Claim link for ticketId %{GD_TransferTicketID} and %{GD_TransferID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify User claimed the ticket using %{GD_TransferTicketID} and %{GD_CustomerName}
    And Verify False ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Verify unable to claim if sender Reclaim ticket
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Send Ticket using %{GD_TransferTicketID}
    And User generate TransferId for %{GD_TransferTicketID}
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Edit or Cancel ticket, new ticket, for %{GD_TransferTicketID}
    And User clicks on Reclaim
    Then Verify success screen
    When User click on done
    Then Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User navigates to "/ticket/claim?%{GD_TransferID}" from NAM
    Then Claim Pop-up is displayed with Error Message

  Scenario: Unable to send with blank spaces in first and last name
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on Transfer to continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    And User enter blank space and verify send button remains disabled %{GD_EMAIL_ADDRESS}

  Scenario: Verify Ticket Price and Currency displayed on UI
    Given Get ticket with Price and ValueID for %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Ticket Details %{GD_TicketID} and verifies values of Purchase Price on UI and API

  Scenario: Verify flip window and on click Make Payment for Tickets with Pending Invoice user is navigated to Invoice
    Given Get ticket with Pending Invoice for %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Make Payment %{GD_TicketID} , Invoice page is displayed
    Given Get transfer ticket IDs
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    Then Verify Ticket details flip window should be displayed only for one ticket. On click of second ticket, previous one should be closed. %{GD_TransferTicketID1} %{GD_TransferTicketID2}

  Scenario: Verify sorting of Events with Date, Time and TBD/TBA
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to "/tickets#/" from NAM
    Given Get all events sorted and verify with name, date and time with UI %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}

  Scenario: Verify Filtering of Tickets
    Given Get Event Id with Maximum Tickets
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    Then Verify filtering of tickets for %{GD_EMAIL_ADDRESS} %{GD_PASSWORD} %{GD_EventId}

  Scenario: Verify visibility of Barcode Number on UI after enabling and disabling from super admin
    Given Get ticket ID without Deferred Delivery
    Given User navigates to Admin Login and Logs in using Admin Credentials
    # And User navigates to "/admin/config/site-settings" from NAM
    And User navigates to "/admin/config/ticket-management" from NAM
    And Barcode check and save setting "ENABLED"
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    Then User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    Then Barcode should be "ENABLED" on UI %{GD_TransferTicketID1}
    When User navigates to "/user/LOGOUT" from NAM
    And User navigates to Admin Login and Logs in using Admin Credentials
    #And User navigates to "/admin/config/site-settings" from NAM
    And User navigates to "/admin/config/ticket-management" from NAM
    And Barcode check and save setting "DISABLED"
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    Then User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    Then Barcode should be "DISABLED" on UI %{GD_TransferTicketID1}
    When User navigates to "/user/LOGOUT" from NAM

  Scenario: Sent Ticket and Claim with Email
    And Save "nitin.mussani@ticketmaster.com" into NEW_EMAIL_ADDRESS
    And Save "iom123" into NEW_PASSWORD
    And Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Given User gets count of Ticket in mail "mailbox_2" "passkey_2"
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    When User navigates to "/user/logout" from NAM
    Then Get Link for "Accept" ticket "mailbox_2" "passkey_2"
    When User clicks on "Accept" link in Email %{GD_TicketsLink}
    When User verify congratulation message
    And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then User logged in successfully
    And User click on Claim link for ticketId %{GD_TransferTicketID} and %{GD_TransferID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Verify ticket status - No Status for ticketId %{GD_TransferTicketID}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    Then Verify ticket flags for %{GD_TransferTicketID}, %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify User claimed the ticket using %{GD_TransferTicketID} and %{GD_CustomerName}
    And Verify False ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}

  Scenario: Sent Ticket and Decline with Email
    And Save "nitin.mussani@ticketmaster.com" into NEW_EMAIL_ADDRESS
    And Save "iom123" into NEW_PASSWORD
    And Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Given User gets count of Ticket in mail "mailbox_2" "passkey_2"
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    When User navigates to "/user/logout" from NAM
    Then Get Link for "Decline" ticket "mailbox_2" "passkey_2"
    When User clicks on "Decline" link in Email %{GD_TicketsLink}
    Then Decline Offer page is displayed and user enters optional message, agrees Term of User and Declines Offer

  Scenario: Bulk Transfer Events, Section and Tickets validation
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    And Click on Bulk Transfer Checkbox and Verify Send Ticket button and Select All checkbox displayed
    And Select first two Events for Bulk Transfer and click Send Tickets button
    Then Verify Sections and Tickets for Bulk Transfer %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}

  Scenario: Bulk Transfer Tickets
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When Click on Bulk Transfer Checkbox, verify Send and Select All buttons, Select two Events with Transfer Tickets enabled and Click Send Tickets %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    And Select two tickets and click on Choose Recipient %{GD_EMAIL_ADDRESS}  %{GD_PASSWORD}
    Then Add Recipient and Send Ticket %{GD_NEW_EMAIL_ADDRESS}

  Scenario: Claim Tickets from Dashboard
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And Accept ticket from dashboard and verify success message

  Scenario: Verify complete section ticket transfer through bulk transfer, multiselect icons, modal close etc.
    When User enters %{GD_TRANSFEREE_EMAIL_ADDRESS} and %{GD_TRANSFEREE_PASSWORD}
    Then User logged in successfully
    # And User declines all pending transfers if any
    And User accept all pending transfers if any
    And Bulk is enabled on Site
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User accept all pending transfers if any
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    Then Verify multiselect icon by default state  at event list page
    And User clicks multiselect icon
    Then Checkboxes are triggered next to each event
    And User is able to select all events via select all Checkbox and send button gets enabled
    And User deselects Select All Button
    And User selects  Valid Event for Complete Section Bulk Transfer and click Send Tickets button for member %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    Then Model to select seats is triggered with correct Event Name and Time
    And Model can be closed by clicking  X button
    When User clicks on Send
    And Model can be closed by clicking Cancel button
    When User clicks on Send
    Then Model to select seats is triggered with correct Event Name and Time
    And User clicks Send All Button for section to send all tickets
    Then Select a Recipient model appears with proper labels for FirstName, LastName, Email and notes
    And Send button does not appear until all mandatory fields are filled
    And User types following details for recipient and clicks Send %{GD_TRANSFEREE_EMAIL_ADDRESS} NAMAutomationTest
    Then Ticket Transfer complete Dialouge appears
    Then User navigates to Manage tickets page by clicking event name
    Then Transferred tickets are shown as Pending with CANCEL TRANSFER link and proper Firstname and LastName of Transferee
    And User navigates to "/user/logout" from NAM
    When User enters %{GD_TRANSFEREE_EMAIL_ADDRESS} and %{GD_TRANSFEREE_PASSWORD}
    Then User logged in successfully
    And User navigates to "/dashboard" from NAM
    And User is able to see tickets transferred info bar on Dashboard with Decline and Accept Button
    And User clicks See Details Link
    Then Transferred tickets are seen
    And User Accepts Tickets Transfer
    Then Success Message is Seen with Go to Event Button
    And User is navigated to events page by clicking Go to Event Button
    And User navigates to "/dashboard" from NAM
    Then Transfer Tickets info bar disappears
    And User navigates to "/user/logout" from NAM
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Then User navigates to Manage tickets page by clicking event name

  #And Transferred tickets are shown as Completed with  proper Firstname and LastName of Transferee and  CANCEL TRANSFER link disappears
  Scenario: Verify transfer Decline through Bulk transfer, Special chars, Cancel transfer and already added Recipient
    When User enters %{GD_TRANSFEREE_EMAIL_ADDRESS} and %{GD_TRANSFEREE_PASSWORD}
    Then User logged in successfully
    And User declines all pending transfers if any
    And Bulk is enabled on Site
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    And User clicks multiselect icon
    And User selects  Valid Event for Complete Section Bulk Transfer and click Send Tickets button for member %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    Then Model to select seats is triggered with correct Event Name and Time
    And User clicks Send All Button for section to send all tickets
    Then Select a Recipient model appears with proper labels for FirstName, LastName, Email and notes
    And User types following details for recipient and clicks Send %{GD_TRANSFEREE_EMAIL_ADDRESS} NAMAutomationTest
    Then Ticket Transfer complete Dialouge appears
    And User navigates to "/user/LOGOUT" from NAM
    When User enters %{GD_TRANSFEREE_EMAIL_ADDRESS} and %{GD_TRANSFEREE_PASSWORD}
    Then User logged in successfully
    And User is able to see tickets transferred info bar on Dashboard with Decline and Accept Button
    And User clicks See Details Link
    Then Transferred tickets are seen
    And User Declines Tickets Transfer
    Then Decline Message is Seen with Go to Event Button
    And User is navigated to My Events page by clicking Go to Event Button
    And User navigates to "/dashboard" from NAM
    Then Transfer Tickets info bar disappears
    And User navigates to "/user/LOGOUT" from NAM
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Then User navigates to Manage tickets page by clicking event name
    And Transferred tickets are shown as Active
    And User navigates to "/dashboard" from NAM
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    Then Verify multiselect icon by default state  at event list page
    And User clicks multiselect icon
    And User selects  Valid Event for Complete Section Bulk Transfer and click Send Tickets button for member %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    Then Model to select seats is triggered with correct Event Name and Time
    And User clicks Send All Button for section to send all tickets
    And Already added Recipient is present
    Then Select a Recipient model appears with proper labels for FirstName, LastName, Email and notes
    And User is able to close subpop of adding new user via cross icon
    And Special char $ is not allowed in FirstName & LastName Section
    And User types following details for recipient and clicks Cancel: fname lname fname@gmail.com
    And User is redirected to my events page

  Scenario: Verify transfer Reclaim through bulk transfer
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And Bulk is enabled on Site
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    And User clicks multiselect icon
    And User selects  Valid Event for Complete Section Bulk Transfer and click Send Tickets button for member %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    Then Model to select seats is triggered with correct Event Name and Time
    And User clicks Send All Button for section to send all tickets
    Then Select a Recipient model appears with proper labels for FirstName, LastName, Email and notes
    And User types new users's mail id for recipient and clicks Send
    Then Ticket Transfer complete Dialouge appears
    And User navigates to "/user/LOGOUT" from NAM
    When New User signs up with new mail id as entered in transfer request
    Then User logged in successfully
    And User is able to see tickets transferred info bar on Dashboard with Decline and Accept Button
    And User navigates to "/user/LOGOUT" from NAM
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Then User navigates to Manage tickets page by clicking event name
    Then Transferred tickets are shown as Pending with CANCEL TRANSFER link and proper Firstname and LastName of Transferee
    And Reclaim is successful
    When User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    And User clicks multiselect icon
    And User selects  Valid Event for Complete Section Bulk Transfer and click Send Tickets button for member %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    Then Model to select seats is triggered with correct Event Name and Time

  Scenario: Verify two events transfer through bulk transfer
    When User enters %{GD_TRANSFEREE_EMAIL_ADDRESS} and %{GD_TRANSFEREE_PASSWORD}
    Then User logged in successfully
    #And User declines all pending transfers if any
    And User accept all pending transfers if any
    And Bulk is enabled on Site
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    #Accepct feature implemented
    And User accept all pending transfers if any
    And User navigates to myevents page by clicking "VIEW ALL" link on dashboard
    And User clicks multiselect icon
    And User selects  Valid  two Event for Complete Section Bulk Transfer and click Send Tickets button for member %{GD_EMAIL_ADDRESS} %{GD_PASSWORD}
    Then Model to select seats is triggered with correct Event Names and Time for both Events
    And User clicks Send All Button for two section to send all tickets
    And User types following details for recipient and clicks Send %{GD_TRANSFEREE_EMAIL_ADDRESS} NAMAutomationTest
    Then Ticket Transfer complete Dialouge appears
    Then User navigates to Manage tickets pages by clicking event names one by one and Transferred tickets are shown for two events as Pending with CANCEL TRANSFER
    And User navigates to "/user/LOGOUT" from NAM
    When User enters %{GD_TRANSFEREE_EMAIL_ADDRESS} and %{GD_TRANSFEREE_PASSWORD}
    Then User logged in successfully
    And User navigates to "/dashboard" from NAM
    And User is able to see tickets transferred info bar on Dashboard with Decline and Accept Button
    And User clicks See Details Link
    Then Tickets transferred are seen for two events
    And User Accepts Tickets Transfer
    Then Success Message is Seen with Go to Event Button
    And User is navigated to My Events page by clicking Go to Event Button
    And User navigates to "/dashboard" from NAM
    Then Transfer Tickets info bar disappears
    And User navigates to "/user/LOGOUT" from NAM
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully

  #Then User navigates to Manage tickets pages by clicking event name one by one  and Transferred tickets for two events are shown as Completed and  CANCEL TRANSFER link disappears
  Scenario: Verify Send ticket functionality for EDP new design with existing user and verify message on interstitial page, link get from to transfer email
    # Verifying Send ticket functionality for EDP new design with existing user
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given EDP is enabled on Site
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    And Save "jitendra.roy@ticketmaster.com" into RECIPIENT_EMAIL_ADDRESS
    And Save "jitendra1" into RECIPIENT_PASSWORD
    When User enters recipient details %{GD_RECIPIENT_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_RECIPIENT_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And Save the state of ticket for %{GD_TransferTicketID}
    And Verify and Save the Status and Expiry of ticket using %{GD_TransferTicketID}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    When User Logout and login again using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then Verify the status and expiry of Send ticket after logout and login %{GD_Status} %{GD_Expiry} %{GD_TransferTicketID}
    And Verify the state of the ticket %{GD_State}
    And Verify ticket flags for %{GD_TransferTicketID}, %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/user/LOGOUT" from NAM
    # Verifying message on interstitial page, link get from to transfer email
    #		When Get reset password link from Email "mailbox" "passkey"
    When user fetch approve link from Email using "mailbox" "passkey"
    And User redirected to link %{GD_AcceptTransfer_link}
    Then verify transfer ticket notification is present above My Account
    When User landed on interstitial page and enters %{GD_RECIPIENT_EMAIL_ADDRESS} and %{GD_RECIPIENT_PASSWORD}
    Then verify transfer ticket detail section in present
    And user accept the ticket
    And User navigates to "/user/LOGOUT" from NAM

  Scenario: Provide automatic comparison of seats data in Archtics DB with the data returned as a result of GetSeatsBySectionId call
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    And User save the seatSectionRow using %{GD_TransferTicketID}
    Then User verify Section Row and Seats In UI
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display

  #And Save the state of ticket for %{GD_TransferTicketID}
  Scenario: Enabled secure barcode toggle from CMS
    When User enters %{GD_TRANSFEREE_EMAIL_ADDRESS} and %{GD_TRANSFEREE_PASSWORD}
    Then User logged in successfully
    And Verify secure barcode toggle status
    And Secure Barcode is enabled on Site
    And Verify secure barcode toggle status

  Scenario: Verify secure barcode should appear when secure barcode toggle is enabled in CMS and RET flag is enabled for an event
    Given User is enabled on mobile
    When User fetch render ticket Ids
    Given User navigates to "/tickets#/%{GD_EventID}" from NAM
    When User landed on interstitial page and enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Clicks on Scan Barcode for %{GD_RenderBarcodeID} and 0
    Then User Accept the Education Information POP Up if present
    Then Secure Barcode gets displayed for %{GD_RenderBarcodeID} and 0
    Given User saves Secure Barcode frontend attribute for %{GD_RenderBarcodeID} and 0
    And Verify the secure barcode for %{GD_RenderBarcodeID} and 0 with %{GD_SecureBarcodeAttribute}

  Scenario: Verify Cancel ticket functionality for EDP Update Phase 3 new design with existing user
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_EMAIL_ADDRESS}
    When User clicks on Done
    Then Verify ticket listing page display
    And User click on cancel transfer link on event details page
    Then Verify ticket listing page display

  Scenario: Verify Decline ticket functionality for EDP Update Phase 3 new desgin with existing user
    And Save "jitendra.roy@ticketmaster.com" into NEW_EMAIL_ADDRESS
    And Save "jitendra1" into NEW_PASSWORD
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And User declines all pending transfers if any
    And Get transfer ticket ID for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And Save ticket flags for ticket Id %{GD_TransferTicketID} using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    And User navigates to "/tickets#/%{GD_EventId}" from NAM
    When User clicks on Send tickets
    And User selects the seat using %{GD_TransferTicketID}
    And User clicks on continue
    Then Verify Event Detail using %{GD_TransferTicketID},NEW TICKETS
    When User enters recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Send
    Then Confirmation page is displayed with Recipient details %{GD_NEW_EMAIL_ADDRESS}
    When User clicks on Done
    Then User Logout and login again using %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
    And User navigates to "/dashboard" from NAM
    And User is able to see tickets transferred info bar on Dashboard with Decline and Accept Button
    And User clicks See Details Link
    And User Declines Tickets Transfer
    Then Decline Message is Seen with Go to Event Button
    And User is navigated to My Events page by clicking Go to Event Button
    And User navigates to "/dashboard" from NAM
    Then Transfer Tickets info bar disappears
    And User navigates to "/user/LOGOUT" from NAM

  Scenario: Verify transfer tickets needs to be sorted in Bulk Transfer when EDP is on
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    Given EDP is enabled on Site
    When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
    Then User logged in successfully
    And Get Event Id with Maximum Tickets
    Then User navigates to "/tickets#/%{GD_EventId}" from NAM
    And Verify that tickets displayed are in sorted order
