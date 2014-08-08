
package com.dictionary.out.tag;

import au.id.jericho.lib.html.EndTagType;
import au.id.jericho.lib.html.ParseText;
import au.id.jericho.lib.html.Source;
import au.id.jericho.lib.html.StartTag;
import au.id.jericho.lib.html.StartTagTypeGenericImplementation;
import au.id.jericho.lib.html.Tag;

final class cfajaximport extends StartTagTypeGenericImplementation {
	protected static final cfajaximport INSTANCE = new cfajaximport();

	private cfajaximport() {
		super("CFML if tag", "<cfajaximport", ">", EndTagType.NORMAL, true, true, false);
	}

}
					