package core.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by SeungUk on 2018. 1. 13..
 */
public interface PreparedStatementSetter {
    void setValues(PreparedStatement pstmt) throws SQLException;
}
