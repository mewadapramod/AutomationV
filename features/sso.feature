Feature: SSO
	Background: User landed on homepage
		Given User is on / Page
		Given User credentials passed from Jenkins
	
	Scenario: SSO from NAM to Classic AMGR for Existing User
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from NAM  to Classic AMGR for New User
		Then User creates account
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from NAM to CAM to STP for Existing User
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User navigates to /stp?redirect_url=invoice/list from CAM
		Then User done SSO successfully to STP with correct %{GD_StpAccountName} or %{GD_AccountName}
		
	Scenario: SSO from NAM to Classic AMGR for Multiple Users
		Given User creates account using ats
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		#Then User logged in successfully
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User logout from CAM
		And User navigates to /user/logout from NAM
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: Edit in Classic AMGR like Password or Profile update
		Then User creates account
		When User clicks your account
		And update %{GD_NEW_PASSWORD} with AMGR1234 
		Given User logout from CAM
		And User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and AMGR1234
		
	Scenario: SSO to Classic AMGR after switching Account
		Given Generate member response with %{GD_SWITCH_ACC_EMAIL_ADDRESS} and %{GD_SWITCH_ACC_PASSWORD} 
		When User enters %{GD_SWITCH_ACC_EMAIL_ADDRESS} and %{GD_SWITCH_ACC_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_SWITCH_ACC_EMAIL_ADDRESS} and %{GD_SWITCH_ACC_PASSWORD}
		When User clicks switch accounts
		And Switch account to %{GD_accId1}
		Then User switched to %{GD_name1}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_name1}
		
	Scenario: SSO from NAM to STP for Existing User
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		And User navigates to /stp?redirect_url=invoice/list from NAM
		Then User done SSO successfully to STP with correct %{GD_StpAccountName} or %{GD_AccountName}
		
	Scenario: SSO from NAM to STP for New User
		Then User creates account
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to /stp from NAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		
	Scenario: SSO from NAM to STP for Multiple User
		Given User creates account using ats
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		#Then User logged in successfully
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to /stp from NAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User logout from STP
		And User navigates to /user/logout from NAM
		And User navigates to / from NAM
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		And User navigates to /stp from NAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		
	Scenario: SSO from NAM to CAM to STP for New User
		Then User creates account
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User navigates to /stp from CAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		
	Scenario: SSO from NAM to CAM to STP for Multiple User
		Given User creates account using ats
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		#Then User logged in successfully
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User navigates to /stp from CAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName} 
		Given User logout from STP
		And User navigates to /classic-amgr?redirect_url=account/settings from NAM
		And User logout from CAM
		And User navigates to /user/logout from NAM
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User navigates to /stp from CAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		
	Scenario: SSO from NAM to STP to CAM for Existing User
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		And User navigates to /stp?redirect_url=invoice/list from NAM
		Then User done SSO successfully to STP with correct %{GD_StpAccountName} or %{GD_AccountName}
		When User navigates to CAM from STP
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from NAM to STP to CAM for New User
		Then User creates account
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to /stp from NAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		When User navigates to CAM from STP
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from NAM to STP to CAM for Multiple User
		Given User creates account using ats
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		#Then User logged in successfully
		Given Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to /stp from NAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		When User navigates to CAM from STP
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User navigates to /stp from NAM
		And User logout from STP
		And User navigates to / from NAM
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User logout from CAM
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		And User navigates to /stp?redirect_url=invoice/list from NAM
		Then User done SSO successfully to STP with correct %{GD_StpAccountName} or %{GD_AccountName}
		When User navigates to CAM from STP
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from STP to NAM to CAM and Email ID change in CAM
		Given User creates account using ats
		And User is on /stp Page
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User navigates to STP homepage after entering %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		When manage profile with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then NAM homepage is displayed
		Given User navigates to /user/logout from NAM
		When User enters %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from STP to NAM for Existing User
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User login to STP with %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		
	Scenario: SSO from STP to NAM for New User
		Given User creates stp account
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		
	Scenario: SSO from STP to NAM for Multiple User
		Given User creates account using ats
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User login to STP with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		Given User navigates to /user/logout from NAM
		And User visits STP homepage
		And User logout from STP
		And Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User login to STP with %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		
	Scenario: SSO from STP to NAM for New User having Email ID containing +1
		Given User creates stp account+1
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to NAM from STP
		Then NAM homepage is displayed
		
	Scenario: SSO from STP to NAM to CAM for New User
		Given User creates stp account
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from STP to NAM to CAM for Multiple User
		Given User creates account using ats
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User login to STP with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User logout from CAM
		And User navigates to /user/logout from NAM 
		And User visits STP homepage
		And User logout from STP
		And Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User login to STP with %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from STP to NAM (login with account ID)
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User login to STP with %{GD_AccountId} and %{GD_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		Given User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		
	Scenario: SSO from STP to NAM to CAM for New User having Email ID containing +1
		Given User creates stp account+1
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		And User navigates to NAM from STP
		And User navigates to / from NAM
		Then User logged in successfully
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: SSO from NAM to CAM and Email ID change in CAM/ logout and relogin with new email
		Given User creates account using ats
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
		And SSO done successfully to NAM with correct %{GD_AccountName} and %{GD_AccountId}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		
	Scenario: TicketBuy from STP and redirect to NAM
		Given User creates account using ats
		And Customer details are fetched for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		When User login to STP with %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		When User buys a GA ticket with Payment Plan using %{GD_CardType} type card with card number %{GD_CardNumber}, CVV %{GD_CVV}, Expiry %{GD_ExpiryDate}
		Then Thank you page is displayed
		Given Invoice Id is generated
		And User navigates to NAM from STP
		#Then User logged in successfully
		And User navigates to "/invoice#/%{GD_STP_INVOICE_ID}" from NAM
		Then Pending invoice found on NAM with id %{GD_STP_INVOICE_ID} and amount due %{GD_STP_INVOICE_AMT_DUE}
		Given User navigates to /tickets from NAM
		When Events page is displayed
		Then Verify events summary for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User navigates to "/tickets#/%{ENV_event_id}" from NAM
		When Tickets page is displayed
		Then Verify tickets count based on status
		Given User navigates to / from NAM
		Then User logged in successfully
		And Verify events details on dashboard for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User navigates to /stp from NAM
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}
		When User buys a GA ticket with Full Payment using %{GD_CardType} type card with card number %{GD_CardNumber}, CVV %{GD_CVV}, Expiry %{GD_ExpiryDate}
		Then Thank you page is displayed
		Given User visits STP homepage
		And User navigates to NAM from STP		
		And User navigates to /tickets from NAM
		When Events page is displayed
		Then Verify events summary for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		Given User navigates to "/tickets#/%{ENV_event_id}" from NAM
		When Tickets page is displayed
		Then Verify tickets count based on status
		Given User navigates to / from NAM
		Then User logged in successfully
		And Verify events details on dashboard for %{GD_NEW_EMAIL_ADDRESS} and %{GD_NEW_PASSWORD}
		
	Scenario: Verify SSO Redirection from Promotiles to STP
		And User navigates to /user/login from NAM
		When User login into CMS
		And Navigate to /admin/group-view
		Then Promotiles page is displayed
		When Click edit icon for logged in promotiles
		Then Logged In Promotiles page is displayed
		Given First promotile is saved with following values
		| %{GD_HeaderText} | %{GD_PromoTitle} | %{GD_URL} | %{GD_backgroundColor} |
		And User navigates to / from NAM
		When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully
		And Verify header text of first promotile with %{GD_HeaderText}
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When Click first promotile
		Then User done SSO successfully to STP Welcome page with correct %{GD_StpAccountName} or %{GD_AccountName}