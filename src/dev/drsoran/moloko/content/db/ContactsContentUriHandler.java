/*
 * Copyright (c) 2010 Ronny R�hricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.content.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import dev.drsoran.moloko.content.AbstractContentUriHandler;
import dev.drsoran.moloko.content.Columns;
import dev.drsoran.moloko.content.Columns.ContactColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmParticipantColumns;


class ContactsContentUriHandler extends AbstractContentUriHandler
{
   private final SQLiteDatabase database;
   
   
   
   public ContactsContentUriHandler( SQLiteDatabase database )
   {
      this.database = database;
   }
   
   
   
   @Override
   protected Cursor queryElement( Uri contentUri,
                                  long id,
                                  String[] projection,
                                  String selection,
                                  String[] selectionArgs )
   {
      selection = getElementSelection( contentUri, id, selection );
      return queryAll( contentUri, projection, selection, selectionArgs, null );
   }
   
   
   
   @Override
   protected Cursor queryAll( Uri contentUri,
                              String[] projection,
                              String selection,
                              String[] selectionArgs,
                              String sortOrder )
   {
      
      Cursor contactsCursor = null;
      try
      {
         contactsCursor = database.query( RtmContactsTable.TABLE_NAME,
                                          projection,
                                          selection,
                                          selectionArgs,
                                          null,
                                          null,
                                          sortOrder );
         
         final List< Object[] > contactsCursorList = new ArrayList< Object[] >( contactsCursor.getCount() );
         while ( contactsCursor.moveToNext() )
         {
            long contactId = contactsCursor.getLong( Columns.ID_IDX );
            int numTasksContactParticiapting = getNumTasksContactIsParticipating( contactId );
            
            final Object[] contactColumns = createContactColumns( contactId,
                                                                  contactsCursor,
                                                                  numTasksContactParticiapting );
            contactsCursorList.add( contactColumns );
         }
         
         return new ListCursor( contactsCursorList, ContactColumns.PROJECTION );
      }
      finally
      {
         if ( contactsCursor != null )
         {
            contactsCursor.close();
         }
      }
   }
   
   
   
   private int getNumTasksContactIsParticipating( long contactId )
   {
      Cursor c = null;
      try
      {
         c = database.query( RtmParticipantsTable.TABLE_NAME, new String[]
         { RtmParticipantColumns.CONTACT_ID }, RtmParticipantColumns.CONTACT_ID
            + "=?", new String[]
         { Long.toString( contactId ) }, null, null, null );
         
         return c.getCount();
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private Object[] createContactColumns( long contactId,
                                          Cursor c,
                                          int numTasksContactParticiapting )
   {
      final Object[] columnValues = new Object[ ContactColumns.PROJECTION.length ];
      
      columnValues[ Columns.ID_IDX ] = contactId;
      columnValues[ ContactColumns.FULLNAME_IDX ] = c.getString( ContactColumns.FULLNAME_IDX );
      columnValues[ ContactColumns.USERNAME_IDX ] = c.getString( ContactColumns.USERNAME_IDX );
      columnValues[ ContactColumns.NUM_TASKS_PARTICIPATING_IDX ] = numTasksContactParticiapting;
      
      return columnValues;
   }
   
   
   
   @Override
   protected long insertElement( Uri contentUri, ContentValues initialValues )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int updateElement( Uri contentUri, long id, ContentValues values )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int deleteElement( Uri contentUri,
                                long id,
                                String where,
                                String[] whereArgs )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   @Override
   protected int deleteAll( Uri contentUri, String where, String[] whereArgs )
   {
      throw new UnsupportedOperationException();
   }
   
   
   
   private String getElementSelection( Uri contentUri,
                                       long id,
                                       String appendedSelection )
   {
      return new ContentProviderSelectionBuilder( contentUri,
                                                  RtmContactsTable.TABLE_NAME ).selectElement( id )
                                                                               .andSelect( appendedSelection )
                                                                               .build();
   }
}
