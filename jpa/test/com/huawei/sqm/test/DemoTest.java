package com.huawei.sqm.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.huawei.sqm.dao.PersistenceManagerBase;
import com.huawei.sqm.model.Demo;
import com.huawei.sqm.model.DemoDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:war/WEB-INF/application-context.xml")
@TransactionConfiguration(defaultRollback = false)
public class DemoTest {

	@Autowired
	private PersistenceManagerBase persistence;
	
	@Test
	@Transactional
	public void create()
	{
		Demo demo = new Demo();
		demo.setName("Demo");
		DemoDetails demoDetails = new DemoDetails();
		demoDetails.setName("DemoDetails");
		demo.setDetails(demoDetails);
		
		persistence.persist(demo);
	}

	@Test
	public void query() {
		Demo demo = persistence.findById(Demo.class, 32768L);
		System.out.println(demo.getName());
		System.out.println(demo.getDetails().getName());
	}

}
