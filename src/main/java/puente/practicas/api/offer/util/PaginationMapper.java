package puente.practicas.api.offer.util;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import puente.practicas.api.offer.presentation.dto.Pagination;

@Component
public class PaginationMapper {

    public Pagination toPagination(Slice<?> slice) {
        return new Pagination(
                slice.getNumber(),
                slice.getSize(),
                slice.hasNext()
        );
    }
}
