update eg_action set enabled=false where name='BpaNewApplication' and contextroot ='bpa';

alter table egbpa_application ADD COLUMN isLPRequestInitiated boolean default false;