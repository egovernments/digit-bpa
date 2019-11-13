package org.egov.bpa.service.noc;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.springframework.stereotype.Service;
@Service
public class NMAService extends NocService {

	@Override
	public Boolean push(BpaApplication application) {
			return true;
	}

	@Override
	public Boolean checkStatus(BpaApplication application) {
		return true;
	}

	@Override
	public Boolean updateStatusAndNocDoc(BpaApplication application) {
		return true;
	}

}
