
Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
values (nextval('SEQ_EGBPA_STATUS'),'NOC','Permit application rejected',
now(),'Permit Application Rejected',true,0,1,now());