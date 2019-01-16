delete from eg_wf_matrix where currentstate  ='Permit Fee paid' and additionalrule='CREATEBPAAPPLICATION' and objecttype ='BpaApplication';

update eg_wf_matrix set nextaction ='Forwarded to update permit conditions', nextstate='Permit Fee Collected' where pendingactions='Permit Fee Collection Pending' and additionalrule='CREATEBPAAPPLICATION' and objecttype ='BpaApplication';

update eg_wf_matrix set nextaction  ='Forwarded to generate permit order', currentstate ='Permit Fee Collected' where nextstate='Record Approved' and additionalrule='CREATEBPAAPPLICATION' and objecttype ='BpaApplication';

update eg_wf_matrix set validactions ='Generate Permit Order', nextstatus ='Order Issued to Applicant', nextaction  ='END', nextstate='END' where currentstate='Record Approved' and additionalrule='CREATEBPAAPPLICATION' and objecttype ='BpaApplication';