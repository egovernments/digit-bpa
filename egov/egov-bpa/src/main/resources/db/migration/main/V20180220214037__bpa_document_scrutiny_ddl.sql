alter table egbpa_slotapplication DROP COLUMN isRescheduledByCitizen;
alter table egbpa_slotapplication DROP COLUMN isRescheduledByEmployee;

alter table egbpa_application ADD COLUMN isRescheduledByCitizen boolean default false;
alter table egbpa_application ADD COLUMN isRescheduledByEmployee boolean default false;