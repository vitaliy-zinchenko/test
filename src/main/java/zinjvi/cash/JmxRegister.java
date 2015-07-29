package zinjvi.cash;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Created by Vitaliy_Zinchenko on 09.07.2015.
 */
public class JmxRegister {

    private MBeanServer mBeanServer;

    public JmxRegister() {
        this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
    }

    public void register(String name, Object mBean) {
        try {
            ObjectName objectName = new ObjectName(name);
            mBeanServer.registerMBean(mBean, objectName);
        } catch (MalformedObjectNameException e) {
            handleRegisterError(e, name);
        } catch (NotCompliantMBeanException e) {
            handleRegisterError(e, name);
        } catch (InstanceAlreadyExistsException e) {
            handleRegisterError(e, name);
        } catch (MBeanRegistrationException e) {
            handleRegisterError(e, name);
        }
    }

    private void handleRegisterError(Exception e, String name) {
        System.out.println("Could not register mBean with name " + name);
        e.printStackTrace();
    }

}
