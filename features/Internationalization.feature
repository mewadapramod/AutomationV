Feature: Internationalization
	Background: User landed on CMS
			Given User is on / Page
			Given User credentials passed from Jenkins
		
	Scenario: Verify langauge installation from CMS
		And User navigates to /user/login from NAM
		#When User login into CMS with support user
		When User login into CMS
		Then Redirect to Language Configuration under Settings tab
		And Verify user lands on Add Language Page
		But Delete %{GD_LANGUAGE} language if already installed
		When Select Language from Add language Dropdown %{GD_LANGUAGE} and Click Add
		Then %{GD_LANGUAGE} language got successfully installed
		When Switch to %{GD_LANGUAGE} langauge from Language Switcher
		And User navigates to Add Email Template Page
		Then Add Invoice Email template and enter text in template fields %{GD_EMAIL_TEMPLATE_TITLE} , %{GD_INVOICE_TEMPLATE_TYPE}, %{GD_SUBJECT} and Save
		And User navigates to Add Email Template Page
		Then Add Payment Email template and enter text in template fields %{GD_EMAIL_TEMPLATE_TITLE} , %{GD_PAYMENT_TEMPLATE_TYPE}, %{GD_SUBJECT} and Save
		When User navigates to Add new Invoice Template page
		Then User Enters text in Invoice template %{GD_INVOICE_TEMPLATE_TITLE}
		And User Selects Email Templates %{GD_INVOICE_PAYMENT_EMAIL} , %{GD_INVOICE_INVOICE_EMAIL} and save
		
	Scenario: Switch language and
		And User navigates to /user/login from NAM
		#When User login into CMS with support user
		When User login into CMS
		Then Redirect to Language Configuration under Settings tab
		And User is on Add Language Page
		But Delete %{GD_LANGUAGE} language if already installed
		When Select Language from Add language Dropdown %{GD_LANGUAGE} and Click Add
		Then Switch to %{GD_LANGUAGE} langauge from Language Switcher
		
		
		
		
