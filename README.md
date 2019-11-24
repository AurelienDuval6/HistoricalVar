# Historical Value at Risk calculation

[![build status](https://travis-ci.org/AurelienDuval6/HistoricalVar.svg?branch=master)](https://travis-ci.org/AurelienDuval6/HistoricalVar)
[![](http://www.wtfpl.net/wp-content/uploads/2012/12/wtfpl-badge-2.png)](http://www.wtfpl.net/)

The following program can be used to calculate the historical VaR from an external CSV file.

## Usage

```shell script
java -jar HistoricalVar -c <computation-date> -d <historical-depth> -f <file> [-h] -p <percentile> [-v]
```

- -c, --computation-date : The ISO formatted date that will serve as base for the VaR calculation
- -d, --historical-depth : The number of days in the past that will be used for the computation
- -f, --file : Path to a CSV file containing PnL information
- -p, --percentile : The risk coefficient that you want to use to guarantee your return on investment (number between 0 and 100 included)
- -v, --verbose : Activates verbose mode
- -h, --help : Displays help for the command

## Installation :

```shell script
mvn clean install
```


## Instructions :
Implement a program that will take as input the following parameters: 

*	Current date : String using ISO format (YYYY-MM-DD) modeling the current date (2019-01-01 in the example)
*	Historical depth: Integer representing the number of days to use starting from the current date. All data in the file post current date and before (current date - historical depth) should be excluded from the computation.
*	Percentile coefficient : Integer between 0 and 100
*	Path to a CSV file (String) formatted as such :
    + No header row 
    + First column: the date of the P&L (ISO format : YYYY-MM-DD) 
    + Second column: the P&L amount (decimal number separated by a ‘.’)
    + File naming convention: “HISTORICAL_PnL_*.csv”
    + CSV separator : ‘;’
    + No specific order can be expected

## Restrictions :
-	Use Java 7 or 8
-	Package in a standard maven project (src/{main|test}/{java|resource})
-	Only “well-known” external libraries are allowed (apache (commons, log4j,…), joda, junit or mockito for instance)

## Contributors
 - Aurélien Duval ([GitHub](https://github.com/AurelienDuval6))