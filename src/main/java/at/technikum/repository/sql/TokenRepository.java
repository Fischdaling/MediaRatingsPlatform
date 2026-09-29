package at.technikum.repository.sql;

import at.technikum.repository.interfaces.ITokenRepo;

import javax.sql.DataSource;

public class TokenRepository implements ITokenRepo {
    private final DataSource ds;

    public TokenRepository(DataSource ds) {
        this.ds = ds;
    }
}
