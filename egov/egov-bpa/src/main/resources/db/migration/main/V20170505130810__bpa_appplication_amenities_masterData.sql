insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval,
 isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired,
  isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'AM20',
'Amenities',false,0,1,now(),null,now(),false,false,true,false,false,false,'AMN',null,null,false); 


insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'10','Well',true,
0,1,now(),null,now(),false,false,false,false,false,false,'WEL',null,null,true);



insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'11','Compound Wall',true,
0,1,now(),null,now(),false,false,false,false,false,false,'CMPWELL',null,null,true);



insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'12','Shutter or Door Conversion/Erection under rule 100 or 101',true,
0,1,now(),null,now(),false,false,false,false,false,false,'STR',null,null,true);


insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'13','Roof Conversion under rule 100 or 101',true,
0,1,now(),null,now(),false,false,false,false,false,false,'RFC',null,null,true);


insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'14','Tower Construction',true,
0,1,now(),null,now(),false,false,false,false,false,false,'TC',null,null,true);



insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'15','Pole Structures',true,
0,1,now(),null,now(),false,false,false,false,false,false,'PLS',null,null,true);