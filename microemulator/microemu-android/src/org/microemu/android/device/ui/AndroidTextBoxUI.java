/**
 *  MicroEmulator
 *  Copyright (C) 2008 Bartek Teodorczyk <barteo@barteo.net>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  @version $Id$
 */

package org.microemu.android.device.ui;

import org.microemu.device.ui.TextBoxUI;

import android.app.Activity;
import android.widget.TextView;

public class AndroidTextBoxUI extends AndroidDisplayableUI implements TextBoxUI {
	
	private Activity activity;
	
	private TextView view;
	
	public AndroidTextBoxUI(Activity activity) {
		this.activity = activity;
		
		this.view = new TextView(activity);
		this.view.setText("AndroidTextBoxUI");
	}

	public void hideNotify() {
	}

	public void showNotify() {
		activity.setContentView(view);
	}

}
