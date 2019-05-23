Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
values (nextval('SEQ_EGBPA_STATUS'),'NOC','When NOC application has not been attended within the SLA by the NOC department',
now(),'NOC_DEEMED_APPROVED',true,0,1,now());