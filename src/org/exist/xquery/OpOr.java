
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
package org.exist.xquery;

import org.exist.dom.NodeSet;
import org.exist.xquery.util.ExpressionDumper;
import org.exist.xquery.value.BooleanValue;
import org.exist.xquery.value.Item;
import org.exist.xquery.value.Sequence;
import org.exist.xquery.value.Type;

/**
 * Boolean operator "or".
 * 
 * @author Wolfgang <wolfgang@exist-db.org>
 */
public class OpOr extends LogicalOp {

	public OpOr(XQueryContext context) {
		super(context);
	}

	public Sequence eval(Sequence contextSequence, Item contextItem) throws XPathException {
		if (getLength() == 0)
			return Sequence.EMPTY_SEQUENCE;
		if(contextItem != null)
			contextSequence = contextItem.toSequence();
		Expression left = getLeft();
		Expression right = getRight();
		if(Type.subTypeOf(left.returnsType(), Type.NODE) &&
			Type.subTypeOf(right.returnsType(), Type.NODE)) {
			NodeSet rl = left.eval(contextSequence, null).toNodeSet();
			rl = rl.getContextNodes(inPredicate);
			NodeSet rr = right.eval(contextSequence, null).toNodeSet();
			rr = rr.getContextNodes(inPredicate);
			rl = rl.union(rr);
			return rl;
		} else {
			boolean ls = left.eval(contextSequence).effectiveBooleanValue();
			if(ls)
				return BooleanValue.TRUE;
			boolean rs = right.eval(contextSequence).effectiveBooleanValue();
			return ls || rs ? BooleanValue.TRUE : BooleanValue.FALSE;
		}
	}
	
	/* (non-Javadoc)
     * @see org.exist.xquery.PathExpr#dump(org.exist.xquery.util.ExpressionDumper)
     */
    public void dump(ExpressionDumper dumper) {
        if (getLength() == 0)
            return;
        getExpression(0).dump(dumper);
        for (int i = 1; i < getLength(); i++) {
            dumper.display(" or ");
            getExpression(i).dump(dumper);
        }
    }
}
