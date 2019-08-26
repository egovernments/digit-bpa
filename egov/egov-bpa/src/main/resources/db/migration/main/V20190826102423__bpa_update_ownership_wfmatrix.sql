


update eg_wf_matrix set  nextstatus='Registered' where objecttype='OwnershipTransfer' and currentstate='Initiated for ownership transfer';

update eg_wf_matrix set currentstatus  = 'Registered', nextstatus='Checked' where objecttype='OwnershipTransfer' and currentstate='Section clerk approved';
update eg_wf_matrix set currentstatus  = 'Checked', nextstatus='Validated' where objecttype='OwnershipTransfer' and currentstate='Town plan overseer approved';
update eg_wf_matrix set currentstatus  = 'Validated', nextstatus='Approved' where objecttype='OwnershipTransfer' and currentstate='Assistant Engineer approved';

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'OWNERSHIP','Submitted',now(),'Submitted',true,0,1,now());



