package org.flymine.dataconversion;

/*
 * Copyright (C) 2002-2003 FlyMine
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  See the LICENSE file for more
 * information or http://www.gnu.org/copyleft/lesser.html.
 *
 */

 /**
 * Abstract parent class of all DataConverters
 * @author Mark Woodbridge
 */
 public abstract class DataConverter
{
    protected ItemWriter writer;

    /**
    * Constructor that should be called by children
    * @param writer an ItemWriter used to handle the resultant Items
    */
    public DataConverter(ItemWriter writer) {
        this.writer = writer;
    }

    /**
    * Perform the data conversion
    * @throws Exception if an error occurs during processing
    */
    public abstract void process() throws Exception;
}
