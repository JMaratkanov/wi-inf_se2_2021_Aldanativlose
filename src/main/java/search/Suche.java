package search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import dtos.impl.StellenanzeigeDTOimpl;

public interface Suche {
    Grid filter(ListDataProvider<StellenanzeigeDTOimpl> dataProvider, Grid grid);
}
