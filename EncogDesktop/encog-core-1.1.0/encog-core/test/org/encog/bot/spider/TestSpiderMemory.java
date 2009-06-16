package org.encog.bot.spider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;

import org.encog.bot.spider.workload.memory.MemoryWorkloadManager;

public class TestSpiderMemory extends TestCase implements SpiderReportable {

    private String base = "www.httprecipes.com";
    
    private int urlsProcessed;
	
	
	public void testSpider() throws Exception
	{            
            SpiderOptions options = new SpiderOptions();
            options.setCorePoolSize( 10 );
            options.setStartup( SpiderOptions.STARTUP_CLEAR );
            options.setWorkloadManager( MemoryWorkloadManager.class.getCanonicalName() );
            options.getFilter().add("org.encog.bot.spider.filter.RobotsFilter");
            Spider spider = new Spider(options, this);
           
            spider.addURL(new URL("http://www.httprecipes.com"), null, 1);
            spider.process();
            TestCase.assertTrue(this.urlsProcessed>100);
            


	}


	public boolean beginHost(String host) {
		return host.equalsIgnoreCase("www.httprecipes.com");
	}


	public void init(Spider spider) {
		// TODO Auto-generated method stub
		
	}


	public boolean spiderFoundURL(URL url, URL source, URLType type) {
		if( type != URLType.HYPERLINK )
		{
			return true;
		}
		else if ((this.base != null) && (!this.base.equalsIgnoreCase(url.getHost()))) {
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
