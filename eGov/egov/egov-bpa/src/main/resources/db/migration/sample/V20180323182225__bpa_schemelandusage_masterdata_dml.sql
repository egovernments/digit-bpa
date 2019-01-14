update egbpa_mstr_scheme set description ='Master Plan for Kozhikode Urban Area 2035' where code ='18';
update egbpa_mstr_schemelandusage  set bpascheme =(select id from egbpa_mstr_scheme where code ='18') where bpascheme =(select id from egbpa_mstr_scheme where code ='19');
update egbpa_mstr_scheme set isactive =false where code ='19';