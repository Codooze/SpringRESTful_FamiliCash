# Our API Decisions
Phew, that was a lot of decisions! To summarize, we decided:

PUT won’t support creating a Cash Card.
Our new Update endpoint (which we'll build in the upcoming Lab):
will use the PUT verb.
accepts a Cash Card, and replaces the existing Cash Card with it.
on success, will return 204 NO CONTENT with an empty body.
will return a 404 NOT FOUND for an unauthorized update, as well as attempts to update nonexistent IDs.

### POST, PUT, PATCH and CRUD Operations - Summary
<table><thead><tr><th>HTTP Method</th><th>Operation</th><th>Definition of Resource URI</th><th>What does it do?</th><th>Response Status Code</th><th>Response Body</th></tr></thead><tbody><tr><td><strong><code>POST</code></strong></td><td><strong>Create</strong></td><td><strong>Server generates and returns the URI</strong></td><td><strong>Creates a sub-resource ("under" or "within" the passed URI)</strong></td><td><strong><code>201 CREATED</code></strong></td><td><strong>The created resource</strong></td></tr><tr><td><code>PUT</code></td><td>Create</td><td>Client supplies the URI</td><td>Creates a resource (at the Request URI)</td><td><code>201 CREATED</code></td><td>The created resource</td></tr><tr><td><strong><code>PUT</code></strong></td><td><strong>Update</strong></td><td><strong>Client supplies the URI</strong></td><td><strong>Replaces the resource: The entire record is replaced by the object in the Request</strong></td><td><strong><code>204 NO CONTENT</code></strong></td><td><strong>(empty)</strong></td></tr><tr><td><code>PATCH</code></td><td>Update</td><td>Client supplies the URI</td><td>Partial Update: modify only fields included in the request on the existing record</td><td><code>200 OK</code></td><td>The updated resource</td></tr></tbody></table>