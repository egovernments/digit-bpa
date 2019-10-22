INSERT INTO eg_roleaction (roleid,actionid)
    SELECT (select id from eg_role where name='CITIZEN'),(select id from eg_action where name = 'Update bpa aplication details form submit by citizen' and contextroot = 'bpa')
WHERE
    NOT EXISTS (select roleid,actionid from eg_roleaction where roleid in (select id from eg_role where name='CITIZEN') and actionid in
 (select id from eg_action where name = 'Update bpa aplication details form submit by citizen' and contextroot = 'bpa'));