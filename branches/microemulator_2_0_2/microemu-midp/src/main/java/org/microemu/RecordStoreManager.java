/**
 *  MicroEmulator
 *  Copyright (C) 2001-2007 Bartek Teodorczyk <barteo@barteo.net>
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
 */
package org.microemu;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

import org.microemu.util.ExtendedRecordListener;
import org.microemu.util.RecordStoreImpl;

public interface RecordStoreManager {
	
	String getName();

	void deleteRecordStore(String recordStoreName) throws RecordStoreNotFoundException, RecordStoreException;

	RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreException;

	String[] listRecordStores();

	void saveChanges(RecordStoreImpl recordStoreImpl) throws RecordStoreNotOpenException, RecordStoreException;

	int getSizeAvailable(RecordStoreImpl recordStoreImpl);

	/**
	 * Initialize RMS Manager before starting MIDlet 
	 */
	void init(MicroEmulator emulator);

	/**
	 * Delete all record stores.
	 */
	void deleteStores();

	void setRecordListener(ExtendedRecordListener recordListener);
	
	void fireRecordStoreListener(int type, String recordStoreName);

}
