delete from eg_appconfig_values where key_id in(select id from eg_appconfig where key_name  in('IS_ASSET_CATEGORYCODE_AUTOGENERATED','ASSET_ACCOUNT_CODE_PURPOSEID','REVALUATION_RESERVE_ACCOUNT_PURPOSEID','DEPRECIATION_EXPENSE_ACCOUNT_PURPOSEID','ACCUMULATED_DEPRECIATION_PURPOSEID','IS_ASSET_CODE_AUTOGENERATED'));