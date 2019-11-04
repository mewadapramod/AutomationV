Feature: Prod Sanity
	Background: User landed on homepage
		Given User is on / Page
		Given User credentials passed from Jenkins
		
	
	Scenario: Prod sanity verification Part1
	    When User navigates to /invites/*a from NAM
	    Then Verify interstitial SignIn component on invites page
	    Given User is on / Page
		Then User creates account
		Then User logged in successfully
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
		Given User logout from CAM
		And User navigates to /user/logout from NAM
		
		
	Scenario: Prod sanity verification
	     When User navigates to /user/login for CMS Login
		Given User is on / Page
	  When User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Then User logged in successfully		
		Given Customer details are fetched for %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}	
	  And Verify all tickets section using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		Given User navigates to /dashboard from NAM
	  When Verify invoice Listing page using %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
		When User clicks your account
		Then Classic AMGR is displayed with correct %{GD_CustomerName} and %{GD_NickName}
		Given User logout from CAM
		And User navigates to /user/logout from NAM
		
		
	Scenario: Verify privacy link is set up correctly
		And User clicked on signup link
		Then Verify user is navigated to correct link on clicking privacy policy or terms link
		
	Scenario: Verify CMS Login successfull
		And User navigates to /user/login from NAM
		When User login into CMS
		And Navigate to /admin/group-view
		Then Promotiles page is displayed
		
	Scenario: Barcode number state after upgrade
		And User navigates to Admin Login and Logs in using Admin Credentials
    And User navigates to "/admin/config/site-settings" from NAM
    Then Verify barcode state after upgrade %{GD_barcode_number_set}
