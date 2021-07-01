package Search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import dtos.impl.StellenanzeigeDTOimpl;
import org.apache.commons.lang3.StringUtils;

public class searchControl implements suche{
    @Override
    public Grid filter(ListDataProvider<StellenanzeigeDTOimpl> dataProvider, Grid grid) {
        HeaderRow filterRow = grid.appendHeaderRow();
        TextField titelfield  = new TextField();
        TextField startfield  = new TextField();
        TextField hoursfield  = new TextField();
        TextField placefield  = new TextField();
        TextField typefield   = new TextField();
        TextField statusfield = new TextField();



//titelfield
        titelfield.addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(ad.getTitle(),
                        titelfield.getValue())));

        titelfield.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("titleColum")).setComponent(titelfield);
        titelfield.setSizeFull();
        titelfield.setPlaceholder("Filter");
//startfield
        startfield.addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(ad.getDateVon().toString(),
                        startfield.getValue())));

        startfield.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("startColum")).setComponent(startfield);
        startfield.setSizeFull();
        startfield.setPlaceholder("Filter");
//hoursfield
        hoursfield .addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(Integer.toString(ad.getStundenProWoche()),
                        hoursfield .getValue())));

        hoursfield .setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("hoursColum")).setComponent(hoursfield );
        hoursfield .setSizeFull();
        hoursfield .setPlaceholder("Filter");
//placefield
        placefield.addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(ad.getStandort(),
                        placefield.getValue())));
        placefield.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("placeColum")).setComponent(placefield);
        placefield.setSizeFull();
        placefield.setPlaceholder("Filter");
//typefield
        typefield .addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(ad.getInseratTyp(),
                        typefield .getValue())));

        typefield .setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("typeColum")).setComponent(typefield);
        typefield.setSizeFull();
        typefield.setPlaceholder("Filter");
//statusfield
        statusfield.addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(ad.getStatus(),
                        statusfield.getValue())));

        statusfield.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("statusColum")).setComponent(statusfield);
        statusfield.setSizeFull();
        statusfield.setPlaceholder("Filter");

        return grid;
    }
}
