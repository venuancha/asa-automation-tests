$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("ASA Automation.feature");
formatter.feature({
  "id": "asa-automation-tests",
  "description": "",
  "name": "ASA Automation Tests",
  "keyword": "Feature",
  "line": 1
});
formatter.scenario({
  "id": "asa-automation-tests;verify-create-editorial-version",
  "description": "",
  "name": "Verify Create Editorial Version",
  "keyword": "Scenario",
  "line": 3,
  "type": "scenario"
});
formatter.step({
  "name": "I have data for new editorial version",
  "keyword": "Given ",
  "line": 4
});
formatter.step({
  "name": "I make a create request for new editorial version",
  "keyword": "When ",
  "line": 5
});
formatter.step({
  "name": "I should get response with created action",
  "keyword": "Then ",
  "line": 6
});
formatter.match({
  "location": "AsaSteps.generateEditorialVersionData()"
});
formatter.result({
  "duration": 273908308,
  "status": "passed"
});
formatter.match({
  "location": "AsaSteps.callCreateEditorialVersion()"
});
formatter.result({
  "duration": 385051944,
  "status": "passed"
});
formatter.match({
  "location": "AsaSteps.verifyCreateEditorialVersion()"
});
formatter.result({
  "duration": 1392714,
  "status": "passed"
});
formatter.scenario({
  "id": "asa-automation-tests;verify-get-editorial-version",
  "description": "",
  "name": "Verify Get Editorial Version",
  "keyword": "Scenario",
  "line": 8,
  "type": "scenario"
});
formatter.step({
  "name": "I create an Editorial Version",
  "keyword": "Given ",
  "line": 9
});
formatter.step({
  "name": "I make a get request for that Editorial Version",
  "keyword": "When ",
  "line": 10
});
formatter.step({
  "name": "I should get the Editorial Version with the required details",
  "keyword": "Then ",
  "line": 11
});
formatter.match({
  "location": "AsaSteps.setEditorialVersion()"
});
formatter.result({
  "duration": 47841828,
  "status": "passed"
});
formatter.match({
  "location": "AsaSteps.getEditorialVersion()"
});
formatter.result({
  "duration": 288121049,
  "status": "passed"
});
formatter.match({
  "location": "AsaSteps.verifyEditorialVersion()"
});
formatter.result({
  "duration": 73629,
  "status": "passed"
});
});