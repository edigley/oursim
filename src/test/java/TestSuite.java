import java.io.IOException;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.edigley.oursim.OurSimAPIVolatilityTest;
import com.edigley.oursim.entities.ProcessorTest;
import com.edigley.oursim.entities.TaskTest;
import com.edigley.oursim.policy.NoFSharingPolicyTest;
import com.edigley.oursim.policy.OurGridPersistentSchedulerMultiplePeersTest;
import com.edigley.oursim.policy.OurGridPersistentSchedulerTest;
import com.edigley.oursim.policy.OurGridReplicationSchedulerTest;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

@RunWith(Suite.class)
@SuiteClasses({ 
	ProcessorTest.class, 
	TaskTest.class, 
	NoFSharingPolicyTest.class, 
	OurGridPersistentSchedulerTest.class, 
	OurSimAPIVolatilityTest.class,
	OurGridReplicationSchedulerTest.class, 
	OurGridPersistentSchedulerMultiplePeersTest.class 
})
public class TestSuite {

	static class Compatibility {

		static Test suite() throws IOException {
			return new JUnit4TestAdapter(TestSuite.class);
		}
	}

}
