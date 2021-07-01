package Search;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import dtos.impl.StellenanzeigeDTOimpl;

import java.util.List;

public interface suche {
    public Grid filter(ListDataProvider<StellenanzeigeDTOimpl> dataProvider, Grid grid);
}
