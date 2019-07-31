import java.io.IOException;

import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class SystemConfigClient {

    public static final String HOST = "10.102.10.131";
    public static final String PORT = "6789";

    public static void main(String[] args) throws IOException, MalformedObjectNameException {
    	
    	try {
	        JMXServiceURL url =
	            new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + HOST + ":" + PORT + "/jmxrmi");
	        
	        /*Map<String, String[]> env = new HashMap<String, String[]>();
	        String[] credentials = {"appsone", "ea23aCyl61"};
	        env.put(JMXConnector.CREDENTIALS, credentials);*/
	        
	        JMXConnector jmxConnector = JMXConnectorFactory.connect(url);
	        MBeanServerConnection mbeanServerConnection = jmxConnector.getMBeanServerConnection();
	        //ObjectName should be same as your MBean name
	        
	
	        System.out.println(mbeanServerConnection.getMBeanCount());
	        CompositeData resultData;
		
			/*resultData = (CompositeData) mbeanServerConnection
					.getAttribute( new ObjectName("java.lang:type=Memory"), "HeapMemoryUsage");
			System.out.println( "Got mbean: heapUsed: " + resultData.get( "used")) ;		
					*/
			
			resultData = (CompositeData) mbeanServerConnection
					.getAttribute( new ObjectName("java.lang:name=Code Cache,type=MemoryPool"), "Usage");
			
			System.out.println( "Got mbean: code cache used : " + resultData.get( "used")) ;
			
			Integer result = (Integer) mbeanServerConnection
					.getAttribute( new ObjectName("jboss.system:type=ServerInfo"), "ActiveThreadGroupCount");
			
			System.out.println( "Got mbean: ActiveThreadGroupCount: " + result) ;
			
			
			ObjectName mbeanName = new ObjectName("jboss.system:type=ServerInfo");
			System.out.println( "Got mbean: ActiveThreadGroupCount: " + mbeanName.getKeyPropertyList()) ;

			Object ob = mbeanServerConnection.invoke(mbeanName, "listMemoryPools", new Object[] { true }, new String[] { boolean.class.getName() });
			System.out.println( "Got ----: " + ob) ;
			
			/*MBeanInfo beanInfo = mbeanServerConnection.getMBeanInfo(mbeanName);
			for (MBeanOperationInfo operation : beanInfo.getOperations()) {
				System.out.println( "Got operation: " + operation.getName()) ;
				if (operation.getName().equals("listMemoryPools")) {
					Object ob = mbeanServerConnection.invoke(mbeanName, operation.getName(), new Object[] { true }, new String[] { boolean.class.getName() });
					System.out.println( "Got ----: " + ob) ;
					break;
				}
			}*/
			
			/*Object obj = mbeanServerConnection.invoke(mbeanName, "listMemoryPools", null, null);
			System.out.println( "Got mbean: obj: " + obj) ;*/
        
	        //Get MBean proxy instance that will be used to make calls to registered MBean
	       /* SystemConfigMBean mbeanProxy =
	            (SystemConfigMBean) MBeanServerInvocationHandler.newProxyInstance(
	                mbeanServerConnection, mbeanName, SystemConfigMBean.class, true);*/
	
	        //let's make some calls to mbean through proxy and see the results.
	       /* System.out.println("Current SystemConfig::" + mbeanProxy.doConfig());
	        
	        mbeanProxy.setSchemaName("NewSchema");
	        mbeanProxy.setThreadCount(5);
	        
	        System.out.println("New SystemConfig::" + mbeanProxy.doConfig());
	        
	        //let's terminate the mbean by making thread count as 0
	        mbeanProxy.setThreadCount(0);*/
	
	        //close the connection
	        jmxConnector.close();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}