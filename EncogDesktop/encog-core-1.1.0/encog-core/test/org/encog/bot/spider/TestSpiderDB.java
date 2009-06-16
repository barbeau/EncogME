package org.encog.bot.spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;

import junit.framework.TestCase;

import org.encog.DerbyUtil;
import org.encog.bot.spider.workload.sql.SQLWorkloadManager;

public class TestSpiderDB extends TestCase implements SpiderReportable {

	private String base = "www.httprecipes.com";
	private int urlsProcessed;

	public void testSpider() throws Exception {
		DerbyUtil.loadDriver();
		DerbyUtil.cleanup();

		Connection conn = null;
		Statement s = null;

		conn = DerbyUtil.getConnection();
		conn.setAutoCommit(true);

		s = conn.createStatement();

		// We create a table...
		StringBuilder sql = new StringBuilder();
		sql.append("CREATE TABLE \"SPIDER_HOST\" (");
		sql.append(" \"HOST_ID\" int generated always as identity,");
		sql.append(" \"HOST\" varchar(255),");
		sql.append(" \"STATUS\" varchar(1),");
		sql.append(" \"URLS_DONE\" int,");
		sql.append(" \"URLS_ERROR\" int");
		sql.append(" )");
		s.execute(sql.toString());

		sql = new StringBuilder();
		sql.append("CREATE TABLE \"SPIDER_WORKLOAD\" (");
		sql
				.append(" \"WORKLOAD_ID\" int  generated always as identity NOT NULL ,");
		sql.append(" \"HOST\" int NOT NULL,");
		sql.append(" \"URL\" varchar(2083) NOT NULL,");
		sql.append(" \"STATUS\" varchar(1) NOT NULL,");
		sql.append(" \"DEPTH\" int NOT NULL,");
		sql.append(" \"URL_HASH\" int NOT NULL,");
		sql.append(" \"SOURCE_ID\" int NOT NULL)");
		s.execute(sql.toString());

		SpiderOptions options = new SpiderOptions();
		options.setCorePoolSize( 10 );
		options.setDbClass( DerbyUtil.DRIVER );
		options.setDbPWD( "user1" );
		options.setDbUID( "user1" );
		options.setDbURL( DerbyUtil.PROTOCOL + DerbyUtil.DB_LOCATION );
		options.setStartup( SpiderOptions.STARTUP_CLEAR );
		options.setWorkloadManager( SQLWorkloadManager.class.getCanonicalName() );

		Spider spider = new Spider(options, this);

		spider.addURL(new URL("http://www.httprecipes.com"), null, 1);
		spider.process();
		TestCase.assertTrue(this.urlsProcessed > 100);

		DerbyUtil.shutdown();
		DerbyUtil.cleanup();
	}

	public boolean beginHost(String host) {
		return host.equalsIgnoreCase("www.httprecipes.com");
	}

	public void init(Spider spider) {
		// TODO Auto-generated method stub

	}

	public boolean spiderFoundURL(URL url, URL source, URLType type) {
		if (type != URLType.HYPERLINK) {
			return true;
		} else if ((this.base != null)
				&& (!this.base.equalsIgnoreCase(url.getHost()))) {
			return false;
		}

		return true;
	}

	public void spiderProcessURL(URL url, InputStream stream)
			throws IOException {
		// TODO Auto-generated method stub

	}

	public void spiderProcessURL(URL url, SpiderParseHTML parse)
			throws IOException {
		try {
			parse.readAll();
		} catch (IOException e) {
		}
		this.urlsProcessed++;

	}

	public void spiderURLError(URL url) {
		// TODO Auto-generated method stub
	}
}
