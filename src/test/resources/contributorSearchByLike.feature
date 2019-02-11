Feature: Return records that match search criteria (Search By Like)

	Scenario: client makes a call to GET /contributor/searchByLike/reference=499000;period=2018;survey=09
		When the client makes a GET call to /contributor/searchByLike/reference=499000;period=2018;survey=09
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should contain at least 1 entity

	Scenario:  client makes a call to GET /contributor/searchByLike/period=0000;survey=076;reference=499001
		When the client makes a GET call to /contributor/searchByLike/period=0000;survey=076;reference=499001
		Then the client receives status code of 200
		And the client receives a valid JSON
		And the response should contain exactly 0 entity