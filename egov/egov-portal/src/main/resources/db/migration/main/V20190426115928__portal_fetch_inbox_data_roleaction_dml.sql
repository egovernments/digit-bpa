Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) values (nextval('SEQ_EG_ACTION'),'FetchServicesApplied','/rest/fetch/servicesapplied',null,(select id from eg_module where name='Portal Services'),0,'Fetch total services applied','false','portal',0,1,now(),1,now(),(select id from eg_module where name='Citizen Portal' and parentmodule is NULL));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) values (nextval('SEQ_EG_ACTION'),'FetchServicesCompleted','/rest/fetch/servicescompleted',null,(select id from eg_module where name='Portal Services'),0,'Fetch total services completed','false','portal',0,1,now(),1,now(),(select id from eg_module where name='Citizen Portal' and parentmodule is NULL));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) values (nextval('SEQ_EG_ACTION'),'FetchServicesUnderScrutiny','/rest/fetch/servicespending',null,(select id from eg_module where name='Portal Services'),0,'Fetch services under scrutiny','false','portal',0,1,now(),1,now(),(select id from eg_module where name='Citizen Portal' and parentmodule is NULL));



INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'CITIZEN') ,(select id FROM eg_action  WHERE name = 'FetchServicesApplied'));

INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'BUSINESS') ,(select id FROM eg_action  WHERE name = 'FetchServicesApplied'));

INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'CITIZEN') ,(select id FROM eg_action  WHERE name = 'FetchServicesCompleted'));

INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'BUSINESS') ,(select id FROM eg_action  WHERE name = 'FetchServicesCompleted'));

INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'CITIZEN') ,(select id FROM eg_action  WHERE name = 'FetchServicesUnderScrutiny'));

INSERT INTO eg_roleaction (ROLEID, ACTIONID) values ((select id from eg_role where name LIKE 'BUSINESS') ,(select id FROM eg_action  WHERE name = 'FetchServicesUnderScrutiny'));