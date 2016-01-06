package com.data;

import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

public class BookingService {

    private final static Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void book(String... persons) {
        Arrays.asList(persons).forEach(person -> {
            log.info("Booking " + person + " in a seat...");
            jdbcTemplate.update(
                    "insert into BOOKINGS(FIRST_NAME) values (?)",
                    person
            );
        });
    }

    public List<String> findAllBookings() {
        RowMapper<String> rowMapper =
                (resultSet, rowNum) -> resultSet.getString("FIRST_NAME");

        return jdbcTemplate.query(
                "select FIRST_NAME from BOOKINGS",
                rowMapper
        );
    }
}
