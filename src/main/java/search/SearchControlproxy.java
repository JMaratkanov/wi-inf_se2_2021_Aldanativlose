package search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import dtos.impl.StellenanzeigeDTOimpl;

public class SearchControlproxy implements Suche {
    @Override
    public  Grid filter(ListDataProvider<StellenanzeigeDTOimpl> dataProvider, Grid grid) {
        SearchControl sc = new SearchControl();
        return sc.filter(dataProvider, grid);
    }
}
