Feature: As a user,
  I can pass a PnL history
  In order to calculate the historical value at risk

  Scenario Outline: Historical value at risk calculation from a CSV file
    Given a PnL history CSV file located at "<path>"
    And a computation date set to <computationDate>
    And an historical depth of <historicalDepth> days
    And a percentile coefficient of <percentileCoef>
    When I want to calculate the historical value at risk
    Then the historical value at risk is <historicalVar>

    Examples:
      | path          | computationDate | historicalDepth | percentileCoef | historicalVar |
      | example.csv   | 2019-11-12      | 12              | 95             | -75342.83     |
      | example.csv   | 2019-11-12      | 12              | 84             | -34754.23     |
      | example.csv   | 2019-11-12      | 12              | 0              | 552345.75     |
      | example.csv   | 2019-11-12      | 12              | 100            | -123987.45    |
      | example.csv   | 2500-11-12      | 12              | 100            | null          |

  Scenario Outline: Historical value at risk calculation from an incorrect CSV file
    Given a PnL history CSV file located at "<path>"
    And a computation date set to <computationDate>
    And an historical depth of <historicalDepth> days
    And a percentile coefficient of <percentileCoef>
    When I want to calculate the historical value at risk
    Then the historical value is rejected with the message "<errorMessage>"

    Examples:
      | path            | computationDate | historicalDepth | percentileCoef | errorMessage                                                                               |
      | invalidDate.csv | 2019-11-12      | 12              | 95             | Failed to load CSV data : Text '2019-1Z-01' could not be parsed at index 5                 |
      | invalidPnl.csv  | 2019-11-12      | 12              | 75             | Failed to load CSV data : Cannot convert '21Some letters in a number !879.84' to a number  |
      | missingPnl.csv  | 2019-11-12      | 12              | 15             | Failed to load CSV data : Could not parse CSV record at line 5                             |
