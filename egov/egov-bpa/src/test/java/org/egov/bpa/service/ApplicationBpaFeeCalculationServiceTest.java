/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2017>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */
package org.egov.bpa.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.egov.bpa.master.entity.BpaFee;
import org.egov.bpa.master.entity.BpaFeeDetail;
import org.egov.bpa.master.entity.ServiceType;
import org.egov.bpa.master.service.BpaFeeService;
import org.egov.bpa.transaction.entity.ApplicationFee;
import org.egov.bpa.transaction.entity.ApplicationFloorDetail;
import org.egov.bpa.transaction.entity.BpaApplication;
import org.egov.bpa.transaction.entity.BuildingDetail;
import org.egov.bpa.transaction.entity.PermitFee;
import org.egov.bpa.transaction.entity.SiteDetail;
import org.egov.bpa.transaction.service.PermitFeeCalculationService;
import org.egov.bpa.transaction.service.ApplicationFeeService;
import org.egov.common.entity.bpa.Occupancy;
import org.egov.common.entity.bpa.SubOccupancy;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ApplicationBpaFeeCalculationServiceTest {

    @InjectMocks
    private PermitFeeCalculationService applicationBpaFeeCalculationService;

    @Mock
    private BpaFeeService bpaFeeService;
    @Mock
    private ApplicationFeeService applicationFeeService;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    @Ignore
    public void testCalculateAreaForAdditionalFee_WithFeeAsZero() {

        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();

        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);
        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(3000),
                BigDecimal.valueOf(1000), "F", "Mercantile / Commercial", Long.valueOf(2), BigDecimal.valueOf(65),
                BigDecimal.valueOf(2.5), BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(1500),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65),
                BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1000), "D", "Assembly", Long.valueOf(3), BigDecimal.valueOf(40),
                BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.5)));

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5000));
        siteDetailList.add(siteDetail);
        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        BigDecimal totalAreaForAdditionalFeeCalculation = applicationBpaFeeCalculationService
                .calculateAreaForAdditionalFeeCalculation(application);
        assertEquals(BigDecimal.valueOf(0), totalAreaForAdditionalFeeCalculation.setScale(0, RoundingMode.HALF_UP));

    }

    @Test
    @Ignore
    public void testCalculateAreaForAdditionalFee_Area_lessthan5000Case() {

        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();

        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);
        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4500),
                BigDecimal.valueOf(1000), "F", "Mercantile / Commercial", Long.valueOf(2), BigDecimal.valueOf(65),
                BigDecimal.valueOf(2.5), BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(5500),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65),
                BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(2000),
                BigDecimal.valueOf(1000), "D", "Assembly", Long.valueOf(3), BigDecimal.valueOf(40),
                BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.5)));

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5000));
        siteDetailList.add(siteDetail);
        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        BigDecimal totalAreaForAdditionalFeeCalculation = applicationBpaFeeCalculationService
                .calculateAreaForAdditionalFeeCalculation(application);
        assertEquals(BigDecimal.valueOf(4500), totalAreaForAdditionalFeeCalculation.setScale(0, RoundingMode.HALF_UP));

    }

    @Test
    @Ignore
    public void testCalculateAreaForAdditionalFee_Area_Greaterthan5000Case() {

        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();

        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5001));
        siteDetailList.add(siteDetail);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(6500),
                BigDecimal.valueOf(1000), "F", "Mercantile / Commercial", Long.valueOf(2), BigDecimal.valueOf(65),
                BigDecimal.valueOf(2.5), BigDecimal.valueOf(4)));

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65),
                BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(3000),
                BigDecimal.valueOf(1000), "D", "Assembly", Long.valueOf(3), BigDecimal.valueOf(40),
                BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.5)));

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        BigDecimal totalAreaForAdditionalFeeCalculation = applicationBpaFeeCalculationService
                .calculateAreaForAdditionalFeeCalculation(application);
        assertEquals(BigDecimal.valueOf(1368), totalAreaForAdditionalFeeCalculation.setScale(0, RoundingMode.HALF_UP));

    }

    @Test
    @Ignore
    public void testCalculateAreaForAdditionalFee_Area_5001Case() {

        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();

        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5001));
        siteDetailList.add(siteDetail);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(5500),
                BigDecimal.valueOf(1000), "F", "Mercantile / Commercial", Long.valueOf(2), BigDecimal.valueOf(65),
                BigDecimal.valueOf(2.5), BigDecimal.valueOf(4)));

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65),
                BigDecimal.valueOf(3), BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(3500),
                BigDecimal.valueOf(1000), "D", "Assembly", Long.valueOf(3), BigDecimal.valueOf(40),
                BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.5)));

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        BigDecimal totalAreaForAdditionalFeeCalculation = applicationBpaFeeCalculationService
                .calculateAreaForAdditionalFeeCalculation(application);
        assertEquals(BigDecimal.valueOf(1075), totalAreaForAdditionalFeeCalculation.setScale(0, RoundingMode.HALF_UP));

    }

    @Test
    @Ignore
    public void testCalculateAreaForAdditionalFee_Area_5001Case1() {

        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();

        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5001));
        siteDetailList.add(siteDetail);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(3500),
                BigDecimal.valueOf(1000), "F", "Mercantile / Commercial", Long.valueOf(2), BigDecimal.valueOf(65),
                BigDecimal.valueOf(2.5),
                BigDecimal.valueOf(4)));

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(3000),
                BigDecimal.valueOf(1000), "D", "Assembly", Long.valueOf(3), BigDecimal.valueOf(40), BigDecimal.valueOf(1.5),
                BigDecimal.valueOf(2.5)));

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        BigDecimal totalAreaForAdditionalFeeCalculation = applicationBpaFeeCalculationService
                .calculateAreaForAdditionalFeeCalculation(application);
        assertEquals(BigDecimal.valueOf(0), totalAreaForAdditionalFeeCalculation.setScale(0, RoundingMode.HALF_UP));

    }

    // @Test
    public void testCalculateAreaForAdditionalFee_Area_5001Case2() {

        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();

        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5001));
        siteDetailList.add(siteDetail);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(10500),
                BigDecimal.valueOf(1000), "F", "Mercantile / Commercial", Long.valueOf(2), BigDecimal.valueOf(65),
                BigDecimal.valueOf(2.5),
                BigDecimal.valueOf(4)));

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4)));
        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1000), "D", "Assembly", Long.valueOf(3), BigDecimal.valueOf(40), BigDecimal.valueOf(1.5),
                BigDecimal.valueOf(2.5)));

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        BigDecimal totalAreaForAdditionalFeeCalculation = applicationBpaFeeCalculationService
                .calculateAreaForAdditionalFeeCalculation(application);
        assertEquals(BigDecimal.valueOf(0), totalAreaForAdditionalFeeCalculation.setScale(0, RoundingMode.HALF_UP));

    }

    @Test
    @Ignore
    public void testCalculatePermissionFee_Residential_with5001Area() {

        PermitFee permitFee = new PermitFee();
        permitFee.setApplicationFee(new ApplicationFee());
        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();
        List<Long> serviceTypeList = new ArrayList<>(0);
        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);
        List<BpaFee> bpaFeeList = new ArrayList<>(0);

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5001));
        siteDetail.setIsappForRegularization(false);
        siteDetailList.add(siteDetail);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4)));

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        application.getPermitOccupancies()
                .add(getOccupancy("01", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                        BigDecimal.valueOf(4)));
        application.setIsEconomicallyWeakerSection(false);

        Map<String, Double> feeDetails = new HashMap<String, Double>();
        feeDetails.put("Residential", Double.valueOf(10));
        feeDetails.put("Others", Double.valueOf(15));
        feeDetails.put("Thatched / Tiled House", Double.valueOf(3));

        BpaFee bpafee1 = buildBpaFee("101", "01", "Permit Fees", true, feeDetails);

        Map<String, Double> feeDetails1 = new HashMap<String, Double>();
        feeDetails1.put("", Double.valueOf(3000));
        BpaFee bpafee2 = buildBpaFee("102", "01", "Additional Fees", true, feeDetails1);

        bpaFeeList.add(bpafee1);
        bpaFeeList.add(bpafee2);
        when(bpaFeeService.getActiveSanctionFeeForListOfServices(Long.valueOf(1))).thenReturn(
                bpaFeeList);
        serviceTypeList.add(Long.valueOf(1));
        applicationBpaFeeCalculationService.calculateFeeByServiceType(application, serviceTypeList, permitFee);
        assertEquals(2, permitFee.getApplicationFee().getApplicationFeeDetail().size());
        // assertEquals(BigDecimal.valueOf(40000), applicationFee.getApplicationFeeDetail().get(0).getAmount());
        assertEquals(BigDecimal.valueOf(0), permitFee.getApplicationFee().getApplicationFeeDetail().get(1).getAmount());

    }

    @Test
    @Ignore
    public void testCalculatePermissionFee_Residential_with5000Area() {

        PermitFee permitFee = new PermitFee();
        permitFee.setApplicationFee(new ApplicationFee());
        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();
        List<Long> serviceTypeList = new ArrayList<>(0);
        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);
        List<BpaFee> bpaFeeList = new ArrayList<>(0);

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(5000));
        siteDetail.setIsappForRegularization(false);
        siteDetailList.add(siteDetail);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000),
                BigDecimal.valueOf(1000), "01", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4)));

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        application.getPermitOccupancies()
                .add(getOccupancy("A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                        BigDecimal.valueOf(4)));
        application.setIsEconomicallyWeakerSection(false);

        Map<String, Double> feeDetails = new HashMap<String, Double>();
        feeDetails.put("Residential", Double.valueOf(10));
        feeDetails.put("Others", Double.valueOf(15));
        feeDetails.put("Thatched / Tiled House", Double.valueOf(3));

        BpaFee bpafee1 = buildBpaFee("101", "01", "Permit Fees", true, feeDetails);

        Map<String, Double> feeDetails1 = new HashMap<String, Double>();
        feeDetails1.put("", Double.valueOf(3000));
        BpaFee bpafee2 = buildBpaFee("102", "01", "Additional Fees", true, feeDetails1);

        bpaFeeList.add(bpafee1);
        bpaFeeList.add(bpafee2);
        when(bpaFeeService.getActiveSanctionFeeForListOfServices(Long.valueOf(1))).thenReturn(
                bpaFeeList);
        serviceTypeList.add(Long.valueOf(1));
        applicationBpaFeeCalculationService.calculateFeeByServiceType(application, serviceTypeList, permitFee);
        assertEquals(2, permitFee.getApplicationFee().getApplicationFeeDetail().size());
        // assertEquals(BigDecimal.valueOf(40000), applicationFee.getApplicationFeeDetail().get(0).getAmount());
        assertEquals(BigDecimal.valueOf(0), permitFee.getApplicationFee().getApplicationFeeDetail().get(1).getAmount());

    }

    @Test
    @Ignore
    public void testCalculatePermissionFee_Residential_with1000Area() {

        PermitFee permitFee = new PermitFee();
        permitFee.setApplicationFee(new ApplicationFee());
        BpaApplication application = new BpaApplication();
        application.setId(Long.valueOf(1));
        BuildingDetail buildingDetail = new BuildingDetail();
        SiteDetail siteDetail = new SiteDetail();
        List<Long> serviceTypeList = new ArrayList<>(0);
        List<SiteDetail> siteDetailList = new ArrayList<>(0);
        List<ApplicationFloorDetail> applicationFloorDetails = new ArrayList<>(0);
        List<BuildingDetail> buildingDetailList = new ArrayList<>(0);
        List<BpaFee> bpaFeeList = new ArrayList<>(0);

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
        buildingDetail.setAdditionalFeePaymentAccepted(true);
        buildingDetail.setName("1");
        buildingDetail.setNumber(1);

        siteDetail.setExtentinsqmts(BigDecimal.valueOf(1000));
        siteDetail.setIsappForRegularization(false);
        siteDetailList.add(siteDetail);

        applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(3500),
                BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4)));

        buildingDetail.setApplicationFloorDetails(applicationFloorDetails);

        buildingDetailList.add(buildingDetail);

        application.setBuildingDetail(buildingDetailList);
        application.setSiteDetail(siteDetailList);

        application.getPermitOccupancies()
                .add(getOccupancy("01", "Residential", Long.valueOf(1), BigDecimal.valueOf(65), BigDecimal.valueOf(3),
                        BigDecimal.valueOf(4)));
        application.setIsEconomicallyWeakerSection(false);

        Map<String, Double> feeDetails = new HashMap<String, Double>();
        feeDetails.put("Residential", Double.valueOf(10));
        feeDetails.put("Others", Double.valueOf(15));
        feeDetails.put("Thatched / Tiled House", Double.valueOf(3));

        BpaFee bpafee1 = buildBpaFee("101", "01", "Permit Fees", true, feeDetails);

        Map<String, Double> feeDetails1 = new HashMap<String, Double>();
        feeDetails1.put("", Double.valueOf(3000));
        BpaFee bpafee2 = buildBpaFee("102", "01", "Additional Fees", true, feeDetails1);

        bpaFeeList.add(bpafee1);
        bpaFeeList.add(bpafee2);
        when(bpaFeeService.getActiveSanctionFeeForListOfServices(Long.valueOf(1))).thenReturn(
                bpaFeeList);
        serviceTypeList.add(Long.valueOf(1));
        applicationBpaFeeCalculationService.calculateFeeByServiceType(application, serviceTypeList, permitFee);
        assertEquals(2, permitFee.getApplicationFee().getApplicationFeeDetail().size());
        // assertEquals(BigDecimal.valueOf(35000), applicationFee.getApplicationFeeDetail().get(0).getAmount());
        assertEquals(BigDecimal.valueOf(1500000), permitFee.getApplicationFee().getApplicationFeeDetail().get(1).getAmount());

    }

    /*
     * @Test public void testCalculatePermissionFee_mixedOuccupancy() { ApplicationFee applicationFee = new ApplicationFee();
     * BpaApplication application = new BpaApplication(); application.setId(Long.valueOf(1)); BuildingDetail buildingDetail = new
     * BuildingDetail(); SiteDetail siteDetail = new SiteDetail(); List<Long> serviceTypeList = new ArrayList<>(0);
     * List<SiteDetail> siteDetailList = new ArrayList<>(0); List<ApplicationFloorDetail> applicationFloorDetails = new
     * ArrayList<>(0); List<BuildingDetail> buildingDetailList = new ArrayList<>(0); List<BpaFee> bpaFeeList = new ArrayList<>(0);
     * buildingDetail.setApplicationFloorDetails(applicationFloorDetails); buildingDetail.setAdditionalFeePaymentAccepted(true);
     * buildingDetail.setName("1"); buildingDetail.setNumber(1); siteDetail.setExtentinsqmts(BigDecimal.valueOf(5001));
     * siteDetail.setIsappForRegularization(false); siteDetailList.add(siteDetail);
     * applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(5500), BigDecimal.valueOf(1000),
     * "F", "Mercantile / Commercial", Long.valueOf(2), Double.valueOf(65), Double.valueOf(2.5), Double.valueOf(4)));
     * applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(4000), BigDecimal.valueOf(1000),
     * "A4", "Residential", Long.valueOf(1), Double.valueOf(65), Double.valueOf(3), Double.valueOf(4)));
     * applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail, BigDecimal.valueOf(3500), BigDecimal.valueOf(1000),
     * "D", "Assembly", Long.valueOf(3), Double.valueOf(40), Double.valueOf(1.5), Double.valueOf(2.5)));
     * buildingDetail.setApplicationFloorDetails(applicationFloorDetails); buildingDetailList.add(buildingDetail);
     * application.setBuildingDetail(buildingDetailList); application.setSiteDetail(siteDetailList);
     * application.setOccupancy(getOccupancy("15", "Mixed", Long.valueOf(1), Double.valueOf(65), Double.valueOf(3),
     * Double.valueOf(4))); application.setIsEconomicallyWeakerSection(false); Map<String, Double> feeDetails = new
     * HashMap<String, Double>(); feeDetails.put("Residential", Double.valueOf(10)); feeDetails.put("Others", Double.valueOf(15));
     * feeDetails.put("Thatched / Tiled House", Double.valueOf(3)); BpaFee bpafee1 = buildBpaFee("101", "01", "Permit Fees", true,
     * feeDetails); Map<String, Double> feeDetails1 = new HashMap<String, Double>(); feeDetails1.put("", Double.valueOf(3000));
     * BpaFee bpafee2 = buildBpaFee("102", "01", "Additional Fees", true, feeDetails1); bpaFeeList.add(bpafee1);
     * bpaFeeList.add(bpafee2); when(bpaFeeService.getActiveSanctionFeeForListOfServices(Long.valueOf(1))).thenReturn(
     * bpaFeeList); serviceTypeList.add(Long.valueOf(1));
     * applicationBpaFeeCalculationService.calculateFeeByServiceType(application, serviceTypeList, applicationFee);
     * assertEquals(2, applicationFee.getApplicationFeeDetail().size()); assertEquals(BigDecimal.valueOf(175000),
     * applicationFee.getApplicationFeeDetail().get(0).getAmount()); assertEquals(BigDecimal.valueOf(3223620),
     * applicationFee.getApplicationFeeDetail().get(1).getAmount()); }
     * @Test public void testCalculatePermissionFee_mixedOuccupancy_Case1() { ApplicationFee applicationFee = new
     * ApplicationFee(); BpaApplication application = new BpaApplication(); application.setId(Long.valueOf(1)); BuildingDetail
     * buildingDetail = new BuildingDetail(); SiteDetail siteDetail = new SiteDetail(); List<Long> serviceTypeList = new
     * ArrayList<>(0); List<SiteDetail> siteDetailList = new ArrayList<>(0); List<ApplicationFloorDetail> applicationFloorDetails
     * = new ArrayList<>(0); List<BuildingDetail> buildingDetailList = new ArrayList<>(0); List<BpaFee> bpaFeeList = new
     * ArrayList<>(0); buildingDetail.setName("1"); buildingDetail.setNumber(1);
     * buildingDetail.setApplicationFloorDetails(applicationFloorDetails); buildingDetail.setAdditionalFeePaymentAccepted(true);
     * siteDetail.setExtentinsqmts(BigDecimal.valueOf(5001)); siteDetail.setIsappForRegularization(false);
     * siteDetailList.add(siteDetail); applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail,
     * BigDecimal.valueOf(6500), BigDecimal.valueOf(1000), "F", "Mercantile / Commercial", Long.valueOf(2), Double.valueOf(65),
     * Double.valueOf(2.5), Double.valueOf(4))); applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail,
     * BigDecimal.valueOf(4000), BigDecimal.valueOf(1000), "A4", "Residential", Long.valueOf(1), Double.valueOf(65),
     * Double.valueOf(3), Double.valueOf(4))); applicationFloorDetails.add(getApplicationFloorDetail(buildingDetail,
     * BigDecimal.valueOf(3000), BigDecimal.valueOf(1000), "D", "Assembly", Long.valueOf(3), Double.valueOf(40),
     * Double.valueOf(1.5), Double.valueOf(2.5))); buildingDetail.setApplicationFloorDetails(applicationFloorDetails);
     * buildingDetailList.add(buildingDetail); application.setBuildingDetail(buildingDetailList);
     * application.setSiteDetail(siteDetailList); application.setOccupancy(getOccupancy("15", "Mixed", Long.valueOf(1),
     * Double.valueOf(65), Double.valueOf(3), Double.valueOf(4))); application.setIsEconomicallyWeakerSection(false); Map<String,
     * Double> feeDetails = new HashMap<String, Double>(); feeDetails.put("Residential", Double.valueOf(10));
     * feeDetails.put("Others", Double.valueOf(15)); feeDetails.put("Thatched / Tiled House", Double.valueOf(3)); BpaFee bpafee1 =
     * buildBpaFee("101", "01", "Permit Fees", true, feeDetails); Map<String, Double> feeDetails1 = new HashMap<String, Double>();
     * feeDetails1.put("", Double.valueOf(3000)); BpaFee bpafee2 = buildBpaFee("102", "01", "Additional Fees", true, feeDetails1);
     * bpaFeeList.add(bpafee1); bpaFeeList.add(bpafee2);
     * when(bpaFeeService.getActiveSanctionFeeForListOfServices(Long.valueOf(1))).thenReturn( bpaFeeList);
     * serviceTypeList.add(Long.valueOf(1)); applicationBpaFeeCalculationService.calculateFeeByServiceType(application,
     * serviceTypeList, applicationFee); assertEquals(2, applicationFee.getApplicationFeeDetail().size());
     * assertEquals(BigDecimal.valueOf(182500), applicationFee.getApplicationFeeDetail().get(0).getAmount());
     * assertEquals(BigDecimal.valueOf(4103820), applicationFee.getApplicationFeeDetail().get(1).getAmount()); }
     */

    private BpaFee buildBpaFee(String feecode, String servicetypecode, String feedescription, boolean isactive,
            Map<String, Double> feeDetails) {
        BpaFee bpafee = new BpaFee();
        List<BpaFeeDetail> feeDetail = new ArrayList<BpaFeeDetail>(0);
        ServiceType serviceType = new ServiceType();

        serviceType.setCode(servicetypecode);
        bpafee.setCode(feecode);
        bpafee.setDescription(feedescription);
        bpafee.setIsActive(isactive);
        bpafee.setServiceType(serviceType);

        for (Entry<String, Double> feedtl : feeDetails.entrySet()) {
            BpaFeeDetail bpaFeeDetail = new BpaFeeDetail();
            bpaFeeDetail.setAmount(feedtl.getValue());
            bpaFeeDetail.setAdditionalType(feedtl.getKey());
            bpaFeeDetail.setBpafee(bpafee);
            feeDetail.add(bpaFeeDetail);
        }
        bpafee.setFeeDetail(feeDetail);
        return bpafee;
    }

    private ApplicationFloorDetail getApplicationFloorDetail(BuildingDetail buildingDetail, BigDecimal floorArea,
            BigDecimal plinthArea,
            String occupancycode, String occupancydesc, Long occupancyid, BigDecimal areapermissibleinpercentage,
            BigDecimal arewithaddnfee, BigDecimal areawithoutaddnfee) {
        ApplicationFloorDetail applicationFloorDetail = new ApplicationFloorDetail();
        applicationFloorDetail.setFloorArea(floorArea);
        applicationFloorDetail.setBuildingDetail(buildingDetail);
        applicationFloorDetail.setPlinthArea(plinthArea);
        applicationFloorDetail
                .setSubOccupancy(getSubOccupancy(occupancycode, occupancydesc, occupancyid, areapermissibleinpercentage,
                        arewithaddnfee, areawithoutaddnfee));
        return applicationFloorDetail;
    }

    private Occupancy getOccupancy(String code, String desc, Long id, BigDecimal areapermissibleinpercentage,
            BigDecimal areawithoutaddnfee, BigDecimal arewithaddnfee) {
        Occupancy occupancy = new Occupancy();
        occupancy.setCode(code);
        occupancy.setDescription(desc);
        occupancy.setId(id);
        occupancy.setIsactive(true);
        occupancy.setMaxFar(arewithaddnfee);
        occupancy.setMinFar(areawithoutaddnfee);
        occupancy.setMaxCoverage(areapermissibleinpercentage);
        return occupancy;
    }

    private SubOccupancy getSubOccupancy(String code, String desc, Long id, BigDecimal areapermissibleinpercentage,
            BigDecimal areawithoutaddnfee, BigDecimal arewithaddnfee) {
        SubOccupancy subOccupancy = new SubOccupancy();
        subOccupancy.setCode(code);
        subOccupancy.setDescription(desc);
        subOccupancy.setId(id);
        subOccupancy.setIsactive(true);
        subOccupancy.setMaxFar(arewithaddnfee);
        subOccupancy.setMinFar(areawithoutaddnfee);
        subOccupancy.setMaxCoverage(areapermissibleinpercentage);
        return subOccupancy;
    }

}