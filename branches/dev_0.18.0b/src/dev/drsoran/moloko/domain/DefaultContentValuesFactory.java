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

package dev.drsoran.moloko.domain;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.text.TextUtils;
import dev.drsoran.moloko.content.Columns.ContactColumns;
import dev.drsoran.moloko.content.Columns.LocationColumns;
import dev.drsoran.moloko.content.Columns.ModificationColumns;
import dev.drsoran.moloko.content.Columns.NoteColumns;
import dev.drsoran.moloko.content.Columns.ParticipantColumns;
import dev.drsoran.moloko.content.Columns.RtmSettingsColumns;
import dev.drsoran.moloko.content.Columns.SyncColumns;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.domain.model.Contact;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Estimation;
import dev.drsoran.moloko.domain.model.Location;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.domain.model.Note;
import dev.drsoran.moloko.domain.model.Participant;
import dev.drsoran.moloko.domain.model.Recurrence;
import dev.drsoran.moloko.domain.model.RtmSettings;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.Sync;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.domain.model.TasksList;


public class DefaultContentValuesFactory implements IContentValuesFactory
{
   
   private final Map< Class< ? >, IFactoryMethod< ? > > factoryMethodLookUp = new HashMap< Class< ? >, IFactoryMethod< ? > >();
   
   
   
   public DefaultContentValuesFactory()
   {
      factoryMethodLookUp.put( TasksList.class,
                               new TasksListContentValuesFactoryMethod() );
      factoryMethodLookUp.put( Task.class, new TaskContentValuesFactoryMethod() );
      factoryMethodLookUp.put( Note.class, new NoteContentValuesFactoryMethod() );
      factoryMethodLookUp.put( Participant.class,
                               new ParticipantContentValuesFactoryMethod() );
      factoryMethodLookUp.put( Contact.class,
                               new ContactContentValuesFactoryMethod() );
      factoryMethodLookUp.put( Location.class,
                               new LocationContentValuesFactoryMethod() );
      factoryMethodLookUp.put( Modification.class,
                               new ModificationContentValuesFactoryMethod() );
      factoryMethodLookUp.put( RtmSettings.class,
                               new RtmSettingsContentValuesFactoryMethod() );
      factoryMethodLookUp.put( Sync.class, new SyncContentValuesFactoryMethod() );
   }
   
   
   
   @Override
   public < T > ContentValues createContentValues( T modelElement ) throws IllegalArgumentException
   {
      @SuppressWarnings( "unchecked" )
      final IFactoryMethod< T > responsibleMethod = (IFactoryMethod< T >) factoryMethodLookUp.get( modelElement.getClass() );
      
      if ( responsibleMethod == null )
      {
         throw new IllegalArgumentException( "The element type '"
            + modelElement.getClass().getName() + "' is not supported." );
      }
      
      final ContentValues contentValues = responsibleMethod.create( modelElement );
      return contentValues;
   }
   
   
   private interface IFactoryMethod< T >
   {
      ContentValues create( T modelElement );
   }
   
   
   private final class TasksListContentValuesFactoryMethod implements
            IFactoryMethod< TasksList >
   {
      @Override
      public ContentValues create( TasksList tasksList )
      {
         final ContentValues values = new ContentValues();
         
         if ( tasksList.getId() != Constants.NO_ID )
         {
            values.put( TasksListColumns._ID, tasksList.getId() );
         }
         
         values.put( TasksListColumns.LIST_CREATED_DATE,
                     tasksList.getCreatedMillisUtc() );
         values.put( TasksListColumns.LIST_MODIFIED_DATE,
                     tasksList.getModifiedMillisUtc() );
         values.put( TasksListColumns.LIST_NAME, tasksList.getName() );
         values.put( TasksListColumns.LOCKED, tasksList.isLocked() ? 1 : 0 );
         values.put( TasksListColumns.ARCHIVED, tasksList.isArchived() ? 1 : 0 );
         values.put( TasksListColumns.POSITION, tasksList.getPosition() );
         
         if ( tasksList.getDeletedMillisUtc() != Constants.NO_TIME )
         {
            values.put( TasksListColumns.LIST_DELETED_DATE,
                        tasksList.getDeletedMillisUtc() );
         }
         else
         {
            values.putNull( TasksListColumns.LIST_DELETED_DATE );
         }
         
         if ( tasksList.isSmartList() )
         {
            final RtmSmartFilter filter = tasksList.getSmartFilter();
            values.put( TasksListColumns.IS_SMART_LIST, 1 );
            values.put( TasksListColumns.FILTER, filter.getFilterString() );
         }
         else
         {
            values.put( TasksListColumns.IS_SMART_LIST, 0 );
            values.putNull( TasksListColumns.FILTER );
         }
         
         return values;
      }
   }
   
   
   private final class TaskContentValuesFactoryMethod implements
            IFactoryMethod< Task >
   {
      @Override
      public ContentValues create( Task task )
      {
         final ContentValues values = new ContentValues();
         
         if ( task.getId() != Constants.NO_ID )
         {
            values.put( TaskColumns._ID, task.getId() );
         }
         
         values.put( TaskColumns.TASK_CREATED_DATE, task.getCreatedMillisUtc() );
         values.put( TaskColumns.TASK_MODIFIED_DATE,
                     task.getModifiedMillisUtc() );
         values.put( TaskColumns.TASK_NAME, task.getName() );
         values.put( TaskColumns.LIST_ID, task.getListId() );
         values.put( TaskColumns.LIST_NAME, task.getListName() );
         
         if ( !TextUtils.isEmpty( task.getSource() ) )
         {
            values.put( TaskColumns.SOURCE, task.getSource() );
         }
         else
         {
            values.putNull( TaskColumns.SOURCE );
         }
         
         if ( !TextUtils.isEmpty( task.getUrl() ) )
         {
            values.put( TaskColumns.URL, task.getUrl() );
         }
         else
         {
            values.putNull( TaskColumns.URL );
         }
         
         final Recurrence recurrence = task.getRecurrence();
         if ( recurrence != null )
         {
            values.put( TaskColumns.RECURRENCE, recurrence.getPattern() );
            values.put( TaskColumns.RECURRENCE_EVERY,
                        recurrence.isEveryRecurrence() ? 1 : 0 );
         }
         else
         {
            values.putNull( TaskColumns.RECURRENCE );
            values.putNull( TaskColumns.RECURRENCE_EVERY );
         }
         
         if ( task.isLocated() )
         {
            values.put( TaskColumns.LOCATION_ID, task.getLocationId() );
            values.put( TaskColumns.LOCATION_NAME, task.getLocationName() );
         }
         else
         {
            values.putNull( TaskColumns.LOCATION_ID );
            values.putNull( TaskColumns.LOCATION_NAME );
         }
         
         final Iterable< String > tags = task.getTags();
         final String tagsJoined = TextUtils.join( TaskColumns.TAGS_SEPARATOR,
                                                   tags );
         
         if ( !TextUtils.isEmpty( tagsJoined ) )
         {
            values.put( TaskColumns.TAGS, tagsJoined );
         }
         else
         {
            values.putNull( TaskColumns.TAGS );
         }
         
         values.put( TaskColumns.ADDED_DATE, task.getAddedMillisUtc() );
         values.put( TaskColumns.PRIORITY, task.getPriority().toString() );
         values.put( TaskColumns.POSTPONED, task.getPostponedCount() );
         
         final Due due = task.getDue();
         if ( due != null )
         {
            values.put( TaskColumns.DUE_DATE, due.getMillisUtc() );
            values.put( TaskColumns.HAS_DUE_TIME, due.hasDueTime() ? 1 : 0 );
         }
         else
         {
            values.putNull( TaskColumns.DUE_DATE );
         }
         
         if ( task.getCompletedMillisUtc() != Constants.NO_TIME )
         {
            values.put( TaskColumns.COMPLETED_DATE,
                        task.getCompletedMillisUtc() );
         }
         else
         {
            values.putNull( TaskColumns.COMPLETED_DATE );
         }
         
         if ( task.getDeletedMillisUtc() != Constants.NO_TIME )
         {
            values.put( TaskColumns.DELETED_DATE, task.getDeletedMillisUtc() );
         }
         else
         {
            values.putNull( TaskColumns.DELETED_DATE );
         }
         
         final Estimation estimation = task.getEstimation();
         if ( estimation != null )
         {
            values.put( TaskColumns.ESTIMATE, estimation.getSentence() );
            values.put( TaskColumns.ESTIMATE_MILLIS, estimation.getMillisUtc() );
         }
         else
         {
            values.putNull( TaskColumns.ESTIMATE );
         }
         
         return values;
      }
   }
   
   
   private final class NoteContentValuesFactoryMethod implements
            IFactoryMethod< Note >
   {
      @Override
      public ContentValues create( Note note )
      {
         final ContentValues values = new ContentValues();
         
         if ( note.getId() != Constants.NO_ID )
         {
            values.put( NoteColumns._ID, note.getId() );
         }
         
         values.put( NoteColumns.NOTE_CREATED_DATE, note.getCreatedMillisUtc() );
         values.put( NoteColumns.NOTE_MODIFIED_DATE,
                     note.getModifiedMillisUtc() );
         values.put( NoteColumns.NOTE_TEXT, note.getText() );
         
         if ( note.getDeletedMillisUtc() != Constants.NO_TIME )
         {
            values.put( NoteColumns.NOTE_DELETED_DATE,
                        note.getDeletedMillisUtc() );
         }
         else
         {
            values.putNull( NoteColumns.NOTE_DELETED_DATE );
         }
         
         if ( !TextUtils.isEmpty( note.getTitle() ) )
         {
            values.put( NoteColumns.NOTE_TITLE, note.getTitle() );
         }
         else
         {
            values.putNull( NoteColumns.NOTE_TITLE );
         }
         
         return values;
      }
   }
   
   
   private final class ParticipantContentValuesFactoryMethod implements
            IFactoryMethod< Participant >
   {
      @Override
      public ContentValues create( Participant participant )
      {
         final ContentValues values = new ContentValues();
         
         if ( participant.getId() != Constants.NO_ID )
         {
            values.put( ParticipantColumns._ID, participant.getId() );
         }
         
         values.put( ParticipantColumns.CONTACT_ID, participant.getContactId() );
         values.put( ParticipantColumns.FULLNAME, participant.getFullname() );
         values.put( ParticipantColumns.USERNAME, participant.getUsername() );
         
         return values;
      }
   }
   
   
   private final class ContactContentValuesFactoryMethod implements
            IFactoryMethod< Contact >
   {
      @Override
      public ContentValues create( Contact contact )
      {
         final ContentValues values = new ContentValues();
         
         if ( contact.getId() != Constants.NO_ID )
         {
            values.put( ContactColumns._ID, contact.getId() );
         }
         
         values.put( ContactColumns.FULLNAME, contact.getFullname() );
         values.put( ContactColumns.USERNAME, contact.getUsername() );
         
         return values;
      }
   }
   
   
   private final class LocationContentValuesFactoryMethod implements
            IFactoryMethod< Location >
   {
      @Override
      public ContentValues create( Location location )
      {
         final ContentValues values = new ContentValues();
         
         if ( location.getId() != Constants.NO_ID )
         {
            values.put( LocationColumns._ID, location.getId() );
         }
         
         values.put( LocationColumns.LOCATION_NAME, location.getName() );
         values.put( LocationColumns.LONGITUDE, location.getLongitude() );
         values.put( LocationColumns.LATITUDE, location.getLatitude() );
         values.put( LocationColumns.VIEWABLE, location.isViewable() ? 1 : 0 );
         values.put( LocationColumns.ZOOM, location.getZoom() );
         
         if ( !TextUtils.isEmpty( location.getAddress() ) )
         {
            values.put( LocationColumns.ADDRESS, location.getAddress() );
         }
         else
         {
            values.putNull( LocationColumns.ADDRESS );
         }
         
         return values;
      }
   }
   
   
   private final class ModificationContentValuesFactoryMethod implements
            IFactoryMethod< Modification >
   {
      @Override
      public ContentValues create( Modification modification )
      {
         final ContentValues values = new ContentValues();
         
         values.put( ModificationColumns.ENTITY_URI,
                     modification.getEntityUri().toString() );
         values.put( ModificationColumns.COL_NAME, modification.getColName() );
         values.put( ModificationColumns.TIMESTAMP, modification.getTimestamp() );
         
         if ( modification.getNewValue() != null )
         {
            values.put( ModificationColumns.NEW_VALUE,
                        modification.getNewValue() );
         }
         else
         {
            values.putNull( ModificationColumns.NEW_VALUE );
         }
         
         String syncedValue = null;
         if ( modification.isSetSyncedValue() )
         {
            syncedValue = modification.getSyncedValue();
         }
         
         if ( modification.getSyncedValue() != null )
         {
            values.put( ModificationColumns.SYNCED_VALUE, syncedValue );
         }
         else
         {
            values.putNull( ModificationColumns.SYNCED_VALUE );
         }
         
         return values;
      }
   }
   
   
   private final class RtmSettingsContentValuesFactoryMethod implements
            IFactoryMethod< RtmSettings >
   {
      @Override
      public ContentValues create( RtmSettings rtmSettings )
      {
         final ContentValues values = new ContentValues();
         
         values.put( RtmSettingsColumns.SYNC_TIMESTAMP,
                     rtmSettings.getSyncTimeStampMillis() );
         values.put( RtmSettingsColumns.TIMEZONE, rtmSettings.getTimezone() );
         values.put( RtmSettingsColumns.DATEFORMAT, rtmSettings.getDateFormat() );
         values.put( RtmSettingsColumns.TIMEFORMAT, rtmSettings.getTimeFormat() );
         
         if ( rtmSettings.getDefaultListId() != Constants.NO_ID )
         {
            values.put( RtmSettingsColumns.DEFAULTLIST_ID,
                        rtmSettings.getDefaultListId() );
         }
         else
         {
            values.putNull( RtmSettingsColumns.DEFAULTLIST_ID );
         }
         
         values.put( RtmSettingsColumns.LANGUAGE, rtmSettings.getLanguage() );
         
         return values;
      }
   }
   
   
   private final class SyncContentValuesFactoryMethod implements
            IFactoryMethod< Sync >
   {
      @Override
      public ContentValues create( Sync sync )
      {
         final ContentValues values = new ContentValues();
         
         if ( sync.getLastSyncInMillis() != Constants.NO_TIME )
         {
            values.put( SyncColumns.LAST_IN, sync.getLastSyncInMillis() );
         }
         else
         {
            values.putNull( SyncColumns.LAST_IN );
         }
         
         if ( sync.getLastSyncOutMillis() != Constants.NO_TIME )
         {
            values.put( SyncColumns.LAST_OUT, sync.getLastSyncOutMillis() );
         }
         else
         {
            values.putNull( SyncColumns.LAST_OUT );
         }
         
         return values;
      }
   }
}