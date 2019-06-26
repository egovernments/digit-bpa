
alter table egbpa_inspection_application add column isLPRequestInitiated boolean default false;

alter table egbpa_inspection_letter_to_party drop column isLPRequestInitiated ;