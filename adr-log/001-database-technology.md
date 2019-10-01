# 1. Database technology  

## Status

Proposed

## Context

We need to store clients orders. Orders will be fetched by
client identifier and order status. We estimate that orders
will reach in next year over 100 millions of records. Orders 
are rarely mutated.

## Decision

We will use MongoDB. 

1. It doesn't have an expensive license.
2. It allows to partition data on multiple servers. We expect a lot of data.
3. It allows to use indexes. On contrary Cassandra doesn't. We need
it to fetch data by client identifier and order status.


## Consequences

Paging might be expensive. It's advised to use sharding key for pagination.
