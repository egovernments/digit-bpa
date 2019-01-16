update eg_wf_matrix set nextdesignation='Assistant Engineer'
where nextaction='Forwarded to Approval' and fromqty=0 and toqty =299 and objecttype='BpaApplication';