Feature: Find all records using a combination of fields (Search By Exact)

	Scenario: Return record that matches search criteria using reference, period, survey, formid and status
		When the client makes a GET call to /contributor/searchBy/reference=49900000002;period=201803;survey=009;formid=9;status=Dead
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response entity at 0 should contain "reference" with value "49900000002"
		And the response entity at 0 should contain "period" with value "201803"
		And the response entity at 0 should contain "survey" with value "009"
		And the response entity at 0 should contain "formid" with value 9
		And the response entity at 0 should contain "status" with value "Dead"
		And the response should contain exactly 1 entity

    Scenario: Returns an empty array as no record is matched
        When the client makes a GET call to /contributor/searchBy/reference=499xxxxxxxx;period=000000;survey=000;formid=9;status=Dead
        Then the client receives status code of 200
        And the client receives a valid JSON
        And the response should contain exactly 0 entity