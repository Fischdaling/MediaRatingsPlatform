package at.technikum.repository;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

public class SchemaInitializer {
    public static void run(DataSource dataSource, String schemaFile) throws Exception {
        String sql = Files.readString(Path.of(schemaFile));
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            for (String statement : sql.split(";")) {
                if (!statement.isBlank()) {
                    stmt.execute(statement.trim());
                }
            }
        }
    }
}
