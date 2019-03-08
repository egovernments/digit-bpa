INSERT into state.eg_userrole values((select id from eg_role  where name  = 'SYSTEM'),(select id from state.eg_user where username ='egovernments'));
------------------END---------------------