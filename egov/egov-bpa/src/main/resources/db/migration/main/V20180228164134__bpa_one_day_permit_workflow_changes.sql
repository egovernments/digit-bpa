update eg_wf_matrix set nextstatus  ='Scheduled For Document Scrutiny', nextaction  ='Document verification pending', nextstate ='Scheduled For Document Scrutiny' where currentstate='NEW' and additionalrule='CREATEBPAAPPLICATION-ONEDAYPERMIT' and objecttype ='BpaApplication';

update eg_wf_matrix set currentstate ='Scheduled For Document Scrutiny', validactions='Forward,Initiate Rejection', pendingactions='Document verification pending' where nextstate='Document Verified' and additionalrule='CREATEBPAAPPLICATION-ONEDAYPERMIT' and objecttype ='BpaApplication';

update eg_wf_matrix set nextaction  ='Application is rejected by approver' where currentstate='Rejected' and additionalrule='CREATEBPAAPPLICATION-ONEDAYPERMIT' and objecttype ='BpaApplication';

update eg_wf_matrix set nextaction  ='Generation of rejection notice pending' where currentstate='Rejection initiated by clerk' and additionalrule='CREATEBPAAPPLICATION-ONEDAYPERMIT' and objecttype ='BpaApplication';