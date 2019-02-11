INSERT INTO eg_roleaction (actionid, roleid)
    SELECT (select id from eg_action where name='GetAllBankName'), (select id from eg_role  where name ='Collection Operator')
WHERE
    NOT EXISTS (select actionid,roleid from eg_roleaction where actionid in (select id from eg_action where name='GetAllBankName') and roleid in
  (select id from eg_role  where name ='Collection Operator'));