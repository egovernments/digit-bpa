----------------------------rejection notice roleaction---------------------------------
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'generate bpa rejection notice','/application/rejectionnotice',
null,(select id from eg_module where name='BPA Transanctions'),29,'generate bpa rejection notice','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='generate bpa rejection notice'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='generate bpa rejection notice'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='generate bpa rejection notice'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='generate bpa rejection notice'));

-------------------------add additional column conditiontype and update type--------------------

alter table egbpa_mstr_permit_conditions add column conditiontype character varying(50);

update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC01';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC02';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC03';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC04';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC05';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC06';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC07';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC08';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC09';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC10';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC11';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC12';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC13';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC14';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC15';
update egbpa_mstr_permit_conditions set conditiontype='PermitCondition' where code='PC16';

----------------reject applications predefined reasons------------------------------

insert into egbpa_mstr_permit_conditions (id, code, description, conditiontype, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'RR01', 'The work or use of the site for the work or particulars comprised in the site plan, ground plan, elevation, sections, or specifications would contravene provisions of law or order, rule, declaration or bye law made under such law.', 'Rejection', 1, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, conditiontype, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'RR02', 'The application does not contain the particulars or is not prepared in the manner required by the rules or bye law made under the Act.', 'Rejection', 2, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, conditiontype, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'RR03', 'The documents required to be signed by a registered Architect, Engineer, Town planner or Supervisor or the owner/applicant as required under the Act or the rules or bye laws made under the Act has not been signed by such Architect, Engineer, Town planner or Supervisor or the owner/applicant.', 'Rejection', 3, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, conditiontype, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'RR04', 'Information or document or certificate required by the Secretary under the rules or bye laws made under the Act has not been duly furnished.', 'Rejection', 4, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, conditiontype, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'RR05', 'The owner of the land has not laid down and made street or streets or road or roads giving access to the site or sites connecting with an existing public or private street while utilizing, selling or leasing out or otherwise disposing of the land or any portion or portions of the same site for construction of building,provided that the site abuts on any existing public or private street no such street or road shall be laid down or made.', 'Rejection', 5, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, conditiontype, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'RR06', 'The proposed building would be an encroachment upon a land belonging to the Government or the Municipality.', 'Rejection', 6, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, conditiontype, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'RR07', 'The land is under acquisition proceedings.', 'Rejection', 7, 0, 1, now(), 1, now());


alter table EGBPA_APPLICATION add column additionalRejectionReasons character varying(2048);

----------------modify buttons in workflow------------------------

update eg_wf_matrix set validactions ='CANCEL APPLICATION' where currentstate='Rejected' and nextaction='Application is Rejected by Superior Officer' and objecttype ='BpaApplication';

update eg_wf_matrix set validactions ='Forward' where currentstate='Registered' and nextaction='Document verification pending' and objecttype ='BpaApplication';