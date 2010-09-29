/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 *
 *
 * This file is part of the RememberTheMilk Java API.
 *
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;

import android.content.ContentProviderClient;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import dev.drsoran.moloko.service.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.service.sync.operation.CompositeContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.service.sync.util.SyncDiffer;


/**
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmTaskList extends RtmData implements
         IContentProviderSyncable< RtmTaskList >
{
   private static final String TAG = RtmTaskList.class.getSimpleName();
   
   
   private static final class LessIdComperator implements
            Comparator< RtmTaskList >
   {
      public int compare( RtmTaskList object1, RtmTaskList object2 )
      {
         return object1.id.compareTo( object2.id );
      }
      
   }
   
   public static final Parcelable.Creator< RtmTaskList > CREATOR = new Parcelable.Creator< RtmTaskList >()
   {
      
      public RtmTaskList createFromParcel( Parcel source )
      {
         return new RtmTaskList( source );
      }
      


      public RtmTaskList[] newArray( int size )
      {
         return new RtmTaskList[ size ];
      }
      
   };
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final String id;
   
   private final List< RtmTaskSeries > series;
   
   

   public RtmTaskList( String id )
   {
      this.id = id;
      this.series = new ArrayList< RtmTaskSeries >();
   }
   


   public RtmTaskList( Parcel source )
   {
      id = source.readString();
      series = source.createTypedArrayList( RtmTaskSeries.CREATOR );
   }
   


   public RtmTaskList( Element elt )
   {
      id = elt.getAttribute( "id" );
      final List< Element > children = children( elt, "taskseries" );
      series = new ArrayList< RtmTaskSeries >( children.size() );
      for ( Element seriesElt : children )
      {
         series.add( new RtmTaskSeries( seriesElt ) );
      }
      // There may also be 'deleted' elements in which are 'taskseries' elements
      for ( Element deletedElement : children( elt, "deleted" ) )
      {
         for ( Element seriesElt : children( deletedElement, "taskseries" ) )
         {
            series.add( new RtmTaskSeries( seriesElt, true ) );
         }
      }
      
      if ( id == null || id.length() == 0 )
      {
         throw new RuntimeException( "No id found in task list." );
      }
   }
   


   public String getId()
   {
      return id;
   }
   


   public List< RtmTaskSeries > getSeries()
   {
      return Collections.unmodifiableList( series );
   }
   


   public void add( RtmTaskSeries taskSeries )
   {
      this.series.add( taskSeries );
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeTypedList( series );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      CompositeContentProviderSyncOperation operation = new CompositeContentProviderSyncOperation( provider,
                                                                                                   IContentProviderSyncOperation.Op.INSERT );
      
      boolean ok = true;
      
      // If this list should be inserted as new list, all taskseries in this list
      // are new and they can simply be added w/o a sync.
      for ( Iterator< RtmTaskSeries > i = series.iterator(); ok && i.hasNext(); )
      {
         final RtmTaskSeries taskSeries = i.next();
         
         final IContentProviderSyncOperation taskSeriesOperation = taskSeries.computeContentProviderInsertOperation( provider,
                                                                                                                     id );
         ok = taskSeriesOperation != null;
         operation.add( taskSeriesOperation );
      }
      
      if ( !ok )
         operation = null;
      
      return operation;
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      // RtmTaskList is no entity in our DB, so deletion would mean deleting all taskseries.
      // But deleting a list means moving all taskseries to the 'Inbox' list (done on server side).
      // So nothing to do here.
      //
      // TODO: Think about behavior if sync direction is from client to server, aka
      // IServerSyncable.
      return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               RtmTaskList update,
                                                                               Object... params )
   {
      // Here booth parties have the same list. We run a sync.
      if ( id.equals( update.id ) )
      {
         // Sync taskseries
         final ContentProviderSyncableList< RtmTaskSeries > local_SyncList = new ContentProviderSyncableList< RtmTaskSeries >( provider,
                                                                                                                               series,
                                                                                                                               RtmTaskSeries.LESS_ID );
         
         final ArrayList< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( update.series,
                                                                                            local_SyncList,
                                                                                            id );
         
         if ( syncOperations != null )
         {
            if ( syncOperations.size() > 0 )
               return new CompositeContentProviderSyncOperation( provider,
                                                                 syncOperations,
                                                                 IContentProviderSyncOperation.Op.UPDATE );
            else
               return NoopContentProviderSyncOperation.INSTANCE;
         }
      }
      else
      {
         Log.e( TAG,
                "ContentProvider update failed. Different RtmTaskList IDs." );
      }
      
      return null;
   }
   
}
