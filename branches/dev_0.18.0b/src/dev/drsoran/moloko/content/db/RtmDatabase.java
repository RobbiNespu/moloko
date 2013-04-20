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

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import dev.drsoran.moloko.ILog;


public class RtmDatabase
{
   private final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   private final ILog log;
   
   private final DatabaseOpenHelper dbAccess;
   
   private final AbstractTable[] tables;
   
   private final Trigger[] triggers;
   
   private final Map< Class< ? >, Object > queries;
   
   private final UriMatcher tableUriMatcher;
   
   
   
   public RtmDatabase( Context context, ILog log )
   {
      this.log = log;
      this.dbAccess = new DatabaseOpenHelper( context );
      this.tables = getTables();
      this.triggers = getTriggers();
      this.queries = getQueries();
      this.tableUriMatcher = createTableUriMatcher();
   }
   
   
   
   public SQLiteDatabase getWritable()
   {
      return dbAccess.getWritableDatabase();
   }
   
   
   
   public SQLiteDatabase getReadable()
   {
      return dbAccess.getReadableDatabase();
   }
   
   
   
   public ILog Log()
   {
      return log;
   }
   
   
   
   public < T > T getQuery( Class< T > queryType )
   {
      @SuppressWarnings( "unchecked" )
      final T query = (T) queries.get( queryType );
      
      if ( query == null )
      {
         throw new IllegalArgumentException( "No query of type '" + queryType
            + "'" );
      }
      
      return query;
   }
   
   
   
   public ITable getTable( String tableName )
   {
      for ( ITable table : tables )
      {
         if ( table.getTableName().equals( tableName ) )
         {
            return table;
         }
      }
      
      return null;
   }
   
   
   
   public ITable getTable( Uri tableUri )
   {
      final int tableIndex = tableUriMatcher.match( tableUri );
      if ( tableIndex > 0 )
      {
         return tables[ tableIndex ];
      }
      
      return null;
   }
   
   
   
   public UriMatcher getTableUriMatcher()
   {
      return tableUriMatcher;
   }
   
   
   
   private AbstractTable[] getTables()
   {
      return new AbstractTable[]
      { new RtmListsTable( this ), new CreationsTable( this ),
       new ModificationsTable( this ), new RawTasksTable( this ),
       new RtmTaskSeriesTable( this ), new RtmNotesTable( this ),
       new RtmContactsTable( this ), new ParticipantsTable( this ),
       new RtmLocationsTable( this ), new RtmSettingsTable( this ),
       new SyncTable( this ) };
   }
   
   
   
   private Trigger[] getTriggers()
   {
      return new Trigger[]
      { new DefaultListSettingConsistencyTrigger( this ),
       new DeleteRawTaskTrigger( this ), new DeleteTaskSeriesTrigger( this ),
       new DeleteContactTrigger( this ),
       new DeleteModificationsTrigger( this, RtmListsTable.TABLE_NAME ),
       new DeleteModificationsTrigger( this, RawTasksTable.TABLE_NAME ),
       new DeleteModificationsTrigger( this, RtmTaskSeriesTable.TABLE_NAME ),
       new DeleteModificationsTrigger( this, RtmNotesTable.TABLE_NAME ) };
   }
   
   
   
   private Map< Class< ? >, Object > getQueries()
   {
      final Map< Class< ? >, Object > queries = new HashMap< Class< ? >, Object >();
      
      queries.put( RtmListsQuery.class,
                   new RtmListsQuery( this,
                                      (RtmListsTable) getTable( RtmListsTable.TABLE_NAME ) ) );
      queries.put( CreationsQuery.class,
                   new CreationsQuery( this,
                                       (CreationsTable) getTable( CreationsTable.TABLE_NAME ) ) );
      queries.put( ModificationsQuery.class,
                   new ModificationsQuery( this,
                                           (ModificationsTable) getTable( ModificationsTable.TABLE_NAME ) ) );
      queries.put( RawTasksQuery.class,
                   new RawTasksQuery( this,
                                      (RawTasksTable) getTable( RawTasksTable.TABLE_NAME ) ) );
      queries.put( RtmTaskSeriesQuery.class,
                   new RtmTaskSeriesQuery( this,
                                           (RtmTaskSeriesTable) getTable( RtmTaskSeriesTable.TABLE_NAME ) ) );
      queries.put( RtmNotesQuery.class,
                   new RtmNotesQuery( this,
                                      (RtmNotesTable) getTable( RtmNotesTable.TABLE_NAME ) ) );
      queries.put( RtmSettingsQuery.class,
                   new RtmSettingsQuery( this,
                                         (RtmSettingsTable) getTable( RtmSettingsTable.TABLE_NAME ) ) );
      queries.put( RtmContactsQuery.class,
                   new RtmContactsQuery( this,
                                         (RtmContactsTable) getTable( RtmContactsTable.TABLE_NAME ) ) );
      queries.put( ParticipantsQuery.class,
                   new ParticipantsQuery( this,
                                          (ParticipantsTable) getTable( ParticipantsTable.TABLE_NAME ) ) );
      queries.put( RtmLocationsQuery.class,
                   new RtmLocationsQuery( this,
                                          (RtmLocationsTable) getTable( RtmLocationsTable.TABLE_NAME ) ) );
      queries.put( SyncQuery.class,
                   new SyncQuery( this,
                                  (SyncTable) getTable( SyncTable.TABLE_NAME ) ) );
      
      return queries;
   }
   
   
   
   private UriMatcher createTableUriMatcher()
   {
      final UriMatcher tableUriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
      
      // We register both, table and table item, to the same match code and so to the
      // same index in the tables array. So they both match to the same entry.
      int matchCode = 0;
      for ( ITable table : tables )
      {
         final Uri tableUri = table.getUri();
         tableUriMatcher.addURI( tableUri.getAuthority(),
                                 tableUri.getPath(),
                                 matchCode );
         
         final Uri tableItemUri = table.getItemUri();
         tableUriMatcher.addURI( tableItemUri.getAuthority(),
                                 tableItemUri.getPath(),
                                 matchCode );
         
         ++matchCode;
      }
      
      return tableUriMatcher;
   }
   
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper
   {
      public DatabaseOpenHelper( Context context )
      {
         super( context,
                RtmDatabase.DATABASE_NAME,
                null,
                RtmDatabase.DATABASE_VERSION );
      }
      
      
      
      @Override
      public void onCreate( SQLiteDatabase db )
      {
         createTables();
         createTriggers();
      }
      
      
      
      @Override
      public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
      {
         for ( AbstractTable table : tables )
         {
            table.upgrade( oldVersion, newVersion );
         }
      }
      
      
      
      private void createTables()
      {
         for ( AbstractTable table : tables )
         {
            table.create();
            table.createIndices();
         }
      }
      
      
      
      private void createTriggers()
      {
         for ( Trigger trigger : triggers )
         {
            trigger.create();
         }
      }
   }
}
