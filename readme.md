# JPMC Message Processing
Developed by Neil Grant

## Running the Message Processor
Requires JDK 14.0.1

## Interpretation of Requirements
In lieu of having a conversation around requirements, I have made the following assumptions/decisions:
* Application will not resume after 50 messages
* On receipt of a sales notification with an adjustment (type 3) it is assumed:
  * the adjustment is of the same product type as the sale in the message
  * the sale in the message will be stored then adjusted
* Adjustment operations will not allow zero or negative values   

## Design Decisions
* Notifications implemented as if API and data model is supplied by external company, so is not used as internal model.
* Given more time would have implemented object model around reporting instead of using String.  

## Possible Future Changes
I considered the following future requirements, but as not specified in the requirements, and being wary of premature abstraction/optimisation, I tried to be pragmatic and leave the current implementation open for extension/quick refactoring.
* New adjustment types can be added
* Different notification producers (i.e. file based) can be added
* Different repositories (e.g. DB, file) could be implemented by introducing a repository interface
* Performance
  * Sales and adjustment repos could introduce indexing on product type to improve on O(n) complexity of grouping
  * If needed, and with current adjustment types, sales report could be updated in real time as opposed to being generated on deman
