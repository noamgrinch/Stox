package CentralLogger;
/**@author Noam Greenshtain
 * A format for logging.
 */

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
		if(record.getSourceMethodName().equals("<init>")) {
			record.setSourceMethodName("init");
		}
		if(record.getSourceClassName().contains(".")) {
			int i = record.getSourceClassName().indexOf(".");
			String app = record.getSourceClassName().substring(0,i);
			record.setResourceBundleName(app);
			i = record.getSourceClassName().lastIndexOf(".");
			app = record.getSourceClassName().substring(i+1,record.getSourceClassName().length());
			record.setSourceClassName(app);
		}
        return "<log>\n" + "<ThreadID>" + record.getThreadID()+"</ThreadID>\n"+ "<Level>"  + record.getLevel() + "</Level>\n" + "<ClassName>" + record.getSourceClassName()+"</ClassName>\n"
                +"<MethodName>"+record.getSourceMethodName()+"</MethodName>\n" + "<Application>" + record.getResourceBundleName() + "</Application>\n" + "<Date>"
                +new java.sql.Timestamp(new java.util.Date().getTime())+"</Date>\n" + "<Severity>"+ record.getLevel() + "</Severity>\n" + "<Message>"
                +record.getMessage() + "</Message>\n" + "</log>\n" + "</logfile>\n";
	}

}