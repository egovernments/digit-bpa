
update egbpa_mstr_applicationtype  set name='One Day Permit' where name='One day permit';

alter table egbpa_mstr_applicationtype add column slotrequired boolean DEFAULT false;

update egbpa_mstr_applicationtype set slotrequired=true where name in ('One Day Permit','Regular');


insert into egbpa_mstr_applicationtype(id,name,description,enabled,createdby,createddate,lastmodifieddate,lastmodifiedby,version,slotrequired) values (nextval('seq_egbpa_mstr_applicationtype'),'Occupancy Certificate','Occupancy Certificate',false,1,now(),now(),1,0,true);