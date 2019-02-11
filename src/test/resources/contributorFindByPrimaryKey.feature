Feature: Return contributor record using Primary Key (Search By Primary Key)

	Scenario: Returning matched record
		When the client makes a GET call to /contributor/searchBy/reference=49900000001;period=201803;survey=009
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response entity at 0 should contain "reference" with value "49900000001"
		And the response entity at 0 should contain "period" with value "201803"
		And the response entity at 0 should contain "survey" with value "009"
		And the response should contain exactly 1 entity

	Scenario: Returns an empty array as no record is matched
		When the client makes a GET call to /contributor/searchBy/reference=499xxxxxxxx;period=000000;survey=000
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should contain exactly 0 entity
