ALTER TABLE egbpa_ownership_transfer add column approverPosition bigint;
ALTER TABLE egbpa_ownership_transfer  add column approverUser bigint;

ALTER TABLE egbpa_ownership_transfer add constraint fk_ownership_transfer_appvr_pos foreign key (approverPosition) references eg_position(id);

ALTER TABLE egbpa_ownership_transfer add constraint fk_ownership_transfer_appvr_user foreign key (approverUser) references state.eg_user(id);