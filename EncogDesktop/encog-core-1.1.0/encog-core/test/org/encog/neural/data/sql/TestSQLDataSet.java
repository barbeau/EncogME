package org.encog.neural.data.sql;

import java.sql.Connection;
import java.sql.Statement;

import org.encog.DerbyUtil;
import org.encog.neural.networks.XOR;

import junit.framework.TestCase;

public class TestSQLDataSet extends TestCase {
	
	public void testSQLDataSet() throws Exception
	{
		DerbyUtil.loadDriver();
		DerbyUtil.cleanup();
		Connection conn = DerbyUtil.getConnection();
		
		conn.setAutoCommit(true);

		Statement s = conn.createStatement();

		// We create a table...
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE \"XOR\" (");
		sql.append(" \"ID\" int generated always as identity,");
		sql.append(" \"IN1\" int,");
		sql.append(" \"IN2\" int,");
		sql.append(" \"IDEAL1\" int");
		sql.append(" )");
		s.execute(sql.toString());
		
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(0,0,0)");
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(1,0,1)");
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(0,1,1)");
		s.execute("INSERT INTO xor(in1,in2,ideal1) VALUES(1,1,0)");
		
		SQLNeuralDataSet data = new SQLNeuralDataSet(
				"SELECT in1,in2,ideal1 FROM xor ORDER BY id",
				2,1, DerbyUtil.DRIVER, DerbyUtil.PROTOCOL+DerbyUtil.DB_LOCATION, DerbyUtil.UID,DerbyUtil.PWD);
		
		XOR.testXORDataSet(data);
				
		DerbyUtil.shutdown();
		DerbyUtil.cleanup();

	}
}
