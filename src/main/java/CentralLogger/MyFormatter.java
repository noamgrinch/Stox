package CentralLogger;
/**@author Noam Greenshtain
 * A format for logging.
 */
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class MyFormatter extends Formatter{

	@Override
	public String format(LogRecord record) {
        return "<ThreadID>" + record.getThreadID()+"</ThreadID>\n"+ "<ClassName>" + record.getSourceClassName()+"</ClassName>\n"
                +"<MethodName>"+record.getSourceMethodName()+"</MethodName>\n" + "<Date>"
                +new Date(record.getMillis())+"</Date>\n" + "<Severity>"+ record.getLevel() + "</Severity" + "<Message>"
                +record.getMessage() + "</Message>" + "\n";
	}

}