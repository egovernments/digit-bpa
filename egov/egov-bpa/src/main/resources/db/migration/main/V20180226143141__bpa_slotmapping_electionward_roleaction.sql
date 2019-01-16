Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='loadElectionWardBoundaryByRevenueWard'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='loadElectionWardBoundaryByRevenueWard'));
