# OWAT Java Implementation Analysis Guide

This is a guide to running the implementation tests, acquiring the csv outputs from it, and using them with the R project to glean useful data on how fast the different implementations are and how to best utilize them.

## Running the tests to get CSV output

The tests don't by default output the CSV data needed. To tell the tests to output, you need to edit the test Java file locally: 

`{project dir} /OWAT-lib/src/test/java/com/ebp/owat/lib/runner/RunnerTestPlusPlus.java`

### Values to change:

`REPORT_TO_CSV` = Turns on/off the CSV reporting. Set to 'true'

`SHORT` = Tells the test to run a short or long test. Set to 'false'

`MODES_TEST` = Tells the test to use all the matrix implementations. Set to 'true'


**REMEMBER NOT TO COMMIT ANY OF THESE FLAG CHANGES TO GIT**

Once these flags are set, you can run the library tests like normal and the tests will create the csv output for analysis.

### CVS output location:

`{project dir} /OWAT-lib/out/`

