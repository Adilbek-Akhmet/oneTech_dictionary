package soft.onetech_dictionary.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import soft.onetech_dictionary.model.WordType;
import soft.onetech_dictionary.repository.WordTypeRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordTypeRepositoryImpl implements WordTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<WordType> findByTypeId(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from word_type where id=?",
                    (rs, rowNum) -> Optional.of(
                            new WordType(
                                    rs.getLong("id"),
                                    rs.getString("name")
                            )
                    ),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<WordType> findByTypeName(String typeName) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from word_type where name=?",
                    (rs, rowNum) -> Optional.of(
                            new WordType(
                                    rs.getLong("id"),
                                    rs.getString("name")
                            )
                    ),
                    typeName
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
