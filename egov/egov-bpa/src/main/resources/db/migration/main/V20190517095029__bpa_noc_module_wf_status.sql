
INSERT INTO eg_module(id, name, enabled, contextroot, parentmodule, displayname, ordernumber)
VALUES (nextval('SEQ_EG_MODULE'), 'NOC', false, 'noc', null, 'No Objection Certificate',(select max(ordernumber)+1 
from eg_module where parentmodule is null));


Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'NOC','When BPA created a new application and push to respective NOC department user',
 now(),'NOC_INITIATED',true,0,1,now());

 Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'NOC','When respective NOC department user approves',
 now(),'NOC_APPROVED',true,0,1,now());

 Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'NOC','When respective NOC department user rejects',
 now(),'NOC_REJECTED',true,0,1,now());


