alter table EGBPA_MSTR_OCCUPANCY add column permissibleAreaInPercentage   decimal  ;
alter table EGBPA_MSTR_OCCUPANCY add column NumOfTimesAreaPermissible   decimal  ;
alter table EGBPA_MSTR_OCCUPANCY add column NumOfTimesAreaPermWitAddnlFee   decimal  ;


update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=65  ,NumOfTimesAreaPermissible=3, NumOfTimesAreaPermWitAddnlFee=4 where code='01';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=65  ,NumOfTimesAreaPermissible=2.5, NumOfTimesAreaPermWitAddnlFee=4 where code='02';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=35  ,NumOfTimesAreaPermissible=2.5, NumOfTimesAreaPermWitAddnlFee=3 where code='03';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=40  ,NumOfTimesAreaPermissible=2, NumOfTimesAreaPermWitAddnlFee=3 where code='04';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=40  ,NumOfTimesAreaPermissible=1.5, NumOfTimesAreaPermWitAddnlFee=2.5 where code='05';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=40  ,NumOfTimesAreaPermissible=2, NumOfTimesAreaPermWitAddnlFee=3 where code='06';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=40  ,NumOfTimesAreaPermissible=1.5, NumOfTimesAreaPermWitAddnlFee=0 where code='08';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=60  ,NumOfTimesAreaPermissible=2.5, NumOfTimesAreaPermWitAddnlFee=3 where code='09';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=60  ,NumOfTimesAreaPermissible=2.5, NumOfTimesAreaPermWitAddnlFee=3 where code='10';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=30  ,NumOfTimesAreaPermissible=1, NumOfTimesAreaPermWitAddnlFee=0 where code='11';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=25  ,NumOfTimesAreaPermissible=0.7, NumOfTimesAreaPermWitAddnlFee=0 where code='12';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=65  ,NumOfTimesAreaPermissible=2.5, NumOfTimesAreaPermWitAddnlFee=4 where code='07';
update EGBPA_MSTR_OCCUPANCY set permissibleAreaInPercentage=65  ,NumOfTimesAreaPermissible=1, NumOfTimesAreaPermWitAddnlFee=0 where code='14';

