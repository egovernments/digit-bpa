update egbpa_mstr_chklistdetail set description = 'Whether all habitable rooms are abutting an exterior or interior open space?'
where code = 'PLSCTY05' and checklist = (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY');


update egbpa_mstr_chklistdetail set description = 'Whether Front yard, Side yard 1, Side yard 2, Rear yard and plot boundary polygons are accurately marked, and conditions with regards to overhangs stipulated in rules 24-10, 24-11, 62-2 are complied with?'
where code = 'PLSCTY06' and checklist = (select id from egbpa_mstr_checklist where checklisttype='PLANSCRUTINY');;