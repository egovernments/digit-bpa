update eg_action set enabled=false where name='Personal Register report';

alter table egbpa_application add column mailPwdRequired boolean default false;