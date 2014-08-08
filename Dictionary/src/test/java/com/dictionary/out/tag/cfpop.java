
package com.dictionary.out.tag;

import au.id.jericho.lib.html.EndTagType;
import au.id.jericho.lib.html.ParseText;
import au.id.jericho.lib.html.Source;
import au.id.jericho.lib.html.StartTag;
import au.id.jericho.lib.html.StartTagTypeGenericImplementation;
import au.id.jericho.lib.html.Tag;

final class cfpop extends StartTagTypeGenericImplementation {
	protected static final cfpop INSTANCE = new cfpop();

	private cfpop() {
		super("CFML if tag", "<cfpop", ">", EndTagType.NORMAL, true, true, false);
	}

}
					