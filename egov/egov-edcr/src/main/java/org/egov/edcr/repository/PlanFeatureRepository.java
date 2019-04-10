/*
 * eGov  SmartCity eGovernance suite aims to improve the internal efficiency,transparency,
 * accountability and the service delivery of the government  organizations.
 *
 *  Copyright (C) <2019>  eGovernments Foundation
 *
 *  The updated version of eGov suite of products as by eGovernments Foundation
 *  is available at http://www.egovernments.org
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see http://www.gnu.org/licenses/ or
 *  http://www.gnu.org/licenses/gpl.html .
 *
 *  In addition to the terms of the GPL license to be adhered to in using this
 *  program, the following additional terms are to be complied with:
 *
 *      1) All versions of this program, verbatim or modified must carry this
 *         Legal Notice.
 *      Further, all user interfaces, including but not limited to citizen facing interfaces,
 *         Urban Local Bodies interfaces, dashboards, mobile applications, of the program and any
 *         derived works should carry eGovernments Foundation logo on the top right corner.
 *
 *      For the logo, please refer http://egovernments.org/html/logo/egov_logo.png.
 *      For any further queries on attribution, including queries on brand guidelines,
 *         please contact contact@egovernments.org
 *
 *      2) Any misrepresentation of the origin of the material is prohibited. It
 *         is required that all modified versions of this material be marked in
 *         reasonable ways as different from the original version.
 *
 *      3) This license does not grant any rights to any user of the program
 *         with regards to rights under trademark law for use of the trade names
 *         or trademarks of eGovernments Foundation.
 *
 *  In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.edcr.repository;

import java.util.ArrayList;
import java.util.List;

import org.egov.common.entity.edcr.PlanFeature;
import org.egov.edcr.feature.AdditionalFeature;
import org.egov.edcr.feature.BlockDistancesService;
import org.egov.edcr.feature.Coverage;
import org.egov.edcr.feature.Far;
import org.egov.edcr.feature.GovtBuildingDistance;
import org.egov.edcr.feature.MonumentDistance;
import org.egov.edcr.feature.OverheadElectricalLineService;
import org.egov.edcr.feature.PlanInfoFeature;
import org.egov.edcr.feature.SetBackService;
import org.springframework.stereotype.Service;

@Service
public class PlanFeatureRepository {

    public List<PlanFeature> getFeatures() {
        ArrayList<PlanFeature> features = new ArrayList<>();
        PlanFeature pf = new PlanFeature(PlanInfoFeature.class);
        features.add(pf);
        pf = new PlanFeature(Far.class);
        features.add(pf);
        pf = new PlanFeature(Coverage.class);
        features.add(pf);
        pf = new PlanFeature(SetBackService.class);
        features.add(pf);
        pf = new PlanFeature(MonumentDistance.class);
        features.add(pf);
		pf = new PlanFeature(BlockDistancesService.class);
		features.add(pf);
		pf = new PlanFeature(GovtBuildingDistance.class);
        features.add(pf);
		/*
		 * pf = new PlanFeature(Sanitation.class); features.add(pf);
		 */
		/*
		 * pf = new
		 * PlanFeature(MeanOfAccess.class); features.add(pf);
		 */
		/*
		 * pf = new PlanFeature(BuildingHeight.class); features.add(pf);
		 */
		/*
		 * pf = new PlanFeature(DistanceToRoad.class); features.add(pf); pf = new
		 * PlanFeature(Parking.class); features.add(pf); pf = new
		 * PlanFeature(ExitWidth.class); features.add(pf); pf = new
		 * PlanFeature(WasteDisposal.class); features.add(pf); pf = new
		 * PlanFeature(WaterTreatmentPlant.class); features.add(pf); pf = new
		 * PlanFeature(RecycleWasteWater.class); features.add(pf); pf = new
		 * PlanFeature(Well.class); features.add(pf); pf = new PlanFeature(Solar.class);
		 * features.add(pf); pf = new PlanFeature(RainWaterHarvesting.class);
		 * features.add(pf); pf = new PlanFeature(BiometricWasteManagement.class);
		 * features.add(pf); pf = new PlanFeature(SolidLiquidWasteTreatment.class);
		 * features.add(pf);
		 */
        
		pf = new PlanFeature(OverheadElectricalLineService.class);
		features.add(pf);
		/*
		 * pf = new PlanFeature(MezzanineFloorService.class); features.add(pf);
		 * 
		 * pf = new PlanFeature(RecreationalSpace.class); features.add(pf); pf = new
		 * PlanFeature(TravelDistanceToExit.class); features.add(pf);
		 */
        pf = new PlanFeature(AdditionalFeature.class); features.add(pf);
        /*
         * pf = new PlanFeature(OverHangs.class); features.add(pf); pf = new PlanFeature(FireStairService.class);
         * features.add(pf); pf = new PlanFeature(GeneralStairService.class); features.add(pf); pf = new
         * PlanFeature(SpiralStairService.class); features.add(pf); pf = new PlanFeature(RampService.class); features.add(pf); pf
         * = new PlanFeature(LiftService.class); features.add(pf);
         */
        
		/*
		 * pf = new PlanFeature(HeightOfRoom.class); features.add(pf);
		 */
        /*pf = new PlanFeature(AdditionalFeature.class); features.add(pf);
         * pf = new PlanFeature(AccessoryBuildingService.class); features.add(pf); pf = new PlanFeature(DepthCutting.class);
         * features.add(pf);
         */

		/*
		 * pf = new PlanFeature(PetrolFillingStation.class); features.add(pf);
		 */
        /*
         * pf = new PlanFeature(CommonFeature.class); features.add(pf); pf = new PlanFeature(DxfToPdfConverter.class);
         * features.add(pf); pf = new PlanFeature(OpenStairs.class); features.add(pf);
         */
        
		/*
		 * pf = new PlanFeature(DxfToPdfConverter.class); features.add(pf);
		 */
        return features;
    }

}
