package puente.practicas.api.offer.presentation.dto;

public record Pagination(
        int currentPage,
        int pageSize,
        boolean hasNextPage
) {
}
