insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Architect','Architect',true,1,now(),now(),1,0  where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Architect');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Building Designer - A','Building Designer - A',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Building Designer - A');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Building Designer - B','Building Designer - B',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Building Designer - B');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Engineer - A','Engineer - A',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Engineer - A');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Engineer - B','Engineer - B',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Engineer - B');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Town Planner - A','Town Planner - A',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Town Planner - A');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Town Planner - B','Town Planner - B',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Town Planner - B');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Supervisor - A','Supervisor - A',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Supervisor - A');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Supervisor - B','Supervisor - B',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Supervisor - B');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Supervisor Senior','Supervisor Senior',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Supervisor Senior');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Chartered Architect','Chartered Architect',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Chartered Architect');

insert into state.egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version)  select nextval('state.seq_egbpa_mstr_stakeholderType'),'Chartered Engineer','Chartered Engineer',true,1,now(),now(),1,0 where not exists(SELECT * FROM state.egbpa_mstr_stakeholderType WHERE name='Chartered Engineer');