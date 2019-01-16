alter table EGBPA_DOCUMENTSCRUTINY alter column neighoutOwnerDtlSubmitted type character varying(30);
alter table EGBPA_DOCUMENTSCRUTINY alter column whetherAllDocAttached type character varying(30);
alter table EGBPA_DOCUMENTSCRUTINY alter column whetherAllPageOfDocAttached type character varying(30);
alter table EGBPA_DOCUMENTSCRUTINY alter column whetherDocumentMatch type character varying(30);

update EGBPA_DOCUMENTSCRUTINY set neighoutOwnerDtlSubmitted='YES' where neighoutOwnerDtlSubmitted='true';
update EGBPA_DOCUMENTSCRUTINY set neighoutOwnerDtlSubmitted='NO' where neighoutOwnerDtlSubmitted='false';

update EGBPA_DOCUMENTSCRUTINY set whetherAllDocAttached='YES' where whetherAllDocAttached='true';
update EGBPA_DOCUMENTSCRUTINY set whetherAllDocAttached='NO' where whetherAllDocAttached='false';

update EGBPA_DOCUMENTSCRUTINY set whetherAllPageOfDocAttached='YES' where whetherAllPageOfDocAttached='true';
update EGBPA_DOCUMENTSCRUTINY set whetherAllPageOfDocAttached='NO' where whetherAllPageOfDocAttached='false';

update EGBPA_DOCUMENTSCRUTINY set whetherDocumentMatch='YES' where whetherDocumentMatch='true';
update EGBPA_DOCUMENTSCRUTINY set whetherDocumentMatch='NO' where whetherDocumentMatch='false';


delete from eg_roleaction where roleid = (select id from eg_role where name='BPA Report Viewer') and actionid = (select id from eg_action where name='Building Plan Application register report');

delete from eg_roleaction where roleid = (select id from eg_role where name='BPA Approver') and actionid = (select id from eg_action where name='Building Plan Application register report');

INSERT INTO eg_role(id, name, description, createddate, createdby, lastmodifiedby, lastmodifieddate, version) VALUES (nextval('seq_eg_role'), 'BPA BAR report viewer', 'Can view BAR report details', now(), 1, 1, now(), 0);

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA BAR report viewer'), (select id from eg_action where name='Building Plan Application register report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA BAR report viewer'), (select id from eg_action where name='loadElectionWardBoundaryByRevenueWard'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA BAR report viewer'), (select id from eg_action where name='get active child boundaries of parent boundary'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA BAR report viewer'), (select id from eg_action where name='loadLocalityBoundaryByWard'));
