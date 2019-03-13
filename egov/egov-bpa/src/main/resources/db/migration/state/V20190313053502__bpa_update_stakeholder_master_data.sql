
update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Architect') where stakeholdertype=0;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Building Designer - A') where stakeholdertype=1;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Building Designer - B') where stakeholdertype=2;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Engineer - A') where stakeholdertype=3;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Engineer - B') where stakeholdertype=4;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Town Planner - A') where stakeholdertype=5;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Town Planner - B') where stakeholdertype=6;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Supervisor - A') where stakeholdertype=7;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Supervisor - B') where stakeholdertype=8;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Supervisor Senior') where stakeholdertype=9;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Chartered Architect') where stakeholdertype=10;

update egbpa_mstr_stakeholder  set stakeholdertype =(select id from EGBPA_MSTR_STAKEHOLDERTYPE where name='Chartered Engineer') where stakeholdertype=11;
