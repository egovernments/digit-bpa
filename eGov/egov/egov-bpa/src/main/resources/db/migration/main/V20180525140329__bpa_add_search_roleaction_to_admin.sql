INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Search BPA Application'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Search BPA Application') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='View BPA Application Details'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='View BPA Application Details') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Download BPA Documents'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Download BPA Documents') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='File Download'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='File Download') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='show bpa inspection details'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='show bpa inspection details') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='generate demand notice for bpa'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='generate demand notice for bpa') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='generate building permit order'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='generate building permit order') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='generate bpa rejection notice'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='generate bpa rejection notice') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='get active child boundaries of parent boundary'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='get active child boundaries of parent boundary') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='loadElectionWardBoundaryByRevenueWard'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='loadElectionWardBoundaryByRevenueWard') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='loadLocalityBoundaryByWard'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='loadLocalityBoundaryByWard') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));


INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Search BPA Application'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Search BPA Application') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='View BPA Application Details'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='View BPA Application Details') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Download BPA Documents'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Download BPA Documents') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='show bpa inspection details'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='show bpa inspection details') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='generate demand notice for bpa'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='generate demand notice for bpa') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='generate building permit order'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='generate building permit order') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='generate bpa rejection notice'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='generate bpa rejection notice') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='File Download'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='File Download') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='get active child boundaries of parent boundary'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='get active child boundaries of parent boundary') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='loadElectionWardBoundaryByRevenueWard'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='loadElectionWardBoundaryByRevenueWard') and roleid in
  (select id from eg_role  where name ='SYSTEM'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='loadLocalityBoundaryByWard'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='loadLocalityBoundaryByWard') and roleid in
  (select id from eg_role  where name ='SYSTEM'));
