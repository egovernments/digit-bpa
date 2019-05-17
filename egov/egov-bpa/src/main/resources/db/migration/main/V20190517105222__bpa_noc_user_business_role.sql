update state.eg_user set tenantid='state' where username in ('environmentnoc','nmanoc','airportnoc','firenoc');

INSERT into state.eg_userrole values ((select id from eg_role  where name  = 'BUSINESS'),(select id from state.eg_user where username ='firenoc') );
INSERT into state.eg_userrole values ((select id from eg_role  where name  = 'BUSINESS'),(select id from state.eg_user where username ='airportnoc') );
INSERT into state.eg_userrole values ((select id from eg_role  where name  = 'BUSINESS'),(select id from state.eg_user where username ='nmanoc') );
INSERT into state.eg_userrole values ((select id from eg_role  where name  = 'BUSINESS'),(select id from state.eg_user where username ='environmentnoc') );
