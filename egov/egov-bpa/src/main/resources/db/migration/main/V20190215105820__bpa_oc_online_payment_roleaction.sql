INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Redirect to collect fees for occupancy certificate application'), (select id from eg_role  where name ='CITIZEN')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Redirect to collect fees for occupancy certificate application') and roleid in
  (select id from eg_role  where name ='CITIZEN'));

INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Redirect to collect fees for occupancy certificate application'), (select id from eg_role  where name ='BUSINESS')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Redirect to collect fees for occupancy certificate application') and roleid in
  (select id from eg_role  where name ='BUSINESS'));