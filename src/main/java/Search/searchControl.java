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
        TextField modelField = new TextField();
        TextField modelField2 = new TextField();
        modelField.addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(ad.getTitle(),
                        modelField.getValue())));

        modelField2.addValueChangeListener(event -> dataProvider.addFilter(
                ad -> StringUtils.containsIgnoreCase(ad.getStandort(),
                        modelField2.getValue())));
//modelfield1
        modelField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("titleColum")).setComponent(modelField);
        modelField.setSizeFull();
        modelField.setPlaceholder("Filter");
//modelfield2
        modelField2.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(grid.getColumnByKey("placeColum")).setComponent(modelField2);
        modelField2.setSizeFull();
        modelField2.setPlaceholder("Filter");

        return grid;
    }
}
