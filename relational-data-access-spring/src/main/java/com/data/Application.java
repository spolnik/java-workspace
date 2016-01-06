package com.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating tables");

        prepareDb();
        insertData();
        queryForJosh();
    }

    private void prepareDb() {
        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
            "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))"
        );
    }

    private void insertData() {
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        splitUpNames.forEach(
                name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1]))
        );

        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
    }

    private void queryForJosh() {
        log.info("Querying for customer records where first_name = 'Josh':");

        RowMapper<Customer> customerRowMapper = (rowSet, rowNum) ->
                new Customer(rowSet.getLong("id"), rowSet.getString("first_name"), rowSet.getString("last_name"));

        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",
                new Object[] {"Josh"},
                customerRowMapper
        ).forEach(customer -> log.info(customer.toString()));
    }
}
