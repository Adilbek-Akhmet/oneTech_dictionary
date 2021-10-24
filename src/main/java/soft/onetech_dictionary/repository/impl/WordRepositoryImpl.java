package soft.onetech_dictionary.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import soft.onetech_dictionary.model.Word;
import soft.onetech_dictionary.repository.WordRepository;
import soft.onetech_dictionary.repository.WordTypeRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordRepositoryImpl implements WordRepository {

    private final JdbcTemplate jdbcTemplate;
    private final WordTypeRepository wordTypeRepository;

    @Override
    public List<Word> findAll() {
        return jdbcTemplate.query(
                "select * from word",
                (rs, rowNum) -> new Word(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("definition"),
                        wordTypeRepository.findByTypeId(rs.getLong("type_id"))
                                .orElse(null),
                        findAllSynonyms( rs.getLong("id"))
                )
        );
    }


    @Override
    public Optional<Word> findWordById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "select * from word WHERE id=?",
                    (rs, rowNum) -> new Word(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("definition"),
                            wordTypeRepository.findByTypeId(rs.getLong("type_id"))
                                    .orElse(null),
                            findAllSynonyms(rs.getLong("id"))
                    ),
                    id
            ));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Word> findWordByName(String name) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from word WHERE name=?",
                    (rs, rowNum) -> Optional.of(
                            new Word(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    rs.getString("definition"),
                                    wordTypeRepository.findByTypeId(rs.getLong("type_id"))
                                            .orElse(null),
                                    findAllSynonyms(rs.getLong("id"))
                            )
                    ),
                    name
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Long findByName(String name) {
        return jdbcTemplate.queryForObject(
                "select id from word WHERE name=?",
                Long.class,
                name
        );
    }

    @Override
    public List<Word> findWordByType(String type_name) {
        return jdbcTemplate.query(
                "select * from word WHERE type_id=(select id from word_type where name=?)",
                (rs, rowNum) -> new Word(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("definition"),
                        wordTypeRepository.findByTypeId(rs.getLong("type_id"))
                                .orElse(null),
                        findAllSynonyms(rs.getLong("id"))
                ),
                type_name
        );
    }

    @Override
    public void save(Word word) {
        jdbcTemplate.update(
                "insert  into word(name, definition, type_id) values (?, ?, ?)",
                word.getName(), word.getDefinition(), word.getType().getId()
        );
        List<Word> synonymList = word.getSynonymList();
        for (Word synonym: synonymList) {

            Long wordId = findByName(word.getName());
            Long synonymId = synonym.getId();

            jdbcTemplate.update(
                    "insert  into WORD_SYNONYM_LIST(word_id, SYNONYM_LIST_ID) " +
                            "values (?, ?)",
                    wordId,
                    synonymId
            );
            jdbcTemplate.update(
                    "insert  into WORD_SYNONYM_LIST(word_id, SYNONYM_LIST_ID) " +
                            "values (?, ?)",
                    synonymId,
                    wordId
            );
        }


    }

    private List<Word> findAllSynonyms(Long id) {
        return jdbcTemplate.query(
                "select * from word where word.id in " +
                        "(select SYNONYM_LIST_ID from WORD_SYNONYM_LIST where WORD_SYNONYM_LIST.word_id = ?)",
                (rs, rowNum) -> new Word(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("definition"),
                        wordTypeRepository.findByTypeId(rs.getLong("type_id"))
                                .orElse(null)
                ),
                id
        );
    }
}

