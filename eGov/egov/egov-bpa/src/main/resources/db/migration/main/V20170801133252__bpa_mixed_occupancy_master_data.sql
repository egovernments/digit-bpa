alter table egbpa_sitedetail alter column extentofland type double precision USING extentofland::double precision;

insert into EGBPA_MSTR_OCCUPANCY (id,code,description,isactive,version,createdBy,createdDate,lastModifiedBy,lastModifiedDate,
   occupantDoors,noofOccupancy,occupantLoad,permissibleareainpercentage,numoftimesareapermissible,numoftimesareapermwitaddnlfee,ordernumber) values(nextval('SEQ_EGBPA_MSTR_OCCUPANCY'),
   '15','Mixed',true,0,1,now(),1,now(),null,null,null,null,null,null,14);