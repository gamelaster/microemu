/**
 *  MicroEmulator
 *  Copyright (C) 2006-2007 Bartek Teodorczyk <barteo@barteo.net>
 *  Copyright (C) 2006-2007 Vlad Skarzhevskyy
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
package org.microemu.device.impl;

import java.util.HashMap;
import java.util.Map;

import javax.microedition.lcdui.Canvas;

/**
 * 
 * This class defines default device key codes and game actions for buttons.
 * 
 * Key code is reported to MIDP application by Canvas.keyPressed()
 * 
 * Game action is reported to MIDP application by Canvas.getGameAction()
 * 
 * Use 'device.xml' to redefine codes for your device if required.
 * 
 * @author vlads
 * 
 */
public abstract class ButtonDetaultDeviceKeyCodes {

	private static Map codes = new HashMap();

	private static Map gameActions = new HashMap();

	public static int getKeyCode(ButtonName name) {
		Integer code = (Integer) codes.get(name);
		if (code != null) {
			return code.intValue();
		}
		return 0;
	}

	public static int getGameAction(ButtonName name) {
		Integer code = (Integer) gameActions.get(name);
		if (code != null) {
			return code.intValue();
		}
		return 0;
	}

	static {
		code(ButtonName.SOFT1, -6);
		code(ButtonName.SOFT2, -7);
		code(ButtonName.SELECT, -5, Canvas.FIRE);
		code(ButtonName.UP, -1, Canvas.UP);
		code(ButtonName.DOWN, -2, Canvas.DOWN);
		code(ButtonName.LEFT, -3, Canvas.LEFT);
		code(ButtonName.RIGHT, -4, Canvas.RIGHT);

		code(ButtonName.KEY_NUM0, Canvas.KEY_NUM0);
		code(ButtonName.KEY_NUM1, Canvas.KEY_NUM1, Canvas.GAME_A);
		code(ButtonName.KEY_NUM2, Canvas.KEY_NUM2);
		code(ButtonName.KEY_NUM3, Canvas.KEY_NUM3, Canvas.GAME_B);
		code(ButtonName.KEY_NUM4, Canvas.KEY_NUM4);
		code(ButtonName.KEY_NUM5, Canvas.KEY_NUM5);
		code(ButtonName.KEY_NUM6, Canvas.KEY_NUM6);
		code(ButtonName.KEY_NUM7, Canvas.KEY_NUM7, Canvas.GAME_C);
		code(ButtonName.KEY_NUM8, Canvas.KEY_NUM8);
		code(ButtonName.KEY_NUM9, Canvas.KEY_NUM9, Canvas.GAME_D);
		code(ButtonName.KEY_STAR, Canvas.KEY_STAR);
		code(ButtonName.KEY_POUND, Canvas.KEY_POUND);
	}

	private static void code(ButtonName name, int code) {
		codes.put(name, Integer.valueOf(code));
	}

	private static void code(ButtonName name, int code, int gameAction) {
		code(name, code);
		gameActions.put(name, Integer.valueOf(gameAction));
	}
}