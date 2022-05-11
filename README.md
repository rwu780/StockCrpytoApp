# StockCrpytoApp


Notes: The current API usage limit is 5 calls/minutes

If you see no data/data unavailable/error fetching data, most likely it exceed the limit.




## Reference API

Reference API
https://www.alphavantage.co/

BASE_URL : https://www.alphavantage.co/
End_point : query

### Retrieve Company Summary

API Parameters
❚ Required: function : function=OVERVIEW

❚ Required: symbol : symbol=IBM.

❚ Required: apikey

Sample: https://www.alphavantage.co/query?function=OVERVIEW&symbol=IBM&apikey=demo

### Retrieve Quote

API Parameters

❚ Required: function -> function=GLOBAL_QUOTE

❚ Required: symbol -> symbol=IBM

❚ Optional: datatype -> datatype=json

❚ Required: apikey

Samples:
- https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=demo
- https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=300135.SHZ&apikey=demo

### Symbol Search

API Parameters

❚ Required: function -> function=SYMBOL_SEARCH

❚ Required: keywords -> keywords=IBM

❚ Optional: datatype -> datatype=json

❚ Required: apikey

Samples
- https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=tesco&apikey=demo
- https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords=tencent&apikey=demo




### Retrieve 

## Reference / Attribution

Header Icon
<a href="https://www.vecteezy.com/free-vector/stock-icon">Stock Icon Vectors by Vecteezy</a>
