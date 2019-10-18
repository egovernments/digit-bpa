update egf_budget set financialyearid=(select id from financialyear where financialyear = '2002-03') where financialyearid=(select id from financialyear where financialyear = '2016-17');

update egf_budget set financialyearid=(select id from financialyear where financialyear = '2016-17') where financialyearid=(select id from financialyear where financialyear = '2002-03');