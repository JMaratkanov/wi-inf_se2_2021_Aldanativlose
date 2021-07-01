package Search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import dtos.impl.StellenanzeigeDTOimpl;

public class SearchControlproxy implements suche{
    @Override
    public  Grid filter(ListDataProvider<StellenanzeigeDTOimpl> dataProvider, Grid grid) {
        searchControl sc = new searchControl();
        return sc.filter(dataProvider, grid);
    }
}
