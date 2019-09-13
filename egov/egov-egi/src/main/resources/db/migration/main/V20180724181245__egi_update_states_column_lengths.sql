DROP VIEW IF EXISTS egpgr_mv_drilldown_report_view;

ALTER TABLE eg_wf_states ALTER COLUMN sendername TYPE VARCHAR(200);
ALTER TABLE eg_wf_states ALTER COLUMN "type" TYPE VARCHAR(50);
ALTER TABLE eg_wf_states ALTER COLUMN "value" TYPE VARCHAR(100);
