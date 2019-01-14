Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='loadElectionWardBoundaryByRevenueWard'));
