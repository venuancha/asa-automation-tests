Feature: ASA Automation Tests
 
Scenario: Verify Create Editorial Version
Given I have data for new editorial version
When I make a create request for new editorial version
Then I should get response with created action

Scenario: Verify Get Editorial Version
Given I create an Editorial Version
When I make a get request for that Editorial Version
Then I should get the Editorial Version with the required details


#Scenario: Verify Update Editoria

