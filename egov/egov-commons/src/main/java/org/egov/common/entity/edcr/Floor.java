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

package org.egov.common.entity.edcr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Floor extends Measurement {

    private static final long serialVersionUID = 26L;

    private List<Occupancy> occupancies = new ArrayList<>();
    private List<Occupancy> convertedOccupancies = new ArrayList<>();
    private List<FloorUnit> units = new ArrayList<>();
    private List<Room> habitableRooms = new ArrayList<>();
    private List<DARoom> daRooms = new ArrayList<>();
    private List<Ramp> ramps = new ArrayList<>();
    private List<Lift> lifts = new ArrayList<>();
    private Measurement exterior;
    private List<Measurement> openSpaces = new ArrayList<>();
    // this is for differently able people
    private List<Measurement> specialWaterClosets = new ArrayList<>();
    private List<Measurement> coverageDeduct = new ArrayList<>();
    private String name;
    private Integer number;
    private List<BigDecimal> exitWidthDoor = new ArrayList<>();
    private List<BigDecimal> exitWidthStair = new ArrayList<>();
    private List<MezzanineFloor> mezzanineFloor = new ArrayList<>();
    private List<Hall> halls = new ArrayList();
    private List<Stair> fireStairs = new ArrayList<>();
    private List<Stair> generalStairs = new ArrayList<>();
    private List<Stair> spiralStairs = new ArrayList<>();

    private List<BigDecimal> floorHeights;

    public List<Stair> getFireStairs() {
        return fireStairs;
    }

    public void setFireStairs(List<Stair> fireStairs) {
        this.fireStairs = fireStairs;
    }

    public List<Stair> getGeneralStairs() {
        return generalStairs;
    }

    public void setGeneralStairs(List<Stair> generalStairs) {
        this.generalStairs = generalStairs;
    }

    public List<Stair> getSpiralStairs() {
        return spiralStairs;
    }

    public void setSpiralStairs(List<Stair> spiralStairs) {
        this.spiralStairs = spiralStairs;
    }

    private List<Measurement> washBasins = new ArrayList<>();
    private Boolean terrace = false;

    public void setExitWidthStair(List<BigDecimal> exitWidthStair) {
        this.exitWidthStair = exitWidthStair;
    }

    public List<Occupancy> getConvertedOccupancies() {
        return convertedOccupancies;
    }

    public List<MezzanineFloor> getMezzanineFloor() {
        return mezzanineFloor;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void setConvertedOccupancies(List<Occupancy> convertedOccupancies) {
        this.convertedOccupancies = convertedOccupancies;
    }

    public List<Lift> getLifts() {
        return lifts;
    }

    public void setLifts(List<Lift> lifts) {
        this.lifts = lifts;
    }

    public void addLifts(Lift lift) {
        this.lifts.add(lift);
    }

    public List<Ramp> getRamps() {
        return ramps;
    }

    public void setRamps(List<Ramp> ramps) {
        this.ramps = ramps;
    }

    public List<DARoom> getDaRooms() {
        return daRooms;
    }

    public void setMezzanineFloor(List<MezzanineFloor> mezzanineFloor) {
        this.mezzanineFloor = mezzanineFloor;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }

    public List<BigDecimal> getExitWidthStair() {
        return exitWidthStair;
    }

    public void addBuiltUpArea(Occupancy occupancy) {
        if (occupancies == null) {
            occupancies = new ArrayList<>();
            occupancies.add(occupancy);
        } else if (occupancies.contains(occupancy)) {
            occupancies.get(occupancies.indexOf(occupancy)).setBuiltUpArea(
                    (occupancies.get(occupancies.indexOf(occupancy)).getBuiltUpArea() == null ? BigDecimal.ZERO
                            : occupancies.get(occupancies.indexOf(occupancy)).getBuiltUpArea())
                                    .add(occupancy.getBuiltUpArea()));
            occupancies.get(occupancies.indexOf(occupancy)).setExistingBuiltUpArea(
                    (occupancies.get(occupancies.indexOf(occupancy)).getExistingBuiltUpArea() == null ? BigDecimal.ZERO
                            : occupancies.get(occupancies.indexOf(occupancy)).getExistingBuiltUpArea())
                                    .add(occupancy.getExistingBuiltUpArea()));

        } else
            occupancies.add(occupancy);

    }

    public void addDeductionArea(Occupancy occupancy) {
        if (occupancies == null) {
            occupancies = new ArrayList<>();
            occupancies.add(occupancy);
        } else if (occupancies.contains(occupancy)) {
            occupancies.get(occupancies.indexOf(occupancy)).setDeduction(
                    (occupancies.get(occupancies.indexOf(occupancy)).getDeduction() == null ? BigDecimal.ZERO
                            : occupancies.get(occupancies.indexOf(occupancy)).getDeduction())
                                    .add(occupancy.getDeduction()));
            occupancies.get(occupancies.indexOf(occupancy)).setExistingDeduction(
                    (occupancies.get(occupancies.indexOf(occupancy)).getExistingDeduction() == null ? BigDecimal.ZERO
                            : occupancies.get(occupancies.indexOf(occupancy)).getExistingDeduction())
                                    .add(occupancy.getExistingDeduction()));
        } else
            occupancies.add(occupancy);

    }

    public List<Occupancy> getOccupancies() {
        return occupancies;
    }

    public void setOccupancies(List<Occupancy> occupancies) {
        this.occupancies = occupancies;
    }

    public List<FloorUnit> getUnits() {
        return units;
    }

    public void setUnits(List<FloorUnit> units) {
        this.units = units;
    }

    public void setExitWidthDoor(List<BigDecimal> exitWidthDoor) {
        this.exitWidthDoor = exitWidthDoor;
    }

    public List<BigDecimal> getExitWidthDoor() {
        return exitWidthDoor;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Room> getHabitableRooms() {
        return habitableRooms;
    }

    public void setHabitableRooms(List<Room> habitableRooms) {
        this.habitableRooms = habitableRooms;
    }

    public Measurement getExterior() {
        return exterior;
    }

    public void setExterior(Measurement exterior) {
        this.exterior = exterior;
    }

    public List<Measurement> getOpenSpaces() {
        return openSpaces;
    }

    public void setOpenSpaces(List<Measurement> openSpaces) {
        this.openSpaces = openSpaces;
    }

    @Override
    public String toString() {

        return "Floor :" + number + " [habitableRooms Count" + habitableRooms.size() + "\n exterior=" + exterior
                + "\n openSpaces Count=" + openSpaces.size() + "]";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {

        return super.clone();

    }

    public List<Measurement> getCoverageDeduct() {
        return coverageDeduct;
    }

    public void setCoverageDeduct(List<Measurement> coverageDeduct) {
        this.coverageDeduct = coverageDeduct;
    }

    public List<Measurement> getSpecialWaterClosets() {
        return specialWaterClosets;
    }

    public void setSpecialWaterClosets(List<Measurement> specialWaterClosets) {
        this.specialWaterClosets = specialWaterClosets;
    }

    public void addDaRoom(DARoom daRoom) {
        this.daRooms.add(daRoom);
    }

    public void addRamps(Ramp ramp) {
        this.ramps.add(ramp);
    }

    public void setDaRooms(List<DARoom> daRooms) {
        this.daRooms = daRooms;
    }

    public void addFireStair(Stair fireStair) {
        this.fireStairs.add(fireStair);
    }

    public List<BigDecimal> getFloorHeights() {
        return floorHeights;
    }

    public void setFloorHeights(List<BigDecimal> floorHeights) {
        this.floorHeights = floorHeights;
    }

    public void addGeneralStair(Stair generalStair) {
        this.generalStairs.add(generalStair);
    }

    public List<Measurement> getWashBasins() {
        return washBasins;
    }

    public void setWashBasins(List<Measurement> washBasins) {
        this.washBasins = washBasins;
    }

    public Boolean getTerrace() {
        return terrace;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

}
