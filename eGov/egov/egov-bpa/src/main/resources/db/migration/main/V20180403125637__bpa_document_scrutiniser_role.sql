delete from eg_roleaction where roleid = (select id from eg_role where name='BPA Approver') and actionid = (select id from eg_action where name='Document Scrutiny for bpa applications');

INSERT INTO eg_role(id, name, description, createddate, createdby, lastmodifiedby, lastmodifieddate, version) VALUES (nextval('seq_eg_role'), 'Document Scrutiniser', 'Document Scrutiniser', now(), 1, 1, now(), 0);

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Document Scrutiny for bpa applications'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='loadElectionWardBoundaryByRevenueWard'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='capturedocumentscrutinydetails'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='getDesignationByDeptAndAreaInSqmtr'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='get positions by department and designation and boundary'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='reschedule appointment for document scrutiny'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='get slotdetails by date and zone'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='get active child boundaries of parent boundary'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='updateBpaApplication'));