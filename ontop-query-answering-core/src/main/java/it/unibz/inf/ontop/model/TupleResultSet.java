package it.unibz.inf.ontop.model;

/*
 * #%L
 * ontop-obdalib-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.inf.ontop.exception.OntopConnectionException;
import it.unibz.inf.ontop.exception.OntopResultConversionException;

import java.util.List;

public interface TupleResultSet extends OBDAResultSet {

	/*
	 * ResultSet management functions
	 */

	int getColumnCount();

	List<String> getSignature();

	int getFetchSize() throws OntopConnectionException;

	@Override
    void close() throws OntopConnectionException;

	OBDAStatement getStatement();

	boolean nextRow() throws OntopConnectionException;

	/*
	 * Main data fetching functions
	 */

	/***
	 * Returns the constant at column "column" recall that columns start at index 1.
	 * 
	 * @param column The column index of the value to be returned, start at 1
	 * @return a constant
	 */
    Constant getConstant(int column) throws OntopConnectionException, OntopResultConversionException;

	Constant getConstant(String name) throws OntopConnectionException, OntopResultConversionException;

}
