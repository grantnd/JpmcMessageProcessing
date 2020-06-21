# JPMC Message Processing
Developed by Neil Grant

## Running the Message Processor
Requires JVM that can support apps built using JDK 14.0.1

## Functional testing
Manual functional testing was carried out, output can be found in `/src/test/manual/functional-output.txt`

## Interpretation of Requirements
In lieu of having a conversation about requirements, I have made the following assumptions/decisions:
* Application will wait for user input to resume when pausing after 50 messages
* A sale's value cannot be negative
* Adjustment operations will not allow zero or negative values
* On receipt of a sales notification with an adjustment (type 3) it is assumed:
  * the adjustment can only be of the same product type as the sale in the message
  * the sale in the message will be stored then adjusted
  * the multiply adjustment operation can be a non whole number
* Report output will be sorted alphabetically by product name
* The sales report after every 10th message will report on all sales received, not just on the last 10 messages      

## Design Decisions
* Notifications implemented as if API and data model is supplied by external company, so not used as internal model.

## Future requirements
I considered the following possible future requirements but tried to be pragmatic and leave the current implementation open for extension/easy refactoring while avoiding premature abstraction/optimisation
* Addition of new adjustment operation types
* Different notification producers (i.e. file based)
* Different repositories (e.g. DB, file)
* Different report writers (e.g. file)
* Performance improvements
  * Sales and adjustment repos could introduce indexing on product type to improve on O(n) complexity of grouping operations
  * With current adjustment types, sales report could be updated in real time as opposed to being generated on demand
