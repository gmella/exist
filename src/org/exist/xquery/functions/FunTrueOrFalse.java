/* eXist Open Source Native XML Database
 * Copyright (C) 2000-03,  Wolfgang M. Meier (meier@ifs.tu-darmstadt.de)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Library General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 * 
 * $Id$
 */

package org.exist.xquery.functions;

import org.exist.dom.QName;
import org.exist.xquery.BasicFunction;
import org.exist.xquery.Cardinality;
import org.exist.xquery.FunctionSignature;
import org.exist.xquery.XQueryContext;
import org.exist.xquery.value.BooleanValue;
import org.exist.xquery.value.Sequence;
import org.exist.xquery.value.SequenceType;
import org.exist.xquery.value.Type;

public class FunTrueOrFalse extends BasicFunction {

	public final static FunctionSignature fnTrue =
			new FunctionSignature(
				new QName("true", ModuleImpl.NAMESPACE_URI, ModuleImpl.PREFIX),
                "Always returns the boolean value true",
				null,
				new SequenceType(Type.BOOLEAN, Cardinality.ONE));
	
	public final static FunctionSignature fnFalse =
		new FunctionSignature(
			new QName("false", ModuleImpl.NAMESPACE_URI, ModuleImpl.PREFIX),
            "Always returns the boolean value false",
			null,
			new SequenceType(Type.BOOLEAN, Cardinality.ONE));
				
	public FunTrueOrFalse(XQueryContext context, FunctionSignature signature) {
		super(context, signature);
	}
	
	public int returnsType() {
		return Type.BOOLEAN;
	}
	
	public Sequence eval(Sequence args[], Sequence contextSequence) {
		if(isCalledAs("true"))
			return BooleanValue.TRUE;
		else
			return BooleanValue.FALSE;
	}
}
