alter table egbpa_application add column totalbuiltuparea double precision;

update egbpa_mstr_chklistdetail set ismandatory  = true where code ='103';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='203';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='303';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='403';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='503';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='603';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='703';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='803';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='903';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='1010';
update egbpa_mstr_chklistdetail set ismandatory  = true where code ='2010';