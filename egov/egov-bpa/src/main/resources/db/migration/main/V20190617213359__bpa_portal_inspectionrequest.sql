Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby,moduleorder,serviceorder) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'INITIATEINSPECTION',15,0,'/bpa/inspection/citizen/create','true','Inspection Request','true','true','/bpa/inspection/citizen/create',
1,now(),now(),1,2,(select max(serviceorder)+1 from EGP_PORTALSERVICE where moduleorder=2));


