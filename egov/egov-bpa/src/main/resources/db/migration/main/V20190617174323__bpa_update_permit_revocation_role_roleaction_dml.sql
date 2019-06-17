INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Generate revocation order for the permit'), (select id from eg_role  where name ='SYSTEM')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Generate revocation order for the permit') and roleid in
  (select id from eg_role  where name ='SYSTEM'));


INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='Generate revocation order for the permit'), (select id from eg_role  where name ='Bpa Administrator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='Generate revocation order for the permit') and roleid in
  (select id from eg_role  where name ='Bpa Administrator'));