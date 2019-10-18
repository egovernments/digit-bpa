truncate table egbpa_sub_usage cascade;

ALTER TABLE egbpa_sub_usage RENAME TO egbpa_builing_usage;

alter table egbpa_builing_usage drop constraint fk_egbpa_sub_usage;
alter table egbpa_builing_usage rename suboccupancy to usage;
alter table egbpa_builing_usage add FOREIGN KEY (usage) REFERENCES egbpa_usage(id);