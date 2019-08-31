ALTER TABLE egbpa_application add column approverPosition bigint;
ALTER TABLE egbpa_application add column approverUser bigint;

ALTER TABLE egbpa_application add constraint fk_application_appvr_pos foreign key (approverPosition) references eg_position(id);
ALTER TABLE egbpa_application add constraint fk_application_appvr_user foreign key (approverUser) references state.eg_user(id);

ALTER TABLE egbpa_occupancy_certificate add column approverPosition bigint;
ALTER TABLE egbpa_occupancy_certificate add column approverUser bigint;

ALTER TABLE egbpa_occupancy_certificate add constraint fk_oc_appvr_pos foreign key (approverPosition) references eg_position (id);
ALTER TABLE egbpa_occupancy_certificate add constraint fk_oc_appvr_user foreign key (approverUser) references state.eg_user (id); 