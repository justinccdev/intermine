package org.intermine.objectstore.intermine;

/*
 * Copyright (C) 2002-2016 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

import org.intermine.model.testmodel.Company;
import org.intermine.model.testmodel.Employee;
import org.intermine.objectstore.ObjectStoreFactory;
import org.intermine.objectstore.query.Query;
import org.intermine.objectstore.query.QueryClass;
import org.intermine.objectstore.query.Results;
import org.junit.BeforeClass;

public class FlatModeObjectStoreInterMineImplTest extends ObjectStoreInterMineCommonTests
{
    @BeforeClass
    public static void oneTimeSetUp() throws Exception {
        setupCommonComponents(
            "os.unittest", "testmodel/testmodel", "testmodel_data_flatmode.xml", "osw.flatmodeunittest");
        os = (ObjectStoreInterMineImpl)ObjectStoreFactory.getObjectStore("os.flatmodeunittest");
    }

    @org.junit.Test
    public void testFailFast2() throws Exception {
        Query q = new Query();
        QueryClass qc = new QueryClass(Employee.class);
        q.addFrom(qc);
        q.addToSelect(qc);

        Results r = os.execute(q);
        storeDataWriter.store((Company) data.get("CompanyA"));
        r.iterator().hasNext();
    }
}
