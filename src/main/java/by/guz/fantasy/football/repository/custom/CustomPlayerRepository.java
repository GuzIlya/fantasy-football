package by.guz.fantasy.football.repository.custom;

import by.guz.fantasy.football.entity.PlayerEntity;
import by.guz.fantasy.football.entity.PlayerEntity_;
import by.guz.fantasy.football.entity.enums.PlayerPositionEntity;
import by.guz.fantasy.football.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class CustomPlayerRepository {

    private final PlayerRepository playerRepository;

    public List<PlayerEntity> getPlayers() {
        return playerRepository.findAll(where(ignoreDeleted()));
    }

    public Optional<PlayerEntity> getOneByExternalId(Long externalId) {
        return playerRepository.findOne(where(findByExternalId(externalId)).and(ignoreDeleted()));
    }


    public Page<PlayerEntity> getQueryResult(List<Filter> filters, Pageable pageable) {
        if (filters.size() > 0) {
            return playerRepository.findAll(getSpecificationFromFilters(filters), pageable);
        } else {
            return playerRepository.findAll(pageable);
        }
    }

    private Specification<PlayerEntity> getSpecificationFromFilters(List<Filter> filter) {
        Specification<PlayerEntity> specification = where(createSpecification(filter.remove(0)));
        for (Filter input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    private Specification<PlayerEntity> createSpecification(Filter input) {
        switch (input.getOperator()) {
            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(root.get(input.getField()),
                                (Double) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN_AGE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function("YEAR", Integer.class, criteriaBuilder.function("AGE", String.class, root.get(input.getField()))),
                                Integer.parseInt(input.getValue()));
            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(root.get(input.getField()),
                                (Double) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LESS_THAN_AGE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function("YEAR", Integer.class, criteriaBuilder.function("AGE", String.class, root.get(input.getField()))),
                                Integer.parseInt(input.getValue()));
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(criteriaBuilder.lower(root.get(input.getField())),
                                criteriaBuilder.lower(criteriaBuilder.literal("%" + input.getValue() + "%")));
            case TRIPLE_OR_LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get(input.getFields().get(0))),
                                criteriaBuilder.lower(criteriaBuilder.literal("%" + input.getValue() + "%"))),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(input.getFields().get(1))),
                                        criteriaBuilder.lower(criteriaBuilder.literal("%" + input.getValue() + "%"))),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get(input.getFields().get(2))),
                                        criteriaBuilder.lower(criteriaBuilder.literal("%" + input.getValue() + "%"))));
            case IS_NULL:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.isNull(root.get(input.getField()));
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    @SneakyThrows
    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType == Double.class) {
            return Double.valueOf(value);
        } else if (fieldType == Integer.class) {
            return Integer.valueOf(value);
        } else if (fieldType == Long.class) {
            return Long.valueOf(value);
        } else if (fieldType == Date.class) {
            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
        } else if (fieldType == PlayerPositionEntity.class)
            return PlayerPositionEntity.fromValue(value);
        return value;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }

    private Specification<PlayerEntity> ignoreDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get(PlayerEntity_.DELETED_AT));
    }

    private Specification<PlayerEntity> findByExternalId(Long externalId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(PlayerEntity_.EXTERNAL_ID), externalId);
    }
}
