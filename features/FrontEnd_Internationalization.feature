Feature: Internationalization Feature
	Background: User landed on homepage
  	Given Get path of Latest POFile
	  And Place latest POFile into %{GD_POFilePath}
    And Place JSON File in English and other language into %{GD_POFilePath}
		Given User is on / Page	
		Given User credentials passed from Jenkins
		
	Scenario: Verify Internationalization enable on Frontend from backend
    And User navigates to /user/login from NAM
		When User login into CMS with support user
		Then Redirect to Language Configuration under Settings tab
		And Get the number of languages added
		
 	Scenario: Verify Internationalization Page after switching on From Backend
    And User navigates to /user/login from NAM
		When User login into CMS with support user
		Then Redirect to Language Configuration under Settings tab
		And Verify user lands on Add Language Page
		But Delete %{GD_CMS_LANGUAGE} language if already installed
		When Select Language from Add language Dropdown %{GD_CMS_LANGUAGE} and Click Add
		Then %{GD_CMS_LANGUAGE} language got successfully installed
		Given User navigates to /user/logout from NAM
		Then Verify URL gets appended with /en
		When Change the language of the site to %{GD_LANGUAGE} from Dropdown
		Then Verify URL gets appended with /fr-ca
		
 	Scenario: Verify Login page in foreign language 
		When Change the language of the site to %{GD_LANGUAGE} from Dropdown
		Then Verify Footer Text in Each Page
		Then Verify Login Component Texts
		
		
 	Scenario: Verify Dashboard Page in other language
   	When Change the language of the site to %{GD_LANGUAGE} from Dropdown
   	And User enters %{GD_EMAIL_ADDRESS} and %{GD_PASSWORD}
   	Then User logged in successfully
   	Then Verify Dashboard Page Texts