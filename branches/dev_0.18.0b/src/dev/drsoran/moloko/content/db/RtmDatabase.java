/* 
 *	Copyright (c) 2013 Ronny R�hricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.content.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.AbstractDatabase;
import dev.drsoran.db.AbstractTable;
import dev.drsoran.db.AbstractTrigger;


public class RtmDatabase extends AbstractDatabase
{
   public final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   
   
   public RtmDatabase( Context context )
   {
      super( context, DATABASE_NAME, DATABASE_VERSION );
   }
   
   
   
   @Override
   protected AbstractTable[] createTables( SQLiteDatabase database )
   {
      return new AbstractTable[]
      { new RtmTasksListsTable( database ), new RtmRawTasksTable( database ),
       new RtmTaskSeriesTable( database ), new RtmNotesTable( database ),
       new RtmContactsTable( database ), new RtmParticipantsTable( database ),
       new RtmLocationsTable( database ), new RtmSettingsTable( database ),
       new ModificationsTable( database ), new SyncTimesTable( database ) };
   }
   
   
   
   @Override
   protected AbstractTrigger[] createTriggers( SQLiteDatabase database )
   {
      return new AbstractTrigger[]
      {
       new DefaultListSettingConsistencyTrigger( database ),
       new DeleteRawTaskTrigger( database ),
       new DeleteTaskSeriesTrigger( database ),
       new DeleteContactTrigger( database ),
       new UpdateContactTrigger( database ),
       new UpdateTaskSeriesListIdTrigger( database ),
       new UpdateTaskSeriesRtmListIdTrigger( database ),
       new UpdateTaskSeriesLocationIdTrigger( database ),
       new UpdateTaskSeriesRtmLocationIdTrigger( database ),
       
       new DeleteModificationsTrigger( database, RtmTasksListsTable.TABLE_NAME ),
       new DeleteModificationsTrigger( database, RtmRawTasksTable.TABLE_NAME ),
       new DeleteModificationsTrigger( database, RtmTaskSeriesTable.TABLE_NAME ) };
   }
}
