package org.egov.edcr.repository;

import java.util.ArrayList;
import java.util.List;

import org.egov.edcr.entity.PlanFeature;
import org.egov.edcr.feature.Coverage;
import org.egov.edcr.feature.Far;
import org.egov.edcr.feature.PlanInfoFeature;
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
      /*  pf = new PlanFeature(SetBackService.class);
        features.add(pf);
        pf = new PlanFeature(Sanitation.class);
        features.add(pf);
        pf = new PlanFeature(BlockDistancesService.class);
        features.add(pf);
        pf = new PlanFeature(MeanOfAccess.class);
        features.add(pf);
        pf = new PlanFeature(BuildingHeight.class);
        features.add(pf);
        pf = new PlanFeature(DistanceToRoad.class);
        features.add(pf);
        pf = new PlanFeature(Parking.class);
        features.add(pf);
        pf = new PlanFeature(ExitWidth.class);
        features.add(pf);
        pf = new PlanFeature(WasteDisposal.class);
        features.add(pf);
        pf = new PlanFeature(WaterTreatmentPlant.class);
        features.add(pf);
        pf = new PlanFeature(RecycleWasteWater.class);
        features.add(pf);
        pf = new PlanFeature(Well.class);
        features.add(pf);
        pf = new PlanFeature(Solar.class);
        features.add(pf);
        pf = new PlanFeature(RainWaterHarvesting.class);
        features.add(pf);
        pf = new PlanFeature(BiometricWasteManagement.class);
        features.add(pf);
        pf = new PlanFeature(SolidLiquidWasteTreatment.class);
        features.add(pf);
        pf = new PlanFeature(OverheadElectricalLine.class);
        features.add(pf);
        pf = new PlanFeature(MezzanineFloorService.class);
        features.add(pf);
        pf = new PlanFeature(RecreationalSpace.class);
        features.add(pf);
        pf = new PlanFeature(TravelDistanceToExit.class);
        features.add(pf);
        pf = new PlanFeature(AdditionalFeature.class);
        features.add(pf);
        pf = new PlanFeature(OverHangs.class);
        features.add(pf);
        pf = new PlanFeature(FireStairService.class);
        features.add(pf);
        pf = new PlanFeature(GeneralStairService.class);
        features.add(pf);
        pf = new PlanFeature(SpiralStairService.class);
        features.add(pf);
        pf = new PlanFeature(RampService.class);
        features.add(pf);
        pf = new PlanFeature(LiftService.class);
        features.add(pf);
        pf = new PlanFeature(HeightOfRoom.class);
        features.add(pf);
        pf = new PlanFeature(AccessoryBuildingService.class);
        features.add(pf);
        pf = new PlanFeature(DepthCutting.class);
        features.add(pf);
        pf = new PlanFeature(PetrolFillingStation.class);
        features.add(pf);
        pf = new PlanFeature(CommonFeature.class);
        features.add(pf);
        pf = new PlanFeature(DxfToPdfConverter.class);
        features.add(pf);
        pf = new PlanFeature(OpenStairs.class);
        features.add(pf);*/
        return features;
    }

}
