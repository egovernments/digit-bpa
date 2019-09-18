Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) values (nextval('SEQ_EG_ACTION'),'Fetch services count by user and module on service group change','/rest/fetch/services/count/by-servicegroup',null,(select id from eg_module where name='Portal Masters'),0,'Fetch services count by user and module on service group change','false','portal',0,1,now(),1,now(),(select id from eg_module where name='Citizen Portal' and parentmodule is NULL));


INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'CITIZEN') ,(select id FROM eg_action  WHERE name = 'Fetch services count by user and module on service group change'));

INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'BUSINESS') ,(select id FROM eg_action  WHERE name = 'Fetch services count by user and module on service group change'));