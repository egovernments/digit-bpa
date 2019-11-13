package org.egov.bpa.service.noc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.NoObjectionCertificate;
import org.egov.bpa.transaction.entity.NocList;
import org.egov.bpa.transaction.entity.enums.NocStatus;

public abstract class NocService {
	
	/**
	 * 
	 * @param application
	 * @return
	 * Push the data to concerned Noc department and add NoObjectionCertificate into application.noclist table
	 */
	public abstract Boolean push(BpaApplication application);
	
	/**
	 * 
	 * @param application
	 * @return
	 * this api should only check the status and return
	 */
	
	public abstract Boolean checkStatus(BpaApplication application);
	/**
	 * 
	 * @param application
	 * @return
	 * API should take care of returning current status by checking third party and update noc document
	 * if available 
	 */
	public abstract Boolean updateStatusAndNocDoc(BpaApplication application);
	
	/**
	 * 
	 * @param application
	 * @return
	 * this api will decide based on given condition noc is mandatory or not 
	 */
	public  Boolean isRequired(BpaApplication application){
		return true;
	}
	
	
	public Boolean updateDeemedApprovals(BpaApplication application) {
		Boolean overAllStatus = true;
		if (application != null) {
			NocList nocList = application.getNocList();
			for (NoObjectionCertificate noc : nocList.getNocList()) {
			 		if (!noc.getStatus().equals(NocStatus.APPROVED) || noc.getStatus().equals(NocStatus.REJECTED)) {
						 Calendar cal=Calendar.getInstance();
						 cal.setTime(noc.getInitiationDate());
						 cal.add(Calendar.DATE, noc.getNocConfig().getDaysForDeemedApproval().intValue());
						 if(cal.getTime().after(new Date()))
						 noc.setEndDate(new Date());
						 noc.setStatus(NocStatus.APPROVED);
						 noc.setIsDeemedApproved(true);
						 

					}
				}
			

		}
		return overAllStatus;
	}
}

	  